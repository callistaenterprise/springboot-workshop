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

## Step 2 ##

### Added structure ###
we have added a structure for our entities as follows:

Hospital --> Care Unit --> Bed

Please note the dependency between Hospital, Care Unit and Bed expressed by @OneToMany and @ManyToOne annotations.
To be sure to not get circular dependencies we have also Json annotations as @JsonManagedReference and @JsonBackReference

### Added OAS ###
By adding a dependency to Open Api (https://www.openapis.org/) we can now more easily interact with our APIs.
We have set a property to make the call to make the UI look like swagger.

1. Try accessing the URL at: http://localhost:8080/swagger-ui.html

### Simple Tests ###
We have also added some simple tests for the Hospital controller and service.

1. Test to run the testsuites in the project

## Step 3 ##

### Introduce DTOs ###
We don't want to expose database entities in the API-layer. This we solve by introducing DTOs.

Here we will use a mapper tool called MapStruct that maps between entities and DTOs.

## Step 4 ##
Using Spring profiles and loading example data

### Spring profiles ###
We now convert our application.properties to application.yaml. This to make it easier to use Spring profiles.

1. Look att application.yaml!

### Example data ###
When using an application it is often very useful to load some example data, especially if the data should be shown in a GUI!

The loading of the example data is tied to the Spring profile example, look at the beginning of the file.

## Step 5 ##
Logging and error handling

### Logging ###
We have added some logging for new incoming data and we are using the Lombok @Slf4j annotation.

### Error handling ###
We have added a new dependency to Spring Boot starter validation in the build gradle file.
After this we annotate beans with the @NotEmpty annotation (DTO classes).

In our controllers we  annotate incoming request bodies with the @Valid annotation to check that all constraints are met.

This play along fine with our new GlobalExceptionHandlers method handleMethodArgumentNotValid and we can easily return custom made error strings!
If you want to validate for example am enum this way look at https://www.baeldung.com/javax-validations-enums

The custom made error string are introduced in the message.properties file and the usage of these are configured in the CustomMessageSourceConfiguration.

We have also added some new tests that will catch different errors.

1. Test to run the application and omit data or enter wrong data to see the errors in the responses.

## Step 6 ##
Here we add Paging and sorting together with some search capabilities.

#### Paging and Sorting ####
To be able to return pages of results we extend our repository with PagingAndSortingRepository<HospitalJPA, Long>.

1. Try it out with the web-browser, use the example profile!

#### Searching with Specification ####
To be able to search in a dynamic way we can use Spring Data Specification. We will extend our REST interface for Hospital API:s with non-required parameters that we can search with.

These are encapsulated in a Criteria that defines the search parameters. With this as input we will create a Specification for searching customers.

The specification is built by adding pieces of smaller specifications for each search field defined in the domain object HospitalJPA.

The Repository will be extended to include the JpaSpecificationExecutor<HospitalJPA> that will enable searching by a specification. 

1. Try to search for a specific hospital by name

#### Searching with QueryDSL ####
You can also search by adding QueryDSL dependencies.

You then extend your repository with QuerydslPredicateExecutor<CareUnitJPA>.

We still encapsulate the search criteria and use this criteria to build a Predicate by using generated Qxxx classes.

See CareUnitService, buildPredicate method.

For more information see
https://www.baeldung.com/rest-search-language-spring-jpa-criteria




