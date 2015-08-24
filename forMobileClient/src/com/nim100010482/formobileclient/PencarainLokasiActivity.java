package com.nim100010482.formobileclient;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nim100010482.formobileclient.connect.JSONHelper;

import android.support.v4.app.FragmentActivity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
 
public class PencarainLokasiActivity extends FragmentActivity implements OnInfoWindowClickListener, OnCheckedChangeListener {
	
	private GoogleMap map;
	private JSONHelper json;
	private ProgressDialog pDialog;
	private ArrayList<Lokasi> listLokasi;
	private final String URL_API = "http://192.168.26.11/mobile/lokasi.php";
	double latitude, longitude;
	private LatLng myLocation;
	public static final String	KEY_NAMA	= "nm_grj";
	public static final String	KEY_ALAMAT	= "alamat_grj";
	public static final String	KEY_LAT_TUJUAN	= "lat_tujuan";
	public static final String	KEY_LNG_TUJUAN	= "lng_tujuan";
	public static final String	KEY_LAT_ASAL	= "lat_asal";
	public static final String	KEY_LNG_ASAL	= "lng_asal";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		json = new JSONHelper();
		new AsynTaskMain().execute();
		setupMapIfNeeded();

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
		
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
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
							for (int i = 0; i < listLokasi.size(); i++)
							{
								map.addMarker(new MarkerOptions()
										.position(new LatLng(listLokasi.get(i).getLat(), listLokasi.get(i).getLng()))
										.title(listLokasi.get(i).getNama())
										.snippet(listLokasi.get(i).getAlamat()));

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

					JSONObject jObject = json.getJSONFromURL(URL_API);
					listLokasi = json.getLokasiAll(jObject);
					return null;
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
		String id = marker.getId();
		id = id.substring(1);

		myLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());

		if (myLocation != null)
		{
			Bundle bundle = new Bundle();
			bundle.putString(KEY_NAMA, listLokasi.get(Integer.parseInt(id)).getNama());
			bundle.putString(KEY_ALAMAT, listLokasi.get(Integer.parseInt(id)).getAlamat());
			bundle.putDouble(KEY_LAT_TUJUAN, marker.getPosition().latitude);
			bundle.putDouble(KEY_LNG_TUJUAN, marker.getPosition().longitude);
			bundle.putDouble(KEY_LAT_ASAL, myLocation.latitude);
			bundle.putDouble(KEY_LNG_ASAL, myLocation.longitude);

			Intent i = new Intent(PencarainLokasiActivity.this, DetailPeta.class);
			i.putExtras(bundle);
			startActivity(i);

		} else
		{
			Toast.makeText(this, "Tidak dapat menemukan lokasi anda ", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}
}
