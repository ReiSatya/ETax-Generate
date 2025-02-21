 package id.etax.utils;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyIntercepter implements Interceptor{
	private String auth;
	public MyIntercepter(String user, String pass){
		this.auth = (Credentials.basic(user, pass));
	}
	public Response intercept(Chain chain) throws IOException {
		// TODO Auto-generated method stub
		Request request = chain.request();

		Request newRequest = request.newBuilder()
				.header("Authorization", auth)
				.header("Content-Type", "application/json")
				.build();
		return chain.proceed(newRequest);
	}

}
