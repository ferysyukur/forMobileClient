package com.nim100010482.formobileclient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.nim100010482.formobileclient.connect.JSONParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPeta extends Activity implements OnClickListener {

	private TextView	tvNama;
	private TextView	tvAlamat;
	private Button		btnGetDirection;

	private LatLng		lokasiTujuan;
	private LatLng		lokasiAwal;
	private String		nm_grj;
	private String		alamat_grj;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_peta);

		initialize();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			nm_grj = bundle.getString(PencarainLokasiActivity.KEY_NAMA);
			alamat_grj = bundle.getString(PencarainLokasiActivity.KEY_ALAMAT);
			lokasiTujuan = new LatLng(bundle.getDouble(PencarainLokasiActivity.KEY_LAT_TUJUAN),
					bundle.getDouble(PencarainLokasiActivity.KEY_LNG_TUJUAN));
			lokasiAwal = new LatLng(bundle.getDouble(PencarainLokasiActivity.KEY_LAT_ASAL),
					bundle.getDouble(PencarainLokasiActivity.KEY_LNG_ASAL));
		}

		setTeksView();

	}

	private void setTeksView()
	{
		tvNama.setText(nm_grj);
		tvAlamat.setText(alamat_grj);
	}

	private void initialize()
	{
		tvAlamat = (TextView) findViewById(R.id.alamatTempatMakan);
		tvNama = (TextView) findViewById(R.id.namaTempatMakan);
		btnGetDirection = (Button) findViewById(R.id.btnDirection);
		btnGetDirection.setOnClickListener(this);
	}

	public void onClick(View v)
	{
		Bundle bundle = new Bundle();
		bundle.putDouble(PencarainLokasiActivity.KEY_LAT_ASAL, lokasiAwal.latitude);
		bundle.putDouble(PencarainLokasiActivity.KEY_LNG_ASAL, lokasiAwal.longitude);
		bundle.putDouble(PencarainLokasiActivity.KEY_LAT_TUJUAN, lokasiTujuan.latitude);
		bundle.putDouble(PencarainLokasiActivity.KEY_LNG_TUJUAN, lokasiTujuan.longitude);
		bundle.putString(PencarainLokasiActivity.KEY_NAMA, nm_grj);

		Intent intent = new Intent(this, DirectionActivity.class);
		intent.putExtras(bundle);

		startActivity(intent);
	}


}