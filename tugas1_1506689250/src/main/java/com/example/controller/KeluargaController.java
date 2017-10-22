package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.model.KeluargaModel;
import com.example.service.KeluargaService;

@Controller
public class KeluargaController {
	@Autowired
	KeluargaService keluargaDAO;

	@GetMapping("/keluarga")
	public String view(Model model, @RequestParam(value = "nkk") String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);

		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "keluarga";
		} else {
			model.addAttribute("status", "NKK " + nkk);
			return "keluarga-not-found";
		}
	}

	@GetMapping("/keluarga/tambah")
	public String tambahKeluarga(Model model, KeluargaModel keluarga) {
		model.addAttribute("keluarga", keluarga);
		return "add-keluarga";
	}

	@PostMapping("/keluarga/tambah/submit")
	public String tambahKeluargaSubmit(Model model, @ModelAttribute KeluargaModel keluarga) {
		String nomor_kk = generateNkk(keluarga.getNama_kelurahan());
		String id_kelurahan = keluargaDAO.selectIdKelurahan(keluarga.getNama_kelurahan());
		keluarga.setNomor_kk(nomor_kk);
		keluarga.setId_kelurahan(id_kelurahan);
		keluargaDAO.addKeluarga(keluarga);
		model.addAttribute("nkk", nomor_kk);
		return "success-add-keluarga";
	}

	public String generateNkk(String nama_kelurahan) {
		String kode = keluargaDAO.selectKodeKelurahan(nama_kelurahan);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String[] date = today.split("-");
		int count = keluargaDAO.counter(kode.substring(0, 6) + date[2] + date[1] + date[0].substring(2)).size();
		int current = count + 1;
		String currentNkk = String.format("%04d", current);
		String nkk = kode.substring(0, 6) + date[2] + date[1] + date[0].substring(2) + currentNkk;
		return nkk;
	}

	@GetMapping("/keluarga/ubah/{nkk}")
	public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);

		if (keluarga != null) {
			System.out.println(keluarga.getId());
			model.addAttribute("keluarga", keluarga);
			return "ubah-data-keluarga";
		} else {
			model.addAttribute("nkk", "NKK " + nkk);
			return "keluarga-not-found";
		}
	}

	@PostMapping("/keluarga/ubah/submit")
	public String ubahKeluargaSubmit(@ModelAttribute KeluargaModel keluarga) {
		String nomor_kk = generateNkk(keluarga.getNama_kelurahan());
		String id_kelurahan = keluargaDAO.selectIdKelurahan(keluarga.getNama_kelurahan());
		keluarga.setNomor_kk(nomor_kk);
		keluarga.setId_kelurahan(id_kelurahan);
		keluargaDAO.ubahKeluargaSubmit(keluarga);
		return "ubah-data-keluarga";
	}
}
