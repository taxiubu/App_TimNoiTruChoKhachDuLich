package com.example.myapplication.View.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentRegister extends Fragment {
    ImageView btConfirm, btCancel;
    EditText etPassword, etRePassword, etEmail;
    FirebaseAuth mAuth;
    ProgressDialog dialog = null;
    private FirebaseAuth.AuthStateListener authStateListener;
    public static FragmentRegister newInstance() {
        Bundle args = new Bundle();
        FragmentRegister fragment = new FragmentRegister();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        btConfirm= view.findViewById(R.id.btConfirm);
        btCancel= view.findViewById(R.id.btCancel);
        etPassword= view.findViewById(R.id.etPassword);
        etRePassword= view.findViewById(R.id.etRePassWord);
        etEmail= view.findViewById(R.id.etEmail);
        mAuth= FirebaseAuth.getInstance();

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email không được để trống");
                    return;
                }
                if(!email.contains("@")||!email.contains(".")){
                    etEmail.setError("Nhập Email");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Mật khẩu không được để trống");
                    return;
                }
                if(checkRePass()==false){
                    etRePassword.setError("Mật khẩu không khớp");
                    return;
                }
                register(email,password);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
    private void register(String email, String password){
        dialog= new ProgressDialog(getContext());
        dialog.setTitle("Loading...");
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_LONG).show();
                            //start fragment_update_profile
                            FragmentManager manager= getActivity().getSupportFragmentManager();
                            FragmentUpdateProfile fragment= new FragmentUpdateProfile();
                            manager.beginTransaction()
                                    .setCustomAnimations(R.anim.amin_enter_right_to_left,R.anim.amin_exit_right_to_left,
                                            R.anim.anim_enter_left_to_right,R.anim.anim_exit_left_to_right)
                                    .replace(R.id.frame_login_register,fragment)
                                    .addToBackStack(null)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                        }
                        else{
                            Toast.makeText(getContext(), "Đăng kí thất bại", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });

    }
    private boolean checkRePass(){
        String pass= etPassword.getText().toString();
        String rePass= etRePassword.getText().toString();
        if(pass.equals(rePass))
            return true;
        return false;
    }
}
