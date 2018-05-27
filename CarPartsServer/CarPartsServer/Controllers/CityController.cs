using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class CityController : Controller
    {
        // GET: City
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            City retval = null;
            using (var db = new EfContext())
            {
                retval = db.Cities.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<City> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Cities.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(City model)
        {
            City retval = null;
            using (var db = new EfContext())
            {
                retval = db.Cities.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

    }
}