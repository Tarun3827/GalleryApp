package com.example.android.tablayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CustomArrayAdapter extends BaseAdapter {

    private Context context;
    //private int[] array;
    //private String[] descriptions;
    private ImageView imageView;
    private TextView textView;

    private ArrayList<File> image_id_list;
    private ArrayList<File> description_id_list;

    /*public CustomArrayAdapter(Context context, int[] array, String[] descriptions) {
        this.context = context;
        this.array = array;
        this.descriptions = descriptions;
    }

    public CustomArrayAdapter(Context context, int[] array, String[] descriptions, ArrayList<File> image_id_list) {
        this.context = context;
        this.array = array;
        this.descriptions = descriptions;
        this.image_id_list = image_id_list;
    }

    public CustomArrayAdapter(Context context, int[] array, String[] descriptions, ArrayList<File> image_id_list, ArrayList<File> description_id_list) {
        this.context = context;
        this.array = array;
        this.descriptions = descriptions;
        this.image_id_list = image_id_list;
        this.description_id_list = description_id_list;
    }*/

    public CustomArrayAdapter(Context context, ArrayList<File> image_id_list, ArrayList<File> description_id_list) {
        this.context = context;
        this.image_id_list = image_id_list;
        this.description_id_list = description_id_list;
    }

    @Override
    public int getCount() {
        return image_id_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.row,viewGroup,false);

        imageView = view.findViewById(R.id.image_view);
        //imageView.setImageResource(array[i]);
        try {
            imageView.setImageDrawable(Drawable.createFromPath(image_id_list.get(i).toString()));
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }


        textView = view.findViewById(R.id.text_view);
        //textView.setText(descriptions[i]);
        try {
            textView.setText(func_read_file(description_id_list.get(i)));
        } catch (Exception e) {
            textView.setText("Problem");
        }



        return view;
    }

    private String func_read_file(File file_to_read) {
        StringBuilder text_final = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file_to_read));
            String line;

            while( (line = br.readLine()) !=null ) {
                text_final.append(line);
                text_final.append("\n");
            }
            br.close();
        } catch (Exception e) {
            Log.e("Adapter","Inside CustomArrayAdapter, Buffer Reader Failed");
        }

        String result = text_final.toString();
        return result;
    }

}
