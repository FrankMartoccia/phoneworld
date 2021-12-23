package it.unipi.dii.lsmsdb.phoneworld.repository;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;

public class PhoneMongo {

    @Autowired
    private IPhoneMongo phoneMongo;
    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    public String createPhone(Phone phone) {
        String id;
        try {
            phoneMongo.save(phone);
            id = phone.getId();
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            id = "";
        }
        return id;
    }

}
