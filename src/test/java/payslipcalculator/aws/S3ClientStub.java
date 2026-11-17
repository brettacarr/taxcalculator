package payslipcalculator.aws;

import java.io.InputStream;

public class S3ClientStub implements S3Client {

  public S3ClientStub() {

  }

  @Override
  public InputStream getFileAsStream(String bucket, String fileName) {
      return ClassLoader.getSystemResourceAsStream(fileName);
  }
}
