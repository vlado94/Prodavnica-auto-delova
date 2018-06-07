using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;

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
                if (id == 99)
                {
                    if (db.Addresses.FirstOrDefault(x => x.Number.Equals(11)) == null)
                    {

                        Address ad = new Address
                        {
                            Number = 11,
                            City = db.Cities.FirstOrDefault(x => x.ID == 1)
                        };
                        db.Addresses.Add(ad);
                        db.SaveChanges();

                        Shop s1 = new Shop
                        {
                            Name = "shop1",
                        };
                        db.Shops.Add(s1);

                        Shop s2 = new Shop
                        {
                            Name = "shop2",
                        };
                        db.Shops.Add(s2);

                        db.SaveChanges();


                        db.Shops.Include(x => x.Addresses).First(x => x.ID == 1).Addresses.Add(db.Addresses.First(y => y.ID == 1));
                        db.Shops.Include(x => x.Addresses).First(x => x.ID == 2).Addresses.Add(db.Addresses.First(y => y.ID == 1));

                        db.SaveChanges();
                    }
                }


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