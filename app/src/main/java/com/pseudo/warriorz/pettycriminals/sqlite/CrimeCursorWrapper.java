package com.pseudo.warriorz.pettycriminals.sqlite;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.pseudo.warriorz.pettycriminals.model.Crime;
import com.pseudo.warriorz.pettycriminals.sqlite.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * @author Shailendra Suriyal
 */

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //getCrime from Db as Crime object
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        return crime;
    }
}
