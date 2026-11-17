package payslipcalculator.payslip;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PayslipCalculator implements PayslipService {

  private final IncomeTaxCalculator taxCalculator;

  public PayslipCalculator(IncomeTaxCalculator taxCalculator) {
    this.taxCalculator = taxCalculator;
  }

  @Override
  public List<Payslip> calculatePayslips(List<Employee> employees) {
    List<Payslip> payslips = new ArrayList<>();
    for (Employee employee : employees) {
      payslips.add(calculatePayslip(employee));
    }
    return payslips;
  }

  private Payslip calculatePayslip(Employee employee) {
    int tax = taxCalculator.calculateTax(employee.getAnnualSalary());
    int grossIncome = BigDecimal.valueOf(employee.getAnnualSalary()).divide(BigDecimal.valueOf(12),0, RoundingMode.HALF_UP).intValue();
    int netIncome = grossIncome - tax;
    int superannuation = BigDecimal.valueOf(grossIncome * employee.getSuperPercentage()).divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP).intValue();

    return new Payslip(tax, grossIncome, netIncome, superannuation);
  }
}
