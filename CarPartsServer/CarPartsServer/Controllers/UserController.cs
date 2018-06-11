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
                        Title = "Nove kazne za preko 2,0 promila",
                        PubishDate = new DateTime(2018, 2, 12),
                        ShortDescription = "Najnovije izmene Zakona o bezbednosti saobraćaja donose i novčanu kaznu za nasilničku vožnju, u koju spada i vožnja sa više od 2,0 promila alkohola u krvi. Dakle, do sada se za nasilničku vožnju nije izricala i novčana kazna.",
                        LongDescription = "Pogledajmo kolike će uskoro biti kazne. " +
                            "Šta je to nasilnička vožnja ? " +
                            "1)  kada dva ili više puta prođete kroz crveno na semaforu za manje od 10 minuta " +
                            "2)  kada pretičete kolonu vozila pri čemu prelazite ili se krećete po punoj liniji " +
                            "3)  kada vozite u naselju brzinom koja je za više od 90 km / h veća od dozvoljene ili van naselja brzinom koja je za više od 100 km / h veća od dozvoljene " +
                            "4)  kada vozite sa više od 2,0 promila alkohola u krvi "
                    });

                    db.News.Add(new News
                    {
                        Title = "Saobraćajnu dozvola",
                        PubishDate = new DateTime(2018, 12, 07),
                        ShortDescription = "Mađarska granična policija ne priznaje potvrdu kojom se produžava važenje saobraćajne dozvole na kojoj piše da je istekla",
                        LongDescription = "Već smo pisali o tom problemu kada je Crna Gora u pitanju, ali su nam iz tamošnje policije dali zvanično objašnjenje da nikakvih problema nema. " +
                            "Što se tiče glasina da su neki vozači imali probleme na granici sa drugim državama, " +
                            "ispostavilo se da je problem ipak realan.Javilo nam se više vozača koji nisu mogli da uđu u Mađarsku. " +

                            "Sada se i zvanično saznaje da je naša policija svesna tog problema.Nažalost, " +
                            "niko nije i zvanično upozorio vozače na taj problem. " +

                            "Takođe se nezvanično saznaje da je mađarska granična policija dobila nalog da ne priznaje te potvrde, " +
                            "te da nije reč o hiru pojedinih graničara. " +

                            "Navodno, " +
                            "naša policija radi sa mađarskom na rešavanju tog problema.Do tada,  " +
                            "ukoliko imate potvrdu o produženju saobraćajne,  " +
                            "odustanite od puta u Mađarsku, " +
                            "a videćemo da li i sa drugim zemljama postoje slični problemi."
                    });

                    db.News.Add(new News
                    {
                        Title = "Benzin od 98/100 oktana?",
                        ShortDescription = "Sipao sam benzin od 100 oktana i auto mi sad ide kao mećava!",
                        PubishDate = new DateTime(2018, 3, 4),
                        LongDescription = "A da li verujete da visokooktanski benzin stvarno tako deluje na automobil? Priča nije crno-bela, pa moramo da se potrudimo da objašnjavamo što jednostavnije kako ne bismo samo uneli dodatnu konfuziju." +
                        "Zablude u vezi oktana " +

                        "Prvo da razjasnimo čestu zabludu - viši oktanski broj nam ne govori da je neko gorivo „jače“, " +
                        "čistije ili kvalitetnije.Ne znači ni da ima veću energetsku vrednost, " +
                        "pa da zbog toga automobil može da razvije veću snagu. " +

                        "Šta je to oktanski broj ? " +

                        "Oktanski broj nam govori koliki pritisak benzin može da izdrži pre nego što se(nekontrolisano) zapali,  " +
                        "odnosno „detonira“. Drugim rečima, " +
                        "oktanski broj označava otpornost benzina na samozapaljenje kada smeša benzina i vazduha počne da se sabija u cilindru dok se klip kreće na gore.Ova smeša ne sme da se zapali „samoinicijativno“ pre nego što svećica baci varnicu."
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

                    List<string> cities = new List<string> { "Beograd", "Novi Sad" };
                    AddCity(cities.ElementAt(0), 1);
                    AddCity(cities.ElementAt(1), 1);

                    db.CarBrands.Add(new CarBrand()
                    {
                        IsDeleted = false,
                        Name = "Opel",
                    });

                    db.CarBrands.Add(new CarBrand()
                    {
                        IsDeleted = false,
                        Name = "Volkswagen",
                    });


                    db.CarBrands.Add(new CarBrand()
                    {
                        IsDeleted = false,
                        Name = "Citroen",
                    });
                    db.CarBrands.Add(new CarBrand()
                    {
                        IsDeleted = false,
                        Name = "Peugeot",
                    });
                    db.CarBrands.Add(new CarBrand()
                    {
                        IsDeleted = false,
                        Name = "Mini",
                    });
                    db.SaveChanges();

                    string[] streets = { "Bulevar oslobodjenja", "Puškinova", "Gogoljeva" };

                   
                    
                   

                    Shop s1 = new Shop
                    {
                        Name = "Auto biznis",
                        Phone = "0653268080",
                        Address = "Karadjordjeva s44",
                        CarBrands = db.CarBrands.ToList()
                    };
                    db.Shops.Add(s1);

                    Shop s2 = new Shop
                    {
                        Name = "Auto centar vdv",
                        Phone = "0653268080",
                        Address ="Jovana Jovanovica 3",
                        CarBrands = db.CarBrands.ToList()
                    };
                    db.Shops.Add(s2);

                    Shop s3 = new Shop
                    {
                        Name = "Slavija auto",
                        Phone = "0653268080",
                        Address = "Petra Petrovica 5",
                        CarBrands = db.CarBrands.ToList()
                    };
                    db.Shops.Add(s3);

                    db.SaveChanges();

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Letva volana",
                        IsDeleted = false,
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
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 0,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Aluminijumske felne 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 18",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 0,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Letva volana",
                        IsDeleted = false,
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
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Aluminijumske felne 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 18",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 0,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });













                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Letva volana",
                        IsDeleted = false,
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
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Zadnje gume 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Zadnje gume 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 22,
                        Price = 132,
                        CarBrand = db.CarBrands.First(),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Zadnje gume 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 12,
                        Price = 132,
                        CarBrand = db.CarBrands.ToList().ElementAt(1),
                        Shop = db.Shops.First(),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Aluminijumske felne 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.ToList().ElementAt(1),
                        Shop = db.Shops.ToList().ElementAt(1),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 16",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 2,
                        Price = 132,
                        CarBrand = db.CarBrands.ToList().ElementAt(1),
                        Shop = db.Shops.ToList().ElementAt(1),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 18",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 11,
                        Price = 132,
                        CarBrand = db.CarBrands.ToList().ElementAt(1),
                        Shop = db.Shops.ToList().ElementAt(1),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 18",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 3,
                        Price = 132,
                        CarBrand = db.CarBrands.ToList().ElementAt(1),
                        Shop = db.Shops.ToList().ElementAt(1),
                    });

                    db.CarParts.Add(new CarPart()
                    {
                        Name = "Celicne felne 18",
                        IsDeleted = false,
                        PublishDate = DateTime.Now,
                        VisitsNumber = 3,
                        LongDescription = "Felne su ORGINAL u ekstra stanju Sa gumam Pireli 245 / 40R18 letnje",
                        ShortDescription = "dsdasdasfa",
                        Quantity = 5,
                        Price = 132,
                        CarBrand = db.CarBrands.ToList().ElementAt(1),
                        Shop = db.Shops.ToList().ElementAt(1),
                    });



                    db.Users.Add(new User()
                    {
                        IsDeleted = false,
                        Email = "olja.miljatovic@yahoo.com",
                        Shop = db.Shops.ToList()[0],
                        Password = "111"
                    });
                    db.Users.Add(new User()
                    {
                        IsDeleted = false,
                        Email = "vladimir94@gmail.com",
                        Shop = db.Shops.ToList()[1],
                        Password = "111"
                    });

                    db.Users.Add(new User()
                    {
                        IsDeleted = false,
                        Email = "nebojsa.basaric@gmail.com",
                        Shop = db.Shops.ToList()[2],
                        Password = "111"
                    });

                    db.Users.Add(new User()
                    {
                        IsDeleted = false,
                        Email = "111@gmail.com",
                        Password = "111"
                    });


                    db.Users.Add(new User()
                    {
                        IsDeleted = false,
                        Email = "222@gmail.com",
                        Password = "111"
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
            if (model.ShopID == 0)
                model.ShopID = null;
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
                if(user!=null && !string.IsNullOrEmpty(userr.FirebaseToken))
                {
                    user.FirebaseToken = userr.FirebaseToken;
                    db.SaveChanges();
                }
                    
                return Json(user, JsonRequestBehavior.AllowGet);
            }
        }
    }
}