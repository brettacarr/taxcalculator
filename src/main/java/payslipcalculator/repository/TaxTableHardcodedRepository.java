package payslipcalculator.repository;

import payslipcalculator.payslip.TaxTableRepository;
import payslipcalculator.payslip.TaxBracket;
import payslipcalculator.payslip.TaxTable;

import java.util.ArrayList;
import java.util.List;

public class TaxTableHardcodedRepository implements TaxTableRepository {
  public TaxTable getTaxTable(int year) {
    List<TaxBracket> taxBrackets = new ArrayList<>();
    taxBrackets.add(new TaxBracket(0,18200,0,0));
    taxBrackets.add(new TaxBracket(18201,37000,0,0.19));
    taxBrackets.add(new TaxBracket(37001,87000,3572,0.325));
    taxBrackets.add(new TaxBracket(87001,180000,19822,0.37));
    taxBrackets.add(new TaxBracket(180001 ,Integer.MAX_VALUE,54232,0.45));
    return new TaxTable(taxBrackets);
  }
}
