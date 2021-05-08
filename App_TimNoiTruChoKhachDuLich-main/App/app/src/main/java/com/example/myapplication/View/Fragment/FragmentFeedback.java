package com.example.myapplication.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.AFeedback;
import com.example.myapplication.Model.Feedback;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentFeedback extends Fragment {
    RatingBar ratingBar;
    TextView tvRateStar;
    FirebaseUser currentUser;
    DatabaseReference mdata;
    EditText etFeedback;
    ImageView btSend;
    RecyclerView rcvFeedback;
    ArrayList<Feedback> list;
    AFeedback adapter;
    ImageView btBack;
    public static FragmentFeedback newInstance(String nameResidence) {
        FragmentFeedback fragment = new FragmentFeedback();
        Bundle args = new Bundle();
        args.putSerializable("nameResidence", nameResidence);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_feedback, container, false);
        ratingBar= view.findViewById(R.id.ratingBar);
        tvRateStar= view.findViewById(R.id.tvRateStar);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mdata= FirebaseDatabase.getInstance().getReference();
        etFeedback= view.findViewById(R.id.etFeedback);
        btSend= view.findViewById(R.id.btSend);
        btBack= view.findViewById(R.id.btBack);
        rcvFeedback= view.findViewById(R.id.rcvFeedback);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvRateStar.setText("Đánh giá: "+rating*2);
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etFeedback.getText().toString().equals("")){
                    etFeedback.setError("Không được để trống");
                    return;
                }
                uploadFeedback();
                Toast.makeText(getContext(),"Gửi phản hồi thành công",Toast.LENGTH_SHORT).show();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //hien thi
        list= new ArrayList<>();
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        adapter= new AFeedback(list, getContext());
        rcvFeedback.setLayoutManager(layoutManager);
        rcvFeedback.setAdapter(adapter);

        //rcvFeedback.setHasFixedSize(true);
        //rcvChatMessage.setItemViewCacheSize(10);
        getFeedback();
        return view;
    }
    private void uploadFeedback(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String time= day+"-"+month+"-"+year;
        String content= etFeedback.getText().toString();
        String idUser= currentUser.getUid();
        String name= currentUser.getDisplayName();
        String nameResidence = (String) getArguments().getSerializable("nameResidence");
        String rateStar = tvRateStar.getText().toString().substring(10);
        mdata.child("feedback").push().setValue(new Feedback(idUser,name,nameResidence,rateStar,content,time));
        etFeedback.setText("");
    }
    private void getFeedback(){
        String nameResidence = (String) getArguments().getSerializable("nameResidence");
        mdata.child("feedback").orderByChild("nameResidence").equalTo(nameResidence).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot s:snapshot.getChildren()){
                        String idUser= s.getValue(Feedback.class).getIdUser();
                        String name= s.getValue(Feedback.class).getName();
                        String nameResidence= s.getValue(Feedback.class).getNameResidence();
                        String rateStar= s.getValue(Feedback.class).getRateStar();
                        String content= s.getValue(Feedback.class).getContent();
                        String time= s.getValue(Feedback.class).getDay();
                        list.add(new Feedback(idUser,name,nameResidence,rateStar,content,time));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}