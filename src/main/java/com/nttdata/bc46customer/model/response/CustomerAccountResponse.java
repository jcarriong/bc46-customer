package com.nttdata.bc46customer.model.response;

import com.nttdata.bc46customer.model.entity.Customer;
import com.nttdata.bc46customer.proxy.beans.account.AccountDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountResponse {
  Customer customer;
  List<AccountDto> listBankAccountDto;


}
