package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserMongo extends MongoRepository<GenericUser, String> {

    List<GenericUser> findByAdmin(boolean admin);
    List<GenericUser> findByUsernameContainingAndAdmin(String username, boolean admin);
    GenericUser findByIdAndAdmin(String id, boolean admin);

}
