package app.service;

import app.aop.security.Authenticated;
import app.domain.Account;
import app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Authenticated
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

    @Authenticated
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

    @Authenticated
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
