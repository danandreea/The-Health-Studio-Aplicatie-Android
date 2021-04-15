package com.example.proiect.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.proiect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.microedition.khronos.opengles.GL;


public class WaterFragment extends Fragment implements TextWatcher,CompoundButton.OnCheckedChangeListener {
    public static final String SHARED_PREF_FILE_NAME = "waterSharedPref";
    private static final String KEY_REMEMBER= "remember_info";
    public static final String GLASSES = "glasses";
    private CheckBox remember_info;
    private FloatingActionButton fabback;

    public WaterFragment()
    {

    }

    private TextView tv_counter;
    private Button minus_bt;
    private Button reset_bt;
    private Button plus_bt;
    private int counter;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private final View.OnClickListener clickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.minus_bt :
                    minusCounter();
                    break;
                case R.id.plus_bt :
                    plusCounter();
                    break;
                case R.id.reset_bt :
                    counter=0;
                    tv_counter.setText(String.valueOf(counter));
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_water, container, false);

        preferences=getActivity().getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();

        tv_counter=(TextView) view.findViewById(R.id.tv_counter);
        minus_bt=(Button) view.findViewById(R.id.minus_bt);
        minus_bt.setOnClickListener(clickListener);
        reset_bt=(Button) view.findViewById(R.id.reset_bt);
        reset_bt.setOnClickListener(clickListener);
        plus_bt=(Button) view.findViewById(R.id.plus_bt);
        plus_bt.setOnClickListener(clickListener);
        remember_info=view.findViewById(R.id.checkbox_prof2);
        fabback=view.findViewById(R.id.fab_back_lv);
        fabback.setOnClickListener(backHome());

        if(preferences.getBoolean(KEY_REMEMBER,false))
            remember_info.setChecked(true);
        else
            remember_info.setChecked(false);

        tv_counter.setText(preferences.getString(GLASSES,""));
        GlobalCounter=preferences.getString(GLASSES, "");

        if((preferences.getString(GLASSES,""))=="")
            counter=0;
        else
            counter=Integer.valueOf(preferences.getString(GLASSES,""));


        tv_counter.addTextChangedListener(this);
        remember_info.setOnCheckedChangeListener(this);

        return view;
    }

    public static String GlobalCounter=null;

    private void managePrefs()
    {
        if(remember_info.isChecked())
        {
            editor.putString(GLASSES,tv_counter.getText().toString().trim());
            GlobalCounter=tv_counter.getText().toString().trim();
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }
        else
        {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(GLASSES);
            editor.apply();
        }
    }


    private void plusCounter(){
        counter++;
        tv_counter.setText(String.valueOf(counter));
    }

    private void minusCounter(){
        counter--;
        tv_counter.setText(String.valueOf(counter));
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }
    private View.OnClickListener backHome() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new HomeFragment());
            }
        };

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}