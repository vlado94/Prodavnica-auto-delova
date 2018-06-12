using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class UserNotify
    {
        public string Token { get; set; }
        public int? CarPartID { get; set; }
        public CarPart CarPart { get; set; }
    }
}