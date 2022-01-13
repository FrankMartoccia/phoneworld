package it.unipi.dii.lsmsdb.phoneworld.repository.mongo;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class PhoneMongo {

    public PhoneMongo() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    @Autowired
    private IPhoneMongo phoneMongo;
    @Autowired
    private MongoOperations mongoOperations;

    public IPhoneMongo getPhoneMongo() {
        return phoneMongo;
    }

    public boolean addPhone(Phone phone) {
        boolean result = true;
        try {
            phoneMongo.save(phone);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public List<Phone> findPhones(String name, String parameter) {
        List<Phone> phones = new ArrayList<>();
        try {
            if (name.isEmpty()) {
                phones.addAll(findRecentPhones());
            } else if (parameter.equals("Name")){
                phones.addAll(phoneMongo.findByNameRegexOrderByReleaseYearDesc(name,
                        Sort.by(Sort.Direction.DESC, "releaseYear")));
            } else if (parameter.equals("Ram (GB)")){
                phones.addAll(phoneMongo.findByRamRegexOrderByReleaseYearDesc(name,
                        Sort.by(Sort.Direction.DESC, "releaseYear")));
            } else if (parameter.equals("Storage (GB)")){
                phones.addAll(phoneMongo.findByStorageRegexOrderByReleaseYearDesc(name,
                        Sort.by(Sort.Direction.DESC, "releaseYear")));
            } else if (parameter.equals("Chipset")){
                phones.addAll(phoneMongo.findByChipsetRegexOrderByReleaseYearDesc(name,
                        Sort.by(Sort.Direction.DESC, "releaseYear")));
            } else if (parameter.equals("Battery Size (mAh)")){
                phones.addAll(phoneMongo.findByBatterySizeRegexOrderByReleaseYearDesc(name,
                        Sort.by(Sort.Direction.DESC, "releaseYear")));
            } else if (parameter.equals("Camera Pixels (MP)")){
                phones.addAll(phoneMongo.findByCameraPixelsRegexOrderByReleaseYearDesc(name,
                        Sort.by(Sort.Direction.DESC, "releaseYear")));
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return phones;
    }

    public Optional<Phone> findPhoneById(String id) {
        Optional<Phone> phone = Optional.empty();
        try {
            phone = phoneMongo.findById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return phone;
    }

    public boolean updatePhone(String id, Phone newPhone) {
        boolean result = true;
        try {
            Optional<Phone> phone = phoneMongo.findById(id);
            if (phone.isPresent()) {
                phone.get().setBrand(newPhone.getBrand());
                phone.get().setName(newPhone.getName());
                phone.get().setPicture(newPhone.getPicture());
                phone.get().setReleaseYear(newPhone.getReleaseYear());
                phone.get().setBody(newPhone.getBody());
                phone.get().setOs(newPhone.getOs());
                phone.get().setStorage(newPhone.getStorage());
                phone.get().setDisplaySize(newPhone.getDisplaySize());
                phone.get().setDisplayResolution(newPhone.getDisplayResolution());
                phone.get().setCameraPixels(newPhone.getCameraPixels());
                phone.get().setVideoPixels(newPhone.getVideoPixels());
                phone.get().setRam(newPhone.getRam());
                phone.get().setChipset(newPhone.getChipset());
                phone.get().setBatterySize(newPhone.getBatterySize());
                phone.get().setBatteryType(newPhone.getBatteryType());

                this.addPhone(phone.get());
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deletePhoneById(String id) {
        boolean result = true;
        try {
            phoneMongo.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deletePhone(Phone phone) {
        boolean result = true;
        try {
            phoneMongo.delete(phone);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public List<Phone> findRecentPhones() {
        List<Phone> phones = null;
        try {
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.DESC, "releaseYear"));
            phones = mongoOperations.find(query, Phone.class);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return phones;
    }

    public Document findTopRatedBrands(int minReviews, int results) {
        UnwindOperation unwindOperation = unwind("reviews");
        GroupOperation groupOperation = group("$brand").avg("$reviews.rating")
                        .as("avgRating").count().as("numReviews");
        MatchOperation matchOperation = match(new Criteria("numReviews").gte(minReviews));
        SortOperation sortOperation = sort(Sort.by(Sort.Direction.DESC, "avgRating",
                "numReviews"));
        LimitOperation limitOperation = limit(results);
        ProjectionOperation projectionOperation = project().andExpression("_id").as
                ("brand").andExpression("avgRating").as("rating").andExclude("_id")
                .andExpression("numReviews").as("reviews");
        Aggregation aggregation = newAggregation(unwindOperation, groupOperation, matchOperation,
                sortOperation, limitOperation, projectionOperation);
        AggregationResults<Phone> result = mongoOperations
                .aggregate(aggregation, "phones", Phone.class);
        return result.getRawResults();
    }
}
