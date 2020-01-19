# chefplanete-backend
Spring Boot backend service

# Authentication
1. Authenticate using Google, keep track of the id_token.
2. Send the id_token to the endpoint: `GET /auth/signup/{id_token}`
3. If valid and successful, the backend will add you to the database and send back a token.
4. Keep track of this token and use it to autneticate future requests to the backend in the form of a Header: `Authorization: Bearer {token}`.

# Mongo DB: First Time Docker Setup
Make sure Docker is installed and setup on your computer, then make sure Docker is running before proceeding.

1. cd into the directory './docker/mongo-db/'
2. Run the command 'docker-compose up --build'. This will start up a docker containing running the MongoDB instance exposed on port 27017.
3. Connect to this the same as you would a regular mongo database.
4. Keep in mind that if this container is destroyed then the data associated will also be destroyed. If the container is stopped, you must find the container id again using "docker ps -a" and start it using "docker start $CONTAINER_ID".
5. Finally, we need to load the food database: `mongorestore -u mongo-root -p password --authenticationDatabase admin --db chefplanete products.bson` where products.bson is the location to the dump downloaded from: `https://world.openfoodfacts.org/data`
