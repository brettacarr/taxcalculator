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

  public TaxTableS3Repository(S3Client s3Client) {

    this.s3Client = s3Client;
  }

  public TaxTable getTaxTable(int year) {
    InputStream taxTableStream = s3Client.getFileAsStream("bc-spike-2", "tax_tables.csv");

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
