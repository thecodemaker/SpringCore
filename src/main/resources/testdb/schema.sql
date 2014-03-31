drop table T_ACCOUNT if exists;
drop table T_TRANSACTION if exists;

create table T_ACCOUNT(
  ID integer identity primary key,
  NUMBER varchar(14) not null,
  NAME varchar(50) not null,
  MONEY_AMOUNT double not null,
  unique(NUMBER)
);

create table T_TRANSACTION(
  ID integer identity primary key,
  PAYER_ACCOUNT varchar(14) not null,
  BENEFICIARY_ACCOUNT varchar(14) not null,
  MONEY_AMOUNT double not null,
  DATE date not null,
  unique(PAYER_ACCOUNT, BENEFICIARY_ACCOUNT, MONEY_AMOUNT, DATE)
);