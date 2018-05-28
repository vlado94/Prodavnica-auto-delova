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

        private void DataScript()
        {
            using (var db = new EfContext())
            {
                List<string> countries = new List<string> { "Srbija", "Bosna i Hercegovina", "Crna Gora"};

                for(int i = 0;i < countries.Count;i++)
                {
                    db.Countries.Add(new Country
                    {
                        IsDeleted = false,
                        Name = countries.ElementAt(i)
                    });
                }
                db.SaveChanges();
            }
            List<string> cities = new List<string> { "Beograd", "Novi Sad", "Sabac", "Sarajevo", "Mostar", "Podgorica", "Budva", "Kotor" };
            AddCity(cities.ElementAt(0), 1);
            AddCity(cities.ElementAt(1), 1);
            AddCity(cities.ElementAt(2), 1);
            AddCity(cities.ElementAt(3), 2);
            AddCity(cities.ElementAt(4), 2);
            AddCity(cities.ElementAt(5), 3);
            AddCity(cities.ElementAt(6), 3);
            AddCity(cities.ElementAt(7), 3);
        }

        private void AddCity(string city,int countryID)
        {
            using (var db = new EfContext())
            {
                db.Cities.Add(new City
                {
                    IsDeleted = false,
                    Name = city,
                    CountryID = db.Countries.First(x => x.ID == countryID).ID
                });
                db.SaveChanges();
            }
        }
        public ActionResult Index() { 
            return View();
        }
        
        public ActionResult Get(int id)
        {
            if (id == 99)
                DataScript();
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