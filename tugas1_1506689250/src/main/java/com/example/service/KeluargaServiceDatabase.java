package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;

	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info("select keluarga with nkk {}", nkk);
		return keluargaMapper.selectKeluarga(nkk);
	}
	
	@Override
	public String selectKodeKelurahan(String nama_kecamatan) {
		log.info("select kode_kelurahan with nama_kelurahan {}", nama_kecamatan);
		return keluargaMapper.selectKodeKelurahan(nama_kecamatan);
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga){
		log.info("add keluarga dengan nkk {}", keluarga.getNomor_kk());
		keluargaMapper.addKeluarga(keluarga);
	}
	
	@Override
	public String selectIdKelurahan(String nama_kelurahan) {
		return keluargaMapper.selectIdKelurahan(nama_kelurahan);
	}

	@Override
	public List<KeluargaModel> counter(String kode) {
		// TODO Auto-generated method stub
		return keluargaMapper.counter(kode);
	}

	@Override
	public void ubahKeluargaSubmit(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.ubahKeluargaSubmit(keluarga);
	}
}
