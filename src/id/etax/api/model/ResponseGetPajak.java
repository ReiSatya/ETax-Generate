package id.etax.api.model;

import java.util.List;

import id.etax.api.model.DataItem;

public class ResponseGetPajak {
	
	private String Message;
	private List<DataItem> Data;

	public void setMessage(String Message){
		this.Message = Message;
	}

	public String getMessage(){
		return Message;
	}

	public void setData(List<DataItem> Data){
		this.Data = Data;
	}

	public List<DataItem> getData(){
		return Data;
	}

}
