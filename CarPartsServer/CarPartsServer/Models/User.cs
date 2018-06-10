using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;

namespace CarPartsServer.Models
{
    public class User : Entity
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string FirebaseToken { get; set; }

        public int? ShopID { get; set; }
        public Shop Shop { get; set; }
    }
}