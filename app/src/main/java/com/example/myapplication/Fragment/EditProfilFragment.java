package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Models.Profil;
import com.example.myapplication.Models.Town;
import com.example.myapplication.Models.Utils.EventTown;
import com.example.myapplication.Models.Utils.SessionDriver;
import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfilFragment extends Fragment implements View.OnClickListener {

    private EditText passEdt;
    private Button imageUpload;
    private LinearLayout ll_btn;
    SessionDriver sessionDriver;
    private EditText first_nameEdt, last_nameEdt, phone_Edt;
    private Button cancelBtn, updateBtn, resetBtn;
    private ImageView update_firstname, update_lastname;
    TextView updatedone;
    private Spinner spinner;
    String[] towns;
    ArrayAdapter<String> adapter;

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
        sessionDriver = new SessionDriver(getActivity());
        towns = new String[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_profil_dialog, container, false);
        init(view);

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, towns);
        spinner.setAdapter(adapter);

        imageUpload.setOnClickListener(this);

        return view;
    }
    private void initEnableView(){
        first_nameEdt.setEnabled(false);
        last_nameEdt.setEnabled(false);
        passEdt.setEnabled(false);
        ll_btn.setVisibility(View.GONE);
    }

    private void init(View view) {
        passEdt = (EditText)view.findViewById(R.id.password);
        imageUpload = (Button) view.findViewById(R.id.upload);
        first_nameEdt = (EditText)view.findViewById(R.id.firstname);
        last_nameEdt = (EditText)view.findViewById(R.id.lastname);
        phone_Edt = (EditText)view.findViewById(R.id.number);
        ll_btn = (LinearLayout)view.findViewById(R.id.ll_btn);
        cancelBtn = (Button)view.findViewById(R.id.cancel);
        updateBtn = (Button)view.findViewById(R.id.update);
        spinner = (Spinner)view.findViewById(R.id.spinner2);
        update_firstname = (ImageView)view.findViewById(R.id.update_firstname);
        update_lastname = (ImageView)view.findViewById(R.id.update_lastname);
        resetBtn = (Button)view.findViewById(R.id.resetpwd);
        updatedone = (TextView)view.findViewById(R.id.done);

        update_lastname.setOnClickListener(this);
        update_firstname.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

        cancelBtn.setOnClickListener(this);

        updateBtn.setOnClickListener(this);
        setValue();
        initEnableView();
        //setValueTest();
    }

    private void setValue() {
        Profil profil = sessionDriver.getDriver().getProfil();
        first_nameEdt.setText(profil.getFirst_name());
        last_nameEdt.setText(profil.getLast_name());
        phone_Edt.setText(sessionDriver.getDriver().getNumber());
        phone_Edt.setEnabled(false);
        passEdt.setText(profil.getPassword());
        updatedone.setText(getString(R.string.update_done));
        updatedone.setTextColor(getResources().getColor(R.color.commentsent));
        initEnableView();
    }
    private void setValueTest() {
        first_nameEdt.setText("Ulrich");
        last_nameEdt.setText("Sinha");
        phone_Edt.setText("695797443");
        phone_Edt.setEnabled(false);
        passEdt.setText("bjr le monde");
    }
    private void SwitchFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getTownList(EventTown eventTown){
        towns = new String[eventTown.getTowns().size()];
        for (int i =0 ; i<eventTown.getTowns().size();i++){
            towns[i] = eventTown.getTowns().get(i).getName();
            Log.d("townvalue","bus after "+eventTown.getTowns().get(i).getName());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_firstname:
                first_nameEdt.setEnabled(true);
                break;
            case R.id.update_lastname:
                last_nameEdt.setEnabled(true);
                break;
            case R.id.resetpwd:
                SwitchFragment(PasswordProfilFragment.newInstance());
                break;
            case R.id.update:
                sessionDriver.getDriver().getProfil().setFirst_name(first_nameEdt.getText().toString());
                sessionDriver.getDriver().getProfil().setFirst_name(last_nameEdt.getText().toString());
                setValue();
                break;
            case R.id.cancel:
                initEnableView();
                break;
            case R.id.upload:
                SwitchFragment(UploadProfilFragment.newInstance());
                break;
            default:
                break;
        }
    }
}