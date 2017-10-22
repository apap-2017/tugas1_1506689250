package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.PendudukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;

	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info("select Penduduk with nik {}", nik);
		return pendudukMapper.selectPenduduk(nik);
	}

	@Override
	public void updateStatusKematian(PendudukModel penduduk) {
		log.info("update Penduduk with nik {}", penduduk.getNik());
		pendudukMapper.updateStatusKematian(penduduk);
	}

	@Override
	public List<String> selectAllIdKeluarga() {
		log.info("select all possible id keluarga");
		return pendudukMapper.selectAllIdKeluarga();
	}

	@Override
	public PendudukModel selectInfoKeluarga(String id) {
		log.info("select info keluarga with id {}", id);
		return pendudukMapper.selectInfoKeluarga(id);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("insert penduduk");
		pendudukMapper.addPenduduk(penduduk);
	}

	@Override
	public List<PendudukModel> selectSiblings(String id) {
		log.info("select siblings with id_keluarga = {}", id);
		return pendudukMapper.selectSiblings(id);
	}

	@Override
	public List<PendudukModel> counter(String kode) {
		log.info("count penduduk with kode_kecamatan ", kode);
		return pendudukMapper.counter(kode);
	}

	@Override
	public void updateKeluargaTidakBerlaku(String id) {
		log.info("update keluarga set tidak berlaku with id {}", id);
		pendudukMapper.updateKeluargaTidakBerlaku(id);
	}

	@Override
	public List<KotaModel> cariKota() {
		log.info("select all kota");
		return pendudukMapper.cariKota();
	}

	@Override
	public List<KecamatanModel> cariKecamatan(String kota) {
		log.info("select kecamatan with idkota {}", kota);
		return pendudukMapper.cariKecamatan(kota);
	}

	@Override
	public List<KelurahanModel> cariKelurahan(String kecamatan) {
		log.info("select kelurahan with idkec {}", kecamatan);
		return pendudukMapper.cariKelurahan(kecamatan);
	}

	@Override
	public KotaModel cariKotaByID(String id) {
		// TODO Auto-generated method stub
		return pendudukMapper.cariKotaByID(id);
	}

	@Override
	public KecamatanModel cariKecamatanByID(String id) {
		// TODO Auto-generated method stub
		return pendudukMapper.cariKecamatanByID(id);
	}

	@Override
	public KelurahanModel cariKelurahanByID(String id) {
		// TODO Auto-generated method stub
		return pendudukMapper.cariKelurahanByID(id);
	}

	@Override
	public List<PendudukModel> selectAllPenduduk(String kelurahan, String kecamatan, String kota) {
		log.info("select penduduk with idkel {} idkec {} idkota {}", kelurahan, kecamatan, kota);
		return pendudukMapper.selectAllPenduduk(kelurahan, kecamatan, kota);
	}


}
