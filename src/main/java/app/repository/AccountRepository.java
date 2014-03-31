package app.repository;


import app.domain.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findByNumber(String number);

    int insert(Account account);

    int updateMoneyAmount(Account account);
}
