package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPhoneMongo extends MongoRepository<Phone, String> {
}
