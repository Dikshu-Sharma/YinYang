//package com.soulharmony.service;
//
//import android.util.Log;
//
//import com.soulharmony.R;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class S3Helper {
//
//
//    //    "AccessKey": "AKIA3NBQZVDLKGAP2OAV",
////            "SecretKey": "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",
////            "Region": "us-east-2"
//    private static final String TAG = "S3Helper";
//    private static final String BUCKET_NAME = "soulharmonyuserimages";
//    private static final String ACCESS_KEY = "AKIA3NBQZVDLKGAP2OAV";
//    private static final String SECRET_KEY = "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";
//
//
//    public static File downloadImage(String objectKey) {
//        File outputFile = null;
//
//        AwsBasicCredentials credentials = AwsBasicCredentials.create("accessKey", "secretKey");
//
//        S3Client s3Client = S3Client.builder()
//                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
//                    .region(Region.US_EAST_2) // Replace with your desired region
//                    .build();
//        try {
//            GetObjectRequest getObjectRequest =
//                    GetObjectRequest.builder().bucket(BUCKET_NAME).key(objectKey).build();
//
//            GetObjectResponse getObjectResponse = s3Client.getObject(getObjectRequest).response();
//
//        } catch (Exception exception){
//            System.out.println(exception);
//        }
//return null;
//
//    }
//}
