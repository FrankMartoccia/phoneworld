package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserMongo extends MongoRepository<User, String> {
}
