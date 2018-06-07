using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class CountryController : Controller
    {
        // GET: Country
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            Country retval = null;
            using (var db = new EfContext())
            {
                retval = db.Countries.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<Country> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Countries.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll1()
        {
            Country retval = null;
            using (var db = new EfContext())
            {
                retval = db.Countries.ToList().First();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(Country model)
        {
            Country retval = null;
            using (var db = new EfContext())
            {
                retval = db.Countries.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}