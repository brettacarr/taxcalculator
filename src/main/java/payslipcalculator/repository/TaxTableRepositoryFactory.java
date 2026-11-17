package payslipcalculator.repository;

import payslipcalculator.aws.S3Client;
import payslipcalculator.payslip.TaxTableRepository;

import java.util.function.Supplier;

public class TaxTableRepositoryFactory {
  private final Supplier<S3Client> s3ClientSupplier;

  /**
   * The S3 client is supplied lazily so that non-S3 repositories don't need AWS credentials.
   */
  public TaxTableRepositoryFactory(Supplier<S3Client> s3ClientSupplier) {
    this.s3ClientSupplier = s3ClientSupplier;
  }

  public TaxTableRepository getRepository(String repoType) {
    if (repoType.equals("s3")) {
      return new TaxTableS3Repository(s3ClientSupplier.get());
    } else if (repoType.equals("hardcoded")) {
      return new TaxTableHardcodedRepository();
    }
    throw new RuntimeException("No implementation found for repoType:" + repoType);
  }
}
