package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by gimhana on 11/19/17.
 */

public class InPersistenceAccountDAO implements AccountDAO {

    private final Map<String, Account> accounts;
    private Context context;
    private DBHelper dbHelper;

    public InPersistenceAccountDAO(Context context) {
        this.context = context;
        accounts = new HashMap<>();
        dbHelper = new DBHelper(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor accountCursor = dbHelper.getAllDetails("account");
        accountCursor.moveToFirst();
        while (accountCursor.moveToNext()) {
            String accountNo = accountCursor.getString(0);
            String bankName = accountCursor.getString(1);
            String accountHolderName = accountCursor.getString(2);
            double balance = accountCursor.getDouble(3);
            Account tempAccount = new Account(accountNo, bankName, accountHolderName, balance);

            accounts.put(accountNo, tempAccount);
        }
        return new ArrayList<>(accounts.keySet());
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor accountCursor = dbHelper.getAllDetails("account");
        accountCursor.moveToFirst();
        while (accountCursor.moveToNext()) {
            String accountNo = accountCursor.getString(0);
            String bankName = accountCursor.getString(1);
            String accountHolderName = accountCursor.getString(2);
            double balance = accountCursor.getDouble(3);
            Account tempAccount = new Account(accountNo, bankName, accountHolderName, balance);

            accounts.put(accountNo, tempAccount);
        }
        return new ArrayList<>(accounts.values());
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        Account searchedAccount = dbHelper.searchAccount(accountNo);
        if (searchedAccount != null) {
            return searchedAccount;
        } else {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
    }

    @Override
    public void addAccount(Account account) {
        dbHelper.AddAccount(account.getAccountNo(), account.getBankName(), account.getAccountHolderName(), account.getBalance());
        Log.i("TAG", "Account Added");
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        boolean result = dbHelper.removeAccount(accountNo);
        if (result) {
            Log.i("TAG", "Account Removed!!");
        } else {
            Log.i("TAG", "Deletion Failed!!");
        }

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        boolean result = dbHelper.updateAccount(accountNo, expenseType, amount);
        if (result) {
            Log.i("TAG", "Account Updated!!");
        } else {
            Log.i("TAG", "Updation Failed!!");
        }
    }
}
