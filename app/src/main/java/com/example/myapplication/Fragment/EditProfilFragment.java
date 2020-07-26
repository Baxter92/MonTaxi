package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfilFragment extends Fragment {

    private EditText passEdt;
    private ImageView imageUpload;
    public EditProfilFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditProfilFragment newInstance() {
        EditProfilFragment fragment = new EditProfilFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_profil_dialog, container, false);
        passEdt = (EditText)view.findViewById(R.id.password);
        imageUpload = (ImageView)view.findViewById(R.id.upload);
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchFragment(UploadProfilFragment.newInstance());
            }
        });
        passEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(passEdt.getCompoundDrawables()[2]!=null){
                        if(event.getX() >= (passEdt.getRight()- passEdt.getLeft() - passEdt.getCompoundDrawables()[2].getBounds().width())) {
                           SwitchFragment(PasswordProfilFragment.newInstance());
                        }
                    }
                }
                return false;
            }
        });
        return view;
    }

    private void SwitchFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}