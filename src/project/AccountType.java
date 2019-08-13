package project;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bilaa
 */
public enum AccountType {

    CHEQUEINGS(1, "CHEQUEINGS"),
    SAVINGS(2, "SAVINGS");

    private int typeIndex;
    private String typeName;

    private Account account;

    private double monthlyInterest = 0;

    private AccountType(int typeIndex, String typeName) {

        if (typeName.equals("SAVINGS")) {
            monthlyInterest = account.getBalance() * .05;
        }

        this.typeIndex = typeIndex;
        this.typeName = typeName;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public String getTypeName() {
        return typeName;
    }

    private Map<Integer, Account> indexToAccount = new HashMap<>();
    private Map<String, Account> nameToAccount = new HashMap<>();

    public Account getAccount(int accountIndex) {
        return indexToAccount.get(accountIndex);
    }

    public Account getAccount(String accountName) {
        return nameToAccount.get(accountName);
    }

    private void initIndexMapping() {
        if (indexToAccount == null) {
            indexToAccount = new HashMap<>();
        }
    }

    private void initNameMapping() {
        if (nameToAccount == null) {
            nameToAccount = new HashMap<>();
        }
    }

    @Override
    public String toString() {
        return typeName;
    }

}
