using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class City : Entity
    {
        public string Name { get; set; }
        
        public int? CountryID { get; set; }
        public Country Country { get; set; }
    }
}