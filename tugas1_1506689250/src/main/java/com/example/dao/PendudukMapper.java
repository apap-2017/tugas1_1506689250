package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("select p.nik, p.nama, p.tempat_lahir, p.tanggal_lahir, k.alamat, k.rt, k.rw, kl.nama_kelurahan, kc.nama_kecamatan, kt.nama_kota, p.golongan_darah, p.agama, p.status_perkawinan, p.pekerjaan, p.is_wni, p.is_wafat from penduduk p, keluarga k, kelurahan kl, kecamatan kc, kota kt where p.id_keluarga=k.id and k.id_kelurahan=kl.id and kl.id_kecamatan = kc.id and kc.id_kota = kt.id and nik = #{nik}")
	PendudukModel selectPenduduk(@Param("nik") String nik);

	@Update("UPDATE penduduk SET is_wafat = 1  WHERE nik=#{nik} ")
	void updateStatusKematian(PendudukModel penduduk);

	@Select("select distinct id from keluarga")
	List<String> selectAllIdKeluarga();

	@Select("Select kc.kode_kecamatan from keluarga kg, kelurahan kl, kecamatan kc where kg.id_kelurahan=kl.id and kl.id_kecamatan=kc.id and kg.id=#{id}")
	PendudukModel selectInfoKeluarga(@Param("id") String id);

	@Insert("insert into penduduk(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) "
			+ "values (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #id_keluarga,  #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(PendudukModel penduduk);

	@Select("select substring(nik, 1, 12) from penduduk where substring(nik, 1, 12) = #{kode}")
	List<PendudukModel> counter(@Param("kode") String kode);

	@Select("select nik, nama, is_wafat from penduduk where id_keluarga=#{id}")
	@Results(value = { @Result(property = "nik", column = "nik"), @Result(property = "nama", column = "nama"),
			@Result(property = "is_wafat", column = "is_wafat") })
	List<PendudukModel> selectSiblings(@Param("id") String id);

	@Update("update keluarga set is_tidak_berlaku=1 where id=#{id}")
	void updateKeluargaTidakBerlaku(@Param("id") String id);

	@Select("Select id, nama_kota from kota")
	List<KotaModel> cariKota();
	
	@Select("select id, nama_kota from kota where id=#{id}")
	KotaModel cariKotaByID(@Param("id") String id);

	@Select("Select kc.id, kc.nama_kecamatan from kecamatan kc, kota kt where kc.id_kota=kt.id and kt.id=#{kota}")
	List<KecamatanModel> cariKecamatan(@Param("kota") String kota);
	
	@Select("select id, nama_kecamatan from kecamatan where id=#{id}")
	KecamatanModel cariKecamatanByID(@Param("id") String id);

	@Select("Select kl.id, kl.nama_kelurahan from kecamatan kc, kelurahan kl where kl.id_kecamatan=kc.id and kc.id=#{kecamatan}")
	List<KelurahanModel> cariKelurahan(@Param("kecamatan") String kecamatan);
	
	@Select("select id, nama_kelurahan from kelurahan where id=#{id}")
	KelurahanModel cariKelurahanByID(@Param("id") String id);

	@Select("select p.nik, p.nama, p.jenis_kelamin from penduduk p, keluarga kg, kelurahan kl, kecamatan kc, kota kt "
			+ "where p.id_keluarga=kg.id and kg.id_kelurahan=kl.id and kl.id_kecamatan=kc.id and kc.id_kota=kt.id and kt.id=#{kota} and kl.id=#{kelurahan} and kc.id=#{kecamatan}")
	List<PendudukModel> selectAllPenduduk(@Param("kelurahan") String kelurahan, @Param("kecamatan") String kecamatan, @Param("kota") String kota);
}
