package com.nim100010482.formobileclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button ViewInfo = (Button) findViewById(R.id.btnViewInfo);
		ViewInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View VInfo) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(VInfo.getContext(), ViewInfoActivity.class);
				startActivityForResult(myIntent, 0);
				
			}
		});
		
		Button Pencarian_Lokasi = (Button) findViewById(R.id.btnPencarianLokasi);
		Pencarian_Lokasi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View VLokasi) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(VLokasi.getContext(), PencarainLokasiActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		Button About = (Button) findViewById(R.id.btnAbout);
		About.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View VAbout) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(VAbout.getContext(), AboutActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
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
}
