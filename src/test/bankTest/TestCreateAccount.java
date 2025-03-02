package Test.bankTest;
import Bank.Bank;
//import Main.Main;

import Accounts.SavingsAccount;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Scanner;

public class TestCreateAccount {
//    Scanner input = new Scanner(System.in);
    Bank b1 = new Bank(123, "BDO", "123BDO");
    @Test
    public void createSavingsAcc() {
        SavingsAccount s1 = b1.createNewSavingsAccount();

        assertEquals("BDO", s1.getBank().getName());
        assertEquals("012345678912", s1.getAccountNumber());
        assertEquals("John", s1.getOwnerFName());
        assertEquals("Doe", s1.getOwnerLName());
        assertEquals("johndoe@mail.com", s1.getOwnerEmail());
        assertEquals("123456", s1.getPin());
        assertEquals(500, s1.getBalance());
    }

}
