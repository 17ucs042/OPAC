package com.appsaga.opac1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class IssueDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);

        ArrayList<String> accession = (ArrayList<String>)getIntent().getExtras().getSerializable("books");
        TextView acc = findViewById(R.id.accession);
        acc.setText(accession.get(0));
    }
}
