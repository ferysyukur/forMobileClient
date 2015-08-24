package com.nim100010482.formobileclient;

import com.nim100010482.formobileclient.connect.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailHome  extends Activity {
	
	private static final String AR_ID = "id";
	JSONArray jadwal = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_home);
        
        Intent in = getIntent();
        String kode = in.getStringExtra(AR_ID);
    	String link_url = "http://mobile-keudps.ml/mobile/detail-jadwal.php?kode="+kode;
        
        JSONParser jParser = new JSONParser();

		JSONObject json = jParser.AmbilJson(link_url);

		try {
			jadwal = json.getJSONArray("jadwal");
			
			for(int i = 0; i < jadwal.length(); i++){
				JSONObject ar = jadwal.getJSONObject(i);
				

		        TextView nm_jadwal = (TextView) findViewById(R.id.judul);
		        TextView waktu = (TextView) findViewById(R.id.detail);
		        TextView ket_jadwal = (TextView) findViewById(R.id.isi);

				String nm_jadwal_d = ar.getString("nm_jadwal")+" - "+ar.getString("nm_grj");
				String waktu_d = ar.getString("waktu")+" - "+ar.getString("nm_romo");
				String ket_jadwal_d = ar.getString("ket_jadwal");

		        nm_jadwal.setText(nm_jadwal_d);
		        waktu.setText(waktu_d);
		        ket_jadwal.setText(ket_jadwal_d);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
    }
}