package com.example.controller;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.model.PendudukModel;
import com.example.model.KotaModel;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.service.PendudukService;

@Controller
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/penduduk")
	public String view(Model model, @RequestParam(value = "nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);

		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "penduduk";
		} else {
			model.addAttribute("nik", "NIK " + nik);
			return "penduduk-not-found";
		}
	}

	@GetMapping("/penduduk/tambah")
	public String tambah(Model model, PendudukModel penduduk) {
		List<String> list_idKeluarga = pendudukDAO.selectAllIdKeluarga();
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("list_idKeluarga", list_idKeluarga);
		return "add-penduduk";
	}

	@PostMapping("/penduduk/tambah")
	public String tambahPenduduk(@ModelAttribute PendudukModel penduduk) throws ParseException {
		String tgl_lahir = penduduk.getTanggal_lahir().toString();
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tgl_lahir);
		String tgl = date.toString();
		String nik = generateNik(penduduk.getId_keluarga(), tgl);
		System.out.println(penduduk.getIs_wni());
		penduduk.setTanggal_lahir(date);
		penduduk.setNik(nik);
		penduduk.setJenis_kelamin(0);
		penduduk.setStatus_dalam_keluarga("anak");
		pendudukDAO.addPenduduk(penduduk);
		return "success-add-penduduk";
	}

	public String generateNik(String id, String tanggal_lahir) {
		System.out.println(tanggal_lahir);
		String[] date = tanggal_lahir.split("-");
		PendudukModel info = pendudukDAO.selectInfoKeluarga(id);
		int count = pendudukDAO
				.counter(info.getKode_kecamatan().substring(0, 6) + date[2] + date[1] + date[0].substring(2)).size();
		System.out.println(info.getKode_kecamatan().substring(0, 6) + date[2] + date[1] + date[0].substring(2));
		int current = count + 1;
		System.out.println(count);
		System.out.println(current);
		String currentNik = String.format("%04d", current);
		System.out.println(currentNik);
		String nik = info.getKode_kecamatan().substring(0, 6) + date[2] + date[1] + date[0].substring(2) + currentNik;
		return nik;
	}

	@RequestMapping("/ubah")
	public String ubah() {
		return "ubah-data";
	}

	@RequestMapping("/penduduk/ubah/{nik}")
	public String ubahPenduduk(Model model, @RequestParam(value = "nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "ubah-data-penduduk";
		} else {
			model.addAttribute("nik", "NIK " + nik);
			return "penduduk-not-found";
		}
	}

	@PostMapping("/penduduk/ubah/{nik}")
	public String ubahPenduduk(@ModelAttribute PendudukModel penduduk) {
		return "success";
	}

	@PostMapping("/penduduk/mati")
	public String updateKematian(Model model, @RequestParam(value ="nik") String nik) {
		model.addAttribute("nik", nik);
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		pendudukDAO.updateStatusKematian(penduduk);
		boolean hasSiblings = cekKeluarga(penduduk.getId_keluarga());
		if (hasSiblings) {
			pendudukDAO.updateKeluargaTidakBerlaku(penduduk.getId_keluarga());
		}
		return "success-update-kematian";
	}
	
	//mencari anggota keluarga yang masih hidup
	public boolean cekKeluarga(String id_keluarga) {
		List<PendudukModel> siblings = pendudukDAO.selectSiblings(id_keluarga);
		if (siblings.size() == 0) {
			return false;
		}
		return true;
	}
	
	@GetMapping("/penduduk/cari")
	public String cari(Model model, @RequestParam(value = "kota", required = false) String kota, 
			@RequestParam(value = "kecamatan", required = false) String kecamatan, 
			@RequestParam(value = "kelurahan", required = false) String kelurahan) {
		if(kota==null && kecamatan==null && kelurahan==null) {
			List<KotaModel> listKota = pendudukDAO.cariKota();
			model.addAttribute("listKota", listKota);
			return "cari-kota";
		}else if(kota!=null && kecamatan==null && kelurahan==null) {
			List<KecamatanModel> listKecamatan = pendudukDAO.cariKecamatan(kota);
			KotaModel inputKota = pendudukDAO.cariKotaByID(kota);
			
			model.addAttribute("kota", inputKota);
			model.addAttribute("listKecamatan", listKecamatan);
			return "cari-kecamatan";
		}else if(kota!=null && kecamatan!=null && kelurahan==null){
			List<KelurahanModel> listKelurahan = pendudukDAO.cariKelurahan(kecamatan);
			KotaModel inputKota = pendudukDAO.cariKotaByID(kota);
			KecamatanModel inputKecamatan = pendudukDAO.cariKecamatanByID(kecamatan);
			
			model.addAttribute("kota", inputKota);
			model.addAttribute("kecamatan", inputKecamatan);
			model.addAttribute("listKelurahan", listKelurahan);
			return "cari-kelurahan";
		}else {
			List<PendudukModel> penduduks = pendudukDAO.selectAllPenduduk(kelurahan,kecamatan,kota);
			KotaModel inputKota = pendudukDAO.cariKotaByID(kota);
			KecamatanModel inputKecamatan = pendudukDAO.cariKecamatanByID(kecamatan);
			KelurahanModel inputKelurahan = pendudukDAO.cariKelurahanByID(kelurahan);
			
			model.addAttribute("kota", inputKota);
			model.addAttribute("kecamatan", inputKecamatan);
			model.addAttribute("kelurahan", inputKelurahan);
			model.addAttribute("penduduks", penduduks);
			return "cari";
		}
	}
}
