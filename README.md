# lms
Library Management System
- Go to root directory of project i.e. lms
- Then run 'mvnw clean install' (Please ensure JAVA_HOME system variable is set to Java 17)
- Previous step will run the tests and build the application which means jar will be available in traget folder
- Please refer to Build logs for details in case you want to see build details
- Now go to target folder i.e. lms/target and run 'java -jar lms-0.0.1-SNAPSHOT.jar' (This will now run the appplication and apis will be accessible at http://localhost:8080/lms/api/v1/login)
- Please refer to Startup logs for details in case you want to see startup details


------------------------------------------------------------------------------------------------------------
- This is initial version of application which can be enhanced further to introduce more features
- Liquibase is used for database/schema/table creation and data insertion
- Swagger is used for api sepcs and DTO generation
- Word document LMS Demostration is attached to show working examples with API request/response as well
- Currently the app is designed and developed using modular monolith approach so that if required in future it can easily be transitioned to microservices approach
- Application can easily be enahced to use caching (Caffeine or REDIS) in future to cache api responses or any other resource which can help improve performance based on application load
- Preferably this application should be deployed on cloud using dockerization and docker orchestaration like Kubernetes so that scaling would be easier
- We can set the initial memory and cpu limits and based on load it can auto scale 
- Monitoring services can also be used to monitor overall application status and resource usage
- Unit tests for book and parton are written. Coverage screenshot is also added in demonstration document
