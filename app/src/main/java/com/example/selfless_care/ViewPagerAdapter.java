package com.example.selfless_care;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new Chats();
            case 1: return new Blood_Centre();
            case 2: return new Req_Services();
            case 3: return new Mental_Care();
        }
        return null;
        }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: return "Doctor Chats";
            case 1: return "Blood Centre";
            case 2: return "Req Services";
            case 3: return "Mental Care";
        }
        return null;
    }
}

