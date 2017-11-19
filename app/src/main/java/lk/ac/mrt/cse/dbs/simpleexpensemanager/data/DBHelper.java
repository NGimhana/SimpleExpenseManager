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
    private static final String DATABASE_NAME = "150105A.db";

    //Table Name
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_TRA = "transac";


    //Table Columns - Account
    private static final String ACCOUNT_NO = "accountNo";
    private static final String BANK_NAME = "bankName";
    private static final String ACCOUNT_HOLDER_NAME = "accountHolderName";
    private static final String BALANCE = "balance";


    //Table Columns - transac
    private static final String TRA_DATE = "tradate";
    private static final String TRA_ACC_NO = "traacc";
    private static final String TRA_EXP = "traexp";
    private static final String TRA_AMO = "traamo";





    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlAccountTable = "CREATE TABLE " + TABLE_ACCOUNT + " ( " + ACCOUNT_NO + " TEXT  , " + BANK_NAME + " TEXT ," + ACCOUNT_HOLDER_NAME + " TEXT ," + BALANCE + " DOUBLE " + " ) ";
        String sqlTraTable = "CREATE TABLE " + TABLE_TRA + " ( " + TRA_DATE + " TEXT, " + TRA_ACC_NO + " TEXT, " + TRA_EXP + " TEXT, " + TRA_AMO + " DOUBLE " + " ) ";
        sqLiteDatabase.execSQL(sqlAccountTable);
        sqLiteDatabase.execSQL(sqlTraTable);
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

    public boolean AddTransactionDetail(String traDate, String traAccNo, String expType, Double traAmount) {
        boolean result = false;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRA_DATE, traDate);
        contentValues.put(TRA_ACC_NO, traAccNo);
        contentValues.put(TRA_EXP, expType);
        contentValues.put(TRA_AMO, traAmount);
        long i = database.insert(TABLE_TRA, null, contentValues);
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
        String sqlTraA = "DROP TABLE IF EXISTS " + TABLE_TRA;
        sqLiteDatabase.execSQL(sqlAcc);
        sqLiteDatabase.execSQL(sqlTraA);
    }
}


