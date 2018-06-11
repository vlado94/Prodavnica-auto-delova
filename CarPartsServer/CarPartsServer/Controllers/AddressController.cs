using CarPartsServer.Models;
using GoogleMaps.LocationServices;
using System;
using System.Collections.Generic;
using System.Data.Entity;
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
                retval = db.Addresses.Include(x=>x.City).ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(Address model)
        {
            Address retval = null;
            using (var db = new EfContext())
            {
                /*
                var address = "75 Ninth Avenue 2nd and 4th Floors New York, NY 10011";
                var locationService = new GoogleLocationService();
                var point = locationService.GetLatLongFromAddress(address);

                var latitude = point.Latitude;

                var longitude = point.Longitude;
                */

                retval = db.Addresses.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}