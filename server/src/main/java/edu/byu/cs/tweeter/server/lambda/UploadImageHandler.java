package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.server.model.request.UploadRequest;
import edu.byu.cs.tweeter.server.model.response.UploadResponse;


/**
 * An AWS lambda function that returns the users a user is following.
 */
public class UploadImageHandler implements RequestHandler<UploadRequest, UploadResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public UploadResponse handleRequest(UploadRequest request, Context context) {

        try {
            byte[] imageBytes = Base64.getDecoder().decode(request.getByteArray().getBytes("UTF-8"));


            InputStream stream = new ByteArrayInputStream(imageBytes);

            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(imageBytes.length);
            meta.setContentType("image/png");

            AmazonS3Client s3Client = (AmazonS3Client)AmazonS3ClientBuilder.defaultClient();
            s3Client.putObject(new PutObjectRequest("tweeter-dillonkh", request.getFileName(), stream, meta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            stream.close();

            return new UploadResponse(true);

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            return new UploadResponse(false);
        }

    }


}
