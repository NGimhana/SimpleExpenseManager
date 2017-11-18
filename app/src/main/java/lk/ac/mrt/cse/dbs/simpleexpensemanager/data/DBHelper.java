package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gimhana on 11/15/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    //Database Name
    private static final String DATABASE_NAME = "MyDatabase.db";

    //Table Name
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_TRANSACTION = "transaction";

    //Table Columns - Account
    private static final String ACCOUNT_NO = "accountNo";
    private static final String BANK_NAME = "bankName";
    private static final String ACCOUNT_HOLDER_NAME = "accountHolderName";
    private static final String BALANCE = "balance";

    //Table Columns - Transaction
    private static final String ACCOUNT_NO_TRA = "accountNoTra";
    private static final String DATE = "date";
    private static final String EXPENSE_TYPE = "expenseType";
    private static final String AMOUNT = "amount";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlAccountTable = "CREATE TABLE " + TABLE_ACCOUNT
                + " ( "
                + ACCOUNT_NO + " TEXT  , "
                + BANK_NAME + " TEXT ,"
                + ACCOUNT_HOLDER_NAME + " TEXT ,"
                + BALANCE + " DOUBLE "
                + " ) ";

//        String sqlTransactionTable  = "CREATE TABLE "+TABLE_TRANSACTION
//                +" ( "
//                + DATE + " TEXT , "
//                + ACCOUNT_NO_TRA + " TEXT ,"
//                + AMOUNT +" DOUBLE ,"
//                + EXPENSE_TYPE + " TEXT "
//                +" ) ";
        String sqlTransactionTable = "CREATE TABLE transaction (date Text,accountNoTra TEXT,amount DOUBLE,expenseType TEXT)";

        sqLiteDatabase.execSQL(sqlAccountTable);
        sqLiteDatabase.execSQL(sqlTransactionTable);
    }

    public boolean AddAccount(String account_NO, String bank_Name, String account_Holder_Name, Double balance) {
        boolean result = false;

        ContentValues contentValues = new ContentValues();

        SQLiteDatabase database = this.getWritableDatabase();

        contentValues.put(ACCOUNT_NO, account_NO);
        contentValues.put(BANK_NAME, bank_Name);
        contentValues.put(ACCOUNT_HOLDER_NAME, account_Holder_Name);

        contentValues.put(BALANCE, balance);
        long i = database.insert(TABLE_ACCOUNT, null, contentValues);
        result = i != -1;
        return result;
    }

    public boolean AddTransaction(String date, String accountNoTra, double amount, String expenseType) {
        boolean result = false;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DATE, date);
        contentValues.put(ACCOUNT_NO_TRA, accountNoTra);
        contentValues.put(AMOUNT, amount);
        contentValues.put(EXPENSE_TYPE, expenseType);

        long i = database.insert(TABLE_TRANSACTION, null, contentValues);

        result = i != -1;
        return result;
    }

    public Cursor getAllDetails(String table_Name) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor result = database.rawQuery("SELECT * FROM " + table_Name, null);
        return result;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlAcc = "DROP TABLE IF EXISTS " + TABLE_ACCOUNT;
        String sqlTra = "DROP TABLE IF EXISTS " + TABLE_TRANSACTION;
        sqLiteDatabase.execSQL(sqlAcc);
        sqLiteDatabase.execSQL(sqlTra);
    }
}


