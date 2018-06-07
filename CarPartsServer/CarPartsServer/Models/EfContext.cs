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
        public DbSet<Category> Categories { get; set; }
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
                        });/*
            modelBuilder.Entity<Shop>()
                        .HasMany<CarBrand>(s => s.CarBrands)
                        .WithMany()
                        .Map(cs =>
                        {
                            cs.MapLeftKey("ShopRefId");
                            cs.MapRightKey("CarBrandRefId");
                            cs.ToTable("ShopCarBrand");
                        });*/

            modelBuilder.Entity<CarPart>()
                        .HasMany<Category>(s => s.Categories)
                        .WithMany()
                        .Map(cs =>
                        {
                            cs.MapLeftKey("CarPartRefId");
                            cs.MapRightKey("CategoryRefId");
                            cs.ToTable("CarPartCategory");
                        });
            modelBuilder.Entity<CarPart>()
                        .HasMany<CarBrand>(s => s.CarBrands)
                        .WithMany()
                        .Map(cs =>
                        {
                            cs.MapLeftKey("CarPartRefId");
                            cs.MapRightKey("CarBrandRefId");
                            cs.ToTable("CarPartCarBrand");
                        });




            base.OnModelCreating(modelBuilder);
        }
    }


    public class MyDbContextInitializer : DropCreateDatabaseIfModelChanges<EfContext>
    {
        protected override void Seed(EfContext db)
        {
            // seed data
            Category cat1 = db.Categories.Add(new Category()
            {
                IsDeleted = false,
                Name = "Kategorija 1",
            });
            Category cat2 = db.Categories.Add(new Category()
            {
                IsDeleted = false,
                Name = "Kategorija 2",
            });

            Shop s = db.Shops.Add(new Shop()
            {
                IsDeleted = false,
                Name = "Radnja 1",
                /*Phone = "053423 42342",
                Image = null,
                Category = cat1,*/
            });

            db.Users.Add(new User() {
                IsDeleted = false,
                Email = "Pera",
                Shop = s,
                Password = "Pera123"
            });
            db.Users.Add(new User()
            {
                IsDeleted = false,
                Email = "Mika",
                Shop = s,
                Password = "Mika123"
            });

            CarBrand cb1 = db.CarBrands.Add(new CarBrand()
            {
                IsDeleted = false,
                Name ="Ford",
            });
            CarBrand cb2 = db.CarBrands.Add(new CarBrand()
            {
                IsDeleted = false,
                Name = "Audi",
            });
            CarBrand cb3 = db.CarBrands.Add(new CarBrand()
            {
                IsDeleted = false,
                Name = "Mercedes",
            });

            var cp1 = db.CarParts.Add(new CarPart()
            {
                Name = "Letva volana",
                IsDeleted = false,
                IpAddresses = 3232,
                PublishDate = DateTime.Now,
                VisitsNumber = 3,
                LongDescription = "dsafdsa s fas fas saf safasf as fas\ndsadasdasdas",
                ShortDescription = "dsdasdasfa",
                Quantity = 3,
                Price = 32,
            });



            base.Seed(db);
        }
    }
}