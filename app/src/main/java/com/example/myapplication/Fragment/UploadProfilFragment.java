package com.example.myapplication.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.Config;
import com.example.myapplication.Models.Driver;
import com.example.myapplication.Models.Profil;
import com.example.myapplication.Models.Utils.SessionDriver;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;
import com.example.myapplication.activities.SignInActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadProfilFragment extends Fragment implements View.OnClickListener, networksJO {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private static final int IMAGE_GALLERY_REQUEST = 12;
    private SessionDriver sessionDriver;

    private ImageView iv_gallery, iv_camera, iv_delete, iv_upload, iv_upload2, view_camera;
    private TextView textCamera, textlabel;

    public UploadProfilFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UploadProfilFragment newInstance() {
        UploadProfilFragment fragment = new UploadProfilFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDriver = new SessionDriver(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.upload_profil_dialog, container, false);
        init(view);
        setCamera();
        return view;
    }

    private void init(View view) {
        iv_gallery = (ImageView)view.findViewById(R.id.gallery);
        iv_gallery.setOnClickListener(this);
        iv_camera = (ImageView)view.findViewById(R.id.camera);
        iv_camera.setOnClickListener(this);
        iv_delete = (ImageView)view.findViewById(R.id.delete);
        iv_delete.setOnClickListener(this);
        iv_upload = (ImageView)view.findViewById(R.id.uploadpic);
        iv_upload.setOnClickListener(this);
        iv_upload2 = (ImageView)view.findViewById(R.id.uploadpic2);
        iv_upload2.setOnClickListener(this);
        view_camera = (ImageView)view.findViewById(R.id.view_camera);
        textCamera = (TextView) view.findViewById(R.id.textcamera);
        textlabel = (TextView) view.findViewById(R.id.textlabel);
        textlabel.setText(getString(R.string.upload_text,sessionDriver.getDriver().getProfil().getFirst_name()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gallery:
                if (view_camera.getVisibility() == View.VISIBLE) {
                   // Toast.makeText(getActivity(), "Gallery clicked", Toast.LENGTH_SHORT).show();
                    dispatchGalleryIntent();
                }
                break;
            case R.id.camera:
                if (view_camera.getVisibility() == View.VISIBLE) {
                    //Toast.makeText(getActivity(), "Camera clicked", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.delete:
                if (view_camera.getVisibility() == View.VISIBLE) {
                    Alert();
                    //Toast.makeText(getActivity(), "delete clicked", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.uploadpic:
                dispatchGalleryIntent();
                //Toast.makeText(getActivity(),"add camera clicked",Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getActivity(),"default clicked",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            Uri selectedImage = data.getData();
            Glide.with(getActivity())
                    .load(selectedImage)
                    .into(iv_upload);

            Glide.with(getActivity())
                    .load(selectedImage)
                    .into(iv_upload2);

            textCamera.setVisibility(View.GONE);
            view_camera.setVisibility(View.VISIBLE);
            Profil profil = sessionDriver.getDriver().getProfil();
            profil.setPicture(selectedImage.toString());
            ((MainActivity) getActivity()).setIconToolbar(selectedImage,-1);
            try {
                if(  selectedImage!=null   ){
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , selectedImage);
                    Upload(getStringImage(bitmap));
                }
            }
            catch (Exception e) {
                //handle exception
            }
        }
    }

    private void Upload(String stringImage) {
        Map<String,String> params = new HashMap<>();
        params.put("picture",stringImage);
        String token = sessionDriver.getDriver().getId();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization",token);

        JSONObject dataJson = new JSONObject(params);
        Networks networks = new Networks(getActivity(),this);
        String url = Config.resetProfil+sessionDriver.getDriver().getUserId()+"?access_token="+sessionDriver.getDriver().getId();
        networks.postData(dataJson.toString(),url,headers);
    }

    /**
     * Create file with current timestamp name
     * @throws IOException
     */
    /*private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }*/

    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, IMAGE_GALLERY_REQUEST);
    }

    private void Alert() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        View contentView = getLayoutInflater().inflate(R.layout.delete_camera_dialog,null);
        dialog.setContentView(contentView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width =310;
        lp.height = 310;
        // lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Button exitText = (Button) contentView.findViewById(R.id.exit);
        Button cancel = (Button) contentView.findViewById(R.id.cancel);

        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity())
                        .load(R.drawable.groupe_1740)
                        .into(iv_upload);

                Glide.with(getActivity())
                        .load(R.drawable.groupe_1737)
                        .into(iv_upload2);

                ((MainActivity) getActivity()).setIconToolbar(null,R.drawable.groupe_1737);
                textCamera.setVisibility(View.VISIBLE);
                view_camera.setVisibility(View.GONE);
                Profil profil = sessionDriver.getDriver().getProfil();
                profil.setPicture("");
               dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setCamera(){
        if (!sessionDriver.getDriver().getProfil().getPicture().equals("")){
            String selectedImage = sessionDriver.getDriver().getProfil().getPicture();
            Glide.with(getActivity())
                    .load(selectedImage)
                    .into(iv_upload);

            Glide.with(getActivity())
                    .load(selectedImage)
                    .into(iv_upload2);

            textCamera.setVisibility(View.GONE);
            view_camera.setVisibility(View.VISIBLE);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {
        try {
            Driver driver = sessionDriver.getDriver();
            String picture = jsonObject.getString("picture");
            sessionDriver.getDriver().setProfil(new Profil(driver.getProfil().getFirst_name(),
                    driver.getProfil().getLast_name(),driver.getProfil().getPassword(),picture, driver.getProfil().getTown()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void geterrorVolley(Context context, String error) {

    }
}