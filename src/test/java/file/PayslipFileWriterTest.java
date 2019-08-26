package file;

import com.opencsv.CSVReader;
import domain.Payslip;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayslipFileWriterTest {

  private static PayslipFileWriter writer;
  private static List<Payslip> payslips = new ArrayList<>();
  private static Payslip payslip1 = new Payslip(1000, 10000, 9000, 500);;
  private static String fileName = "payslips.csv";

  @BeforeAll
  public static void setup() {
    writer = PayslipFileWriter.getInstance();
    payslips.add(payslip1);
  }

  @Test
  public void shouldWriteIncomeTax() {
    writer.writePayslips(fileName, payslips);
    List<Payslip> csvData = load(fileName);
    assertEquals(1000, csvData.get(0).getIncomeTax());
  }

  @Test
  public void shouldWriteGrossIncome() {
    writer.writePayslips(fileName, payslips);
    List<Payslip> csvData = load(fileName);
    assertEquals(10000, csvData.get(0).getGrossIncome());
  }

  @Test
  public void shouldWriteNetIncome() {
    writer.writePayslips(fileName, payslips);
    List<Payslip> csvData = load(fileName);
    assertEquals(9000, csvData.get(0).getNetIncome());
  }

  @Test
  public void shouldWriteSuperannuation() {
    writer.writePayslips(fileName, payslips);
    List<Payslip> csvData = load(fileName);
    assertEquals(500, csvData.get(0).getSuperannuation());
  }

  private List<Payslip> load(String fileName) {

    CSVReader csvReader = new CSVReader(getReader(fileName));
    List<Payslip> payslips = new ArrayList<>();
    String[] line = null;
    try {
      while ((line = csvReader.readNext()) != null) {
        payslips.add(new Payslip(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])));
      }
    } catch (Exception e) {
      throw new RuntimeException("Problem CSV line. Line:" + line);
    }

    return payslips;
  }

  private Reader getReader(String fileName) {
    File employeeFile = new File(fileName);
    try {
      return new FileReader(employeeFile);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Problem reading file. Check file path.");
    }
  }

}