package payslipcalculator.payslip;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncomeTaxCalculatorTest {

  private IncomeTaxCalculator calculator = new IncomeTaxCalculator(new FakeTaxTableRepository());

  @Test
  public void shouldHaveZeroTaxForLowestBracket() {
    int tax = calculator.calculateTax(1000d);
    assertEquals(0, tax);
  }

  @Test
  public void shouldHaveTaxAmountWhenAboveThreshold() {
    // (22,000 - 10,000) * 10% = 1,200 per year -> 100 per month
    int tax = calculator.calculateTax(22000d);
    assertEquals(100, tax);
  }

  @Test
  public void shouldIncludeAccumulatedTax() {
    // (62,000 - 50,000) * 20% + 4,000 = 6,400 per year -> 533.33 per month
    int tax = calculator.calculateTax(62000d);
    assertEquals(533, tax);
  }

  @Test
  public void shouldThrowExceptionWhenInvalidIncome() {
    RuntimeException exception = assertThrows(RuntimeException.class, () -> calculator.calculateTax(-1));
    assertEquals("No tax bracket found. Invalid income. Income:-1.0", exception.getMessage());
  }

}
