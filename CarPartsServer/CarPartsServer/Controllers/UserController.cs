using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
using System.Web.Security;

namespace CarPartsServer.Controllers
{
    public class UserController : Controller
    {
        // GET: User

        private void DataScript()
        {
            using (var db = new EfContext())
            {
                if (db.Countries.FirstOrDefault(x => x.Name.Equals("Srbija")) == null)
                {

                    db.News.Add(new News
                    {
                        Title = "Vijest 1",
                        PubishDate = new DateTime(2018,2,12),
                        ShortDescription = "Opis neki",
                        LongDescription = "Opis opis opis opis dugi dugi dugi"
                    });

                    db.News.Add(new News
                    {
                        Title = "Vijest 2",
                        PubishDate = new DateTime(2018, 12, 07),
                        ShortDescription = "Opis neki neki ",
                        LongDescription = "Opisfsafa opis opis faasf opis dugi dugi dugi"
                    });

                    db.SaveChanges();

                    List<string> countries = new List<string> { "Srbija", "Bosna i Hercegovina", "Crna Gora" };

                    for (int i = 0; i < countries.Count; i++)
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

                db.CarBrands.Add(new CarBrand()
                {
                    IsDeleted = false,
                    Name = "Ford",
                });
                db.CarBrands.Add(new CarBrand()
                {
                    IsDeleted = false,
                    Name = "Audi",
                });
                db.CarBrands.Add(new CarBrand()
                {
                    IsDeleted = false,
                    Name = "Mercedes",
                });
                db.SaveChanges();


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
                        Phone = "35232532",
                        Addresses = db.Addresses.ToList(),
                        CarBrands = db.CarBrands.ToList()
                    };
                    db.Shops.Add(s1);

                    Shop s2 = new Shop
                    {
                        Name = "shop2",
                        Phone = "35232532",
                        Addresses = db.Addresses.ToList(),
                        CarBrands = db.CarBrands.ToList()
                    };
                    db.Shops.Add(s2);

                    db.SaveChanges();


                    db.Shops.Include(x => x.Addresses).First(x => x.ID == 1).Addresses.Add(db.Addresses.First(y => y.ID == 1));
                    db.Shops.Include(x => x.Addresses).First(x => x.ID == 2).Addresses.Add(db.Addresses.First(y => y.ID == 1));

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Letva volana",
                        IsDeleted = false,
                        IpAddresses = 3232,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "dsafdsa s fas fas saf safasf as fas\ndsadasdasdas",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 3,
                        Price = 32,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Zadnje gume 16",
                        IsDeleted = false,
                        IpAddresses = 32323,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "dsafdsa s fas fas saf safasf as fas\ndsadasdasdas",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.Users.Add(new User()
                    {
                        IsDeleted = false,
                        Email = "Pera",
                        Shop = db.Shops.First(),
                        Password = "Pera123"
                    });
                    db.Users.Add(new User()
                    {
                        IsDeleted = false,
                        Email = "Mika",
                        Shop = db.Shops.First(),
                        Password = "Mika123"
                    });
                    db.SaveChanges();

                }
            }
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
                db.SaveChanges();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Login(User userr)
        {
            using (var db = new EfContext())
            {
                User user = db.Users.FirstOrDefault(x => x.Email == userr.Email && x.Password == userr.Password);
                return Json(user, JsonRequestBehavior.AllowGet);
            }
        }
    }
}