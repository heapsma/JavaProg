package project;

/**
 *
 * @author bilaa
 */
public class Account {

    private String name;
    private int Id;
    private String address;
    private String pin;
    private AccountType accountType;
    private double balance;

    public Account(String name, int Id, String address, String pin, AccountType accountType) {
        this.name = name;
        this.Id = Id;
        this.address = address;
        this.pin = pin;
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        return name + address + Id + accountType;
    }

    public boolean isValidName(String name) {
        String validName = "^[a-zA-Z]+$";
        if (name.matches(validName)) {
            return true;
        }
        return false;
    }

    public boolean isAddressValid(String address) {

        String validAddress = "^[0-9]*\\s[a-zA-Z]*[A-Z][0-9][A-Z]\\s[0-9][A-Z][0-9]";
        //String validAddress = "^(?!.[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
        if (address.matches(validAddress)) {
            return true;
        }
        return false;
    }
}
