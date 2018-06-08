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

        [HttpPost]
        public ActionResult Save(CarPart model)
        {
            CarPart retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarParts.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}