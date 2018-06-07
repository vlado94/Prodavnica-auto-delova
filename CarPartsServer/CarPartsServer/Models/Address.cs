using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class Address : Entity
    {
/*        public string Name { get; set; }
        public string Street { get; set; }*/
        public int Number { get; set; }

        public int? CityID { get; set; }
        public City City { get; set; }

    }
}