package com.example.myapplication.View.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static androidx.constraintlayout.widget.Constraints.TAG;
public class FragmentLogin extends Fragment {
    Button btLogin, btRegister;
    EditText etEmail, etPassword;
    FirebaseAuth mAuth;
    public static FragmentLogin newInstance() {
        Bundle args = new Bundle();
        FragmentLogin fragment = new FragmentLogin();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        btLogin= view.findViewById(R.id.btLogin);
        btRegister= view.findViewById(R.id.btRegister);
        etEmail= view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        mAuth=FirebaseAuth.getInstance();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmail.getText().toString();
                String password= etPassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email không được để trống");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Mật khẩu không được để trống");
                    return;
                }
                logIn(email,password);
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getActivity().getSupportFragmentManager();
                FragmentRegister fragmentRegister= new FragmentRegister();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.amin_enter_right_to_left,R.anim.amin_exit_right_to_left,
                                R.anim.anim_enter_left_to_right,R.anim.anim_exit_left_to_right)
                        .replace(R.id.frame_login_register,fragmentRegister)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        return view;
    }
    private void logIn(String email, String password) {
        final ProgressDialog dialog= new ProgressDialog(getContext());
        dialog.setTitle("Loading...");
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            getActivity().finish();
                        }
                        else Toast.makeText(getContext(),"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
