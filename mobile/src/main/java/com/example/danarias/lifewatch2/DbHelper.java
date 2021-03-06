package com.example.danarias.lifewatch2;

/**
 * Created by danarias on 15-02-22.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import java.sql.SQLException;


public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "lifewatch.db";
    private static final int DATABASE_VERSION = 30;
    public static final String CONTACTS_TABLE_NAME = "Contacts";
    public static final String MEDICATION_TABLE_NAME = "Medication";

    public static final String USER_ID = "_id";
    public static final String NAME_CONTACT = "name";
    public static final String PHONE_CONTACT = "phone";

    public static final String IF_EMERG_CONTACT = "ifEmergContact";
    public static final String TEXT_MESSAGE = "message";

    public static final String MED_ID = "med_id";
    public static final String NAME_MED = "medname";
    public static final String QUANT_MED = "medquantity";
    public static final String NOTES_MED = "mednotes";
    public static final String INTERVAL_NUM = "remnumber";
    public static final String INTERVAL = "interval";



    public static final String[] ALL_KEYS = new String[] {USER_ID, NAME_CONTACT, PHONE_CONTACT, IF_EMERG_CONTACT, TEXT_MESSAGE};

    private static final String LIFEWATCH_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + CONTACTS_TABLE_NAME + "(" +USER_ID
                    +" INTEGER PRIMARY KEY, "+
                    NAME_CONTACT + " TEXT NOT NULL, "+PHONE_CONTACT+ " TEXT NOT NULL, "+ IF_EMERG_CONTACT+" TEXT, "+ TEXT_MESSAGE+" TEXT DEFAULT \"** LifeWatch Automated Message ** Help! I have fallen and can't get up.\");";

    private static final String MEDICATION_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + MEDICATION_TABLE_NAME + "(" +MED_ID
                    +" INTEGER PRIMARY KEY, "+
                    NAME_MED + " TEXT NOT NULL, "+QUANT_MED+ " TEXT NOT NULL, "+NOTES_MED+ " TEXT, "+INTERVAL_NUM+ " TEXT NOT NULL, "+INTERVAL+ " TEXT NOT NULL);";

    private SQLiteDatabase db;

    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("In constructor");

    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            //Create Database
            db.execSQL(LIFEWATCH_TABLE_CREATE);

            db.execSQL(MEDICATION_TABLE_CREATE);


            System.out.println("In onCreate");
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public List<String> getAllContacts() {
        List<String> contacts = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                contacts.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return contacts;
    }



        public List<String> getAllNumbers(){
            List<String> recipients = new ArrayList<String>();

            String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME+ " WHERE ifEmergContact = ?" ;

            SQLiteDatabase db = this.getReadableDatabase();


            Cursor cursor = db.rawQuery(selectQuery, new String[] {"yes"});




            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    recipients.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }

            // closing connection
            cursor.close();
            db.close();


        return recipients;
    }

    public List<String> getAllMedication(){
        List<String> medication = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + MEDICATION_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                medication.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        return medication;
    }

    public String getName(Integer contact_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT};
        String clause = USER_ID +" = '"+contact_id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iname)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getPhone(Integer contact_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT};
        String clause = USER_ID +" = '"+contact_id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iphone)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }



    public String getIfEmergContact(Integer contact_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT, IF_EMERG_CONTACT};
        String clause = USER_ID +" = '"+contact_id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="no";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);

        int iEmergContact = c.getColumnIndex(IF_EMERG_CONTACT);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iEmergContact)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getTextMessage(Integer id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT, IF_EMERG_CONTACT, TEXT_MESSAGE};
        String clause = USER_ID +" = '"+id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="** LifeWatch Automated Message ** Help! I have fallen and can't get up.";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);

        int iEmergContact = c.getColumnIndex(IF_EMERG_CONTACT);
        int iTextMessage = c.getColumnIndex(TEXT_MESSAGE);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iTextMessage)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getRecipeintTextMessage(String phoneNum){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT, IF_EMERG_CONTACT, TEXT_MESSAGE};
        String clause = PHONE_CONTACT +" = '"+ phoneNum+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="** LifeWatch Automated Message ** Help! I have fallen and can't get up.";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);
        int iEmergContact = c.getColumnIndex(IF_EMERG_CONTACT);
        int iTextMessage = c.getColumnIndex(TEXT_MESSAGE);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iTextMessage)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getMedName(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM,INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int imedname = c.getColumnIndex(NAME_MED);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(imedname)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getMedQuantity(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM, INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int imedquant = c.getColumnIndex(QUANT_MED);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(imedquant)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getMedNotes(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM, INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int imednotes = c.getColumnIndex(NOTES_MED);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(imednotes)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getIntervalNum(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM,INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int intervalweek = c.getColumnIndex(INTERVAL_NUM);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(intervalweek)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getInterval(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM, INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int intervalday = c.getColumnIndex(INTERVAL);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(intervalday)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }




    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(CONTACTS_TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(DbHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+ CONTACTS_TABLE_NAME);
        onCreate(db);

        Log.w(DbHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+ MEDICATION_TABLE_NAME);
        onCreate(db);


    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;

        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }



}
