package com.example.home.excelsiouruser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.excelsiouruser.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.name)EditText mNameEdit;
    @BindView(R.id.email) EditText mEmailEdit;
    @BindView(R.id.mobile)EditText mMobileEdit;
    @BindView(R.id.age)EditText mAgeEdit;
    @BindView(R.id.radioGender) RadioGroup mRadioGrp;
    @BindView(R.id.password)EditText mPasswordEdit;
    @BindView(R.id.password2)EditText mConfirmPassEdit;
    @BindView(R.id.workSpinner)Spinner mSpinner;
    @BindView(R.id.addressTv)TextView mWorkAddressTv;
    @BindView(R.id.address)EditText mWorkAddressEdit;
    @BindView(R.id.college)EditText mCollegeEdit;
    @BindView(R.id.agree)CheckBox mAgreeCheck;
    @BindView(R.id.terms)CheckBox mTermsTv;
    @BindView(R.id.register)Button mRegisterBtn;
    @BindView(R.id.login)Button mLoginBtn;
    private RadioButton mGenderBtn;

    private String name,email,mobile,age,gender,password,password2,
                    ocupation,collegeTv,college,address,agree;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        List<String> ocupationList = new ArrayList<>();
        ocupationList.add("I am A Student");
        ocupationList.add("I Work");

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ocupationList);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ocupation = parent.getItemAtPosition(position).toString();
                if (position==0){
                    mWorkAddressTv.setText("College");
                }
                else {
                    mWorkAddressTv.setText("Describe What You Do");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataAndRegister();
            }
        });
    }

    private void checkDataAndRegister() {
        int selectedId = mRadioGrp.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        mGenderBtn = (RadioButton) findViewById(selectedId);
        Toast.makeText(RegisterActivity.this,
                mGenderBtn.getText().toString(), Toast.LENGTH_SHORT).show();


        name = mNameEdit.getText().toString();
        mobile = mMobileEdit.getText().toString();
        email = mEmailEdit.getText().toString();
        age = mAgeEdit.getText().toString();
        password = mPasswordEdit.getText().toString();
        password2 = mConfirmPassEdit.getText().toString();
        address = mWorkAddressEdit.getText().toString();
        gender =  mGenderBtn.getText().toString();
        college = mCollegeEdit.getText().toString();

        if (name.equals("")|| mobile.equals("")|| email.equals("")|| age.equals("")||
                password.equals("")||password2.equals("")||address.equals("")||
                gender.equals("")||college.equals("")){
            Toast.makeText(getApplicationContext(),"All Fields Are Mandatory",Toast.LENGTH_LONG).show();
            return;
        }
        if (!mAgreeCheck.isChecked()){
            Toast.makeText(getApplicationContext(),"Please Agree To Continue",Toast.LENGTH_LONG).show();

        }
        else {
            uploadDataAndLogin();
        }




    }

    private void uploadDataAndLogin() {
        if (Utils.isNetworkAvailable()){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        uploadDataToServer();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void uploadDataToServer() {

    }
}
