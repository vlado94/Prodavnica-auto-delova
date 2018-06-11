namespace CarPartsServer.Models
{
    public class Notification : Entity
    {
        public int PartID { get; set; }
        public int UserID { get; set; }
    }
}