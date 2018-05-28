using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace CarPartsServer.Models
{
    public class Entity
    {
        
        [Key]
        public virtual int ID { get; set; }

        public Boolean IsDeleted { get; set; }

        public virtual void Update(Entity entity) { }
    }
}