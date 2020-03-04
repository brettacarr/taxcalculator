package payslipcalculator.aws;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class S3ClientDefaultTest {

  @Test
  public void shouldGetFileStream() {

    S3ClientDefault s3 = new S3ClientDefault();
    InputStream inputStream = s3.getFileAsStream("bc-spike-2", "tax_tables.csv");
    assertStreamContains(inputStream, "2018,0,18200,0,0");
  }

  private void assertStreamContains(InputStream inputStream, String expected) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    try {
      StringBuffer content = new StringBuffer();
      String line = null;
      while ((line = reader.readLine()) != null) {
        content.append(line);
      }
      assertTrue(content.toString().contains(expected), "Expected ["+content.toString()+"] to contain " + expected);
    } catch (IOException e)
    {
      fail("IOException thrown");
    }
  }
}