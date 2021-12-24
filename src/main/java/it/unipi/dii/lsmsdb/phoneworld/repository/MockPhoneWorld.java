//package it.unipi.dii.lsmsdb.phoneworld.repository;
//
//import it.unipi.dii.lsmsdb.phoneworld.model.User;
//import org.springframework.stereotype.Component;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//
//@Component
//public class MockPhoneWorld {
//
//    public MockPhoneWorld() {
//    }
//
//
//    private IUserMongo userMongo;
//    private IPhoneMongo phoneMongo;
//    private IReviewMongo reviewMongo;
//
//    public void initMock() {
//
//    }
//
//    public void initUser() {
//        Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
//        User user = new User("Polly", "sadassa", "sadajsd5sds", "sdafdsfdsf4",
//                false, "male", "Paolo", "Bitta", "21",
//                "Via Roma", "Milano",
//                "Polonia", "ciao@gmail.com", dateOfBirth, 47);
//        UserMongo userMongo = new UserMongo();
//        userMongo.addUser(user);
//    }
//
//    public int getNumberOfItems(String collectionName) {
//        if (collectionName.equalsIgnoreCase("phones")) {
//            return phoneMongo.findAll().size();
//        } else if (collectionName.equalsIgnoreCase("reviews")) {
//            return reviewMongo.findAll().size();
//        } else if (collectionName.equalsIgnoreCase("users")) {
//            return userMongo.findAll().size();
//        } else {
//            return -1;
//        }
//    }
//
////    public Document createUser(String gender, String firstName, String lastName, String streetNumber,
////                          String streetName, String city, String country, String email,
////                          Date dateOfBirth, int age) {
////        return new Document("gender", gender)
////                .append("firstName", firstName)
////                .append("lastName", lastName)
////                .append("streetNumber", streetNumber)
////                .append("streetName", streetName)
////                .append("city", city)
////                .append("country", country)
////                .append("email", email)
////                .append("dateOfBirth", dateOfBirth)
////                .append("age", age)
////                .append("reviews", new ArrayList<>())
////                .append("phones", new ArrayList<>());
////    }
//
//}
