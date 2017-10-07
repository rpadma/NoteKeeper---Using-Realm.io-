package com.etuloser.padma.rohit.inclass07;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {
//    public CustomDatabaseManager dm;
  //  private DatabaseReference mData;
    Realm realm;
    Spinner pspinner;
    RecyclerView lv;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    List<CustomObject> notelist=new ArrayList<CustomObject>();
    List<CustomObject> notelistt=new ArrayList<CustomObject>();
EditText edxnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm=Realm.getDefaultInstance();

        //dm = new CustomDatabaseManager(this);

        pspinner=(Spinner)findViewById(R.id.spinner);
        edxnote=(EditText)findViewById(R.id.NoteText);

        lv=(RecyclerView)findViewById(R.id.mainListView);
        lv.setHasFixedSize(true);
        lv.setDrawingCacheEnabled(true);
        lv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        spinnerArray.add("Priority");
        spinnerArray.add("High");
        spinnerArray.add("Medium");
        spinnerArray.add("Low");

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        pspinner.setAdapter(spinnerArrayAdapter);

        Realm.init(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        setview();
/*
        mData = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dref = mData.child("Notes");
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CustomObject co = dataSnapshot.getValue(CustomObject.class);
                notelist.add(co);
                setview();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.showall) {

            setview();
            //Toast.makeText(this,"Showall",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id==R.id.showcompleted)
        {setviewbycomplete();
            return true;
        }
        else if(id==R.id.showpending)
        {
            setviewbypending();
            return true;
        }
        else if(id==R.id.sortbyPriority){
            setviewbypriority();
             return true;

        }
        else if(id==R.id.sortbytime)
        {
            setviewbytime();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getdatabase()
    {
        RealmResults<CustomObject> result=realm.where(CustomObject.class).findAllAsync();
        result.load();

        notelist=result;
    }



    public void AddNote(View v)
    {

        Realm realm=Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                CustomObject n = bgRealm.createObject(CustomObject.class);
                n.setNote(edxnote.getText().toString().trim());
                n.setPriority(String.valueOf(pspinner.getSelectedItemId()));
                n.set_id(UUID.randomUUID().toString());
                // PrettyTime p = new PrettyTime();
                n.setUpdate_time(String.valueOf(System.currentTimeMillis()));
                n.setStatus("0");

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                showStatus("Added A note");
                edxnote.setText("");
                pspinner.setSelection(0);

                setview();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                showStatus("Error occurred- Adding  A note");
            }
        });
         /*
        if(edxnote.getText().length()>0 && pspinner.getSelectedItemId()>0) {

            CustomObject co=new CustomObject();
            co.setNote(edxnote.getText().toString().trim());
            co.setPriority(String.valueOf(pspinner.getSelectedItemId()));
           // PrettyTime p = new PrettyTime();
            co.setUpdate_time(String.valueOf(System.currentTimeMillis()));
            co.setStatus("0");
            mData = FirebaseDatabase.getInstance().getReference();
            DatabaseReference dref = mData.child("Notes").push();
            String key = dref.getKey();
            co.setUid(key);
            dref.setValue(co);

            //dm.save(co);
            //setview();
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show();
            edxnote.setText("");
            pspinner.setSelection(0);
            }
        else
        {
            Toast.makeText(this, "Please proper enter note / select a priority", Toast.LENGTH_SHORT).show();

        }
*/
    }


    private void showStatus(String txt) {
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }


    public void setviewbypriority()
    {
        RealmResults<CustomObject> result=realm.where(CustomObject.class).findAllSorted("priority", Sort.ASCENDING);
        result.load();

        notelist=result;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecycleAdapter ca=new RecycleAdapter(this,notelist);

        lv.setLayoutManager(linearLayoutManager);
        lv.setAdapter(ca);


        ca.notifyDataSetChanged();

    }


    public void setviewbytime()
    {

        RealmResults<CustomObject> result=realm.where(CustomObject.class).findAllSorted("update_time", Sort.DESCENDING);
        result.load();

        notelist=result;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecycleAdapter ca=new RecycleAdapter(this,notelist);

        lv.setLayoutManager(linearLayoutManager);
        lv.setAdapter(ca);

    }


    public void setviewbypending()
    {

        RealmResults<CustomObject> result=realm.where(CustomObject.class).equalTo("status","0").findAllAsync();
        result.load();

        notelist=result;


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecycleAdapter ca=new RecycleAdapter(this,notelist);

        lv.setLayoutManager(linearLayoutManager);

        lv.setAdapter(ca);
    }

    public void setviewbycomplete()
    {
        //notelist=(ArrayList<CustomObject>) dm.getallbycompleted();
        RealmResults<CustomObject> result=realm.where(CustomObject.class).equalTo("status","1").findAllAsync();
        result.load();

        notelist=result;
        //Collections.sort(objectList, CustomObject.comparatorPrior);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecycleAdapter ca=new RecycleAdapter(this,notelist);

        lv.setLayoutManager(linearLayoutManager);
        lv.setAdapter(ca);

    }



    public void setview()
    {

        getdatabase();
        //notelist=(ArrayList<CustomObject>) dm.getAll();
        notelistt.clear();
        notelistt.addAll(notelist);
        if (notelistt.size()>0){

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            RecycleAdapter ca=new RecycleAdapter(this,notelist);

            lv.setLayoutManager(linearLayoutManager);
            lv.setAdapter(ca);

        }

    }

    public void updateStatus(final CustomObject g, String pos){

        if (notelistt.contains(g)){

final String id=g.get_id();
            final String posi=pos;
            Realm realm=Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {


                    CustomObject co = bgRealm.where(CustomObject.class).equalTo("_id", id).findFirst();

                    co.setStatus(posi);
                    co.setUpdate_time(String.valueOf(System.currentTimeMillis()));


                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // Transaction was a success.
                    showStatus("Updated the status of note");
                    setview();

                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    showStatus("Error occurred- Adding  A note");
                }
            });

        }


    }

    public void deletenote(CustomObject g, int pos){

        final String id=g.get_id();
        if (notelistt.contains(g)){


            Realm realm=Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {


                    CustomObject co = bgRealm.where(CustomObject.class).equalTo("_id", id).findFirst();
                    co.deleteFromRealm();


                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // Transaction was a success.
                    showStatus("Deleted the note");
                    setview();

                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    showStatus("Error occurred- Adding  A note");
                }
            });

        }


    }

}
