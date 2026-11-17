package payslipcalculator.payslip;

import java.util.List;

/**
 * Primary (driving) port: the use case adapters call to turn employees into payslips.
 */
public interface PayslipService {
  List<Payslip> calculatePayslips(List<Employee> employees);
}
