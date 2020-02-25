# chefplanete-backend
Spring Boot backend service

# Setting Up the Project in IntelliJ
1. 

# Authentication
1. Authenticate using Google, keep track of the id_token.
2. Send the id_token to the endpoint: `GET /auth/signup/{id_token}`
3. If valid and successful, the backend will add you to the database and send back a token.
4. Keep track of this token and use it to autneticate future requests to the backend in the form of a Header: `Authorization: Bearer {token}`.

# JSON
There needs to be a credentials JSON in the resources folder. Message me for the link.

# Mongo DB: First Time Docker Setup
Make sure Docker is installed and setup on your computer, then make sure Docker is running before proceeding.

1. cd into the directory './docker/mongo-db/'
2. Run the command 'docker-compose up --build'. This will start up a docker containing running the MongoDB instance exposed on port 27017.
3. Connect to this the same as you would a regular mongo database.
4. Keep in mind that if this container is destroyed then the data associated will also be destroyed. If the container is stopped, you must find the container id again using "docker ps -a" and start it using "docker start $CONTAINER_ID".
5. Finally, we need to load the food database: `mongoimport -u mongo-root -p password --authenticationDatabase admin --db chefplanete foodItem.json` where the json is the dump downloaded from: `https://drive.google.com/file/d/1uWeJOonHygV3cYfEZ24AbAwmhJT2S-Cm/view?usp=sharing`
