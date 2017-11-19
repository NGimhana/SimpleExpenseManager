package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by gimhana on 11/19/17.
 */

public class InPersistenceTransactionDAO implements TransactionDAO {

    private final List<Transaction> transactions;
    private Context context;
    private DBHelper dbHelper;

    public InPersistenceTransactionDAO(Context context) {
        this.context = context;
        this.transactions = new ArrayList<>();
        dbHelper = new DBHelper(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String traDate = sdf.format(date);
        dbHelper.AddTransactionDetail(traDate, accountNo, expenseType.toString(), amount);
        Log.i("TAG", "Transaction Added");
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor transacCursor = dbHelper.getAllDetails("transac");
        transacCursor.moveToFirst();

        while (transacCursor.moveToNext()) {

            String traDate = transacCursor.getString(0);
            String traAccNo = transacCursor.getString(1);
            String expenseType = transacCursor.getString(2);
            double amo = transacCursor.getDouble(3);

            Date date = null;
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            try {
                date = df.parse(traDate);
            } catch (ParseException e) {
                Log.i("TAG", "Time Error");
            }

            Transaction transaction = new Transaction(date, traAccNo, ExpenseType.valueOf(expenseType.toUpperCase()), amo);
            transactions.add(transaction);

        }
        Log.i("TAG", "Transaction");
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }
}
