using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class Shop : Entity
    {
        public string Name { get; set; }
        /*public byte[] Image { get; set; }
       
    
        */
        public string Phone { get; set; }
        public Address Address { get; set; }
        public ICollection<CarBrand> CarBrands { get; set; }
    }
}