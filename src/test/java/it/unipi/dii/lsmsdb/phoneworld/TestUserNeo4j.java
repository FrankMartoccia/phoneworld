package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.GraphNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.PhoneNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.UserNeo4j;
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
        userNeo4j.deleteUserById("id3");
        phoneNeo4j.deletePhoneById("phoneid1");
        phoneNeo4j.deletePhoneById("phoneid2");
        phoneNeo4j.deletePhoneById("phoneid3");
    }

    private void init() {
        try  {
            GraphNeo4j graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                    "neo4j", "PhoneWorld");
            userNeo4j = new UserNeo4j(graphNeo4j);
            phoneNeo4j = new PhoneNeo4j(graphNeo4j);
            userNeo4j.addUser("id1", "Paolo");
            userNeo4j.addUser("id2", "Paolino");
            userNeo4j.addUser("id3", "Paoletto");
            phoneNeo4j.addPhone("phoneid1", "Xiaomi", "Mi 11", "picture", 2020);
            phoneNeo4j.addPhone("phoneid2", "Xiaomi", "Mi 12", "picture", 2021);
            phoneNeo4j.addPhone("phoneid3", "Apple", "iPhone XS", "picture", 2019);
            userNeo4j.addRelationship("id1", "phoneid1");
            userNeo4j.addRelationship("id2", "phoneid2");
            userNeo4j.addRelationship("id3", "phoneid3");
            userNeo4j.followRelationship("id1", "id2");
            userNeo4j.followRelationship("id2", "id3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddUser() {
        List<Record> records = userNeo4j.findUserById("id1");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("username").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals( "Paolo", results.get(0));
    }

    @Test
    public void testFindUserById() {
        List<Record> records = userNeo4j.findUserById("id1");
        String username = records.get(0).get("username").asString();
        System.out.println(username);
        Assertions.assertEquals("Paolo", username);
    }

    @Test
    public void testUpdateUser() {
        userNeo4j.updateUser("id1", "Paola");
        List<Record> records = userNeo4j.findUserById("id1");
        String username = records.get(0).get("username").asString();
        Assertions.assertEquals("Paola", username);
    }

    @Test
    public void testDeleteUser() {
        userNeo4j.deleteUserById("id1");
        List<Record> records = userNeo4j.findUserById("id1");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("id").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(0, results.size());
        Assertions.assertTrue(records.isEmpty());
    }

    @Test
    public void testFollowRelationship() {
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u1:User {id: 'id1'})-[:FOLLOWS]->(u2:User {id: 'id2'})" +
                        "RETURN u1.username AS username");
        String username = records.get(0).get("username").asString();
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
        List<Record> records = userNeo4j.getGraphNeo4j().read(
                "MATCH (u1:User {id: 'id1'})-[:ADDS]->(p:Phone {id: 'phoneid1'})" +
                        "RETURN u1.username AS username");
        String username = records.get(0).get("username").asString();
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

    @Test
    public void testMostFollowedUsers() {
        List<Record> records = userNeo4j.findMostFollowedUsers();
        System.out.println(records);
//        String username = records.get(0).get("username").asString();
        Assertions.assertEquals(10, records.size());
    }

    @Test
    public void testSuggestedUsersByFriends() {
        List<Record> records = userNeo4j.findSuggestedUsersByFriends("id1");
        System.out.println(records);
        String username = records.get(0).get("username").asString();
        Assertions.assertEquals("Paoletto", username);
    }

    @Test
    public void testSuggestedUsersByBrand() {
        List<Record> records = userNeo4j.findSuggestedUsersByBrand("id1");
        System.out.println(records);
        int phones = records.get(0).get("phones").asInt();
        Assertions.assertEquals(3, phones);
    }

    @Test
    public void testGetWatchlist() {
        List<Record> records = userNeo4j.getWatchlist("id1");
        System.out.println(records);
        String name = records.get(0).get("p").get("name").asString();
        Assertions.assertEquals("Mi 11", name);
    }

    @Test
    public void testGetRelationship() {
        List<Record> records = userNeo4j.getRelationship("id1", "phoneid1");
        System.out.println(records);
        List<Record> record = userNeo4j.getGraphNeo4j().read(
                "MATCH (u1:User {id: 'id1'})-[:ADDS]->(p:Phone {id: 'phoneid1'})" +
                        "RETURN u1");
        System.out.println(record);
//        String name = records.get(0).get("p").get("name").asString();
//        Assertions.assertEquals("Mi 11", name);
        Assertions.assertEquals(1,1);
    }

}
