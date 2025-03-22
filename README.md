# BANKING SYSTEM ADVANCED 

## OVERVIEW

This project is a Java-based banking system implementing SOLID principles and utilizing SQLite as the database. It handles core banking functionalities, including:

- Management of Banks
- Management of Bank Accounts
- Transaction operations 
  - withdrawal, deposit, fund transfer for Savings Accounts
  - payment and recompense for Credit Accounts
- Persistent data storage through SQLite


## SOLID IMPLEMENTATION

### Single-Responsibility Principle

Changes to the original system design includes separation of responsibilities of model, service, controller, and database access classes:

#### Models
- Bank ➝ Represents bank entities with ID, name, and limits.
- Account ➝ Abstract base class representing shared account properties.
- SavingsAccount and CreditAccount ➝ Specialized account types, inheriting from Account.

#### Services

- WithdrawService, DepositService, TransferService, PaymentService, RecompenseService ➝ Handle specific transaction types, ensuring modular and reusable transaction logic.

- BankCreationService ➝ Handles the creation of new banks
- BankDisplayerService ➝ Displays existing banks, and available accounts in a specific bank
- BankLoginService ➝ Manages the log session of banks

- AccountCreationService ➝ Handles the creation of new accounts, including assigning them to banks.
- AccountLoginService ➝ Manages log session for individual account logins.
- BalanceManager ➝ Manages balance operations for SavingsAccount by performing checking if it has enough balance to perform transactions, and adjusting account balance. 
- LoanManager ➝ Manages loan operations for CreditAccount, by checking if an account can do credits as well as adjusting the account laon.
- IDGenerator ➝ Generates unique IDs for banks, accounts, and transactions to prevent duplication.

#### Controllers
- AccountLauncher
- BankLauncher
- CreditAccountLauncher
- SavingsAccountLauncher

#### Database access classes
- DBConnection ➝ Establishes and manages the SQLite database connection. Executes SQL queries using prepared statements.
- AccountDBManager ➝ Manages SQLite operations related to accounts, including creation, retrieval, and validation.
- BankDBManager ➝ Handles database operations for banks, including creation, retrieval, and listing of accounts.
- TransactionDBManager ➝ Manages transaction records in the database, including insertion and retrieval of transaction history.


### Interface Segregation Principle
The project adheres to ISP by defining specific, role-based interfaces that are implemented only by the relevant classes:

- BalanceHolder Interface ➝ Implemented by SavingsAccount to handle balance-related operations.

- LoanHolder Interface ➝ Implemented by CreditAccount to handle loan-specific operations.

#### For specific method descriptions, refer to the attached DocStrings.pdf file. 

## DATABASE IMPLEMENTATION

The project uses SQLite with the following schema:

- banks: Stores bank details (ID, name, passcode, limits, and processing fee).
- accounts: Stores account information (ID, bank ID, name, email, pin, and type).
- savings_accounts: Contains the balance of savings accounts.
- credit_accounts: Contains the loan amounts of credit accounts.
- transactions: Stores transaction records (account ID, type, description).

### banks 
```sql
CREATE TABLE IF NOT EXISTS banks (
    bank_id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    passcode TEXT NOT NULL,
    depositLimit REAL NOT NULL,
    creditLimit REAL NOT NULL,
    withdrawLimit REAL NOT NULL,
    processingFee REAL NOT NULL
);
```

### accounts
```sql
CREATE TABLE IF NOT EXISTS accounts (
    account_id TEXT PRIMARY KEY,
    bank_id TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL,
    pin TEXT NOT NULL,
    type INT NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES banks (bank_id)
);
```

### savings_accounts
```sql
CREATE TABLE IF NOT EXISTS savings_accounts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id TEXT NOT NULL UNIQUE,
    balance REAL NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);
```

### credit_accounts
```sql
CREATE TABLE IF NOT EXISTS credit_accounts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id TEXT NOT NULL UNIQUE,
    loan REAL NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);
```


### transactions
```sql
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id TEXT NOT NULL,
    type TEXT NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);
```


