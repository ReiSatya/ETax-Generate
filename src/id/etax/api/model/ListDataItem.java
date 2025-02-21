package id.etax.api.model;

import java.math.BigDecimal;

public class ListDataItem {
	
	    private String jenispenghasilan;
	    private BigDecimal jumlahbunga;
	    private Integer tarif;
	    private BigDecimal pphpotong;
	    private String dnln;

		public String getJenispenghasilan() {
			return jenispenghasilan;
		}

		public void setJenispenghasilan(String jenispenghasilan) {
			this.jenispenghasilan = jenispenghasilan;
		}

		public BigDecimal getJumlahbunga() {
			return jumlahbunga;
		}

		public void setJumlahbunga(BigDecimal jumlahbunga) {
			this.jumlahbunga = jumlahbunga;
		}

		public Integer getTarif() {
			return tarif;
		}

		public void setTarif(Integer tarif) {
			this.tarif = tarif;
		}

		public BigDecimal getPphpotong() {
			return pphpotong;
		}

		public void setPphpotong(BigDecimal pphpotong) {
			this.pphpotong = pphpotong;
		}

		public String getDnln() {
			return dnln;
		}

		public void setDnln(String dnln) {
			this.dnln = dnln;
		}

}
