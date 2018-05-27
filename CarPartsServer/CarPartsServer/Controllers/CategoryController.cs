using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class CategoryController : Controller
    {
        // GET: Category
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            Category retval = null;
            using (var db = new EfContext())
            {
                retval = db.Categories.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<Category> retval = null;
            using (var db = new EfContext())
            {
                retval = db.Categories.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(Category model)
        {
            Category retval = null;
            using (var db = new EfContext())
            {
                retval = db.Categories.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}