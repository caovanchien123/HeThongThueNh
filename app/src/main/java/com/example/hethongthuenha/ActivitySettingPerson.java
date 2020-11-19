package com.example.hethongthuenha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hethongthuenha.API.PersonAPI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ActivitySettingPerson extends AppCompatActivity {
    private ListView lvSetting;
    private FirebaseAuth mAuth;
    private TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_person);
        mAuth=FirebaseAuth.getInstance();
        lvSetting=findViewById(R.id.lvSettingPerson);
        tvName=findViewById(R.id.tv_name_setting_person);

        tvName.setText(PersonAPI.getInstance().getName());

        ArrayList<String> controls=new ArrayList<>();
        controls.add("Xem trang cá nhân");
        controls.add("Thanh toán hoa hồng");
        controls.add("Đổi ngôn ngữ");
        controls.add("Thay đổi mật khẩu");
        controls.add("Đăng xuất");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,controls);
        lvSetting.setAdapter(adapter);



        lvSetting.setOnItemClickListener((parent, view, position, id) -> {
            if(position==0){
                Intent intent=new Intent(this,ActivityPerson.class);
                intent.putExtra("id_person",PersonAPI.getInstance().getUid());
                startActivity(intent);
            }
            if(position==4){
                mAuth.signOut();
            }
        });
    }
}