package com.opra.alumniportalmanagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTabs;


    public TabAdapter(Context context, @NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EditInfoFragment editInfoFragment = new EditInfoFragment();
                return editInfoFragment;
            case 1:
                SearchAlumniFragment searchAlumniFragment = new SearchAlumniFragment();
                return searchAlumniFragment;
            case 2:
                ReportFragment reportFragment = new ReportFragment();
                return reportFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
