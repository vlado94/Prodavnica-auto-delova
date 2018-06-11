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
<<<<<<< HEAD
        public string Address { get; set; }
        public ICollection<Address> Addresses { get; set; }
=======
        public Address Address { get; set; }
>>>>>>> d1e36b76efeb05019c821a567f634c8fbb524efe
        public ICollection<CarBrand> CarBrands { get; set; }


        [NotMapped]
        public int? UserId { get; set; }

    }
}