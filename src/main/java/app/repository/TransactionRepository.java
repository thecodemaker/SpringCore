package app.repository;

import app.domain.Transaction;

public interface TransactionRepository {

    int insert(Transaction transaction);

}
