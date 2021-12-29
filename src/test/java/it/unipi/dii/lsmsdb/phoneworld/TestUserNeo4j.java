package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.repository.GraphNeo4j;
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
    private GraphNeo4j graphNeo4j;

    @Before
    public void start() {
        init();
    }

    @After
    public void clean() {
        userNeo4j.deleteUserById("id1");
    }

    private void init() {
        try  {
            graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                    "neo4j", "PhoneWorld");
            userNeo4j = new UserNeo4j(graphNeo4j);
            userNeo4j.addUser("id1", "Paolo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddUser() {
        List<Record> records = graphNeo4j.read("MATCH (u:User {id: 'id1'}) RETURN u.username");
//        System.out.println(records.get(0).get("u.username"));
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("u.username").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals( "Paolo", results.get(0));
    }

    @Test
    public void testDeleteUser() {
        userNeo4j.deleteUserById("id1");
        List<Record> records = graphNeo4j.read("MATCH (u:User {id: 'id1'}) RETURN u.id");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("u.id").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(0, results.size());
        Assertions.assertTrue(records.isEmpty());
    }

}
