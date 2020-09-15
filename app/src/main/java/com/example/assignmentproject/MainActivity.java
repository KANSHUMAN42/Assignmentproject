package com.example.assignmentproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.assignmentproject.Model.Myadapter;
import com.example.assignmentproject.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    final int Item_load_count=11;
    int total_item=0,last_visitem;
    Myadapter myadapter;
    boolean isLoading=false,isMaxdata=false;
    String last_node="";
    String last_key="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       recyclerView=(RecyclerView)findViewById(R.id.myrecycler);
        getLastKeyfromFirebase();
        final LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        myadapter =new Myadapter(this);
        recyclerView.setAdapter(myadapter);
        getUsers();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                total_item=layoutManager.getItemCount();
                last_visitem=layoutManager.findLastVisibleItemPosition();
                if(!isLoading && total_item<=((last_visitem+Item_load_count))){
                    getUsers();
                    isLoading=true;
                }
            }
        });


    }

    private void getUsers() {
        if(!isMaxdata){
            Query query;
            if(TextUtils.isEmpty(last_node)){
                query=FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .orderByKey()
                        .startAt(last_node)
                        .limitToFirst(Item_load_count);
            }
            else{
                query=FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .orderByKey()
                        .limitToFirst(Item_load_count);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            List<User> newUsers = new ArrayList<>();
                            User man =new User("anshuman","is","a","good");
                            newUsers.add(man);

                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                newUsers.add(userSnapshot.getValue(User.class));
                            }
                            last_node = newUsers.get(newUsers.size() - 1).getId();
                            if(!last_node.equals(last_key)){
                                newUsers.remove(newUsers.size()-1);

                            }else {
                                last_node = "end";
                            }
                            myadapter.addAll(newUsers);
                            isLoading=false;
                        }
                        else{
                            isLoading=false;
                            isMaxdata=true;

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                     isLoading=false;

                    }
                });
            }
        }



    }

    private void getLastKeyfromFirebase() {
        Query getLastkey= FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .orderByKey()
                .limitToLast(1);
         getLastkey.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for(DataSnapshot lastkey:dataSnapshot.getChildren())
                     last_key=lastkey.getKey();

                              }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Toast.makeText(getApplicationContext(),"error occured in fetching",Toast.LENGTH_SHORT).show();
             }
         });
    }
}





















