package com.pseudo.warriorz.pettycriminals.Activity;

import android.support.v4.app.Fragment;

import com.pseudo.warriorz.pettycriminals.fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}