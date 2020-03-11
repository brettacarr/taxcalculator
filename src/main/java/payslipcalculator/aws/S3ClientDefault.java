package payslipcalculator.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;

public class S3ClientDefault implements S3Client {

  private AmazonS3 amazonS3;

  public S3ClientDefault() {
    initS3();
  }

  private void initS3() {
    AWSCredentials credentials = new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY"), System.getenv("AWS_SECRET_KEY"));
    amazonS3 = AmazonS3ClientBuilder.standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withRegion(Regions.AP_SOUTHEAST_2)
      .build();
  }

  @Override
  public InputStream getFileAsStream(String bucket, String fileName) {
    S3Object s3object = amazonS3.getObject(bucket, fileName);
    return s3object.getObjectContent();
  }
}
