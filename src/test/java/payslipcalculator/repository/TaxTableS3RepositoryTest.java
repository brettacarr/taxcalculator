package payslipcalculator.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import payslipcalculator.aws.S3ClientStub;
import payslipcalculator.payslip.TaxTable;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxTableS3RepositoryTest {
  private static TaxTableS3Repository repo;
  private static TaxTable taxTable;

  @BeforeAll
  public static void setup() {
    repo = new TaxTableS3Repository(new S3ClientStub());
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
}