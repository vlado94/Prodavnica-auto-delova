using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace CarPartsServer.Controllers
{
    public class NewsController : Controller
    {
        // GET: News
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            if(id == 99)
            {
                using (var db = new EfContext())
                {
                    db.News.Add(new News
                    {
                        LongDescription = "long desc",
                        PubishDate = new DateTime(2016,3,4),
                        ShortDescription = "short",
                        Title = "title"
                    });
                    db.SaveChanges();
                }

            }
            News retval = null;
            using (var db = new EfContext())
            {
                retval = db.News.FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<News> retval = null;
            using (var db = new EfContext())
            {
                retval = db.News.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll1()
        {
            News retval = null;
            using (var db = new EfContext())
            {
                retval = db.News.ToList().First();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(News model)
        {
            News retval = null;
            using (var db = new EfContext())
            {
                retval = db.News.Add(model);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }
    }
}