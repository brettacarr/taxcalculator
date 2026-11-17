package payslipcalculator.payslip;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import payslipcalculator.repository.TaxTableHardcodedRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayslipCalculatorTest {

  private static PayslipCalculator paySlipService = new PayslipCalculator(new IncomeTaxCalculator(new TaxTableHardcodedRepository()));
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
    assertEquals(5004, payslips.get(0).getGrossIncome());
  }

  @Test
  public void shouldHavePayslipWithTax() {
    assertEquals(922, payslips.get(0).getIncomeTax());
  }

  @Test
  public void shouldHavePayslipWithNetIncome() {
    assertEquals(4082, payslips.get(0).getNetIncome());
  }

  @Test
  public void shouldHavePayslipWithSuper() {
    assertEquals(450, payslips.get(0).getSuperannuation());
  }

}