package it_school.sumdu.edu.parkadmin.bind;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import it_school.sumdu.edu.parkadmin.model.Bind;
import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.others.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static it_school.sumdu.edu.parkadmin.stat.Constant.*;

public class BindImplementation implements ContactRequest.BindRequest {

    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void createBind(int ownerId, int carId, Response<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(OWNER_ID_FK, ownerId);
        contentValues.put(CAR_ID_FK, carId);

        try {
            long rowCount = sqLiteDatabase.insertOrThrow(TABLE_BIND, null, contentValues);

            if (rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("Car bind error");

        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void readAllBindByOwner(int ownerId, Response<List<Car>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String QUERY = "SELECT * FROM cars as d JOIN bind as l ON d._id = l.car_id " +
                "WHERE l.owner_id = " + ownerId;
        Cursor cursor = null;
        try {
            List<Car> carList = new ArrayList<>();
            cursor = sqLiteDatabase.rawQuery(QUERY, null);

            if(cursor.moveToFirst()){
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(CAR_ID));
                    @SuppressLint("Range") String carNumber = cursor.getString
                            (cursor.getColumnIndex(CAR_NUMBER));
                    @SuppressLint("Range") String carMark = cursor.getString
                            (cursor.getColumnIndex(CAR_MARK));
                    @SuppressLint("Range") String carModel = cursor.getString
                            (cursor.getColumnIndex(CAR_MODEL));

                    Car car = new Car(id, carNumber, carMark, carModel);
                    carList.add(car);

                } while (cursor.moveToNext());

                response.onSuccess(carList);
            } else
                response.onFailure("Bind car list is empty");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if (cursor!=null)
                cursor.close();
        }
    }

    @SuppressLint("Range")
    @Override
    public void readAllBind(int ownerId, Response<List<Bind>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String QUERY = "SELECT d._id, d.number, d.mark, d.model, l.owner_id " +
                "FROM cars as d LEFT JOIN bind as l ON d._id = l.car_id " +
                "AND l.owner_id = " + ownerId;

        Cursor cursor = null;
        try {
            List<Bind> bindList = new ArrayList<>();
            cursor = sqLiteDatabase.rawQuery(QUERY, null);

            if(cursor.moveToFirst()){
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(CAR_ID));
                    @SuppressLint("Range") String carNumber = cursor.getString(cursor.getColumnIndex(CAR_NUMBER));
                    @SuppressLint("Range") String carMark = cursor.getString(cursor.getColumnIndex(CAR_MARK));
                    @SuppressLint("Range") String carModel = cursor.getString(cursor.getColumnIndex(CAR_MODEL));

                    boolean isTaken = cursor.getInt(cursor.getColumnIndex(OWNER_ID_FK)) > 0;

                    Bind bind = new Bind(id, carNumber, carMark, carModel, isTaken);
                    bindList.add(bind);

                } while (cursor.moveToNext());

                response.onSuccess(bindList);
            } else
                response.onFailure("Bind list is empty");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if (cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void deleteBind(int ownerId, int carId, Response<Boolean> response) {

        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            long rowCount = sqLiteDatabase.delete(TABLE_BIND,
                    OWNER_ID_FK + " =? AND " + CAR_ID_FK + " =? ",
                    new String[]{String.valueOf(ownerId), String.valueOf(carId)});

            if (rowCount > 0)
                response.onSuccess(true);
            else
                response.onFailure("Delete error");

        } catch (Exception e) {
            response.onFailure(e.getMessage());
        }
    }
}
