package com.nim100010482.formobileclient;

public class Lokasi {

	    private int id;
		private String	nm_grj;
		private String	alamat_grj;
		private double	latitude;
		private double	longitude;
		private String	ket_lokasi;

		public Lokasi()
		{
			// TODO Auto-generated constructor stub
		}

		public Lokasi(int id, String nm_grj, String alamat_grj, double latitude, double longitude, String ket_lokasi)
		{
			super();
	        this.id = id;
			this.nm_grj = nm_grj;
			this.alamat_grj = alamat_grj;
			this.latitude = latitude;
			this.longitude = longitude;
			this.ket_lokasi = ket_lokasi;
		}

		public String getNama()
		{
			return nm_grj;
		}

		public void setNama(String nm_grj)
		{
			this.nm_grj = nm_grj;
		}

		public String getAlamat()
		{
			return alamat_grj;
		}

		public void setAlamat(String alamat_grj)
		{
			this.alamat_grj = alamat_grj;
		}

		public double getLat()
		{
			return latitude;
		}

		public void setLat(double latitude)
		{
			this.latitude = latitude;
		}

		public double getLng()
		{
			return longitude;
		}

		public void setLng(double longitude)
		{
			this.longitude = longitude;
		}
		
		public String getKetLok()
		{
			return ket_lokasi;
		}

		public void setKetLok(String ket_lokasi)
		{
			this.ket_lokasi = ket_lokasi;
		}

	}

