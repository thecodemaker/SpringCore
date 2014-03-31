package app.service;

import app.domain.Transaction;
import app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TransactionManagerService implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionManagerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void save(Transaction transaction) {
        int count = transactionRepository.insert(transaction);
        if (count == 0) {
            throw new IncorrectResultSizeDataAccessException("Transaction not saved.", 1);
        }
        if (count > 1) {
            throw new IncorrectResultSizeDataAccessException("More than once transaction was saved.", 1);
        }
    }
}
