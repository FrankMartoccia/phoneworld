package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.GraphPhone;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PhoneNeo4j {

    public PhoneNeo4j() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneNeo4j.class);

    @Autowired
    private IPhoneNeo4j phoneNeo4j;

    public PhoneNeo4j(IPhoneNeo4j phoneNeo4j) {
        this.phoneNeo4j = phoneNeo4j;
    }

    public IPhoneNeo4j getPhoneNeo4j() {
        return phoneNeo4j;
    }

    public void addPhone(GraphPhone phone) {
        try {
            phoneNeo4j.save(phone);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public Optional<GraphPhone> findPhoneById(String id) {
        Optional<GraphPhone> phone = Optional.empty();
        try {
            phone = phoneNeo4j.findById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return phone;
    }

    public void updatePhone(String id, Phone newPhone) {
        try {
            Optional<GraphPhone> phone = phoneNeo4j.findById(id);
            if (phone.isPresent()) {
                phone.get().setBrand(newPhone.getBrand());
                phone.get().setName(newPhone.getName());
                phone.get().setPicture(newPhone.getPicture());

                this.addPhone(phone.get());
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deletePhoneById(String id) {
        try {
            phoneNeo4j.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deletePhone(GraphPhone phone) {
        try {
            phoneNeo4j.delete(phone);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

}
