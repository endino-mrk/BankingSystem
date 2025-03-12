package Test.bankTest;

import bank.Bank;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class bankCompare {

    // if lessThan, equal, or greaterThan
    @Test
    public void if_lessThan() {
        Bank bank1 = new Bank(1, "BDO", "417244");
        Bank bank2 = new Bank(2, "Landbank", "84829");
        bank.BankCredentialsComparator comparator = bank1.new BankCredentialsComparator();
        bank.BankComparator comparator1 = bank1.new BankComparator();
        bank.BankIDComparator comparator2 = bank1.new BankIDComparator();
        assertEquals(-1, comparator.compare(bank1, bank2));
        assertEquals(-1, comparator1.compare(bank1, bank2));
        assertEquals(-1, comparator2.compare(bank1, bank2));
    }

    @Test
    public void if_equal() {
        Bank bank1 = new Bank(5, "Metrobank", "09137");
        Bank bank2 = new Bank(5, "Metrobank", "09137");
        bank.BankCredentialsComparator comparator = bank1.new BankCredentialsComparator();
        bank.BankComparator comparator1 = bank1.new BankComparator();
        bank.BankIDComparator comparator2 = bank1.new BankIDComparator();

        assertEquals(0, comparator.compare(bank1, bank2));
        assertEquals(0, comparator1.compare(bank1, bank2));
        assertEquals(0, comparator2.compare(bank1, bank2));
    }

    @Test
    public void if_greaterThan() {
        Bank bank1 = new Bank(3, "UnionBank", "74893");
        Bank bank2 = new Bank(2, "Chinabank", "15582");
        bank.BankCredentialsComparator comparator = bank1.new BankCredentialsComparator();
        bank.BankComparator comparator1 = bank1.new BankComparator();
        bank.BankIDComparator comparator2 = bank1.new BankIDComparator();

        assertEquals(1, comparator.compare(bank1, bank2));
        assertEquals(1, comparator1.compare(bank1, bank2));
        assertEquals(1, comparator2.compare(bank1, bank2));
    }
}
