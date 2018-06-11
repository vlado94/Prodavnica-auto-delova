using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
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

        public string Address { get; set; }

        public ICollection<CarBrand> CarBrands { get; set; }


        [NotMapped]
        public int? UserId { get; set; }

        [NotMapped]
        public List<CarPart> CarParts { get; set; }

    }
}