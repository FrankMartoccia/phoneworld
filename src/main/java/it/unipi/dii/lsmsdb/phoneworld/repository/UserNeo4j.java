package it.unipi.dii.lsmsdb.phoneworld.repository;

import org.neo4j.driver.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class UserNeo4j {

    private final static Logger logger = LoggerFactory.getLogger(PhoneNeo4j.class);
    private final GraphNeo4j graphNeo4j;

    public GraphNeo4j getGraphNeo4j() {
        return graphNeo4j;
    }

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

    public void updateUser(String id, String username) {
        graphNeo4j.write("MATCH (u:User {id: $id}) SET u.username = $username RETURN u",
                parameters("id", id, "username", username));
    }

    public void deleteUserById(String id) {
        graphNeo4j.write(("MATCH (u:User {id: $id}) DETACH DELETE u"),
                parameters( "id", id));
    }

    public void followRelationship(String id1, String id2) {
        graphNeo4j.write("MATCH (u1:User {id: $id1})" +
                               "MATCH (u2:User {id: $id2})" +
                               "CREATE (u1)-[:FOLLOWS]->(u2)",
                parameters("id1", id1, "id2", id2));
    }

    public void unfollowRelationship(String id1, String id2) {
        graphNeo4j.write("MATCH (u1:User {id: $id1})-[r:FOLLOWS]->(u2:User {id: $id2}) DELETE r",
                parameters("id1", id1, "id2", id2));
    }

    public void addRelationship(String userId, String phoneId) {
        graphNeo4j.write("MATCH (u:User {id: $userId})" +
                               "MATCH (p:Phone {id: $phoneId})" +
                               "CREATE (u)-[:ADDS]->(p)",
                parameters("userId", userId, "phoneId", phoneId));
    }

    public void removeRelationship(String userId, String phoneId) {
        graphNeo4j.write("MATCH (u:User {id: $userId})-[r:ADDS]->(p:Phone {id: $phoneId}) DELETE r",
                parameters("userId", userId, "phoneId", phoneId));
    }

}
