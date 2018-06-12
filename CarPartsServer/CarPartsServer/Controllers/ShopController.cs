using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
using GoogleMaps.LocationServices;

namespace CarPartsServer.Controllers
{
    public class ShopController : Controller
    {
        // GET: Shop
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            Shop retval = null;
            using (var db = new EfContext())
            {
                retval = db.Shops
                    .Include(x=>x.CarBrands)
                    .FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetByUserID(int id)
        {
            Shop retval = null;
            using (var db = new EfContext())
            {
                int shopId = 0;
                User u = db.Users.FirstOrDefault(x => x.ID == id);
                if(u != null)
                {
                    if(u.ShopID != null)
                    {
                        shopId = u.ShopID.Value;
                    }
                }
                retval = db.Shops
                    .Include(x => x.CarBrands)
                    .FirstOrDefault(x => x.ID == shopId);
                
                List<CarPart> carparts = db.CarParts.Where(x => x.ShopID == shopId).ToList();
                foreach (var c in carparts)
                {
                    c.Shop = null;
                    c.ShopID = null;
                }
                if(carparts.Count() > 0)
                    retval.CarParts = carparts;
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<Shop> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Shops
                    .Include(x=>x.CarBrands)
                    .ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(Shop model)
        {
            Shop retval = null;
            using (var db = new EfContext())
            {
                var locationService = new GoogleLocationService();
                var point = locationService.GetLatLongFromAddress(model.Address);
                if (point != null)
                {
                    model.Latitude = point.Latitude;
                    model.Longitude = point.Longitude;
                    if (model.UserId != null)
                    {
                        User u = db.Users.FirstOrDefault(x => x.ID == model.UserId);
                        u.Shop = model;
                    }

                    if (model.CarBrands != null)
                    {
                        List<CarBrand> brands = model.CarBrands.ToList();
                        model.CarBrands.Clear();
                        foreach (CarBrand a in brands)
                        {
                            CarBrand fromDb = db.CarBrands.FirstOrDefault(x => x.ID == a.ID);
                            model.CarBrands.Add(fromDb);
                        }
                    }




                    retval = db.Shops.Add(model);
                    db.SaveChanges();
                }
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public ActionResult FindCarParts(int id)
        {
            List<CarPart> retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarParts
                    .Where(x => x.ShopID == id)
                    .ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Search(string name, string carBrand, string city)
        {
            List<Shop> retval = null;
            using (var db = new EfContext())
            {

                var query = db.Shops
                    .Include(x => x.CarBrands);

                if (!carBrand.Equals("") && !carBrand.Equals("Marka auta"))
                    query = db.Shops.Where(s => s.CarBrands.Any(cb => cb.Name.ToLower().Equals(carBrand)));

                if (!name.Equals(""))
                   query = query.Where(x => x.Name.ToLower().Contains(name.ToLower()));

                if (!city.Equals(""))
                    query = query.Where(x => x.Address.Contains(city));

                retval = query.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}