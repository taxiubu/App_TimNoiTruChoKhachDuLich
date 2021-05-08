 package com.example.myapplication.View.Fragment;

 import android.content.Context;
 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.os.Bundle;
 import android.text.SpannableString;
 import android.text.method.LinkMovementMethod;
 import android.text.style.URLSpan;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.ProgressBar;
 import android.widget.RelativeLayout;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.cardview.widget.CardView;
 import androidx.fragment.app.Fragment;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.example.myapplication.Adapter.AItemHistoryResidence;
 import com.example.myapplication.Adapter.AItemResidence;
 import com.example.myapplication.ConnectReceiver;
 import com.example.myapplication.IOnClickItemPlace;
 import com.example.myapplication.Model.GetJsonData;
 import com.example.myapplication.Model.Residence;
 import com.example.myapplication.R;
 import com.example.myapplication.SQLHistoryItem;
 import com.example.myapplication.View.DetailItemAct;
 import com.example.myapplication.View.ListDistrictAct;
 import com.example.myapplication.View.Main2Act;

 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;


 public class FragmentHome extends Fragment {
    TextView tvNews,tvDatePicker,tvRoom;
    CardView layoutNotification;
    SharedPreferences sharedPref;
    EditText et;
    Button btFind;
    String urlWeb;
    ImageView logo;
    int countRoom=1, countDay=1;
     RecyclerView rcvResidences, rcvHistory;
     AItemResidence adapter;
     ArrayList<Residence> residences;
     ProgressBar progressBar;
     SQLHistoryItem sqlHistoryItem;
     RelativeLayout layoutHistory;
     public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false); // Inflate the layout for this fragment
        sharedPref= getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        tvNews= view.findViewById(R.id.tvNews);
        tvDatePicker= view.findViewById(R.id.tvDatePicker);
        tvRoom= view.findViewById(R.id.tvRoom);
        btFind= view.findViewById(R.id.btFind);
        layoutNotification = view.findViewById(R.id.layoutNotification);
        layoutHistory = view.findViewById(R.id.layoutHistory);
        et= view.findViewById(R.id.etSearch);
        rcvResidences= view.findViewById(R.id.rcvResidence);
        rcvHistory= view.findViewById(R.id.rcvHistory);
        logo= view.findViewById(R.id.logo);
        sqlHistoryItem= new SQLHistoryItem(getContext());
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ListDistrictAct.class));
            }
        });

        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Main2Act.class);
                i.putExtra("IdFragment",3);
                startActivity(i);
            }
        });
        tvRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Main2Act.class);
                i.putExtra("IdFragment",4);
                startActivity(i);
            }
        });
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectReceiver.isConnected()){
                    loadBar();
                    countRoom= getCountRoom();
                    if(urlWeb!=null)
                        new GetData(customURL(urlWeb)).execute();
                }
                else Toast.makeText(getContext(),"Không có kết nối mạng",Toast.LENGTH_SHORT).show();

            }
        });
        customNewsText();
        createHistoryList();
        return view;
    }
    private void createHistoryList(){
         ArrayList<Residence> list= (ArrayList<Residence>) sqlHistoryItem.getAllItem();
        AItemHistoryResidence aItemHistoryResidence= new AItemHistoryResidence(getContext(),list,countRoom);
        RecyclerView.LayoutManager manager= new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcvHistory.setLayoutManager(manager);
        rcvHistory.setAdapter(aItemHistoryResidence);
        aItemHistoryResidence.setIOnClickItemPlace(new IOnClickItemPlace() {
            @Override
            public void onClickItem(Residence residence) {
                if(ConnectReceiver.isConnected()){
                    Intent i= new Intent(getContext(), DetailItemAct.class);
                    i.putExtra("residence",residence);
                    startActivity(i);
                }else Toast.makeText(getContext(),"Không có kết nối mạng",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void customNewsText(){
        //thông báo
        CharSequence ch= getText(R.string.news);
        SpannableString sp= new SpannableString(ch);
        sp.setSpan(new URLSpan("https://soyte.hanoi.gov.vn/tin-tuc-su-kien"),192,ch.length(),0);
        tvNews.setMovementMethod(LinkMovementMethod.getInstance());
        tvNews.setText(sp);
    }
     private void setDate() throws ParseException {
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(System.currentTimeMillis());
         int year = calendar.get(Calendar.YEAR);
         int month = calendar.get(Calendar.MONTH)+1;
         int day = calendar.get(Calendar.DAY_OF_MONTH);
         String dayStart = sharedPref.getString("dayStart", "1");
         String dayFinish = sharedPref.getString("dayFinish", "2");
         String monthStart = sharedPref.getString("monthStart", "1");
         String monthFinish = sharedPref.getString("monthFinish", "1");
         SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
         Date date1= sdf.parse(year+"-"+month+"-"+day);
         Date date2= sdf.parse(year+"-"+monthStart+"-"+dayStart);

         if(date1.compareTo(date2)>0)
             tvDatePicker.setText(day + " Th" +month+" - "+(day+1)+" Th"+month);
         else tvDatePicker.setText(dayStart + " Th"+monthStart+" - " +dayFinish+ " Th"+ monthFinish);
     }
    private void getRoomAndTourist(){
        int room= sharedPref.getInt("room",1);
        int tourist= sharedPref.getInt("room",1);
        tvRoom.setText(tourist+" khách, "+room+" phòng");
    }
    private int getCountDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayStart = Integer.parseInt(sharedPref.getString("dayStart", String.valueOf(day)));
        int dayFinish = Integer.parseInt(sharedPref.getString("dayFinish", String.valueOf(day+1)));
        int monthStart = Integer.parseInt(sharedPref.getString("monthStart", String.valueOf(month)));
        int monthFinish = Integer.parseInt(sharedPref.getString("monthFinish", String.valueOf(month)));
        countDay= dayFinish-dayStart;
        if(monthStart==monthFinish) return countDay;
        if(monthStart==1&&monthStart==3&&monthStart==5&&monthStart==7&&monthStart==8&&monthStart==10&&monthStart==12)
            countDay+=31;
        else if(monthStart==2)
            countDay+=28;
        else countDay+=30;
        return countDay;
    }
    private int getCountRoom(){
        int room= sharedPref.getInt("room",1);
        return room;
    }
    private void getDistrict(){
         String name= sharedPref.getString("nameDistrict","");
         et.setText(name);
     }
     @Override
     public void onResume() {
         super.onResume();
         try {
             setDate();
         } catch (ParseException e) {
             e.printStackTrace();
         }
         getRoomAndTourist();
         getDistrict();
         urlWeb= sharedPref.getString("url","");
     }
     private void loadBar(){
         layoutNotification.setVisibility(View.INVISIBLE);
         layoutHistory.setVisibility(View.INVISIBLE);
         progressBar.setVisibility(View.VISIBLE);
         //logo.setVisibility(View.INVISIBLE);
     }
     class GetData extends GetJsonData {

         public GetData(String url) {
             super(url);
         }

         @Override
         protected void onPostExecute(Void aVoid) {
             super.onPostExecute(aVoid);
             residences= (ArrayList<Residence>) getResidenceList();
             adapter= new AItemResidence(getContext(), residences,countRoom);
             RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
             rcvResidences.setLayoutManager(layoutManager);
             rcvResidences.setAdapter(adapter);
             adapter.setIOnClickItemPlace(new IOnClickItemPlace() {
                 @Override
                 public void onClickItem(Residence residence) {
                     sqlHistoryItem.insertItem(residence);
                     Intent i= new Intent(getContext(), DetailItemAct.class);
                     i.putExtra("residence",residence);
                     startActivity(i);
                 }
             });
             adapter.notifyDataSetChanged();
             if(residences!=null)
                 progressBar.setVisibility(View.INVISIBLE);
         }
     }
     private String customURL(String s){
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(System.currentTimeMillis());
         int month = calendar.get(Calendar.MONTH)+1;
         int day = calendar.get(Calendar.DAY_OF_MONTH);
         int year= calendar.get(Calendar.YEAR);
         String dayStart= sharedPref.getString("dayStart",String.valueOf(day));
         String dayFinish= sharedPref.getString("dayFinish",String.valueOf(day+1));
         String monthStart= sharedPref.getString("monthStart",String.valueOf(month));
         String monthFinish= sharedPref.getString("monthFinish",String.valueOf(month));
         return s.replace("checkin_year=2021&checkin_month=4&checkin_monthday=24&checkout_year=2021&checkout_month=4&checkout_monthday=25","checkin_year="+year+"&checkin_month="+monthStart+"&checkin_monthday="+dayStart+"&checkout_year="+year+"&checkout_month="+monthFinish+"&checkout_monthday="+dayFinish);
     }
 }