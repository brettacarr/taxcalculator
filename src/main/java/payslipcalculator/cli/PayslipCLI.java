package payslipcalculator.cli;

import payslipcalculator.payslip.PayslipService;
import payslipcalculator.payslip.Employee;
import payslipcalculator.payslip.IncomeTaxCalculator;
import payslipcalculator.payslip.PayslipCalculator;
import payslipcalculator.payslip.Payslip;
import payslipcalculator.repository.TaxTableHardcodedRepository;

import java.util.List;

public class PayslipCLI {

    private static final EmployeeFileReader employeeFileReader = EmployeeFileReader.getInstance();
    private static final PayslipFileWriter payslipFileWriter = PayslipFileWriter.getInstance();
    private static IncomeTaxCalculator incomeTaxCalculator = new IncomeTaxCalculator(new TaxTableHardcodedRepository());
    private static PayslipService paySlipService = new PayslipCalculator(incomeTaxCalculator);

    public static void main(String... args) {

        validateInput(args);
        String inputFileName = args[0];
        String outputFileName = args[1];

        List<Employee> employees = employeeFileReader.load(inputFileName);
        List<Payslip> payslips = paySlipService.calculatePayslips(employees);
        payslipFileWriter.writePayslips(outputFileName,payslips);

    }

    private static void validateInput(String... args) {
        if (args.length < 2) {
            throw new RuntimeException("Input and output files must be defined.");
        }
    }
}
