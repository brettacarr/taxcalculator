package payslipcalculator.repository;

import org.junit.jupiter.api.Test;
import payslipcalculator.payslip.IncomeTaxCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Checks the real 2018 tax rates held by the hardcoded adapter, expressed as monthly tax.
 */
class TaxTableHardcodedRepositoryTest {

  private IncomeTaxCalculator calculator = new IncomeTaxCalculator(new TaxTableHardcodedRepository());

  @Test
  public void shouldHaveZeroTaxBelowTaxFreeThreshold() {
    assertEquals(0, calculator.calculateTax(1000d));
  }

  @Test
  public void shouldApplySecondBracket() {
    assertEquals(29, calculator.calculateTax(20000d));
  }

  @Test
  public void shouldApplyThirdBracketWithAccumulatedTax() {
    assertEquals(325, calculator.calculateTax(38000));
  }

  @Test
  public void shouldMatchSampleEmployee() {
    assertEquals(922, calculator.calculateTax(60050));
  }

  @Test
  public void shouldApplyHighestBracket() {
    assertEquals(5269, calculator.calculateTax(200000));
  }

}
