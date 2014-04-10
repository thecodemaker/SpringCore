package app.service;

import app.aop.security.Authenticated;
import app.domain.Transaction;
import app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Service
// TODO -> 3. make service classes transactional
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Authenticated
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
