package com.appsaga.opac1;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchBooks extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    BookLoading bookload;

    private static final String TAG = "ViewDatabase";
    EditText findBooks;
    ImageView search;
    Handler handler;

    TextView userEmail,userName;
    ImageView userPic;
    ArrayList<BookInformation> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);
        dl = (DrawerLayout) findViewById(R.id.drawer);
        t = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

bookload=(BookLoading)findViewById(R.id.bookloading);
        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.issuedetails) {
                    //startActivity(new Intent(SearchBooks.this,IssueDetails.class));
                } else {
                    FirebaseAuth.getInstance().signOut();
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    if (status.isSuccess()){
                                        gotoMainActivity();
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Session not close",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                return true;
            }

        });

        View headerView = nv.getHeaderView(0);
        userEmail =  headerView.findViewById(R.id.user_id);
        userName =  headerView.findViewById(R.id.user_name);
         userPic = headerView.findViewById(R.id.user_pic);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ImageView filter = findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner.performClick();
            }
        });

        findBooks=findViewById(R.id.find_books);
        search=findViewById(R.id.search);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final DatabaseReference booksRef = databaseReference.child("Books");
        //booksRef.push().child("001").setValue("Iridov");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String book_name=findBooks.getText().toString().trim();
               if(findBooks.getText().toString().trim().equalsIgnoreCase(""))
              {
                  findBooks.setError("Please Enter");
               }
              else{
                  bookload.setVisibility(View.VISIBLE);
                   bookload.start();

                booksRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        books = new ArrayList<>();
                        books.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            BookInformation bookInformation = ds.getValue(BookInformation.class);
                            //bookInformation.setName(ds.getValue(BookInformation.class).getName());
                            //bookInformation.setName(ds.child("Book").getValue(BookInformation.class).getName());

                            if(book_name.equalsIgnoreCase(bookInformation.getName()) || bookInformation.getName().toUpperCase().contains(book_name.toUpperCase())) {

                                Log.d(TAG, "showData: name: " + bookInformation.name +"and"+ bookInformation.getId());
                                Log.d(TAG,"BOOKDATA: "+ book_name);
                                books.add(bookInformation);
                                //Toast.makeText(SearchBooks.this, "Name: " + bookInformation.getName(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(SearchBooks.this, "Id: " + bookInformation.getId(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                           public void run() {
                                bookload.stop();
                                bookload.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(SearchBooks.this,Books.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("books",books);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                       },2500);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });}


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            userName.setText(account.getDisplayName());
            userEmail.setText(account.getEmail());
            try{
                Glide.with(this).load(account.getPhotoUrl()).into(userPic);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            finish();
        }
    }
    private void gotoMainActivity(){
       Intent intent=new Intent(this,SignIn.class);
        startActivity(intent);
   }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



}