package com.nim100010482.formobileclient;
 
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
 
 
public class LokasiActivity extends ListActivity {
    String [] teman ={"Andra", "Dina", "Edo", "Julia"};
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
     
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teman));
         
    }
}