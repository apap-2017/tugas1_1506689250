package com.example.service;

import java.util.List;
import com.example.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(String nik);
	
	String selectKodeKelurahan(String nama_kecamatan);
	
	void addKeluarga(KeluargaModel keluarga);
	
	List<KeluargaModel> counter(String kode);
	
	String selectIdKelurahan(String nama_kelurahan);
	
	void ubahKeluargaSubmit(KeluargaModel keluarga);
}
