package com.example.android.tablayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    File file_image,file_text;

    public Tab3(File file_image, File file_text) {
        Log.e("Tabs","Inside Tab3 Constructor");
        this.file_image = file_image;
        this.file_text = file_text;
    }

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        //LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_tab3);

        //ImageView imageView = new ImageView(getContext());
        //imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //ImageView imageView = (ImageView) view.findViewById(R.id.image_view_tab3);
        //File add_image_file = new File(file_image + "/first.jpg");
        //imageView.setImageDrawable(Drawable.createFromPath(add_image_file.toString()));

        //linearLayout.addView(imageView);

        final TextView textView = (TextView) view.findViewById(R.id.text_tab3);
        String final_text_tab3 = "";

        File[] files_list = file_text.listFiles();

        for(int i =0; i<files_list.length ;i++) {
            String line = files_list[i].toString();
            int index = line.lastIndexOf('/');
            final_text_tab3 += line.substring(index+1,line.length()-4) + "\n";

            //Log.e("Tab3","Name : " + line.substring(index+1,line.length()-4));
        }

        textView.setText(final_text_tab3);

        return view;
    }
}