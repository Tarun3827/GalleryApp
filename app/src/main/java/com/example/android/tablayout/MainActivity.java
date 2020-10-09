package com.example.android.tablayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity implements FragmentToActivity{

    ActionBar actionBar;
    TabLayout tabLayout;
    int set_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ContextWrapper cw = new ContextWrapper(getApplicationContext());
        //final File directory = cw.getDir("imageDirectory", Context.MODE_PRIVATE);

        actionBar = getSupportActionBar();

        //part1
        File file_image = new File(MainActivity.this.getFilesDir(),"ImageDirectory");
        File file_text = new File(MainActivity.this.getFilesDir(),"TextDirectory");

        if(!file_image.exists()) file_image.mkdir();
        if(!file_text.exists()) file_text.mkdir();

        Log.e("MainActivity","Path of Images :" + file_image.toString());
        Log.e("MainActivity","Path of Texts :" + file_text.toString());


        //part2
        /*
        File file_image_external = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"TabLayout");
        File file_text_2 = new File(MainActivity.this.getFilesDir(),"TextDirectory2");

        if(!file_image_external.exists()) file_image_external.mkdir();
        if(!file_text_2.exists()) file_text_2.mkdir();

        Log.e("MainActivity","Path of Images :" + file_image_external.toString());
        Log.e("MainActivity","Path of Texts :" + file_text_2.toString());

        */

        // Change in the custom pager adapter line   ----- IMPORTANT

        /*  // if you want to delete any file

        File delete_file = new File(file_image + "/.jpg");
        delete_file.delete();

        File delete_text = new File(file_text + "/.txt");
        delete_text.delete();  */

        /*
        Here file --- file_image
             file2 -- file_text

        if(!file.exists()) {
            Log.e("CameraMode","Debug : Not There");
            file.mkdir();
        }
        else
            Log.e("CameraMode","Debug : " + file.toString());

        if(!file2.exists()) {
            Log.e("CameraMode","Debug : Not There");
            file2.mkdir();
        }
        else
            Log.e("CameraMode","Debug : " + file2.toString());
        */

        //File[] files = file.listFiles();
        //File[] files2 = file2.listFiles();

        //Log.e("CameraMode","Length : " + file.listFiles().length );
        //Log.e("CameraMode","Length : " + file2.listFiles().length );


        /*

            // this code is for saving image

        Drawable drawable = getResources().getDrawable(R.drawable.img3);
        Bitmap bitmap = ( (BitmapDrawable) drawable ).getBitmap();

        File new_file = new File(file_image, "third" + ".jpg" );
        if(!new_file.exists()) {
            Log.e("CameraMode","Name : " + new_file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new_file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);   // decreasing the quality
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                //.....
                e.printStackTrace();
            }

        } else {
            //Toast.makeText(getApplicationContext(), "Already that image file exists", Toast.LENGTH_SHORT).show();
        }


        // to save text

        File new_text_file = new File(file_text,"third" + ".txt");
        if(!new_text_file.exists()) {
            try {
                FileWriter writer = new FileWriter(new_text_file);

                writer.append("I am Avva Tarun Kumar");
                writer.flush();
                writer.close();
            } catch (Exception e) {

            }

        } else {
            //Toast.makeText(getApplicationContext(), "Already that text file exists", Toast.LENGTH_SHORT).show();
        }
        */

        /*
        // Taoast the written file
        File check_file = new File(MainActivity.this.getFilesDir() + "/TextDirectory/first.txt");
        StringBuilder check_text_final = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(check_file));
            String line;

            while( (line = br.readLine()) != null ) {
                check_text_final.append(line);
                check_text_final.append("\n");
            }
            br.close();
        } catch (Exception e) {
            Log.e("Debug","Check Text Final Failed");
        }

        String result = check_text_final.toString();
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        */


        //......................................

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Take Photo"));
        tabLayout.addTab(tabLayout.newTab().setText("View Photos"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tags"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);


        // IMPORTANT TO CHOOSE ONE AMONG THESE TWO
        final CustumPagerAdapter adapter = new CustumPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),file_image,file_text);
        //final CustumPagerAdapter adapter = new CustumPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),file_image_external,file_text_2);


        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0) {
                    adapter.notifyDataSetChanged();
                } else if(tab.getPosition() == 1) {
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        //Tab1 tab = new Tab1();
        //boolean val = tab.get_state_of_slide_show();
        /*if(val) {
            left();
        }*/

        if(set_value == 1) left();
    }

    protected void left() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            Rational aspectRatio = new Rational(width,height);
            PictureInPictureParams.Builder pip = new PictureInPictureParams.Builder();
            pip.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pip.build());

            /*PictureInPictureParams pip = new PictureInPictureParams.Builder().setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pip);*/

        } else {
            Toast.makeText(this,"Not possible",Toast.LENGTH_SHORT).show();
            Log.e("Tab1","" + Build.VERSION.SDK_INT);
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if(isInPictureInPictureMode) {
            actionBar.hide();
            tabLayout.setVisibility(View.GONE);
            //Log.e("Main Activity","byee");
        }
        else {
            actionBar.show();
            tabLayout.setVisibility(View.VISIBLE);
            //Log.e("Main Activity","I am back");
        }

        //Log.e("main activity","Value is :" + new Tab1().layout_slide_show.getVisibility());
    }

    @Override
    public void communicate(int comm) {
        set_value = comm;
        //Log.e("Main Activity","Received : " + comm);
    }
}