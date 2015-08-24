package com.nim100010482.formobileclient;

import java.text.DecimalFormat;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;







import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nim100010482.formobileclient.connect.JSONHelper;

import android.support.v4.app.FragmentActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
//import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
//import android.widget.SpinnerAdapter;
//import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
 
public class PencarainLokasiActivity extends FragmentActivity implements OnInfoWindowClickListener, OnCheckedChangeListener, OnItemSelectedListener {
	
	private GoogleMap map;
	RadioGroup rg;
	private JSONHelper json;
	private JSONObject jObject;
	private JSONArray jsonarray;
	private ProgressDialog pDialog;
	private ArrayList<Lokasi> listLokasi;
	private ArrayList<String> lokasi;
//	private ArrayAdapter<String> adapter;
	private final String URL_API = "http://mobile-keudps.ml/mobile/lokasi.php";
	double latitude, longitude;
	double srcstart= -8.688874;
	double dntend= 115.242366;
	private LatLng myLocation;
	public static final String	KEY_ID	= "id_lokasi";
	public static final String	KEY_NAMA	= "nm_grj";
	public static final String	KEY_ALAMAT	= "alamat_grj";
	public static final String	KEY_KET_LOKASI	= "ket_lokasi";
	public static final String	KEY_LAT_TUJUAN	= "latitude";
	public static final String	KEY_LNG_TUJUAN	= "longitude";
	public static final String	KEY_LAT_ASAL	= "lat_asal";
	public static final String	KEY_LNG_ASAL	= "lng_asal";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lokasi_map);
		
		json = new JSONHelper();
		
		new AsynTaskMain().execute();
		new GetCategories().execute();
		
		setupMapIfNeeded();
		
	    rg=(RadioGroup)findViewById(R.id.viewRG);
	    rg.setOnCheckedChangeListener(this);

	}
	
	private void setupMapIfNeeded()
	{
		if (map == null)
		{
			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

			if (map != null)
			{
				setupMap();
			}
		}

	}
	
	private void setupMap()
	{
		map.setMyLocationEnabled(true);
		map.setOnInfoWindowClickListener(this);
		moveToMyLocation();
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are Here").icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));
	}
	
	private void moveToMyLocation()
	{
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);
		
		if(location != null){
            latitude = location.getLatitude();
            longitude =location.getLongitude();
       }else{
        Location getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
           longitude = getLastLocation.getLongitude();
           latitude = getLastLocation.getLatitude();
       }

	}

		@Override
		protected void onResume()
		{
			// TODO Auto-generated method stub
			super.onResume();

			int resCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
			if (resCode != ConnectionResult.SUCCESS)
			{
				GooglePlayServicesUtil.getErrorDialog(resCode, this, 1);
			}
		}
		
		

			@SuppressLint("NewApi")
			private class AsynTaskMain extends AsyncTask<Void, Void, Void>
			{

				@Override
				protected void onPostExecute(Void result)
				{
					// TODO Auto-generated method stub
					pDialog.dismiss();
					runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							
							LocationManager locationManagerA = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
							Criteria criteriaA = new Criteria();
							String providerA = locationManagerA.getBestProvider(criteriaA, true);

							Location locationA = locationManagerA.getLastKnownLocation(providerA);
							
							if(locationA != null){
					            latitude = locationA.getLatitude();
					            longitude =locationA.getLongitude();
					       }else{
					        Location getLastLocationA = locationManagerA.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
					           longitude = getLastLocationA.getLongitude();
					           latitude = getLastLocationA.getLatitude();
					       }
							
//							Location location = new Location("point A");

							locationA.setLatitude(latitude);
							locationA.setLongitude(longitude);

							Location locationB = new Location("point B");
							
							for (int i = 0; i < listLokasi.size(); i++)
							{
								locationB.setLatitude(listLokasi.get(i).getLat());
								locationB.setLongitude(listLokasi.get(i).getLng());

								float distance = locationA.distanceTo(locationB)/100;
								
								if(distance < 50){
									
									map.addMarker(new MarkerOptions()
									.position(new LatLng(listLokasi.get(i).getLat(), 
											listLokasi.get(i).getLng()))
									.title(listLokasi.get(i).getNama())
									.icon(BitmapDescriptorFactory.fromResource(R.drawable.gereja))
									.snippet(listLokasi.get(i).getAlamat()));
//									.snippet(""+String.valueOf(distance)+" Km"));
									
								}
										
							}
							
							
						}
						
					});

					super.onPostExecute(result);
				}

				@Override
				protected void onPreExecute()
				{
					// TODO Auto-generated method stub
					super.onPreExecute();
					pDialog = new ProgressDialog(PencarainLokasiActivity.this);
					pDialog.setMessage("Loading....");
					pDialog.setCancelable(true);
					pDialog.show();
				}

				@Override
				protected Void doInBackground(Void... params)
				{
					// TODO Auto-generated method stub

					jObject = json.getJSONFromURL(URL_API);
					listLokasi = json.getLokasiAll(jObject);
					return null;
				}
			}
			
	@SuppressLint("NewApi")
	private class GetCategories extends AsyncTask<Void, Void, Void>{
 
        @Override
        protected Void doInBackground(Void... arg0) {

        	listLokasi = new ArrayList<Lokasi>();
        	// Create an array to populate the spinner 
        	lokasi = new ArrayList<String>();
        	// JSON file URL address
        	jObject = JSONHelper.getJSONFromURL(URL_API);
        	try {
				// Locate the NodeList name
				jsonarray = jObject.getJSONArray("lokasi");
				for (int i = 0; i < jsonarray.length(); i++) {
					jObject = jsonarray.getJSONObject(i);
					
					Lokasi lokasilo = new Lokasi();

					lokasilo.setNama(jObject.optString(KEY_NAMA));
//					lokasilo.setAlamat(jObject.optString(KEY_ALAMAT));
					lokasilo.setLat(jObject.optDouble(KEY_LAT_TUJUAN));
					lokasilo.setLng(jObject.optDouble(KEY_LNG_TUJUAN));
//					lokasilo.setKetLok(jObject.optString(KEY_KET_LOKASI));
					
//		        	LocationManager locationManagerA = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//					Criteria criteriaA = new Criteria();
//					String providerA = locationManagerA.getBestProvider(criteriaA, true);
//
//					Location locationA = locationManagerA.getLastKnownLocation(providerA);
//					
//					if(locationA != null){
//			            latitude = locationA.getLatitude();
//			            longitude =locationA.getLongitude();
//			       }else{
//			        Location getLastLocationA = locationManagerA.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//			           longitude = getLastLocationA.getLongitude();
//			           latitude = getLastLocationA.getLatitude();
//			       }
//					
////					Location location = new Location("point A");
//
//					locationA.setLatitude(latitude);
//					locationA.setLongitude(longitude);
//
//					Location locationB = new Location("point B");
//					
//					locationB.setLatitude(jObject.optDouble(KEY_LAT_TUJUAN));
//					locationB.setLongitude(jObject.optDouble(KEY_LNG_TUJUAN));
//
//					float distance = locationA.distanceTo(locationB)/100;
					
//					if(distance < 50){
						
						listLokasi.add(lokasilo);
						
						// Populate spinner with country names
						lokasi.add(jObject.optString(KEY_NAMA));
//					}

				}
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
            return null;
        }
 
        @SuppressLint("NewApi")
		@Override
        protected void onPostExecute(Void result) {

        	Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        	

			
			// Spinner adapter
			spinner.setAdapter(new ArrayAdapter<String>(PencarainLokasiActivity.this,
							android.R.layout.simple_spinner_dropdown_item,
							lokasi));

			// Spinner on item click listener
			spinner
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int position, long arg3) {
							// TODO Auto-generated method stub
								
								map.animateCamera(CameraUpdateFactory.
										newLatLngZoom(new LatLng(listLokasi
												.get(position).getLat(), listLokasi
												.get(position).getLng()), 13));
							
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});
        }
 
    }	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
//		int str= 0;
//		str = Integer.valueOf(marker.getId());
//		int jml = str + 1;
//		String id = String.valueOf(jml);
//		String id = marker.getId();
//		id = id.substring(1);
		
//		LocationManager locationManagerA = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		Criteria criteriaA = new Criteria();
//		String providerA = locationManagerA.getBestProvider(criteriaA, true);
//
//		Location locationA = locationManagerA.getLastKnownLocation(providerA);
//		
//		if(locationA != null){
//            latitude = locationA.getLatitude();
//            longitude =locationA.getLongitude();
//       }else{
//        Location getLastLocationA = locationManagerA.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//           longitude = getLastLocationA.getLongitude();
//           latitude = getLastLocationA.getLatitude();
//       }
//		
////		Location location = new Location("point A");
//
//		locationA.setLatitude(latitude);
//		locationA.setLongitude(longitude);
//
//		Location locationB = new Location("point B");
//		
//		locationB.setLatitude(listLokasi.get(Integer.parseInt(id)).getLat());
//		locationB.setLongitude(listLokasi.get(Integer.parseInt(id)).getLat());
//
//		float distance = locationA.distanceTo(locationB)/100;
		
//		if(distance < 50){
		
		
		
		for(int n=0; n<listLokasi.size(); n++){
			LatLng lokasiB = new LatLng(listLokasi.get(n).getLat(), listLokasi.get(n).getLng());
			
			Log.w("marker klik koord", lokasiB.toString() );
			if(marker.getPosition() == lokasiB){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				  builder
				  .setTitle(listLokasi.get(n).getNama())
				  .setMessage("Alamat:\n "+listLokasi.get(n).getAlamat()+"\n\n"
						  +"Keterangan:\n "+listLokasi.get(n).getKetLok())
				  .setCancelable(false) 
				  .setNegativeButton("Selesai",new DialogInterface.OnClickListener() { 
				  public void onClick(DialogInterface dialog,int id) {
					  dialog.cancel();
				    } 
				  }).show();
			}
			
		}

			
//		}
		  
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.sateliteRB:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.streetRB:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
