package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.repository.GraphNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneNeo4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.List;

public class TestPhoneNeo4j {

    private PhoneNeo4j phoneNeo4j;
    private GraphNeo4j graphNeo4j;

    @Before
    public void start() {
        init();
    }

    @After
    public void clean() {
        phoneNeo4j.deletePhoneById("id1");
    }

    private void init() {
        try  {
            graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                    "neo4j", "PhoneWorld");
            phoneNeo4j = new PhoneNeo4j(graphNeo4j);
            phoneNeo4j.addPhone("id1", "Nokia", "Nokia Lumia 900",
                    "https://fdn2.gsmarena.com/vv/bigpic/nokia-800-ofic.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddPhone() {
        List<Record> records = graphNeo4j.read("MATCH (p:Phone {id: 'id1'}) RETURN p.id");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("p.id").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals( "id1", results.get(0));
    }

    @Test
    public void testDeletePhone() {
        phoneNeo4j.deletePhoneById("id1");
        List<Record> records = graphNeo4j.read("MATCH (p:Phone {id: 'id1'}) RETURN p.id");
        List<String> results = new ArrayList<>();
        for (Record record : records) {
            results.add(record.get("p.id").asString());
        }
        System.out.println(results);
        Assertions.assertEquals(0, results.size());
        Assertions.assertTrue(records.isEmpty());
    }

}
