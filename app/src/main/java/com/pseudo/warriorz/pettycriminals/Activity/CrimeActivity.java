package com.pseudo.warriorz.pettycriminals.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.pseudo.warriorz.pettycriminals.fragment.CrimeFragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID =
            "com.pseudo.warriorz.pettycriminals.fragment.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

}