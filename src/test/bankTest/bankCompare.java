package test.bankTest;

import bank.Bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class bankCompare {

    // BankCredentialsComparator if lessThan, equal, or greaterThan
    @Test
    public void if_lessThan() {
        Bank bank1 = new Bank(1, "BDO", "417244");
        Bank bank2 = new Bank(2, "Landbank", "84829");
        Bank.BankCredentialsComparator comparator = bank1.new BankCredentialsComparator();

        assertEquals(-1, comparator.compare(bank1, bank2));
    }

    @Test
    public void if_equal() {
        Bank bank1 = new Bank(5, "Metrobank", "09137");
        Bank bank2 = new Bank(5, "Metrobank", "09137");
        Bank.BankCredentialsComparator comparator = bank1.new BankCredentialsComparator();

        assertEquals(0, comparator.compare(bank1, bank2));
    }

    @Test
    public void if_greaterThan() {
        Bank bank1 = new Bank(3, "UnionBank", "74893");
        Bank bank2 = new Bank(2, "Chinabank", "15582");
        Bank.BankCredentialsComparator comparator = bank1.new BankCredentialsComparator();

        assertEquals(1, comparator.compare(bank1, bank2));
    }
}
