# PhoneWorld

![alt text](https://github.com/FrankMartoccia/phoneworld/blob/master/src/main/resources/logo-3.png)

## General info

PhoneWorld is an application that everyone should use before buying a new
phone or just to feed the desire to know which smartphone is considered the best
by the community.

The application's main functionalities are collecting, organizing and presenting to
users information related to various models of phone, with
all their specifications and the reviews of the users.

Users can browse phones, read their specifications, add them to their watchlist
and write a review for them, but also interact with other users, following them
and view their watchlist.

## Technologies
The application is developed with the following **language**:
* Java

The **DBMSs** used are:
* MongoDB
* Neo4j

The **frameworks** used are:
* Spring Data MongoDB
* JavaFX (for the GUI)
* JUnit (for the testing)

In order to work on the initial datasets, **scripts** have been written in:
* Python

## Execution

In order to execute the application, the following software is needed: 

* [Intellij IDEA](https://www.jetbrains.com/idea/download/) 
* [JavaFX](https://openjfx.io/) (To correctly configure JavaFX runtime components follow [this](https://ashley-tharp.medium.com/solved-error-javafx-runtime-components-are-missing-and-are-required-to-run-this-application-ec4779eb796d) guide) 
* [MongoDB](https://www.mongodb.com/try/download/community)
* [Neo4j](https://neo4j.com/download/) 

### Configure MongoDB

You can set the **MongoDB** connection URI and the database name in the **application.properties** file, in the folder `resources`.

To import the collections found in the `final datasets` folder, you can use the [mongoimport](https://www.mongodb.com/docs/database-tools/mongoimport/#mongodb-binary-bin.mongoimport) commmand: 
```sh
mongoimport <options> <connection-string> <file>
```
(Run `mongoimport` from the system command line, not the mongo shell)

### Configure Neo4j

For the **Neo4j** instance, username, password and URI can be set in the '**App.java**' class. 

To reconstruct the graph from the CSV files provided in the `neo4j` folder in `final datasets`, you can execute the following commands (Note: `phonesNeo.csv` and `usersNeo.csv` are in the `import` directory of the database in this case, if you haven't them you can either copy them or specify a different path): 

Create the nodes

```sh
load csv with headers from "file:///usersNeo.csv" as row    
create (u:User) set u.id = row._id, u.username = row.username

load csv with headers from "file:///phonesNeo.csv" as row    
create (p:Phone) set p.id = row._id, p.name = row.name, p.brand= row.brand, p.picture= row.picture
```
Generate `ADDS` relationships
```sh
WITH range(0,10) as phonesRange
MATCH (p:Phone)
WITH collect(p) as phones, phonesRange
MATCH (u:User)
WITH u, apoc.coll.randomItems(phones, apoc.coll.randomItem(phonesRange)) as phones
FOREACH (phone in phones | CREATE (u)-[:ADDS]->(phone))
```
Generate `FOLLOWS` relationship
```sh
WITH range(0,10) as usersRange
MATCH (u1:User)
WITH collect(u1) as users, usersRange
MATCH (u2:User)
WITH u2, apoc.coll.randomItems(users, apoc.coll.randomItem(usersRange)) as users
FOREACH (user in users | CREATE (u2)-[:FOLLOWS]->(user))
```
Delete self-follow relationships
```sh
MATCH (u:User)-[rel:FOLLOWS]->(u) 
delete rel
```







