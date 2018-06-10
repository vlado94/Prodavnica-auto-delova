﻿using CarPartsServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;

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
                retval = db.CarParts.FirstOrDefault(x => x.ID == id);
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
                    query = query.Where(x => x.Name.Equals(carPart));

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
                    cpedit.LongDescription = cpedit.LongDescription;
                    cpedit.Name = cpedit.Name;
                    cpedit.Price = cpedit.Price;
                    cpedit.ShortDescription = cpedit.ShortDescription;
                    db.SaveChanges();
                    if (cpedit.Quantity == 0 && model.Quantity != 0)
                    {
                        SendNotification(cpedit.ID);
                    }
                }
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

        private void SendNotification(int carPartID)
        {
            using (var db = new EfContext())
            {
                List<Notification> notification = db.Notifications.Where(x => x.PartID == carPartID).ToList();
                foreach(Notification n in notification)
                {
                    //Basara ovde dodaj svoj kod za notifikacije




                    db.Notifications.First(x => x.ID == n.ID).IsDeleted = true;
                }
                db.SaveChanges();
            }
        }
    }
}