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
        districts.add(new District("Qu???n Ba ????nh", Define.baDinh));
        districts.add(new District("Qu???n B???c T??? Li??m", Define.bacTuLiem));
        districts.add(new District("Qu???n C???u Gi???y", Define.cauGiay));
        districts.add(new District("Qu???n ?????ng ??a", Define.dongDa));
        districts.add(new District("Qu???n H?? ????ng", Define.haDong));
        districts.add(new District("Qu???n Hai B?? Tr??ng", Define.haiBaTrung));
        districts.add(new District("Qu???n Ho??n Ki???m", Define.hoanKiem));
        districts.add(new District("Qu???n Ho??ng Mai", Define.hoangMai));
        districts.add(new District("Qu???n Long Bi??n", Define.longBien));
        districts.add(new District("Qu???n Nam T??? Li??m", Define.namTuLiem));
        districts.add(new District("Qu???n T??y H???", Define.tayHo));
        districts.add(new District("Qu???n Thanh Xu??n", Define.thanhXuan));
        districts.add(new District("Th??? x?? S??n T??y", Define.sonTay));
        districts.add(new District("Huy???n Ba V??", Define.baVi));
        districts.add(new District("Huy???n Ch????ng M???", Define.chuongMy));
        districts.add(new District("Huy???n ??an Ph?????ng", Define.danPhuong));
        districts.add(new District("Huy???n ????ng Anh", Define.dongAnh));
        districts.add(new District("Huy???n Gia L??m", Define.giaLam));
        districts.add(new District("Huy???n Ho??i ?????c", Define.hoaiDuc));
        districts.add(new District("Huy???n M?? Linh", Define.meLinh));
        districts.add(new District("Huy???n M??? ?????c", Define.myDuc));
        districts.add(new District("Huy???n Ph?? Xuy??n", Define.phuXuyen));
        districts.add(new District("Huy???n Ph??c Th???", Define.phucTho));
        districts.add(new District("Huy???n Qu???c Oai", Define.quocOai));
        districts.add(new District("Huy???n S??c S??n", Define.socSon));
        districts.add(new District("Huy???n Th???ch Th???t", Define.thachThat));
        districts.add(new District("Huy???n Thanh Oai", Define.thanhOai));
        districts.add(new District("Huy???n Thanh Tr??", Define.thanhTri));
        districts.add(new District("Huy???n Th?????ng T??n", Define.thuongTin));
        districts.add(new District("Huy???n ???ng H??a", Define.ungHoa));
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