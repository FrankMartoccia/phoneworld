package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.GraphUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserNeo4j extends Neo4jRepository<GraphUser, String> {
}
