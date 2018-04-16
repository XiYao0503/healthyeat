package com.example.tanxueying.healthyeats;

/**
 * Created by cheng on 4/13/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
//import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
//import com.amazonaws.AmazonClientException;
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import java.util.List;

public class DetectLabelsExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String photo = "burger.jpg";
        String bucket = "healthyeats";

//        AWSCredentials credentials;
//        try {
//            credentials = new ProfileCredentialsProvider("AdminUser").getCredentials();
//        } catch(Exception e) {
//            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
//                    + "Please make sure that your credentials file is at the correct "
//                    + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
//        }
//
//        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
//                .standard()
//                .withRegion(Regions.US_WEST_2)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:75d2373d-07d5-47fc-a23e-1c7bde459498", // Identity pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonRekognition rekognitionClient = new AmazonRekognitionClient(credentialsProvider);

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo).withBucket(bucket)))
                .withMaxLabels(10)
                .withMinConfidence(75F);

        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List <Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photo);
            for (Label label: labels) {
                System.out.println(label.getName() + ": " + label.getConfidence().toString());
            }
        } catch(Exception e) {
            Log.e("AmazonRekognitionError:","Message:"+e.getMessage());
        }
    }
}