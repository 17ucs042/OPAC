package com.appsaga.opac1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Reserve extends AppCompatActivity {

    ArrayList<Copies> to_reserve;
    ListView to_reserve_list;
    ReserveAdapter reserveAdapter;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        to_reserve = (ArrayList<Copies>) getIntent().getExtras().getSerializable("toReserve");
        to_reserve_list = findViewById(R.id.to_reserve_list);
        reserveAdapter = new ReserveAdapter(this,to_reserve);
        to_reserve_list.setAdapter(reserveAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        final Button reserve = findViewById(R.id.complete_reserve);

        final DatabaseReference booksRef = databaseReference.child("Books");
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<to_reserve_list.getChildCount();i++)
                {
                    View view = to_reserve_list.getChildAt(i);
                    CheckBox checkBox = view.findViewById(R.id.check);
                    if(checkBox.isChecked())
                    {
                        TextView textView = view.findViewById(R.id.accession);
                        String name = textView.getText().toString();
                        Toast.makeText(Reserve.this,"Reserving "+name,Toast.LENGTH_SHORT).show();
                        Log.d("Keyyyyyy",to_reserve.get(i).getKey()+"  "+to_reserve.get(i).getParent_key());
                        databaseReference.child("Books").child(to_reserve.get(i).getParent_key()).child("copies").child(to_reserve.get(i).getKey()).child("reserved").setValue("Done");
                    }
                }
            }
        });
    }
}
