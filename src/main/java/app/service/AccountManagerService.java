package app.service;

import app.domain.Account;
import app.repository.AccountRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.List;

public class AccountManagerService implements AccountService {

    private AccountRepository accountRepository;

    public AccountManagerService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findByNumber(String number) {
        List<Account> accounts = accountRepository.findByNumber(number);
        if (accounts == null || accounts.size() ==0) {
            throw new EmptyResultDataAccessException("No account found for number " + number + ".", 1);
        }
        if (accounts.size() > 1) {
            throw new IncorrectResultSizeDataAccessException("More than one account found for number" + number + ".", 1);
        }
        return accounts.get(0);
    }

    @Override
    public void save(Account account) {
        int count = accountRepository.insert(account);
        if (count == 0) {
            throw new IncorrectResultSizeDataAccessException("Account not saved.", 1);
        }
        if (count > 1) {
            throw new IncorrectResultSizeDataAccessException("More than once account was saved.", 1);
        }
    }

    @Override
    public void updateMoneyAmount(Account account) {
        int count = accountRepository.updateMoneyAmount(account);
        if (count == 0) {
            throw new IncorrectResultSizeDataAccessException("No accounts were updated", 1);
        }
        if (count > 1) {
            throw new IncorrectResultSizeDataAccessException("More than once account was updated", 1);
        }
    }
}