package helper;

import java.util.Random;

public class RandomString {

    static Random random = new Random();

    public static String name(){
        return "name" + random.nextInt(10000);
    }

    public static String company(){
        return "company" + random.nextInt(10000);
    }

    public static String phoneNumber(){
        return "6289192101" + random.nextInt(10000);
    }

    public static String email(){
        return "randomEmail" + random.nextInt(1000) + "@mail.com";
    }

    public static String website(){
        return "randomWebsite" + random.nextInt(1000) + ".com";
    }
}
