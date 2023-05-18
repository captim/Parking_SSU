package it_school.sumdu.edu.parkadmin.cars;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.others.DatabaseHelper;
import it_school.sumdu.edu.parkadmin.others.Response;

import java.util.ArrayList;
import java.util.List;

import static it_school.sumdu.edu.parkadmin.stat.Constant.CAR_ID;
import static it_school.sumdu.edu.parkadmin.stat.Constant.CAR_NUMBER;
import static it_school.sumdu.edu.parkadmin.stat.Constant.CAR_MARK;
import static it_school.sumdu.edu.parkadmin.stat.Constant.CAR_MODEL;
import static it_school.sumdu.edu.parkadmin.stat.Constant.TABLE_CARS;

public class CarImplementation implements ContactRequest.CarRequest {

    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void createCar(Car car, Response<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAR_NUMBER, car.getNumber());
        contentValues.put(CAR_MARK, car.getMark());
        contentValues.put(CAR_MODEL, car.getModel());

        try {
            long id = sqLiteDatabase.insertOrThrow(TABLE_CARS, null, contentValues);
            if(id>0) {
                response.onSuccess(true);
            }
            else
                response.onFailure("Create car error");
        } catch (SQLiteException e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void readAllCar(Response<List<Car>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Car> carList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_CARS, null, null, null, null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Car car = getCarFromCursor(cursor);
                    carList.add(car);
                } while (cursor.moveToNext());

                response.onSuccess(carList);
            } else
                response.onFailure("Car database is empty");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if (cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateCar(Car car, Response<Boolean> response) {

        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesFromCar(car);
            long rowCount = sqLiteDatabase.update(TABLE_CARS, contentValues,
                    CAR_ID + " =? ", new String[]{String.valueOf(car.getId())});

            if (rowCount > 0)
                response.onSuccess(true);
            else
                response.onFailure("Update car error");

        } catch (Exception e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void deleteCar(int carId, Response<Boolean> response) {

        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            long rowCount = sqLiteDatabase.delete(TABLE_CARS,
                    CAR_ID + " =? ", new String[]{String.valueOf(carId)});

            if (rowCount > 0)
                response.onSuccess(true);
            else
                response.onFailure("Delete car list is empty");

        } catch (Exception e) {
            response.onFailure(e.getMessage());
        }
    }

    private Car getCarFromCursor(Cursor cursor) {
        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(CAR_ID));
        @SuppressLint("Range") String carNumber = cursor.getString(cursor.getColumnIndex(CAR_NUMBER));
        @SuppressLint("Range") String carMark = cursor.getString(cursor.getColumnIndex(CAR_MARK));
        @SuppressLint("Range") String carModel = cursor.getString(cursor.getColumnIndex(CAR_MODEL));

        return new Car(id, carNumber, carMark, carModel);
    }

    private ContentValues getContentValuesFromCar(Car car) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CAR_NUMBER, car.getNumber());
        contentValues.put(CAR_MARK, car.getMark());
        contentValues.put(CAR_MODEL, car.getModel());

        return contentValues;
    }
}
