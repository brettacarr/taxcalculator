package payslipcalculator.repository;

import com.opencsv.CSVReader;
import payslipcalculator.aws.S3Client;
import payslipcalculator.payslip.TaxBracket;
import payslipcalculator.payslip.TaxTable;
import payslipcalculator.payslip.TaxTableRepository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TaxTableS3Repository implements TaxTableRepository {

  private S3Client s3Client;
  private String s3fileName;
  private String s3bucket;

  public TaxTableS3Repository(S3Client s3Client) {
    s3fileName = "tax_tables.csv";
    s3bucket = "bc-spike-2";
    this.s3Client = s3Client;
  }

  public TaxTableS3Repository(S3Client s3Client, String s3bucket, String s3fileName) {
    this.s3Client = s3Client;
    this.s3fileName = s3fileName;
    this.s3bucket = s3bucket;
  }

  public TaxTable getTaxTable(int year) {
    InputStream taxTableStream = s3Client.getFileAsStream(s3bucket, s3fileName);

    return loadTaxTableForYear(year, taxTableStream);
  }

  private TaxTable loadTaxTableForYear(int year, InputStream inputStream) {
    List<TaxBracket> taxBrackets = new ArrayList<>();
    CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
    String[] line = null;
    try {
      while ((line = csvReader.readNext()) != null) {
        int lineTaxYear = Integer.parseInt(line[0]);
        if (year == lineTaxYear) {
          taxBrackets.add(processLine(line));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Problem CSV line.",e);
    }

    return new TaxTable(taxBrackets);
  }

  private TaxBracket processLine(String[] line) {
    return new TaxBracket(
      Integer.parseInt(line[1]),
      Integer.parseInt(line[2]),
      Integer.parseInt(line[3]),
      Double.parseDouble(line[4]));
  }
}
