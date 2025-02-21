package id.etax.api.model;

import java.util.List;

import id.etax.api.model.DataItemNasabah;

public class ResponseGetNasabah {
	
	private String message;

	private List<DataItemNasabah> data;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setData(List<DataItemNasabah> Data){
		this.data = Data;
	}

	public List<DataItemNasabah> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetCustomer{" + 
			"message = '" + message + '\'' +
			",data = '" + data + '\'' +
			"}";
		}
}