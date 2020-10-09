package com.example.android.tablayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {

    private FragmentToActivity mCallback;
    //................................

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    File file_image,file_text;


    public Tab1() {

    }

    public Tab1(File file_image, File file_text) {
        //Log.e("Tabs","Inside Tab1 Constructor");
        this.file_image = file_image;
        this.file_text = file_text;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Log.e("Tabs","Inside Tab1 newinstance");
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LinearLayout layout_slide_show;
    int value;
    ViewFlipper viewFlipper, textFlipper;

    //changed
    RelativeLayout r1,r2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        //final int[] images_id = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
        //final String[] descriptions = {"Wear a Mask","Beach Time","Best Tech Youtuber"};
        //LinearLayout linearLayout_1 = (LinearLayout) view.findViewById(R.id.layout_tab_1_main);

        /*
        r1 = (RelativeLayout) view.findViewById(R.id.relative_layout_1);
        r2 = (RelativeLayout) view.findViewById(R.id.relative_layout_2);

        RelativeLayout.LayoutParams r11 = (RelativeLayout.LayoutParams) r1.getLayoutParams();
        RelativeLayout.LayoutParams r22 = (RelativeLayout.LayoutParams) r2.getLayoutParams();
        */

        //.....................................


        final ArrayList<File> image_id_list = new ArrayList<>();
        if(file_image.exists())
        {
            File[] files_list = file_image.listFiles();
            for(int i=0;i<files_list.length;i++) {
                File single_file = files_list[i];
                image_id_list.add(single_file);
                //Log.e("Tab1","Name : " + single_file.toString() );
            }
        }

        final ArrayList<File> description_id_list = new ArrayList<>();
        if(file_text.exists()) {
            File[] files_list = file_text.listFiles();
            for(int i=0; i<files_list.length ; i++) {
                File single_file = files_list[i];
                description_id_list.add(single_file);
                //Log.e("Tab1","Inside For Loop : " + i + " : " + single_file.toString() );
            }
        }

        ListView list = view.findViewById(R.id.list_view);

        //CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(), images_id, descriptions);
        //CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(), images_id, descriptions,image_id_list,description_id_list);

        CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(), image_id_list, description_id_list);
        list.setAdapter(adapter);

        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"" + func_read_file(description_id_list.get(i)) ,Toast.LENGTH_SHORT).show();
            }
        });*/

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"" + func_read_file(description_id_list.get(i)) ,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //Log.e("Inside the Tab1.java","Path : " + file_image );
        Button slideshow = (Button) view.findViewById(R.id.slide_show);
        layout_slide_show = (LinearLayout) view.findViewById(R.id.linear_layout_tab1);
        value = 1;

        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper_1);
        textFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper_2);

        slideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("Tab1","Inside Slideshow Click");
                sendData(value);
                if(value == 1) {
                    value = 0;

                    for(int i = 0 ; i < image_id_list.size() ; i++) {
                        setFlipperImage(image_id_list.get(i));
                        setFlipperText(description_id_list.get(i));
                    }

                    layout_slide_show.setVisibility(View.VISIBLE);
                    //Log.e("Tab1","Set visible");
                } else {
                    value = 1;
                    layout_slide_show.setVisibility(View.GONE);
                    //Log.e("Tab1","Set gone");
                }

                //if(layout_slide_show.getVisibility() == 2) layout_slide_show.setVisibility(0);
                //else layout_slide_show.setVisibility(2);
            }
        });


        return view;
    }


    private void setFlipperImage(File file) {
        //Log.e("Tab1","Flipper called");
        ImageView image = new ImageView(getContext());
        image.setImageDrawable(Drawable.createFromPath(file.toString()));
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewFlipper.addView(image);
    }

    private void setFlipperText(File file) {
        //Log.e("Tab1","Flipper called");
        TextView textView = new TextView(getContext());
        textView.setText(func_read_file(file));
        textView.setTextSize(getResources().getDimension(R.dimen.textsize));
        textView.setGravity(Gravity.CENTER);
        textFlipper.addView(textView);
    }

    private String func_read_file(File file_to_read) {
        StringBuilder text_final = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file_to_read));
            String line;

            while( (line = br.readLine()) !=null ) {
                text_final.append(line);
                //text_final.append("\n");
            }
            br.close();
        } catch (Exception e) {
            Log.e("Adapter","Inside CustomArrayAdapter, Buffer Reader Failed");
        }

        String result = text_final.toString();
        return result;
    }

    public boolean get_state_of_slide_show() {
        if(layout_slide_show.getVisibility() == View.VISIBLE) return true;
        else return false;
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        if(isInPictureInPictureMode) {
            Log.e("Tab1","Pip mode : Byeee");
        } else {
            Log.e("Tab1","Pip mode : I am back");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.e("Tab1","Pip mode : onpause");
    }


    //................................
    private void sendData(int comm) {
        mCallback.communicate(comm);
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //changed
        try {
            mCallback = (FragmentToActivity) context;
        } catch (Exception e) {
            Log.e("Debug","Interface failed in onAttach");
        }
    }
}