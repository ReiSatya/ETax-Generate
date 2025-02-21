package id.etax.api;

import id.etax.api.model.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface IApiService {
	
	@GET("data/etax")
    Call<ResponseGetPajak>getPajak(@Query(value = "cif",encoded = true) String id);

    @GET("data/nasabah")
    Call<ResponseGetNasabah>getAllCustomerAPI();
}

