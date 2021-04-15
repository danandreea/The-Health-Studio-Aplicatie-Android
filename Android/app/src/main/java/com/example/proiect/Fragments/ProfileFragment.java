package com.example.proiect.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proiect.Fragments.HomeFragment;
import com.example.proiect.ProfileDetails;
import com.example.proiect.R;
import com.example.proiect.RoomDatabase.model.Meals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment{


    public static final String SHARED_PREF_FILE_NAME = "profileSharedPref";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String GENDER_RB_ID = "gender_rb_id";
    public static final String PHONE = "phone";
    public static final String ACTIVITY_LEVEL = "activity_level";
    private static final String KEY_REMEMBER= "remember_info";
    private TextInputEditText tietName;
    private TextInputEditText tietSurname;
    private TextInputEditText tietAge;
    private TextInputEditText tietWeight;
    private TextInputEditText tietHeight;
    private TextInputEditText tietPhone;
    private RadioGroup rgGender;
    private CheckBox remember_info;
    private Button btnSave;
    private Spinner spnActivities;
    private String id2;
    private RadioButton radioButton;
    private FloatingActionButton fab;

public ProfileFragment()
{

}

    //obiect utilizat pentru a reprezenta un fisier de preferinte incarcat in memorie
    private SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        initComponents(view);
        return view;
    }


    private void initComponents(View view) {
        tietName = view.findViewById(R.id.tiet_profile_name);
        tietSurname = view.findViewById(R.id.tiet_profile_surname);
        tietAge = view.findViewById(R.id.tiet_profile_age);
        tietHeight = view.findViewById(R.id.tiet_profile_height);
        tietWeight = view.findViewById(R.id.tiet_profile_weight);
        rgGender = view.findViewById(R.id.rg_profile_gender);
        int selectedID=rgGender.getCheckedRadioButtonId();
        radioButton=(RadioButton)view.findViewById(selectedID);
        tietPhone=view.findViewById(R.id.tiet_phone);
        btnSave = view.findViewById(R.id.btn_profile_save);
        remember_info=view.findViewById(R.id.checkbox_prof);
        spnActivities=view.findViewById(R.id.spn_add_activities);
        addActivityAdapter();
        fab=view.findViewById(R.id.fab_back);
        fab.setOnClickListener(backHome());

        //deschidere fisier, daca numele specificat nu exista atunci acesta o sa fie creat pe loc.
        preferences = getActivity().getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        btnSave.setOnClickListener(getSaveSharedPreferenceEventListener());
        getProfileDetailsFromSharedPreference();
    }

    private View.OnClickListener backHome() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new HomeFragment());
            }
        };

    }

    private View.OnClickListener getSaveSharedPreferenceEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savetofirebase();
                saveToSharedPreference();
                changeFragment(new HomeFragment());
            }
        };
    }

    private void savetofirebase()
    {
        String mName=tietName.getText().toString().trim();
        String mSurname=tietSurname.getText().toString().trim();
        String mAge=tietAge.getText().toString().trim();
        String mHeight=tietHeight.getText().toString().trim();
        String mWeight=tietWeight.getText().toString().trim();
        String mrgGender=radioButton.getText().toString().trim();
        String mPhone=tietPhone.getText().toString().trim();
        String mLevel = spnActivities.getSelectedItem().toString();

        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Users");

        ProfileDetails newProfile=new ProfileDetails(mName,mSurname,mAge,mHeight,mWeight,mrgGender,mPhone,mLevel);
        reference.child(mPhone).setValue(newProfile);

    }


    private void saveToSharedPreference() {
        SharedPreferences.Editor editor = preferences.edit();
        if(remember_info.isChecked()) {
            String name = tietName.getText() != null ? tietName.getText().toString() : "";
            String surname = tietSurname.getText() != null ? tietSurname.getText().toString() : "";
            String age = tietAge.getText() != null ? tietAge.getText().toString() : "";
            String height = tietHeight.getText() != null ? tietHeight.getText().toString() : "";
            String weight = tietWeight.getText() != null ? tietWeight.getText().toString() : "";
            int genderRbId = rgGender.getCheckedRadioButtonId();
            String phone = tietPhone.getText() != null ? tietPhone.getText().toString() : "";
            String level = spnActivities.getSelectedItem() != null ? spnActivities.getSelectedItem().toString() : "";

            editor.putString(NAME, name);
            editor.putString(SURNAME, surname);
            editor.putString(AGE, age);
            editor.putString(HEIGHT, height);
            editor.putString(WEIGHT, weight);
            editor.putInt(GENDER_RB_ID, genderRbId);
            editor.putString(PHONE, phone);
            editor.putString(ACTIVITY_LEVEL, level);

            editor.apply();
        }
        else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(NAME);
            editor.remove(SURNAME);
            editor.remove(AGE);
            editor.remove(HEIGHT);
            editor.remove(WEIGHT);
            editor.remove(PHONE);
            editor.remove(ACTIVITY_LEVEL);
            editor.apply();
        }
    }

    private void getProfileDetailsFromSharedPreference() {
        //citire informatii din fisierul de preferinte in functie de cheie.
        //cheia este aceeasi ca la operatia put
        String name = preferences.getString(NAME, "");
        String surname = preferences.getString(SURNAME, "");
        String age = preferences.getString(AGE, "");
        String weight = preferences.getString(WEIGHT, "");
        String height = preferences.getString(HEIGHT, "");
        int genderRbId = preferences.getInt(GENDER_RB_ID, R.id.rb_profile_gender_male);
        String phone = preferences.getString(PHONE, "");
        String level = preferences.getString(ACTIVITY_LEVEL, "");

        if(preferences.getBoolean(KEY_REMEMBER,false))
            remember_info.setChecked(true);
        else
            remember_info.setChecked(false);

        //incarcare informatii in componente vizuale
        tietName.setText(name);
        tietSurname.setText(surname);
        tietAge.setText(age);
        tietWeight.setText(weight);
        tietHeight.setText(height);
        rgGender.check(genderRbId);
        tietPhone.setText(phone);
        remember_info.setChecked(true);
        selectActivity(level);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

    private void addActivityAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.add_activity_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnActivities.setAdapter(adapter);
    }
    private void selectActivity(String value) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.add_activity_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnActivities.setAdapter(adapter);
        if (value != null) {
            int spinnerPosition = adapter.getPosition(value);
            spnActivities.setSelection(spinnerPosition);
        }
    }

}