package payslipcalculator.payslip;

import java.util.Arrays;

/**
 * Test double for the TaxTableRepository port. Uses round, made-up brackets so
 * the domain can be tested without depending on any real adapter or tax year:
 *
 *        0 - 10,000   0%
 *   10,001 - 50,000  10%
 *   50,001 +         20%  plus 4,000 accumulated
 */
class FakeTaxTableRepository implements TaxTableRepository {

  @Override
  public TaxTable getTaxTable(int year) {
    return new TaxTable(Arrays.asList(
      new TaxBracket(0, 10000, 0, 0),
      new TaxBracket(10001, 50000, 0, 0.10),
      new TaxBracket(50001, Integer.MAX_VALUE, 4000, 0.20)));
  }
}
