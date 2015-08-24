package com.nim100010482.formobileclient;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
 
@SuppressWarnings("deprecation")
public class ViewInfoActivity extends TabActivity {
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
         
        intent = new Intent().setClass(this, HomeActivity.class);//content pada tab yang akan kita buat
        spec = tabhost.newTabSpec("home").setIndicator("Home",null).setContent(intent);//mengeset nama tab dan mengisi content pada menu tab anda.
        tabhost.addTab(spec);//untuk membuat tabbaru disini bisa diatur sesuai keinginan anda
         
        intent = new Intent().setClass(this, JadwalActivity.class);
        spec = tabhost.newTabSpec("jadwal").setIndicator("Jadwal",null).setContent(intent);
        tabhost.addTab(spec);
         
        intent = new Intent().setClass(this, ProfilActivity.class);
        spec = tabhost.newTabSpec("profil gereja").setIndicator("Profil Gereja",null).setContent(intent);
        tabhost.addTab(spec);
        
        intent = new Intent().setClass(this, PencarainLokasiActivity.class);
        spec = tabhost.newTabSpec("lokasi").setIndicator("Lokasi",null).setContent(intent);
        tabhost.addTab(spec);
 
    }
}