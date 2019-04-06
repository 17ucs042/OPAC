package com.appsaga.opac1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Reserve extends AppCompatActivity {

    ArrayList<Copies> to_reserve;
    ListView to_reserve_list;
    ReserveAdapter reserveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        to_reserve = (ArrayList<Copies>) getIntent().getExtras().getSerializable("toReserve");
        to_reserve_list = findViewById(R.id.to_reserve_list);
        reserveAdapter = new ReserveAdapter(this,to_reserve);
        to_reserve_list.setAdapter(reserveAdapter);
    }
}
