db.createCollection("user");
db.createCollection("dietaryProfile");
db.createCollection("fridge");
db.createCollection("weeklySchedule");
db.createUser({
    user: "chef",
    pwd: "password",
    roles: [{ role: "readWrite", db: "chefplanete" }]
});
