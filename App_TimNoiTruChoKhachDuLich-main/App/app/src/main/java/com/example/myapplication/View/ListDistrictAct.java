package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.Adapter.AItemDistrict;
import com.example.myapplication.Model.Define;
import com.example.myapplication.IOnClickDistrict;
import com.example.myapplication.Model.District;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ListDistrictAct extends AppCompatActivity {
    AItemDistrict adapter;
    RecyclerView rcvDistrict;
    ImageView btBack;
    EditText etSearch;
    ArrayList<District> districtArrayList;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_district);
        rcvDistrict= findViewById(R.id.rcvDistricts);
        btBack= findViewById(R.id.btBack);
        etSearch= findViewById(R.id.etChoseList);
        sharedPref= getBaseContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        districtArrayList= createList();

        adapter= new AItemDistrict(getBaseContext(),districtArrayList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        rcvDistrict.setLayoutManager(layoutManager);
        rcvDistrict.setAdapter(adapter);
        adapter.setiOnClickDistrict(new IOnClickDistrict() {
            @Override
            public void onClickItem(District district) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("url", district.getLink());
                editor.putString("nameDistrict", district.getName());
                editor.commit();
                finish();
                overridePendingTransition(R.anim.anim_enter_left_to_right, R.anim.anim_exit_left_to_right);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_enter_left_to_right, R.anim.anim_exit_left_to_right);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private ArrayList<District> createList(){
        ArrayList<District> districts= new ArrayList<>();
        districts.add(new District("Quận Ba Đình", Define.baDinh));
        districts.add(new District("Quận Bắc Từ Liêm", Define.bacTuLiem));
        districts.add(new District("Quận Cầu Giấy", Define.cauGiay));
        districts.add(new District("Quận Đống Đa", Define.dongDa));
        districts.add(new District("Quận Hà Đông", Define.haDong));
        districts.add(new District("Quận Hai Bà Trưng", Define.haiBaTrung));
        districts.add(new District("Quận Hoàn Kiếm", Define.hoanKiem));
        districts.add(new District("Quận Hoàng Mai", Define.hoangMai));
        districts.add(new District("Quận Long Biên", Define.longBien));
        districts.add(new District("Quận Nam Từ Liêm", Define.namTuLiem));
        districts.add(new District("Quận Tây Hồ", Define.tayHo));
        districts.add(new District("Quận Thanh Xuân", Define.thanhXuan));
        districts.add(new District("Thị xã Sơn Tây", Define.sonTay));
        districts.add(new District("Huyện Ba Vì", Define.baVi));
        districts.add(new District("Huyện Chương Mỹ", Define.chuongMy));
        districts.add(new District("Huyện Đan Phượng", Define.danPhuong));
        districts.add(new District("Huyện Đông Anh", Define.dongAnh));
        districts.add(new District("Huyện Gia Lâm", Define.giaLam));
        districts.add(new District("Huyện Hoài Đức", Define.hoaiDuc));
        districts.add(new District("Huyện Mê Linh", Define.meLinh));
        districts.add(new District("Huyện Mỹ Đức", Define.myDuc));
        districts.add(new District("Huyện Phú Xuyên", Define.phuXuyen));
        districts.add(new District("Huyện Phúc Thọ", Define.phucTho));
        districts.add(new District("Huyện Quốc Oai", Define.quocOai));
        districts.add(new District("Huyện Sóc Sơn", Define.socSon));
        districts.add(new District("Huyện Thạch Thất", Define.thachThat));
        districts.add(new District("Huyện Thanh Oai", Define.thanhOai));
        districts.add(new District("Huyện Thanh Trì", Define.thanhTri));
        districts.add(new District("Huyện Thường Tín", Define.thuongTin));
        districts.add(new District("Huyện Ứng Hòa", Define.ungHoa));
        return districts;
    }
    private void filter(String text){
        ArrayList<District> filterList= new ArrayList<>();
        for (District item : districtArrayList) {
                if (item.getName().toLowerCase().contains((text.toLowerCase()))) {
                    filterList.add(item);
                }
        }
        if(filterList.size()==0)    filterList.add(new District("",""));
        adapter.filterList(filterList);
    }
}