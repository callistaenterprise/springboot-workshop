# springboot-rest-sample

This sample will be enhanced in a number of steps each tagged in GIT which are the headline below

## Start ##

The tag start will have the initial set-up of the project with one entity and here we want to highlight some parts.

### Initial project structure for Java files ###

![Project Structure](docs/start.png)

#### Controller classes ####

Here we receive incoming REST calls. The only logic that should be performed here is preparing data to call a service that will do the work and to create the response.
As you can see we are right now exposing the domain classes to the outside, this will be changed in the coming updates.

#### Service classes ####

Here we call the corresponding repository classes to do the intended work. We will add some more error handling later...

#### Domain classes ####

Here we use Lombok to minimize the code to write.

Initially we have only one domain class, this we will soon change ...

Note the annotation @Enumerated(EnumType.String) in the Bed class.
This will make sure that we write the String values to the database and not the ordinal numbers for our enum.

We will also have repository classes in this directory.

### Test application
1. Build the application

1. Start the application from Gradle task application -> bootRun

#### Curl access
1. Use curl to get data from the application

    curl -X GET "http://localhost:8080/api/v1/bed" | jq

1. As you can see we don't have any data yet! Insert data

    curl -d '{"name":"kalle","state":"FREE"}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/bed

1. Now try to get data!

#### Database access

1.Open the url http://localhost:8080/h2-console

1. Use the JDBC URL found in output when the application starts.

1. Select BED and get all records.

1. Simplify the JDBC URL by adding a H2 property in the application properties! (Uncomment)