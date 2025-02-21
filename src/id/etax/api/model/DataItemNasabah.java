package id.etax.api.model;

public class DataItemNasabah {
	
	private String cif;

	private String npwp;

	private String nama;

	private String alamat;


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


	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"cif = '" + cif + '\'' +
			",npwp = '" + npwp + '\'' +
			",nama = '" + nama + '\'' +
			",alamat = '" + alamat + '\'' +
			"}";
		}

}
