package id.etax.utils;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
  private static String BASE_URL = SQLData.getBaseUrl();
  
  private static Retrofit retrofit;
  
  public static Retrofit getRetrofitClient() {
    System.out.println("ini url API : " + BASE_URL);
    if (retrofit == null)
      retrofit = (new Retrofit.Builder())
        .baseUrl(BASE_URL)
        .addConverterFactory((Converter.Factory)GsonConverterFactory.create())
        .client((new OkHttpClient()).newBuilder()
          .connectTimeout(100L, TimeUnit.SECONDS)
          .readTimeout(100L, TimeUnit.SECONDS)
          
          .build())
        .build(); 
    return retrofit;
  }
}
