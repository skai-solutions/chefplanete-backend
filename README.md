# chefplanete-backend
Spring Boot backend service

# Authentication
1. Authenticate using Google, keep track of the id_token.
2. Send the id_token to the endpoint: `GET /auth/signup/{id_token}`
3. If valid and successful, the backend will add you to the database and send back a token.
4. Keep track of this token and use it to autneticate future requests to the backend in the form of a Header: `Authorization: Bearer {token}`.

Getting OAuth Token: `https://developers.google.com/oauthplayground/#step2&scopes=profile%20email%20openid&auth_code=4%2FvAGq0jFjPtMyfNLvWZLin17e68X6gzJSpF3YaoeAMrhvho7nauHGAwPK4m2EWzMQBBLv6NE25cqz44463VP2s3Y&url=https%3A%2F%2F&content_type=application%2Fjson&http_method=GET&useDefaultOauthCred=checked&oauthEndpointSelect=Google&oauthAuthEndpointValue=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&oauthTokenEndpointValue=https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv4%2Ftoken&oauthClientId=269141253852-k5l3rnk3r9guv45i4ovsi8haoc91qpmp.apps.googleusercontent.com&oauthClientSecret=FigyJoqHGLZAgLXW5tYvn2zH&includeCredentials=checked&accessTokenType=bearer&autoRefreshToken=unchecked&accessType=offline&prompt=consent&response_type=code&wrapLines=on`
# Mongo DB: First Time Docker Setup
Make sure Docker is installed and setup on your computer, then make sure Docker is running before proceeding.

1. cd into the directory './docker/mongo-db/'
2. Run the command 'docker-compose up --build'. This will start up a docker containing running the MongoDB instance exposed on port 27017.
3. Connect to this the same as you would a regular mongo database.
4. Keep in mind that if this container is destroyed then the data associated will also be destroyed. If the container is stopped, you must find the container id again using "docker ps -a" and start it using "docker start $CONTAINER_ID".
