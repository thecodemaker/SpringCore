package app.service;

import app.domain.Account;

public interface AccountService {

    Account findByNumber(String number);

    void save(Account account);

    void updateMoneyAmount(Account account);
}
