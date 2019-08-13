package project;

import java.util.ArrayList;

/**
 *
 * @author bilaa
 */
public class AccountList {

    private ArrayList<Account> accountList = new ArrayList<>();

    public AccountList() {
    }

    public AccountList(Account... accountList) {

    }

    public Account get(int index) {
        return accountList.get(index);
    }

    public void add(Account account) {
        accountList.add(account);
    }

    public void insert(Account account, int index) {
        accountList.add(index, account);
    }

    public Account remove(int index) {
        return accountList.remove(index);
    }

    public void set(Account account, int index) {
        accountList.set(index, account);
    }

    public int indexOf(Account account) {
        return accountList.indexOf(account);
    }

    public int size() {
        return accountList.size();
    }

    public void clear() {
        accountList.clear();
    }

}
