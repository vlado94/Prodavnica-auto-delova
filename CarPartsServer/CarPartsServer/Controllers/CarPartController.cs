using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
using System.Net;
using System.Web.Script.Serialization;
using System.Text;
using System.IO;

namespace CarPartsServer.Controllers
{
    public class CarPartController : Controller
    {
        // GET: CarPart
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Get(int id)
        {
            CarPart retval = null;
            using (var db = new EfContext())
            {
                db.CarParts.FirstOrDefault(x => x.ID == id).VisitsNumber++;
                db.SaveChanges();
                retval = db.CarParts
                    .Include(x=>x.Shop)
                    .Include(x=>x.CarBrand)
                    .FirstOrDefault(x => x.ID == id);
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAll()
        {
            List<CarPart> retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarParts
                    .Include(x => x.CarBrand)
                    .Include(x => x.Shop)
                    .ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAllByDate()
        {
            List<CarPart> retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarParts
                    .Include(x => x.CarBrand)
                    .Include(x => x.Shop)
                    .OrderByDescending(x=>x.PublishDate)
                    .Take(10)
                    .ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult GetAllByPopularity()
        {
            List<CarPart> retval = null;
            using (var db = new EfContext())
            {
                retval = db.CarParts
                    .Include(x => x.CarBrand)
                    .Include(x => x.Shop)
                    .OrderByDescending(x => x.VisitsNumber)
                    .Take(10)
                    .ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Search(string carPart, string carBrand, double? maxPrice, double? minPrice)
        {
            List<CarPart> retval = null;
            using (var db = new EfContext())
            {
                var query = db.CarParts
                    .Include(x => x.CarBrand);
                if (!carBrand.Equals("") && !carBrand.Equals("Nije odabrano"))
                    query = query.Where(x => x.CarBrand.Name.Equals(carBrand));

                if (!carPart.Equals(""))
                    query = query.Where(x => x.Name.ToLower().Contains(carPart.ToLower()));

                if (maxPrice != null && maxPrice.Value > 0)
                    query = query.Where(x => x.Price < maxPrice);

                if (minPrice != null && minPrice.Value > 0)
                    query = query.Where(x => x.Price > minPrice);

                retval = query.ToList();
            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public ActionResult Save(CarPart model)
        {
            
            CarPart retval = null;
            using (var db = new EfContext())
            {
                model.PublishDate = DateTime.Now;
                if (!string.IsNullOrEmpty(model.Image64))
                {
                    model.Image = Convert.FromBase64String(model.Image64);
                }

                if (model.CarBrandID == 0)
                    model.CarBrandID = null;
                if (model.ShopID == 0)
                    model.ShopID = null;
                if (model.CarBrand == null)
                    model.CarBrand = db.CarBrands.FirstOrDefault();
                CarBrand cb = db.CarBrands.FirstOrDefault(x => x.Name.Equals(model.CarBrand.Name));
                model.CarBrand = cb;
                User user = db.Users.Include(x => x.Shop).FirstOrDefault(x => x.ID == model.UserID);
                if(user != null)
                    model.Shop = user.Shop;
                

                if (model.ID == 0)
                {
                    model.VisitsNumber = 0;
                    model.PublishDate = DateTime.Now;
                    retval = db.CarParts.Add(model);
                }
                else
                {
                    CarPart cpedit = db.CarParts
                        .FirstOrDefault(x => x.ID == model.ID);
                    cpedit.LongDescription = model.LongDescription;
                    cpedit.Name = model.Name;
                    cpedit.Price = model.Price;
                    cpedit.ShortDescription = model.ShortDescription;
                    cpedit.Image = model.Image;
                    if (cpedit.Quantity == 0 && model.Quantity != 0)
                    {
                        SendNotification(cpedit.ID);
                    }
                    retval = cpedit;
                }
                db.SaveChanges();

            }
            return Json(retval, JsonRequestBehavior.AllowGet);
        }

        public ActionResult PrepareToNotify(int userID,int productID)
        {
            using (var db = new EfContext())
            {
                db.Notifications.Add(new Notification
                {
                    PartID = productID,
                    UserID = userID
                });
                db.SaveChanges();
            }
            return Json(null, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public void SendNotification(int carPartID)
        {
            using (var db = new EfContext())
            {
                CarPart cp = db.CarParts.FirstOrDefault(x => x.ID == carPartID);
                List<User> users = new List<User>();
                List<Notification> notification = db.Notifications.Where(x => x.PartID == carPartID).ToList();
                foreach(Notification n in notification)
                {
                    List<User> usersTmp = db.Users.Where(x => x.ID == n.UserID).ToList();
                    users.AddRange(usersTmp);
                    db.Notifications.First(x => x.ID == n.ID).IsDeleted = true;
                }
                List<string> tokens = new List<string>();
                foreach(User u in users)
                {
                    if(!string.IsNullOrEmpty(u.FirebaseToken))
                    {
                        tokens.Add(u.FirebaseToken);
                    }
                }
                NotifyPhones(tokens, "Proizvod je na stanju", "Proizvod " + cp.Name + ", na koji ste se pretplatili je sada ponovo na stanju.");

                db.SaveChanges();
            }
        }


        private static void NotifyPhones(List<string> tokens, string messageSubject, string messageText)
        {
            if (tokens == null || tokens.Count == 0)
                return;
            try
            {
                string serverId = "AIzaSyAWk2-pa84RrfMfKwKrzje6s9KxxHFYkXM";
                string senderId = "1068719150645";

                WebRequest tRequest = WebRequest.Create("https://fcm.googleapis.com/fcm/send");
                tRequest.Method = "post";
                tRequest.ContentType = "application/json";
                var data = new
                {
                    registration_ids = tokens.ToArray<string>(),
                    notification = new
                    {
                        body = messageText,
                        tag = "GEA",
                        title = messageSubject,
                        sound = "Enabled"
                    }
                };
                var serializer = new JavaScriptSerializer();
                var json = serializer.Serialize(data);
                Byte[] byteArray = Encoding.UTF8.GetBytes(json);
                tRequest.Headers.Add(string.Format("Authorization: key={0}", serverId));
                tRequest.Headers.Add(string.Format("Sender: id={0}", senderId));
                tRequest.ContentLength = byteArray.Length;
                using (Stream dataStream = tRequest.GetRequestStream())
                {
                    dataStream.Write(byteArray, 0, byteArray.Length);
                    using (WebResponse tResponse = tRequest.GetResponse())
                    {
                        using (Stream dataStreamResponse = tResponse.GetResponseStream())
                        {
                            using (StreamReader tReader = new StreamReader(dataStreamResponse))
                            {
                                String sResponseFromServer = tReader.ReadToEnd();
                                string str = sResponseFromServer;
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                string str = ex.Message;
            }
        }


    }
}