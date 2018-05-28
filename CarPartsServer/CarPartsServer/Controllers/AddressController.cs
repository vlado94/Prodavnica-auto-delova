using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class AddressController : Controller
    {
        // GET: Address
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            Address retval = null;
            using (var db = new EfContext())
            {
                retval = db.Addresses.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<Address> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Addresses.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(Address model)
        {
            Address retval = null;
            using (var db = new EfContext())
            {
                retval = db.Addresses.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}