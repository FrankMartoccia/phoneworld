package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.repository.GraphNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserNeo4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.List;

public class TestUserNeo4j {

    private UserNeo4j userNeo4j;
    private PhoneNeo4j phoneNeo4j;

    @Before
    public void start() {
        init();
    }

    @After
    public void clean() {
        userNeo4j.deleteUserById("id1");
        userNeo4j.deleteUserById("id2");
        phoneNeo4j.deletePhoneById("phoneid1");
    }

    private void init() {
        try  {
            GraphNeo4j graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                    "neo4j", "PhoneWorld");
            userNeo4j = new UserNeo4j(graphNeo4j);
            phoneNeo4j = new PhoneNeo4j(graphNeo4j);
            userNeo4j.addUser("id1", "Paolo");
            userNeo4j.addUser("id2", "Paolino");
            phoneNeo4j.addPhone("phoneid1", "Xiaomi", "Mi 11", "picture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddUser() {
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u:User {id: 'id1'}) RETURN u.username");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("u.username").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals( "Paolo", results.get(0));
    }

    @Test
    public void testFindUserById() {
        List<Record> records = userNeo4j.findUserById("id1");
        String username = records.get(0).get("u").get("username").asString();
        System.out.println(username);
        Assertions.assertEquals("Paolo", username);
    }

    @Test
    public void testUpdateUser() {
        userNeo4j.updateUser("id1", "Paola");
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u:User {id: 'id1'}) RETURN u.username");
        String username = records.get(0).get("u.username").asString();
        Assertions.assertEquals("Paola", username);
    }

    @Test
    public void testDeleteUser() {
        userNeo4j.deleteUserById("id1");
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u:User {id: 'id1'}) RETURN u.id");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("u.id").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(0, results.size());
        Assertions.assertTrue(records.isEmpty());
    }

    @Test
    public void testFollowRelationship() {
        userNeo4j.followRelationship("id1", "id2");
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u1:User {id: 'id1'})-[:FOLLOWS]->(u2:User {id: 'id2'})" +
                        "RETURN u1");
        String username = records.get(0).get("u1").get("username").asString();
        System.out.println(username);
        Assertions.assertEquals("Paolo", username);
    }

    @Test
    public void testUnfollowRelationship() {
        userNeo4j.unfollowRelationship("id1", "id2");
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u1:User {id: 'id1'})-[:FOLLOWS]->(u2:User {id: 'id2'})" +
                        "RETURN u1");
        Assertions.assertTrue(records.isEmpty());
    }

    @Test
    public void testAddRelationship() {
        userNeo4j.addRelationship("id1", "phoneid1");
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u1:User {id: 'id1'})-[:ADDS]->(p:Phone {id: 'phoneid1'})" +
                        "RETURN u1");
        String username = records.get(0).get("u1").get("username").asString();
        System.out.println(username);
        Assertions.assertEquals("Paolo", username);
    }

    @Test
    public void testRemoveRelationship() {
        userNeo4j.removeRelationship("id1", "phoneid1");
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u1:User {id: 'id1'})-[:ADDS]->(p:Phone {id: 'phoneid1'})" +
                        "RETURN u1");
        Assertions.assertTrue(records.isEmpty());
    }

}
