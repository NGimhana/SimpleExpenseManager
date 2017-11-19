package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InPersistenceAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InPersistenceTransactionDAO;
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

        TransactionDAO inPersistenceTransactionDAO = new InPersistenceTransactionDAO(context);
        setTransactionsDAO(inPersistenceTransactionDAO);


        AccountDAO inPersistenceAccountDAO = new InPersistenceAccountDAO(context);
        setAccountsDAO(inPersistenceAccountDAO);


        //Get Account No  From Database
        List<Account> accountsList = inPersistenceAccountDAO.getAccountsList();
        for (Account account : accountsList) {
            getAccountsDAO().addAccount(account);
        }

        //Get Transaction History From Database
        inPersistenceTransactionDAO.getAllTransactionLogs();


    }
}
