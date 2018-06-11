using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;

namespace CarPartsServer.Controllers
{
    public class CarPartController : Controller
    {
        // GET: CarPart
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            CarPart retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarParts.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<CarPart> retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarParts.Include(x => x.CarBrand)
                    .Include(x=>x.Shop)
                    .ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Search(string carPart, string carBrand, double? maxPrice, double? minPrice)
        {
            List<CarPart> retval = null;
            using (var db = new EfContext())
            {
                var query = db.CarParts
                    .Include(x => x.CarBrand);
                if (!carBrand.Equals("") && !carBrand.Equals("Nije odabrano"))
                    query = query.Where(x => x.CarBrand.Name.Equals(carBrand));

                if (!carPart.Equals(""))
                    query = query.Where(x => x.Name.Equals(carPart));

                if (maxPrice != null && maxPrice.Value > 0)
                    query = query.Where(x => x.Price < maxPrice);

                if (minPrice != null && minPrice.Value > 0)
                    query = query.Where(x => x.Price > minPrice);

                retval = query.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(CarPart model)
        {
            model.PublishDate = DateTime.Now;

            CarPart retval = null;
            using (var db = new EfContext())
            {
                if (model.CarBrandID == 0)
                    model.CarBrandID = null;
                if (model.ShopID == 0)
                    model.ShopID = null;
                CarBrand cb = db.CarBrands.FirstOrDefault(x => x.Name.Equals(model.CarBrand.Name));
                model.CarBrand = cb;
                User user = db.Users.Include(x => x.Shop).FirstOrDefault(x => x.ID == model.UserID);
                if(user != null)
                {
                    model.Shop = user.Shop;
                }


                retval = db.CarParts.Add(model);
                db.SaveChanges();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}