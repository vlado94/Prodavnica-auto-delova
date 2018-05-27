using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class UserController : Controller
    {
        // GET: User
        public ActionResult Index()
        {
            return View();
        }
        
        public ActionResult Get(int id)
        {
            User retval = null;
            using(var db = new EfContext())
            {
                retval = db.Users.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<User> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Users.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(User model)
        {
            User retval = null;
            using (var db = new EfContext())
            {
                retval = db.Users.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}