package payslipcalculator.repository;

import payslipcalculator.aws.S3Client;
import payslipcalculator.payslip.TaxTableRepository;

public class TaxTableRepositoryFactory {
  private S3Client s3Client;

  public TaxTableRepositoryFactory(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public TaxTableRepository getRepository(String repoType) {
    if (repoType.equals("s3")) {
      return new TaxTableS3Repository(s3Client);
    } else if (repoType.equals("hardcoded")) {
      return new TaxTableHardcodedRepository();
    }
    throw new RuntimeException("No implementation found for repoType:" + repoType);
  }
}
