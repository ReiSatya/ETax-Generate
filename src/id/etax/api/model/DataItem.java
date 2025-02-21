package id.etax.api.model;

import java.util.List;

public class DataItem {
	
	private String cif;
	private String npwp;
	private String nama;
	private String alamat;
	private List<ListDataItem> pajak;
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getNpwp() {
		return npwp;
	}
	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public List<ListDataItem> getPajak() {
		return pajak;
	}
	public void setPajak(List<ListDataItem> pajak) {
		this.pajak = pajak;
	}
	
	

}
