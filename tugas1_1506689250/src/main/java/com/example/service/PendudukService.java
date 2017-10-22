package com.example.service;

import java.util.List;
import com.example.model.PendudukModel;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	
	void updateStatusKematian(PendudukModel penduduk);
	
	List<String> selectAllIdKeluarga();
	
	PendudukModel selectInfoKeluarga(String id);
	
	void addPenduduk(PendudukModel penduduk);
	
	List<PendudukModel> counter(String kode);
	
	List<PendudukModel> selectSiblings(String id);
	
	void updateKeluargaTidakBerlaku(String id);
	
	List<KotaModel> cariKota();
	
	KotaModel cariKotaByID(String id);
	
	List<KecamatanModel> cariKecamatan(String kota);
	
	KecamatanModel cariKecamatanByID(String id);
	
	List<KelurahanModel> cariKelurahan(String kecamatan);
	
	KelurahanModel cariKelurahanByID(String id);
	
	List<PendudukModel> selectAllPenduduk(String kelurahan, String kecamatan, String kota);

}
