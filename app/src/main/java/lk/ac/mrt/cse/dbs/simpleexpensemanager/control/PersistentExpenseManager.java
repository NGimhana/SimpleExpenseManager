package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by gimhana on 11/15/17.
 */

public class PersistentExpenseManager extends ExpenseManager {

    DBHelper db;
    private Context context;

    public PersistentExpenseManager(Context context) throws ExpenseManagerException {
        this.context = context;
        db = new DBHelper(context);
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {
/////////////////
        TransactionDAO inMemoryTransactionDAO = new InMemoryTransactionDAO();
        setTransactionsDAO(inMemoryTransactionDAO);

        AccountDAO inMemoryAccountDAO = new InMemoryAccountDAO();
        setAccountsDAO(inMemoryAccountDAO);

//////////////////////

        SQLiteDatabase database = db.getReadableDatabase();

        List<lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account> accountList = new ArrayList<>();

        Cursor res = db.getAllDetails("account");
        res.moveToFirst();

        while (res.moveToNext()) {

            String accountNo = res.getString(0);
            String bankName = res.getString(1);
            String accountHolderName = res.getString(2);
            Double balance = res.getDouble(3);

            Account account = new Account(accountNo, bankName, accountHolderName, balance);
            accountList.add(account);

            //Adding New Account to SQL database
            getAccountsDAO().addAccount(account);

        }
    }
}
