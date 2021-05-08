package com.example.myapplication.View.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.UserProfile;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentUpdateProfile extends Fragment {
    ImageView btConfirm, btCancel;
    EditText etName, etPhone;
    FirebaseUser user;
    DatabaseReference mData;
    public static FragmentUpdateProfile newInstance() {
        FragmentUpdateProfile fragment = new FragmentUpdateProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_profile, container, false);
        btConfirm= view.findViewById(R.id.btConfirm);
        btCancel= view.findViewById(R.id.btCancel);
        etName= view.findViewById(R.id.etName);
        etPhone= view.findViewById(R.id.etPhone);
        user= FirebaseAuth.getInstance().getCurrentUser();
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= etName.getText().toString();
                String phone= etPhone.getText().toString();
                if(TextUtils.isEmpty(name)){
                    etName.setError("Tên không được để trống");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    etPhone.setError("Số điện thoại không được để trống");
                    return;
                }
                update(name,phone);
            }
        });
        return view;
    }
    private void update(String name, String phone){
        /*SharedPreferences sharedPref= getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("phone", phone);*/
        UserProfileChangeRequest
                profileUpdates = new UserProfileChangeRequest
                .Builder()
                .setDisplayName(name)
                .build();
        if(user!=null)
        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mData= FirebaseDatabase.getInstance().getReference();
                    UserProfile userProfile= new UserProfile(user.getUid(),
                            user.getEmail(),name,phone);
                    mData.child("user").push().setValue(userProfile);
                    Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        });

    }
}