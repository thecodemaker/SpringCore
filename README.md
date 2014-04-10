Spring Transaction Management
=============================

You need to apply transaction management:

1. define transaction manager bean in tx-config.xml

2. enable annotation based transaction management in tx-config.xml

3. make service classes transactional

4. make AccountServiceImpl#findByNumber(...) readonly transaction

5. add a time limit TransferServiceImpl#transferAmount(...) transaction