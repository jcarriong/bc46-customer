package com.nttdata.bc46customer.proxy;


import com.nttdata.bc46customer.proxy.beans.account.AccountDto;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author jcarriong
 */
public interface AccountRetrofitClient {
  @GET(value = "findAccountsByCustomer/{idCustomer}")
  Flux<List<AccountDto>> getAccountsByCustomer(@Path("idCustomer") String idCustomer);

  @POST(value = "saveAccount/")
  Mono<AccountDto> registerAccount(@Body AccountDto bankAccountDto);
}