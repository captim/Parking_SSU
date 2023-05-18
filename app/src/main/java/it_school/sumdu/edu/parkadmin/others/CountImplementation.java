package it_school.sumdu.edu.parkadmin.others;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import it_school.sumdu.edu.parkadmin.model.Count;

import it_school.sumdu.edu.parkadmin.stat.Constant;

public class CountImplementation implements ContactRequest.CountRequest {

    @Override
    public void getCount(Response<Count> response) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase()) {
            long ownerCount = DatabaseUtils.queryNumEntries(sqLiteDatabase, Constant.TABLE_OWNERS);
            long carCount = DatabaseUtils.queryNumEntries(sqLiteDatabase, Constant.TABLE_CARS);
            long bindCount = DatabaseUtils.queryNumEntries(sqLiteDatabase, Constant.TABLE_BIND);

            Count count = new Count(ownerCount, carCount, bindCount);
            response.onSuccess(count);

        } catch (Exception e) {
            response.onFailure(e.getMessage());
        }

    }
}
