package payslipcalculator.cli;

import payslipcalculator.aws.S3ClientDefault;
import payslipcalculator.payslip.Employee;
import payslipcalculator.payslip.IncomeTaxCalculator;
import payslipcalculator.payslip.Payslip;
import payslipcalculator.payslip.PayslipCalculator;
import payslipcalculator.payslip.PayslipService;
import payslipcalculator.payslip.TaxTableRepository;
import payslipcalculator.repository.TaxTableRepositoryFactory;

import java.util.List;

public class PayslipCLI {

    private static final EmployeeFileReader employeeFileReader = EmployeeFileReader.getInstance();
    private static final PayslipFileWriter payslipFileWriter = PayslipFileWriter.getInstance();
    private static IncomeTaxCalculator incomeTaxCalculator;
    private static PayslipService paySlipService;

    public static void main(String... args) {

        validateInput(args);
        String inputFileName = args[0];
        String outputFileName = args[1];
        String taxTableLocation = args.length > 2 ? args[2] : "hardcoded";

        init(taxTableLocation);

        List<Employee> employees = employeeFileReader.load(inputFileName);
        List<Payslip> payslips = paySlipService.calculatePayslips(employees);
        payslipFileWriter.writePayslips(outputFileName,payslips);
    }

    private static void init(String taxTableLocation) {
        TaxTableRepository taxTableRepository = new TaxTableRepositoryFactory(S3ClientDefault::new).getRepository(taxTableLocation);
        incomeTaxCalculator = new IncomeTaxCalculator(taxTableRepository);
        paySlipService = new PayslipCalculator(incomeTaxCalculator);
    }

    private static void validateInput(String... args) {
        if (args.length < 2) {
            throw new RuntimeException("Input and output files must be defined.");
        }
    }
}
