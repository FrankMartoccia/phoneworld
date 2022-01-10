package it.unipi.dii.lsmsdb.phoneworld.repository;

import org.neo4j.driver.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class PhoneNeo4j{

    private final static Logger logger = LoggerFactory.getLogger(PhoneNeo4j.class);

    private final GraphNeo4j graphNeo4j;

    public GraphNeo4j getGraphNeo4j() {
        return graphNeo4j;
    }

    public PhoneNeo4j(GraphNeo4j graphNeo4j) {
        this.graphNeo4j = graphNeo4j;
    }

    public boolean addPhone(String id, String brand, String name, String picture) {
        boolean result = true;
        try {
            graphNeo4j.write("MERGE (p:Phone {id: $id, brand: $brand, name: $name, " +
                            "picture: $picture})",
                    parameters("id", id, "brand", brand, "name", name,
                            "picture", picture));
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public List<Record> findPhoneById(String id) {
        return graphNeo4j.read("MATCH (p:Phone {id: $id})" +
                                     "RETURN p.id AS id, p.brand AS brand, p.name AS name, p.picture " +
                        "AS picture",
                parameters("id", id));
    }

    public boolean updatePhone(String id, String brand, String name, String picture) {
        boolean result = true;
        try {
            graphNeo4j.write("MATCH (p:Phone {id: $id}) " +
                            "SET p.brand = $brand, p.name = $name, p.picture = $picture " +
                            "RETURN p.id AS id, p.brand AS brand, p.name AS name, p.picture AS picture",
                    parameters("id", id, "brand", brand, "name", name, "picture", picture));
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deletePhoneById(String id) {
        boolean result = true;
        try {
            graphNeo4j.write("MATCH (p:Phone {id: $id}) DETACH DELETE p",
                    parameters("id", id));
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public List<Record> findBestBrands() {
        List<Record> result = new ArrayList<>();
        try {
            return graphNeo4j.read("MATCH (:User)-[:ADDS]->(p:Phone) " +
                    "RETURN p.brand AS brand, COUNT(p.brand) AS numPhones " +
                    "ORDER BY numPhones DESC " +
                    "LIMIT 10");
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return result;
    }

    public List<Record> findSuggestedPhonesByFriends(String id) {
        List<Record> result = new ArrayList<>();
        try {
            return graphNeo4j.read("MATCH (u1:User{id:$id})-[:FOLLOWS]->(u2:User)-[:ADDS]->(p:Phone) " +
                    "WHERE u1.id <> u2.id AND NOT EXISTS ((u1)-[:ADDS]->(p)) " +
                    "RETURN DISTINCT p " +
                    "LIMIT 9", parameters("id",id));
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return result;
    }

    public List<Record> findSuggestedPhonesByBrand(String id) {
        List<Record> result = new ArrayList<>();
        try {
            return graphNeo4j.read("MATCH (u1:User{id:$id})-[:ADDS]->(p:Phone) " +
                    "WITH COUNT (p.brand) AS numPhones, p.brand AS favBrand, u1 " +
                    "ORDER BY numPhones DESC " +
                    "LIMIT 1 " +
                    "MATCH (newPhone:Phone{brand:favBrand}) " +
                    "WHERE NOT EXISTS ((u1)-[:ADDS]->(newPhone)) " +
                    "WITH rand() AS number, newPhone " +
                    "ORDER BY number " +
                    "RETURN DISTINCT newPhone " +
                    "LIMIT 9", parameters("id",id));
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return result;
    }

}