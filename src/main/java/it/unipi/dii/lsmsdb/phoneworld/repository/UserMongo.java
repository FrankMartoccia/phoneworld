package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMongo {

    @Autowired
    private IUserMongo userMongo;
    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    public String createUser(User user) {
        String id;
        try {
            userMongo.save(user);
            id = user.getId();
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            id = "";
        }
        return id;
    }
}
