using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class News : Entity
    {
        public string Title { get; set; }
        public DateTime PubishDate { get; set; }
        public string ShortDescription { get; set; }
        public string LongDescription { get; set; }
    }
}