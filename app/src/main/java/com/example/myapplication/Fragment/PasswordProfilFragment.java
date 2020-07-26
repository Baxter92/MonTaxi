package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordProfilFragment extends Fragment {

    public PasswordProfilFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PasswordProfilFragment newInstance() {
        PasswordProfilFragment fragment = new PasswordProfilFragment();
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
        return inflater.inflate(R.layout.password_profil_dialog, container, false);
    }
}