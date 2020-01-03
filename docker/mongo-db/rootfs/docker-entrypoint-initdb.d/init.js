db.createCollection("user");
db.createCollection("dietaryProfile");
db.createCollection("pantry");
db.createCollection("weeklySchedule");
db.createUser({
    user: "chef",
    pwd: "password",
    roles: [{ role: "readWrite", db: "chefplanete" }]
});
