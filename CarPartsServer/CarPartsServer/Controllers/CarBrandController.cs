using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class CarBrandController : Controller
    {
        // GET: CarBrand
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            CarBrand retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarBrands.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<CarBrand> retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarBrands.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(CarBrand model)
        {
            CarBrand retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarBrands.Add(model);

            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}