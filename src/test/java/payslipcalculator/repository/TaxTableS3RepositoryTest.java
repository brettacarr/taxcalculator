package payslipcalculator.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import payslipcalculator.aws.S3ClientStub;
import payslipcalculator.payslip.TaxTable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaxTableS3RepositoryTest {
  private static TaxTableS3Repository repo;
  private static TaxTable taxTable;
  private static TaxTableS3Repository corruptRepo;

  @BeforeAll
  public static void setup() {
    repo = new TaxTableS3Repository(new S3ClientStub());
    corruptRepo = new TaxTableS3Repository(new S3ClientStub(), "", "employees_invalid_number.csv");
    taxTable = repo.getTaxTable(2018);
  }

  @Test
  public void shouldLoadLowerBounds() {
    assertEquals(taxTable.getTaxBracket(40000).getLowerBounds(),37001);
  }

  @Test
  public void shouldLoadUpperBounds() {
    assertEquals(taxTable.getTaxBracket(40000).getUpperBounds(),87000);
  }

  @Test
  public void shouldLoadTaxAmountPercent() {
    assertEquals(taxTable.getTaxBracket(40000).getTaxAmountPercent(),0.325);
  }

  @Test
  public void shouldLoadAccumulatedTax() {
    assertEquals(taxTable.getTaxBracket(40000).getAccumulatedTax(),3572);
  }

  @Test
  public void shouldThrowExceptionWhenCorruptFile() {
    assertThrows(RuntimeException.class, () -> corruptRepo.getTaxTable(2008));
  }
}