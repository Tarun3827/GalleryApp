package com.example.android.tablayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int CAMERA_CODE = 1002;

    File file_image,file_text;
    ImageView imageView;
    Bitmap bitmap_for_store;

    EditText tag,description;

    String currentPhotoPath;

    public Tab2(File file_image, File file_text) {
        //Log.e("Tabs","Inside Tab2 Constructor");
        this.file_image = file_image;
        this.file_text = file_text;
    }

    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
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

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        tag = (EditText) view.findViewById(R.id.tag);
        description = (EditText) view.findViewById(R.id.description);
        Button gallery = (Button) view.findViewById(R.id.gallery_text);
        Button camera = (Button) view.findViewById(R.id.camera_text);
        Button submit = (Button) view.findViewById(R.id.submit);
        imageView = (ImageView) view.findViewById(R.id.image_view_tab2);

        //final String tag_text = tag.getText().toString();
        //final String description_text = description.getText().toString();

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                  == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Permission wait" , Toast.LENGTH_SHORT).show();
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    } else {
                        // permission granted
                        Toast.makeText(getContext(), "Permission Success 1" , Toast.LENGTH_SHORT).show();
                        pickImage();
                    }
                } else {
                    // system os is less than Marshmallow, no need of permissions
                    Toast.makeText(getContext(), "Permission Success 2" , Toast.LENGTH_SHORT).show();
                    pickImage();
                }
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_CODE);
            }
        });

        //BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        //Bitmap bitmap = drawable.getBitmap();

        //Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        //Drawable drawable = getResources().getDrawable(R.id.img2);
        //final Bitmap bitmap = ( (BitmapDrawable) drawable).getBitmap();

        //bitmap_for_store written in onActivityForResult function

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 //String tag_text = tag.getText().toString();
                //int len = file_image.listFiles().length;
                //String tag_text = "image" + String.valueOf(file_image.listFiles().length);
                String tag_text = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                final String description_text = description.getText().toString();

               /* if(tag_text == "") {
                    //Toast.makeText(getContext(), "Enter Tag", Toast.LENGTH_SHORT).show();
                    //return;
                    tag_text = "image" + file_image.listFiles().length;
                }*/

                File save_image_file = new File(file_image , tag_text + ".jpg");
                File save_description_file = new File(file_text, tag_text + ".txt");
                /*
                int k = 0 ;
                while(save_image_file.exists()) {
                    k++;
                    tag_text += "(" + k + ")";
                    File save_image_file = new File(file_image , tag_text + ".jpg");
                    File save_description_file = new File(file_text, tag_text + ".txt");
                }*/

                if(save_image_file.exists()) {
                    Toast.makeText(getContext(),"Tag already exists",Toast.LENGTH_SHORT).show();
                } else {
                    //save the image
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(save_image_file);
                        bitmap_for_store.compress(Bitmap.CompressFormat.JPEG,100,fos);


                        currentPhotoPath = save_image_file.toString();
                        func_show_to_gallery();

                        Log.e("Tab2"," Inside FileOutputStream, saving image to storage ");
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        Log.e("Tab2"," Inside error in saving image ");
                        e.printStackTrace();
                    }

                    //save the text
                    try {
                        FileWriter writer = new FileWriter(save_description_file);
                        Log.e("Tab2"," Inside Filewriter :  " + description_text);
                        writer.append(description_text);
                        writer.flush();
                        writer.close();
                        Toast.makeText(getContext(),"Successful : " + file_text.listFiles().length,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    /*File[] files_lists = file_text.listFiles();
                    for(int i = 0 ; i<file_text.listFiles().length ; i++ ) {
                        Log.e("Tab2","Inside for loop : " + i + " : " + files_lists[i].toString());
                    }*/

                }

            }
        });

        return view;
    }

    private void func_show_to_gallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    private void pickImage() {
        Log.e("Tab2"," Inside Pick Image Function, Gallery Intent send ");

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
            {
                Toast.makeText(getContext(), "Into onRequest" , Toast.LENGTH_SHORT).show();
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission Success" , Toast.LENGTH_SHORT).show();
                    pickImage();
                } else {
                    Toast.makeText(getContext(), "Permission Denied" , Toast.LENGTH_SHORT).show();
                }
            }
            default:
                Toast.makeText(getContext(), "Permission Denied" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("Tab2"," Entered OnActivityResult function ");

        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            Log.e("Tab2"," Inside OnActivityResult, gallery code ");
            try {
                bitmap_for_store = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
            } catch (Exception e) {
                Log.e("Tabs","Inside Tab2.java, onActivityForResult");
            }

        }

        if(resultCode == RESULT_OK && requestCode == CAMERA_CODE) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap_for_store = bitmap;
            imageView.setImageBitmap(bitmap);
            Log.e("Tab2"," Inside OnActivityResult, camera code ");
        }
    }
}