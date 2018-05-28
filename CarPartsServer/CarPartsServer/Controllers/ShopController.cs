using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

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
                retval = db.Shops.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<Shop> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Shops.ToList();
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