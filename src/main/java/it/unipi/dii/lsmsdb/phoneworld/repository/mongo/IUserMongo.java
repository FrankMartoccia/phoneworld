package it.unipi.dii.lsmsdb.phoneworld.repository.mongo;

import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserMongo extends MongoRepository<GenericUser, String> {

    @Query("{'username': {$regex : ?0, $options: 'i'}, '_class': ?1}")
    List<GenericUser> findByUsernameRegexAnd_class(String username, String classType);

    Optional<GenericUser> findByUsername(String username);


}
