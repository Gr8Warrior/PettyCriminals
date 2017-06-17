package com.pseudo.warriorz.pettycriminals.singleton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pseudo.warriorz.pettycriminals.model.Crime;
import com.pseudo.warriorz.pettycriminals.sqlite.CrimeBaseHelper;
import com.pseudo.warriorz.pettycriminals.sqlite.CrimeCursorWrapper;
import com.pseudo.warriorz.pettycriminals.sqlite.CrimeDbSchema;
import com.pseudo.warriorz.pettycriminals.sqlite.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Shailendra Suriyal
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();

        // getWritableDatabase : Create and/or open a database that will be used for reading and writing.
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        ;
    }

    public void addCrime(Crime c) {
        ContentValues contentValues = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, contentValues);

    }

    public List<Crime> getCrimes() {

        List crimes = new ArrayList();

        CrimeCursorWrapper crimeCursorWrapper = queryCrimes(null, null);

        try {
            crimeCursorWrapper.moveToFirst();

            while (!crimeCursorWrapper.isAfterLast()) {
                crimes.add(crimeCursorWrapper.getCrime());
                crimeCursorWrapper.moveToNext();
            }
        } finally {
            crimeCursorWrapper.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {

        CrimeCursorWrapper crimeCursorWrapper = queryCrimes(CrimeTable.Cols.UUID + " = ?", new String[]{
                id.toString()
        });
        try {
            if (crimeCursorWrapper.getCount() == 0) {
                return null;
            } else {
                crimeCursorWrapper.moveToFirst();
                return crimeCursorWrapper.getCrime();
            }
        } finally {
            crimeCursorWrapper.close();
        }
    }

    private static ContentValues getContentValues(Crime crime) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        contentValues.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return contentValues;
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{
                        uuidString
                });
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(CrimeTable.NAME,
                null,//Column names = null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null //orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }
}
