package payslipcalculator.aws;

import java.io.InputStream;

public interface S3Client {
  InputStream getFileAsStream(String bucket, String fileName);
}
