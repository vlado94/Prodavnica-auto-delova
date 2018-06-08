using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class CarPart : Entity
    {
        public string Name { get; set; }
        //public byte[] Image { get; set; }
        public double Price { get; set; }
        public string ShortDescription { get; set; }
        public string LongDescription { get; set; }
        public int Quantity { get; set; }
        public int VisitsNumber { get; set; }
        public DateTime PublishDate { get; set; }
        public int IpAddresses { get; set; }
        
        public int CarBrandID { get; set; }
        public CarBrand CarBrand { get; set; }

        public int ShopID { get; set; }
        public Shop Shop { get; set; }
    }
}