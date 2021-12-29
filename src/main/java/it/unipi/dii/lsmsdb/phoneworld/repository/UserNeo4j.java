package it.unipi.dii.lsmsdb.phoneworld.repository;

import org.neo4j.driver.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class UserNeo4j {

    private final static Logger logger = LoggerFactory.getLogger(PhoneNeo4j.class);
    private final GraphNeo4j graphNeo4j;

    public UserNeo4j(GraphNeo4j graphNeo4j) {
        this.graphNeo4j = graphNeo4j;
    }

    public void addUser(String id, String username) {
        graphNeo4j.write("MERGE (u:User {id: $id, username: $username})",
                parameters( "id", id, "username", username));
    }

    public List<Record> findUserById(String id) {
        return graphNeo4j.read("MATCH (u:User {id: $id})" +
                                     "RETURN u",
                parameters("id", id));
    }

    public void updateUser(String username) {
        graphNeo4j.write("MATCH (u:User {id: $id}) " +
                               "SET u.username = $username" +
                               "RETURN u",
                parameters("username", username));
    }

    public void deleteUserById(String id) {
        graphNeo4j.write(("MATCH (u:User {id: $id}) DETACH DELETE u"),
                parameters( "id", id));
    }


}
