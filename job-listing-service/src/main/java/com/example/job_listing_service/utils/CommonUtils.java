package com.example.job_listing_service.utils;

import java.util.Random;

public class CommonUtils {
    public static int generateRandom() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
