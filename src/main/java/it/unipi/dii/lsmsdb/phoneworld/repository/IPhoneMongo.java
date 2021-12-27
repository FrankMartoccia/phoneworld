package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPhoneMongo extends MongoRepository<Phone, String> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Phone> findByNameRegex(String name);

}
