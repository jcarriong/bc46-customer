package com.nttdata.bc46customer.proxy.config;

import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import com.nttdata.bc46customer.proxy.AccountRetrofitClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class ClientRetrofitConfig {
  @Bean
  AccountRetrofitClient accountRetrofitClient() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://localhost:8052/api/accounts/")
        .addCallAdapterFactory(ReactorCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit.create(AccountRetrofitClient.class);
  }

    /* @Bean
    ProductRetrofitClient productRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8084/api/v1/product/")
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ProductRetrofitClient.class);
    }*/

  /*  @Bean
    OperationRetrofitClient operationRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8086/api/v1/operation/")
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(OperationRetrofitClient.class);
    }*/
}