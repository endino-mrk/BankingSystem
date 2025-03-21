package main;

import account.Account;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import database.sqlite.DBConnection;
import database.sqlite.TransactionDBManager;
import launcher.AccountLauncher;
import launcher.BankLauncher;
import services.bank.BankCreationService;
import services.bank.BankDisplayerService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main
{

    private static final Scanner input = new Scanner(System.in);
    /**
     * Option field used when selection options during menu prompts. Do not create a different
     * option variable in menus. Just use this instead. <br>
     * As to how to utilize Field objects properly, refer to the following:
     * 
     * @see #prompt(String, boolean)
     * @see #setOption() How Field objects are used.
     */
    public static Field<Integer, Integer> option = new Field<Integer, Integer>("Option",
            Integer.class, -1, new Field.IntegerFieldValidator());

    public static void main(String[] args)
    {
        // Opens a connection to the database.
        try {
            new DBConnection();
            BankDBManager.createTable();
            AccountDBManager.createTable();
            TransactionDBManager.createTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        while (true)
        {
            showMenuHeader("Main Menu");
            showMenu(1);
            setOption();
            // Account Option
            if (getOption() == 1)
            {
                // READ ME: Refer to this code block on how one should properly utilize
                // showMenuHeader(), showMenu(),
                // setOption(), and getOption() methods...
                showMenuHeader("Account Login Menu");
                showMenu(2, 1);
                setOption();
                showMenu(getOption(), 1);

                AccountLauncher.accountLogin();
                if (AccountLauncher.getLogSession().isLoggedIn()) {
   //                  polymorphic call of init method to whatever type the logged account is;
                  AccountLauncher.getLogSession().getLoggedAccount().init();
                }
            }
            // Bank Option
            else if (getOption() == 2)
            {
                while(true) {
                    showMenuHeader("Bank Login Menu");
                    showMenu(3, 1); // Menu BankLogin: [1]Login [2] Go Back
                    setOption();

                    // Login
                    if (getOption() == 1) {
                        while (true) {
                            if (BankDBManager.getBanks() == null) {
                                System.out.println("\nThere no currently registered banks in the system!");
                                break;
                            }

                            // Displays all registered bank names
                            BankDisplayerService.showBanks();
                            BankLauncher.bankLogin();

                            if (!BankLauncher.getLogSession().isLogged()) {
                                break;
                            }

                            // initializing bankInit
                            BankLauncher.bankInit();
                            break;
                        }
                    }
                    // Go back
                    else if (getOption() == 2) {
                        break;
                    }
                    break;
                }
            }
            // Create New Bank
            else if (getOption() == 3)
            {
                BankCreationService.createNewBank();
            }
            else if (getOption() == 4)
            {
                System.out.println("Exiting. Thank you for banking!");
                break;
            }
            else
            {
                System.out.println("Invalid option!");
            }
        }
        DBConnection.closeConnection();
    }

    /**
     * Show menu based on index given. <br>
     * Refer to Menu enum for more info about menu indexes. <br>
     * Use this method if you want a single menu option every line.
     * 
     * @param menuIdx Main.Menu index to be shown
     */
    public static void showMenu(int menuIdx)
    {
        showMenu(menuIdx, 1);
    }

    /**
     * Show menu based on index given. <br>
     * Refer to Menu enum for more info about menu indexes.
     * 
     * @param menuIdx Main.Menu index to be shown
     * @param inlineTexts Number of menu options in a single line. Set to 1 if you only want a
     *        single menu option every line.
     * @see Menu
     */
    public static void showMenu(int menuIdx, int inlineTexts)
    {
        String[] menu = Menu.getMenuOptions(menuIdx);
        if (menu == null)
        {
            System.out.println("Invalid menu index given!");
        }
        else
        {
            String space = inlineTexts == 0 ? "" : "%-20s";
            String fmt = "[%d] " + space;
            int count = 0;
            for (String s : menu)
            {
                count++;
                System.out.printf(fmt, count, s);
                if (count % inlineTexts == 0)
                {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Prompt some input to the user. Only receives on non-space containing String. This string can
     * then be parsed into targeted data type using DataTypeWrapper.parse() method.
     * 
     * @param phrase Prompt to the user.
     * @param inlineInput A flag to ask if the input is just one entire String or receive an entire
     *        line input. <br>
     *        Set to <b>true</b> if receiving only one String input without spaces. <br>
     *        Set to <b>false</b> if receiving an entire line of String input.
     * @return Value of the user's input.
     * @see Field#setFieldValue(String, boolean) How prompt is utilized in Field.
     */
    public static String prompt(String phrase, boolean inlineInput)
    {
        System.out.print(phrase);
        if (inlineInput)
        {
            String val = input.next();
            input.nextLine();
            return val;
        }
        return input.nextLine();
    }

    /**
     * Prompts user to set an option based on menu outputted.
     * 
     * @throws NumberFormatException May happen if the user attempts to input something other than
     *         numbers.
     */
    public static void setOption() throws NumberFormatException
    {
        option.setFieldValue("\nSelect an option: ");
    }

    /**
     * @return Recently inputted option by the user.
     */
    public static int getOption()
    {
        return Main.option.getFieldValue();
    }

    /**
     * Used for printing the header whenever a new menu is accessed.
     * 
     * @param menuTitle Title of the menu to be outputted.
     */
    public static void showMenuHeader(String menuTitle)
    {
        System.out.printf("\n<---- %s ----->\n", menuTitle);
    }
}
