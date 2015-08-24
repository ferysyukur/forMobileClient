package com.nim100010482.formobileclient;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nim100010482.formobileclient.connect.JSONParser;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
 
 
public class ProfilActivity extends Activity {
	
//	private static final String AR_ID = "nig";
	JSONArray profil = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        
//        Intent in = getIntent();
        String kode = "KD1-001";
    	String link_url = "http://192.168.26.11/mobile/detail-profil.php?kode="+kode;
        
        JSONParser jParser = new JSONParser();

		JSONObject json = jParser.AmbilJson(link_url);

		try {
			profil = json.getJSONArray("profil");
			
			for(int i = 0; i < profil.length(); i++){
				JSONObject ar = profil.getJSONObject(i);
				

		        TextView nig = (TextView) findViewById(R.id.nig);
		        TextView nm_grj = (TextView) findViewById(R.id.nm_grj);
		        TextView alamat_grj = (TextView) findViewById(R.id.alamat_grj);
		        TextView sjrh_grj = (TextView) findViewById(R.id.sjrh_grj);
		        TextView fasilitas = (TextView) findViewById(R.id.fasilitas);

				String nig_d = ar.getString("id");
				String nm_grj_d = ar.getString("nm_grj");
				String alamat_grj_d = ar.getString("alamat_grj");
				String sjrh_grj_d = ar.getString("sjrh_grj");
				String fasilitas_d = ar.getString("fasilitas");

		        nig.setText(nig_d);
		        nm_grj.setText(nm_grj_d);
		        alamat_grj.setText(alamat_grj_d);
		        sjrh_grj.setText(sjrh_grj_d);
		        fasilitas.setText(fasilitas_d);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
    }
}