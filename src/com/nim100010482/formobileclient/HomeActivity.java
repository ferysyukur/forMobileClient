package com.nim100010482.formobileclient;
 
import java.util.ArrayList;
import java.util.HashMap;

import com.nim100010482.formobileclient.connect.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HomeActivity extends ListActivity {

	private static String link_url = "http://mobile-keudps.ml/mobile/jadwal-json.php";
	
	private static final String AR_ID = "id";
	private static final String AR_NM_JADWAL = "nm_jadwal";
	private static final String AR_KET_JADWAL = "ket_jadwal";

	JSONArray jadwal = null;
	ArrayList<HashMap<String, String>> daftar_jadwal = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		

		JSONParser jParser = new JSONParser();

		JSONObject json = jParser.AmbilJson(link_url);

		try {
			jadwal = json.getJSONArray("jadwal");
			
			for(int i = 0; i < jadwal.length(); i++){
				JSONObject ar = jadwal.getJSONObject(i);
				
				String id = ar.getString(AR_ID);
				String judul = ar.getString(AR_NM_JADWAL);
				String content = ar.getString(AR_KET_JADWAL)
						.substring(0,10)+"...(baca selengkapnya)";
				
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(AR_ID, id);
				map.put(AR_NM_JADWAL, judul);
				map.put(AR_KET_JADWAL, content);

				daftar_jadwal.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.adapter_listview();
	}
	
	public void adapter_listview() {

		ListAdapter adapter = new SimpleAdapter(this, daftar_jadwal,
				R.layout.list_home,
				new String[] { AR_NM_JADWAL, AR_KET_JADWAL, AR_ID}, new int[] {
						R.id.judul, R.id.content, R.id.kode});

		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String kode = ((TextView) view.findViewById(R.id.kode)).getText().toString();
				
				Intent in = new Intent(HomeActivity.this, DetailHome.class);
				in.putExtra(AR_ID, kode);
				startActivity(in);

			}
		});


		
	}
}