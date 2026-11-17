package payslipcalculator.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import payslipcalculator.aws.S3ClientStub;
import payslipcalculator.payslip.TaxTableRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaxTableRepositoryFactoryTest {
  private static TaxTableRepositoryFactory factory;

  @BeforeAll
  public static void setup() {
    factory = new TaxTableRepositoryFactory(S3ClientStub::new);
  }

  @Test
  public void shouldReturnHardcodedRepo() {
    TaxTableRepository repository = factory.getRepository("hardcoded");
    assertTrue(repository instanceof TaxTableHardcodedRepository);
  }

  @Test
  public void shouldNotCreateS3ClientForHardcodedRepo() {
    TaxTableRepositoryFactory failingS3Factory = new TaxTableRepositoryFactory(() -> {
      throw new AssertionError("S3 client should not be created");
    });
    assertTrue(failingS3Factory.getRepository("hardcoded") instanceof TaxTableHardcodedRepository);
  }

  @Test
  public void shouldReturnS3Repo() {
    TaxTableRepository repository = factory.getRepository("s3");
    assertTrue(repository instanceof TaxTableS3Repository);
  }

  @Test
  public void shouldThrowExceptionWhenUnknowType() {
    assertThrows(RuntimeException.class, () -> factory.getRepository("sdjfhsdkf"));
  }

}
