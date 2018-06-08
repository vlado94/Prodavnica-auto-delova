using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
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
                    .Include(x=>x.Addresses.Select(y=>y.City))
                    .Include(x=>x.CarBrands)
                    .FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<Shop> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Shops
                    .Include(x=>x.Addresses)
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
                retval = db.Shops.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}