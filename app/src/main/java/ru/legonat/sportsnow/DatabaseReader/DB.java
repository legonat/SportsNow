package ru.legonat.sportsnow.DatabaseReader;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;


public class DB extends SQLiteOpenHelper {

    private static final String LOG_TAG = "";
    //The Android's default system path of your application database.
    private static String DB_PATH = "data/data/ru.legonat.sportsnow/databases/";
    private static String DB_NAME = "RFPL";
    private static String TABLE_LOCATION = "club";
    private static final int DB_VERSION = 7;


    private final Context context;
    private SQLiteDatabase db;


    // constructor
    public DB(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }


    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            this.getReadableDatabase();
            //do nothing - database already exist
        } else {

            // By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {


            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            // database don't exist yet.
            Log.d(LOG_TAG, "checked2: ");
            e.printStackTrace();

        }

        if (checkDB != null) {

            checkDB.close();

        }
//        else {
//            try {
//                copyDataBase();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return checkDB != null ? true : false;
    }

    // copy your assets db to the new system DB
    public void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    //Open the database
    public boolean open() {

        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;

        } catch (SQLException sqle) {
            db = null;
            return false;
        }

    }

    @Override
    public synchronized void close() {

        if (db != null)
            db.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // PUBLIC METHODS TO ACCESS DB CONTENT
    // -----------------------------------------------------------------------------------------------------------------


    // Get clubs
    public List<Club> getClubs() {
        try {
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Club> clubs = null;

        try {

            String query = "SELECT * FROM " + TABLE_LOCATION;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            // go over each row, build elements and add it to list
            clubs = new LinkedList<Club>();

            if (cursor.moveToFirst()) {
                do {

                    Club club = new Club();
                    club.id = Integer.parseInt(cursor.getString(0));
                    club.name = cursor.getString(1);
                    club.url = cursor.getString(2);

                    clubs.add(club);

                } while (cursor.moveToNext());
                //  cursor.close();
            }
        } catch (Exception e) {
            // sql error
        }


        return clubs;
    }
}