package app.service;

import app.domain.Transaction;

public interface TransferService {

    void transferAmount(Transaction transaction);

}
