using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class CarPart : Entity
    {
        public string Name { get; set; }
        public byte[] Image { get; set; }
        public double Price { get; set; }
        public string ShortDescription { get; set; }
        public string LongDescription { get; set; }
        public int Quantity { get; set; }
        public int VisitsNumber { get; set; }
        public DateTime PublishDate { get; set; }
        public int IpAddresses { get; set; }


        public ICollection<Category> Categories { get; set; }
        public ICollection<CarBrand> CarBrands { get; set; }
    }
}