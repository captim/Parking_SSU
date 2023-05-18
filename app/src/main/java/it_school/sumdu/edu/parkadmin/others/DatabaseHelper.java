package it_school.sumdu.edu.parkadmin.others;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import it_school.sumdu.edu.parkadmin.stat.App;

import static it_school.sumdu.edu.parkadmin.stat.Constant.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "parkingadminDB";
    private static final int DATABASE_VERSION = 1;

    private static volatile DatabaseHelper databaseHelper;

    private DatabaseHelper() {
        super(App.context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance() {

        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class){
                if (databaseHelper == null)
                    databaseHelper = new DatabaseHelper();
            }
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_OWNER_TABLE = "CREATE TABLE " + TABLE_OWNERS + "("
                + OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OWNER_NAME + " TEXT NOT NULL, "
                + OWNER_STATUS + " TEXT NOT NULL, "
                + OWNER_PHONE + " TEXT, "
                + OWNER_EMAIL + " TEXT "
                + ")";

        String CREATE_CARS_TABLE = "CREATE TABLE " + TABLE_CARS + "("
                + CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CAR_NUMBER + " TEXT NOT NULL, "
                + CAR_MARK + " TEXT NOT NULL , "
                + CAR_MODEL + " TEXT NOT NULL"
                + ")";

        String CREATE_BIND_TABLE = "CREATE TABLE " + TABLE_BIND + "("
                + OWNER_ID_FK + " INTEGER NOT NULL, "
                + CAR_ID_FK + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + OWNER_ID_FK + ") REFERENCES " + TABLE_OWNERS + "(" + OWNER_ID + ") " +
                "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + CAR_ID_FK + ") REFERENCES " + TABLE_CARS + "(" + CAR_ID + ") " +
                "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + CONSTRAINT + " UNIQUE (" + OWNER_ID_FK + "," + CAR_ID_FK + ")"
                + ")";

        sqLiteDatabase.execSQL(CREATE_OWNER_TABLE);
        sqLiteDatabase.execSQL(CREATE_CARS_TABLE);
        sqLiteDatabase.execSQL(CREATE_BIND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BIND);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}
