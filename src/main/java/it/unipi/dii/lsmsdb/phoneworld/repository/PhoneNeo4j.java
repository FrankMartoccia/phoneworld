package it.unipi.dii.lsmsdb.phoneworld.repository;

import org.neo4j.driver.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class PhoneNeo4j{

    private final static Logger logger = LoggerFactory.getLogger(PhoneNeo4j.class);

    private final GraphNeo4j graphNeo4j;

    public PhoneNeo4j(GraphNeo4j graphNeo4j) {
        this.graphNeo4j = graphNeo4j;
    }

    public void addPhone(String id, String brand, String name, String picture) {
        graphNeo4j.write("MERGE (p:Phone {id: $id, brand: $brand, name: $name, " +
                        "picture: $picture})",
                parameters("id", id, "brand", brand, "name", name,
                        "picture", picture));
    }

    public List<Record> findPhoneById(String id) {
        return graphNeo4j.read("MATCH (p:Phone {id: $id})" +
                                     "RETURN p",
                parameters("id", id));
    }

    public void updatePhone(String brand, String name, String picture) {
        graphNeo4j.write("MATCH (p:Phone {id: $id}) " +
                                "SET p.brand = $brand, p.name = $name, p.picture = $picture" +
                                "RETURN p",
                parameters("brand", brand, "name", name, "picture", picture));
    }

    public void deletePhoneById(String id) {
        graphNeo4j.write("MATCH (p:Phone {id: $id}) DETACH DELETE p",
                parameters("id", id));
    }

}