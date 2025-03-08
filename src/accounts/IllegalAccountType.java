package accounts;

/**
 * This error is thrown when some bank account process is handled
 * ibetween two incompatible bank account types.
 */
public class IllegalAccountType extends Exception {

    public IllegalAccountType(String errorMessage) {
        super(errorMessage);
    }
}