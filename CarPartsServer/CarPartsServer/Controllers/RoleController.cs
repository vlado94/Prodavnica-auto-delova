using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class RoleController : Controller
    {
        // GET: Role
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            Role retval = null;
            using (var db = new EfContext())
            {
                retval = db.Roles.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<Role> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Roles.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(Role model)
        {
            Role retval = null;
            using (var db = new EfContext())
            {
                retval = db.Roles.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}