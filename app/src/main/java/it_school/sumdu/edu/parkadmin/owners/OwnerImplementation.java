package it_school.sumdu.edu.parkadmin.owners;

import static it_school.sumdu.edu.parkadmin.stat.Constant.OWNER_EMAIL;
import static it_school.sumdu.edu.parkadmin.stat.Constant.OWNER_ID;
import static it_school.sumdu.edu.parkadmin.stat.Constant.OWNER_NAME;
import static it_school.sumdu.edu.parkadmin.stat.Constant.OWNER_PHONE;
import static it_school.sumdu.edu.parkadmin.stat.Constant.OWNER_STATUS;
import static it_school.sumdu.edu.parkadmin.stat.Constant.TABLE_OWNERS;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.others.DatabaseHelper;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.model.Owner;
import it_school.sumdu.edu.parkadmin.stat.Constant;

import java.util.ArrayList;
import java.util.List;


public class OwnerImplementation implements ContactRequest.OwnerRequest {

    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void createOwner(Owner owner, Response<Boolean> response) {

        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForOwner(owner);
            long id = sqLiteDatabase.insertOrThrow(TABLE_OWNERS, null, contentValues);
            if (id > 0) {
                response.onSuccess(true);
                owner.setId((int) id);
            } else
                response.onFailure("Failed to create owner. Unknown Reason!");
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void readOwner(int ownerId, Response<Owner> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_OWNERS, null,
                    Constant.OWNER_ID + " =? ", new String[]{String.valueOf(ownerId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()) {
                Owner owner = getOwnerFromCursor(cursor);
                response.onSuccess(owner);
            }
            else
                response.onFailure("Owner not found with this ID in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    public void readAllOwner(Response<List<Owner>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Owner> ownerList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_OWNERS, null, null, null,
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Owner owner = getOwnerFromCursor(cursor);
                    ownerList.add(owner);
                } while (cursor.moveToNext());

                response.onSuccess(ownerList);
            } else
                response.onFailure("Owner list is empty");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateOwner(Owner owner, Response<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = getContentValuesForOwner(owner);

        try {
            long rowCount = sqLiteDatabase.update(TABLE_OWNERS, contentValues,
                    OWNER_ID + " =? ", new String[]{String.valueOf(owner.getId())});
            if(rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("Update owner error");
        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void deleteOwner(int ownerId, Response<Boolean> response) {

        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            long rowCount = sqLiteDatabase.delete(TABLE_OWNERS, OWNER_ID + " =? ",
                    new String[]{String.valueOf(ownerId)});

            if (rowCount > 0)
                response.onSuccess(true);
            else
                response.onFailure("Delete owner error");
        } catch (Exception e) {
            response.onFailure(e.getMessage());
        }
    }

    private ContentValues getContentValuesForOwner(Owner owner){
        ContentValues contentValues = new ContentValues();

        contentValues.put(OWNER_NAME, owner.getName());
        contentValues.put(OWNER_STATUS, owner.getStatus());
        contentValues.put(OWNER_PHONE, owner.getPhone());
        contentValues.put(OWNER_EMAIL, owner.getEmail());

        return contentValues;
    }

    private Owner getOwnerFromCursor(Cursor cursor){
        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(OWNER_ID));
        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(OWNER_NAME));
        @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(OWNER_STATUS));
        @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(OWNER_PHONE));
        @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(OWNER_EMAIL));

        return new Owner(id, name, status, phone, email);
    }
}

