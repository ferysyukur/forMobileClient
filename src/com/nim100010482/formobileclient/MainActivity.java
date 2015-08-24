package com.nim100010482.formobileclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public final class MainActivity extends Activity implements OnClickListener {
	
	public static final int Exit = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.btnViewInfo).setOnClickListener(this);
		findViewById(R.id.btnPencarianLokasi).setOnClickListener(this);
		findViewById(R.id.btnAbout).setOnClickListener(this);
		findViewById(R.id.btnExit).setOnClickListener(this);
		
		
//		ImageButton ViewInfo = (ImageButton) findViewById(R.id.btnViewInfo);
//		ViewInfo.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View VInfo) {
//				// TODO Auto-generated method stub
//				Intent myIntent = new Intent(VInfo.getContext(), ViewInfoActivity.class);
//				startActivityForResult(myIntent, 0);
//				
//			}
//		});
//		
//		ImageButton Pencarian_Lokasi = (ImageButton) findViewById(R.id.btnPencarianLokasi);
//		Pencarian_Lokasi.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View VLokasi) {
//				// TODO Auto-generated method stub
//				Intent myIntent = new Intent(VLokasi.getContext(), PencarainLokasiActivity.class);
//				startActivityForResult(myIntent, 0);
//			}
//		});
//		
//		ImageButton About = (ImageButton) findViewById(R.id.btnAbout);
//		About.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View VAbout) {
//				// TODO Auto-generated method stub
//				Intent myIntent = new Intent(VAbout.getContext(), AboutActivity.class);
//				startActivityForResult(myIntent, 0);
//			}
//		});
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnViewInfo:
			Intent myIntent = new Intent(this, ViewInfoActivity.class);
			startActivity(myIntent);
			break;
		case R.id.btnPencarianLokasi:
			Intent myIntent2 = new Intent(this, PencarainLokasiActivity.class);
			startActivity(myIntent2);
			break;
		case R.id.btnAbout:
			Intent myIntent3 = new Intent(this, AboutActivity.class);
			startActivity(myIntent3);
			break;
		case R.id.btnExit:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			  builder.setMessage("Apakah Anda Benar-Benar ingin"+ " keluar?")
			  .setCancelable(false) 
			  .setPositiveButton("Ya",new DialogInterface.OnClickListener() { 
			  public void onClick(DialogInterface dialog,int id) {
			    MainActivity.this.finish();} 
			  }) 
			  .setNegativeButton("Tidak",new DialogInterface.OnClickListener(){ 
			  public void onClick(DialogInterface dialog, int id) {
			  dialog.cancel();
			  }}).show(); 
			break;
		}
		
	}
	
//	protected Dialog onCreateDialog(int id){
//		
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		switch (id) {
//			case Exit:
//		}
//		return builder.setMessage("Anda Yakin Ingin Keluar ?")
//				.setCancelable(false)
//				.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
//		public void onClick(DialogInterface dialog, int id){
//			MainActivity.this.finish();}
//		})
//		.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//		public void onClick(DialogInterface dialog, int id) {
//				// TODO Auto-generated method stub
//				dialog.cancel();
//			}
//		});show();
//	}

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
