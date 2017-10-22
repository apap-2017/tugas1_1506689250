package com.example.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	@Select("select kg.id, kg.nomor_kk, kg.alamat, kg.rt, kg.rw, kl.nama_kelurahan, kc.kode_kecamatan, kc.nama_kecamatan, kt.nama_kota, kg.is_tidak_berlaku from keluarga kg, kelurahan kl, kecamatan kc, kota kt where kg.id_kelurahan = kl.id and kl.id_kecamatan=kc.id and kc.id_kota=kt.id and kg.nomor_kk = #{nkk}")
	@Results(value = { @Result(property = "nomor_kk", column = "nomor_kk"),
			@Result(property = "alamat", column = "alamat"), @Result(property = "rt", column = "rt"),
			@Result(property = "rw", column = "rw"), @Result(property = "nama_kelurahan", column = "nama_kelurahan"),
			@Result(property = "nama_kecamatan", column = "nama_kecamatan"),
			@Result(property = "nama_kota", column = "nama_kota"),
			@Result(property = "penduduk", column = "nomor_kk", javaType = List.class, many = @Many(select = "selectAnggotaKeluarga")) })
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);

	@Select("select p.nama, p.nik, p.jenis_kelamin, p.tempat_lahir, p.tanggal_lahir, p.agama, p.pekerjaan, p.status_perkawinan, p.status_dalam_keluarga, p.is_wni "
			+ "from penduduk p join keluarga kg " + "on p.id_keluarga=kg.id " + "where kg.nomor_kk= #{nkk}")
	List<PendudukModel> selectAnggotaKeluarga(@Param("nkk") String nkk);

	@Select("Select kl.kode_kelurahan from kelurahan kl where kl.nama_kelurahan = #{nama_kelurahan}")
	String selectKodeKelurahan(@Param("nama_kelurahan") String nama_kelurahan);
	
	@Select("Select kl.id from kelurahan kl where kl.nama_kelurahan = #{nama_kelurahan}")
	String selectIdKelurahan(@Param("nama_kelurahan") String nama_kelurahan);

	@Insert("insert into keluarga(nomor_kk, alamat, rt, rw, id_kelurahan) " + "values (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan})")
	void addKeluarga(KeluargaModel keluarga);
	
	@Select("select substring(nomor_kk, 1, 12) from keluarga where substring(nomor_kk, 1, 12) = #{kode}")
	List<KeluargaModel> counter(@Param("kode") String kode);
	
	@Update("update keluarga set nomor_kk=#{nomor_kk}, alamat=#{alamat}, rt=#{rt}, rw=#{rw}, id_kelurahan=#{id_kelurahan} where id=#{id}")
	void ubahKeluargaSubmit(KeluargaModel keluarga);
	
	
}
