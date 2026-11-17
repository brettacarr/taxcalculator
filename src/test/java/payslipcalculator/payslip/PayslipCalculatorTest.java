package payslipcalculator.payslip;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayslipCalculatorTest {

  private static PayslipService paySlipService = new PayslipCalculator(new IncomeTaxCalculator(new FakeTaxTableRepository()));
  private static List<Payslip> payslips  =  null;


  @BeforeAll
  public static void setup() {
    Employee employee = new Employee("David", "Rudd", 60050, 9, "01 March - 31 March");
    List<Employee> employees = new ArrayList<>();
    employees.add(employee);
    payslips = paySlipService.calculatePayslips(employees);
  }

  @Test
  public void shouldHavePayslipWithGrossIncome() {
    // 60,050 / 12 = 5,004.17
    assertEquals(5004, payslips.get(0).getGrossIncome());
  }

  @Test
  public void shouldHavePayslipWithTax() {
    // (60,050 - 50,000) * 20% + 4,000 = 6,010 per year -> 500.83 per month
    assertEquals(501, payslips.get(0).getIncomeTax());
  }

  @Test
  public void shouldHavePayslipWithNetIncome() {
    assertEquals(4503, payslips.get(0).getNetIncome());
  }

  @Test
  public void shouldHavePayslipWithSuper() {
    // 5,004 * 9% = 450.36
    assertEquals(450, payslips.get(0).getSuperannuation());
  }

}
