package ourtest;
import accounts.Account;
import accounts.CreditAccount;
import accounts.IllegalAccountType;
import accounts.SavingsAccount;
import bank.Bank;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    Bank b = new Bank(123, "MyBank", "123mybank");
    SavingsAccount acc1 = new SavingsAccount(b, "01234565789", "Michaela", "Endino", "mrk@mail.com", "mrk@mail.com", 500);

    @Test
    public void TestDeposit() {
        acc1.cashDeposit(1000);
        assertEquals(1500, acc1.getBalance(),0.0);
    }

    @Test
    public void TestWithdraw() {
        acc1.withdrawal(1000);
        assertEquals(500, acc1.getBalance(),0.0);
        acc1.withdrawal(500);
        assertEquals(0, acc1.getBalance(),0.0);
    }

    Bank b2 = new Bank(456, "MyBank2", "456mybank2");
    SavingsAccount acc2 = new SavingsAccount(b2, "1234567890", "RJ Kate", "Endino", "rjk@mail.com", "654321", 500);

    SavingsAccount acc3 = new SavingsAccount(b, "1231231231", "Brian", "Eno", "beno@mail.com", "000111", 100);

    @Test
    public void TestTransfer1() throws IllegalAccountType {
        b.addNewAccount(acc1);
        b2.addNewAccount(acc2);
        acc1.transfer(b2, acc2, 250);
        assertEquals(240, acc1.getBalance(), 0.0);
        assertEquals(750, acc2.getBalance(), 0.0);
    }

    @Test
    public void TestTransfer2() throws IllegalAccountType{
        b.addNewAccount(acc1);
        b.addNewAccount(acc3);
        acc1.transfer(acc3, 300);
        assertEquals(200, acc1.getBalance(), 0.0);
    }

    CreditAccount c1 = new CreditAccount(b, "2345678901", "Lin-Manuel", "Miranda", "lmm@mail.com", "555444");
    CreditAccount c2 = new CreditAccount(b, "3456789012", "Marvin", "Gaye", "mg@mail.com", "444777");

    @Test
    public void TestPay() throws IllegalAccountType{
        b.addNewAccount(acc1);
        b.addNewAccount(c1);

        c1.pay(acc1, 1000);
        assertEquals(1500, acc1.getBalance(), 0.0);
        assertEquals(1000, c1.getLoan(), 0.0);
    }

    @Test
    public void TestRecompense() throws IllegalAccountType{
        c1.pay(acc1, 1000);
        c1.recompense(500);
        assertEquals(500, c1.getLoan(), 0.0);
    }
}
