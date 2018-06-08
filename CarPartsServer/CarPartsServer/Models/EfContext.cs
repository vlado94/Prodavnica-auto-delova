using System;
using System.Data.Entity;
using static System.Data.Entity.Migrations.Model.UpdateDatabaseOperation;


namespace CarPartsServer.Models
{
    public class EfContext : DbContext
    {
        public EfContext() : base("EfContext")
        {

        }


        public DbSet<Address> Addresses { get; set; }
        public DbSet<CarBrand> CarBrands { get; set; }
        public DbSet<CarPart> CarParts { get; set; }
        public DbSet<City> Cities { get; set; }
        public DbSet<Country> Countries { get; set; }
        public DbSet<News> News { get; set; }
        public DbSet<Role> Roles { get; set; }
        public DbSet<Shop> Shops { get; set; }
        public DbSet<User> Users { get; set; }


        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            Database.SetInitializer(new MyDbContextInitializer());
            modelBuilder.Entity<Shop>()
                        .HasMany<Address>(s => s.Addresses)
                        .WithMany()
                        .Map(cs =>
                        {
                            cs.MapLeftKey("ShopRefId");
                            cs.MapRightKey("AddressRefId");
                            cs.ToTable("ShopAddress");
                        });
            modelBuilder.Entity<Shop>()
                        .HasMany<CarBrand>(s => s.CarBrands)
                        .WithMany()
                        .Map(cs =>
                        {
                            cs.MapLeftKey("ShopRefId");
                            cs.MapRightKey("CarBrandRefId");
                            cs.ToTable("ShopCarBrand");
                        });

            base.OnModelCreating(modelBuilder);
        }
    }


    public class MyDbContextInitializer : DropCreateDatabaseIfModelChanges<EfContext>
    {
        protected override void Seed(EfContext db)
        {
            // seed data




            base.Seed(db);
        }
    }
}