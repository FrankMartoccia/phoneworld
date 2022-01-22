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

public class TestPhoneNeo4j {

    private PhoneNeo4j phoneNeo4j;
    private UserNeo4j userNeo4j;

    @Before
    public void start() {
        init();
    }

    @After
    public void clean() {
        phoneNeo4j.deletePhoneById("id1");
        phoneNeo4j.deletePhoneById("id2");
        phoneNeo4j.deletePhoneById("id3");
        userNeo4j.deleteUserById("userId1");
        userNeo4j.deleteUserById("userId2");
    }

    private void init() {
        try  {
            GraphNeo4j graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                    "neo4j", "PhoneWorld");
            phoneNeo4j = new PhoneNeo4j(graphNeo4j);
            userNeo4j = new UserNeo4j(graphNeo4j);
            userNeo4j.addUser("userId1", "username1");
            userNeo4j.addUser("userId2", "username2");
            phoneNeo4j.addPhone("id1", "Nokia", "Nokia Lumia 900",
                    "https://fdn2.gsmarena.com/vv/bigpic/nokia-800-ofic.jpg", 2012);
            phoneNeo4j.addPhone("id2", "Samsung", "Galaxy S20",
                    "https://fdn2.gsmarena.com/vv/bigpic/nokia-800-ofic.jpg", 2020);
            phoneNeo4j.addPhone("id3", "Nokia", "Nokia 3210",
                    "https://fdn2.gsmarena.com/vv/bigpic/nokia-800-ofic.jpg", 2000);

            userNeo4j.addRelationship("userId1", "id1");
            userNeo4j.addRelationship("userId2", "id2");
            userNeo4j.followRelationship("userId1", "userId2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddPhone() {
        List<Record> records = phoneNeo4j.findPhoneById("id1");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("id").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals( "id1", results.get(0));
    }

    @Test
    public void testFindPhoneById() {
        List<Record> records = phoneNeo4j.findPhoneById("id1");
        String name = records.get(0).get("name").asString();
        System.out.println(name);
        Assertions.assertEquals("Nokia Lumia 900", name);
    }

    @Test
    public void testUpdatePhone() {
        phoneNeo4j.updatePhone("id1", "Apple","https://fdn2.gsmarena.com/vv/bigpic/nokia-800-ofic.jpg", 2013);
        List<Record> records = phoneNeo4j.findPhoneById("id1");
        String brand = records.get(0).get("brand").asString();
        Assertions.assertEquals("Apple", brand);
    }

    @Test
    public void testDeletePhone() {
        phoneNeo4j.deletePhoneById("id1");
        List<Record> records = phoneNeo4j.findPhoneById("id1");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("id").asString());
        }
        System.out.println(records);
        Assertions.assertEquals(0, results.size());
        Assertions.assertTrue(records.isEmpty());
    }

    @Test
    public void testSuggestedPhonesByFriends() {
        List<Record> records = phoneNeo4j.findSuggestedPhonesByFriends("userId1");
        String name = String.valueOf(records.get(0).get("newPhone").get("name").asString());
        System.out.println(records.get(0).get("newPhone"));
        Assertions.assertEquals("Galaxy S20",name);
    }

    @Test
    public void testSuggestedPhonesByBrand() {
        List<Record> records = phoneNeo4j.findSuggestedPhonesByBrand("userId1");
        String name = String.valueOf(records.get(0).get("newPhone").get("name").asString());
        System.out.println(name);
        Assertions.assertEquals(9,records.size());
    }

    @Test
    public void testFindBestBrands() {
        List<Record> records = phoneNeo4j.findBestBrands(10);
        String name = String.valueOf(records.get(0).get("brand").asString());
        Assertions.assertEquals(10,records.size());
        Assertions.assertEquals("Samsung", name);
    }

}
