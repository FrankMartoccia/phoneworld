package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMongo {

    public UserMongo() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    @Autowired
    private IUserMongo userMongo;

    public IUserMongo getUserMongo() {
        return userMongo;
    }

    public String addUser(User user) {
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
