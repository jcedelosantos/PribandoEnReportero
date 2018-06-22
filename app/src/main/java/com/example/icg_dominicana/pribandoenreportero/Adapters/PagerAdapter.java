package com.example.icg_dominicana.pribandoenreportero.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.icg_dominicana.pribandoenreportero.Fragments.MyReport;
import com.example.icg_dominicana.pribandoenreportero.Fragments.Postear;

public class PagerAdapter  extends FragmentStatePagerAdapter{

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super( fm );
        this.numberOfTabs =  numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MyReport();
            case 1:
                return new Postear();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
