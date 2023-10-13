package com.nttdata.bc46customer.model.response;

import com.nttdata.bc46customer.model.entity.Customer;
import com.nttdata.bc46customer.proxy.beans.account.AccountDto;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAccountResponse implements Serializable {
  Customer customer;
  List<AccountDto> listBankAccountDto;


}
