package com.example.myapplication.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.AdapterSliderView;
import com.example.myapplication.Model.BookingReference;
import com.example.myapplication.Model.GetJsonData;
import com.example.myapplication.Model.Residence;
import com.example.myapplication.Model.UserProfile;
import com.example.myapplication.R;
import com.example.myapplication.SQLFavoriteItem;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class
DetailItemAct extends AppCompatActivity {
    SliderView imageSlider;
    TextView tvNameItem, tvAddressItem, tvDescriptionItem, tvRateStar, tvRate;
    LinearLayout layoutDescription, layoutComment;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;
    ImageView iconAddress;
    Button btBooking;
    SharedPreferences sharedPref;
    DatabaseReference mData;
    FirebaseUser user;
    Residence residence=new Residence();
    private SQLFavoriteItem sql;
    final int[] checkButton = {0};
    private ImageView btBack, btLove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        anhXa();
        setRate();
        new GetDataDetail(residence.getWebLink()).execute();
        inVisibility();
        tvAddressItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i= new Intent(getBaseContext(),Main2Act.class);
                i.putExtra("IdFragment",2);
                i.putExtra("nameResidence",tvNameItem.getText().toString());
                startActivity(i);
                overridePendingTransition(R.anim.anim_enter_left_to_right,R.anim.anim_exit_left_to_right);*/
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+tvAddressItem.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        btBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBookingReference();
            }
        });
        layoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    Intent i= new Intent(getBaseContext(),Main2Act.class);
                    i.putExtra("IdFragment",5);
                    i.putExtra("nameResidence",tvNameItem.getText().toString());
                    startActivity(i);
                    overridePendingTransition(R.anim.anim_enter_left_to_right,R.anim.anim_exit_left_to_right);

                }
                else dialog();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkButton[0] ==0){
                    Toast.makeText(getBaseContext(),"Yêu thích",Toast.LENGTH_SHORT).show();
                    btLove.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red);
                    sql.insertItem(residence);
                    checkButton[0] =1;
                }
                else {
                    Toast.makeText(getBaseContext(),"Hủy yêu thích",Toast.LENGTH_SHORT).show();
                    btLove.setBackgroundResource(R.drawable.ic_baseline_favorite_24_white);
                    sql.deleteItem(residence.getName());
                    checkButton[0] =0;
                }
            }
        });
    }
    private void anhXa(){
        tvNameItem = findViewById(R.id.tvNameItem);
        tvAddressItem = findViewById(R.id.tvAddressItem);
        tvDescriptionItem = findViewById(R.id.tvDescriptionItem);
        layoutDescription = findViewById(R.id.layoutDescription);
        layoutComment = findViewById(R.id.layoutComment);
        progressBar = findViewById(R.id.progressBar);
        shimmerFrameLayout = findViewById(R.id.shimmerSliderView);
        iconAddress = findViewById(R.id.iconAddress);
        imageSlider= findViewById(R.id.cover);
        tvRateStar= findViewById(R.id.tvRateStar);
        tvRate= findViewById(R.id.tvRate);
        btBooking= findViewById(R.id.btBooking);
        btBack= findViewById(R.id.btBack);
        btLove= findViewById(R.id.btLove);
        user= FirebaseAuth.getInstance().getCurrentUser();
        sql=new SQLFavoriteItem(getBaseContext());
        sharedPref= getBaseContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        sql=new SQLFavoriteItem(getBaseContext());
        residence=(Residence) getIntent().getSerializableExtra("residence");
    }
    private void loadSliderView(List<String> listSliderView){
        //sliderView
        AdapterSliderView adapterSliderView= new AdapterSliderView(listSliderView,getBaseContext());
        imageSlider.setSliderAdapter(adapterSliderView);
        adapterSliderView.notifyDataSetChanged();
    }
    class GetDataDetail extends GetJsonData {

        public GetDataDetail(String url) {
            super(url);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadSliderView(getListImage());
            Document document= Jsoup.parse(getResult());
            if(document!=null){
                tvNameItem.setText(document.selectFirst("h2#hp_hotel_name").text());
                tvAddressItem.setText(document.selectFirst("p#showMap2> span").text());
                Element des_content= document.selectFirst("div.hp_desc_main_content ");
                //Element des_main_content= des_content.selectFirst("div#property_description_content ");
                Elements list= des_content.select("p");
                for(Element e:list){
                    String text= tvDescriptionItem.getText().toString();
                    tvDescriptionItem.setText(text+e.text()+"\n"+"\n");
                }

            }
            if(getListImage()!=null)
                visibility();
        }
    }
    private void inVisibility(){
        layoutDescription.setVisibility(View.INVISIBLE);
        layoutComment.setVisibility(View.INVISIBLE);
        tvNameItem.setVisibility(View.INVISIBLE);
        tvAddressItem.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        iconAddress.setVisibility(View.INVISIBLE);
    }
    private void visibility(){
        layoutDescription.setVisibility(View.VISIBLE);
        layoutComment.setVisibility(View.VISIBLE);
        tvNameItem.setVisibility(View.VISIBLE);
        tvAddressItem.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
        iconAddress.setVisibility(View.VISIBLE);
    }
    private void setRate(){
        if(residence.getRateStar()!=null)
            tvRateStar.setText(residence.getRateStar().replace(",","."));
        tvRate.setText(residence.getRate());
    }
    private void updateBookingReference(){
        if(user!=null){
            mData= FirebaseDatabase.getInstance().getReference();
            String dateIn= sharedPref.getString("dayStart","")+"-"+sharedPref.getString("monthStart", "")+"-2021";
            String dateOut = sharedPref.getString("dayFinish", "")+"-"+sharedPref.getString("monthFinish", "")+"-2021";
            String nameResidence = tvNameItem.getText().toString();
            String countRoom = String.valueOf(sharedPref.getInt("room",1));
            String countPeople = String.valueOf(sharedPref.getInt("tourist",1));
            final String[] phone = {null};
            String email = user.getEmail();
            mData.child("user").orderByChild("id").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot e:snapshot.getChildren()){
                            phone[0] =e.getValue(UserProfile.class).getPhoneNumber();
                        }
                    }
                    BookingReference b= new BookingReference(dateIn,dateOut,email, phone[0],nameResidence,countRoom,countPeople,"Chờ duyệt");
                    mData.child("bookingreference").push().setValue(b);
                    Toast.makeText(getBaseContext(),"Đặt thành công",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {
            dialog();
        }
    }
    private  void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn chưa đăng nhập").setMessage("Đăng nhập để tiếp tục!!");
        builder.setCancelable(true);
        builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getBaseContext(),Main2Act.class));
            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user= FirebaseAuth.getInstance().getCurrentUser();
    }
}