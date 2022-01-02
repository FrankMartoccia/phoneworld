package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Admin;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class UserMongo {

    public UserMongo() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    @Autowired
    private IUserMongo userMongo;
    @Autowired
    private MongoOperations mongoOperations;

    public IUserMongo getUserMongo() {
        return userMongo;
    }

    public boolean addUser(GenericUser user) {
        boolean result = true;
        try {
            userMongo.save(user);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public List<GenericUser> findUsers(String username, boolean admin) {
        List<GenericUser> users = new ArrayList<>();
        try {
            if (username.isEmpty()) {
                users.addAll(userMongo.findByAdmin(admin));
            } else {
                users.addAll(userMongo.findByUsernameContainingAndAdmin(username, admin));
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return users;
    }

    public GenericUser findUserById(String id, boolean admin) {
        GenericUser user = null;
        try {
            user = userMongo.findByIdAndAdmin(id, admin);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return user;
    }

    public boolean updateUser(String id, GenericUser newGenericUser, boolean admin) {
        boolean result = true;
        try {
            Optional<GenericUser> genericUser = userMongo.findById(id);
            if (genericUser.isPresent()) {
                if (admin) {
                    Admin administrator = (Admin) genericUser.get();
                    administrator.setUsername(newGenericUser.getUsername());
                    administrator.setAdmin(newGenericUser.isAdmin());
                    administrator.setSha(newGenericUser.getSha());
                    administrator.setSalt(newGenericUser.getSalt());
                    this.addUser(administrator);
                } else {
                    User user = (User) genericUser.get();
                    User newUser = (User)newGenericUser;
                    user.setUsername(newUser.getUsername());
                    user.setAdmin(newUser.isAdmin());
                    user.setSha(newUser.getSha());
                    user.setSalt(newUser.getSalt());
                    user.setAge(newUser.getAge());
                    user.setCity(newUser.getCity());
                    user.setCountry(newUser.getCountry());
                    user.setEmail(newUser.getEmail());
                    user.setGender(newUser.getGender());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setDateOfBirth(newUser.getDateOfBirth());
                    user.setStreetName(newUser.getStreetName());
                    user.setStreetNumber(newUser.getStreetNumber());
                    this.addUser(user);
                } 
            } 
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deleteUserById(String id) {
        boolean result = true;
        try {
            userMongo.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deleteUser(GenericUser user) {
        boolean result = true;
        try {
            userMongo.delete(user);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public Document findAvgUserAgeByCountry() {
        MatchOperation matchOperation = match(new Criteria("admin").is(false));
        GroupOperation groupOperation = group("$country").avg("$age").as("avgAge");
        ProjectionOperation projectionOperation = project()
                .andExpression("avgAge").as("avgAge").andExclude("_id")
                .andExpression("_id").as("country");
        Aggregation aggregation = newAggregation(matchOperation, groupOperation, projectionOperation);
        AggregationResults<User> result = mongoOperations
                .aggregate(aggregation, "users", User.class);
        return result.getRawResults();
    }





}




