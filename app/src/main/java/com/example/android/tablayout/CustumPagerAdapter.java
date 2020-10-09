package com.example.android.tablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.io.File;

public class CustumPagerAdapter extends FragmentStatePagerAdapter {

    int tabs;
    File file_image,file_text;

    public CustumPagerAdapter(@NonNull FragmentManager fm, int tabs, File file_image, File file_text) {
        super(fm);
        this.tabs = tabs;
        this.file_image = file_image;
        this.file_text = file_text;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab2(file_image,file_text);
            case 1:
                return new Tab1(file_image,file_text);
            //case 2:
                //return new Tab3(file_image,file_text);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
