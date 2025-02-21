package id.etax.api.service;

import java.util.HashMap;
import java.util.List;

import id.etax.api.IApiService;
import id.etax.api.model.DataItemNasabah;
import id.etax.api.model.ResponseGetNasabah;
import id.etax.api.model.ResponseGetPajak;
import id.etax.utils.RetrofitInstance;
import id.etax.api.model.DataItem;
import id.etax.api.model.ListDataItem;
import retrofit2.Call;
import retrofit2.Response;

public class GetAPIData {
	
	public HashMap<String , Object[]>getStatement(String id){
        HashMap<String,Object[]> result = new HashMap<>();
        System.out.println("masuk getStatement");

        try{
            IApiService api =RetrofitInstance.getRetrofitClient().create(IApiService.class);
            Call<ResponseGetPajak> call =  api.getPajak(id);
            Response<ResponseGetPajak> response = call.execute();
            ResponseGetPajak rgp = response.body();
            System.out.println(response.body());
          int i = 0;
          List<DataItem> lidi = rgp.getData();
          for(DataItem di : lidi){
        	  System.out.println("ini di "+di);
             List<ListDataItem> lidim = di.getPajak();
             if(lidim.isEmpty()) {
            	 System.out.println("masuk lidim.isEmpty");
            	 result.put("kosong", new Object[]{});
             }
             else {
            	 for(ListDataItem ldi : lidim){
                	 System.out.println("Masuk sini: "+ldi);
                     result.put(String.valueOf(i++),new Object[]{rgp.getMessage(),di.getCif(),di.getNpwp(),
                             di.getNama(),di.getAlamat(),ldi.getJenispenghasilan(), 
                             ldi.getJumlahbunga(), ldi.getTarif(),ldi.getPphpotong(), ldi.getDnln()});
                 }
            	 System.out.println("di sini");
             }
             System.out.println(lidim);
             
          }
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
	
	public HashMap<String,String[]> GetNasabahDataAll(){
		System.out.println("masuk getNasabahDataAll");
    	HashMap<String,String[]> result = new HashMap<>();
        try{
            IApiService api = RetrofitInstance.getRetrofitClient().create(IApiService.class);
            Call<ResponseGetNasabah> call = api.getAllCustomerAPI();;
            Response<ResponseGetNasabah> response = call.execute();
            ResponseGetNasabah rgc = response.body();
            List<DataItemNasabah> dicList = rgc.getData();
            int i = 0;
            for(DataItemNasabah dic : dicList){
                System.out.println(dic.getCif());
                System.out.println(dic.getNpwp());
                System.out.println(dic.getNama());
                System.out.println(dic.getAlamat());
                System.out.println("===================");
//
                result.put(String.valueOf(i++),new String[]{dic.getCif(), dic.getNpwp(),dic.getNama(),dic.getAlamat()});
            }
//            System.out.println(response.body());
        }catch (Exception e){
            e.printStackTrace();

        }
        System.out.println("berhasil lewat getNasabahDataAll");
        return result;
    }

}
