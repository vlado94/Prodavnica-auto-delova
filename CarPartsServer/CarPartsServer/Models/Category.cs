using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class Category : Entity
    {
        public string Name { get; set; }
        public byte Image { get; set; }
    }
}