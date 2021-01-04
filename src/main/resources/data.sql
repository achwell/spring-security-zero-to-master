drop table IF EXISTS users;
drop table IF EXISTS authorities;
drop table IF EXISTS customer;
drop table IF EXISTS accounts;
drop table IF EXISTS account_transactions;
drop table IF EXISTS loans;
drop table IF EXISTS cards;
drop table IF EXISTS notice_details;
drop table IF EXISTS contact_messages;

create TABLE users
(
    id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(500) NOT NULL,
    enabled  INT         NOT NULL
);

create TABLE authorities
(
    id        int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  varchar(45) NOT NULL,
    authority varchar(45) NOT NULL
);

create TABLE customer
(
    customer_id    int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(255) NOT NULL,
    mobile_number varchar(20) NOT NULL,
    pwd   varchar(500) NOT NULL,
    role  varchar(100) NOT NULL,
    create_dt date DEFAULT NULL
);

create TABLE accounts (
  customer_id int NOT NULL,
  account_number bigint NOT NULL PRIMARY KEY,
  account_type varchar(100) NOT NULL,
  branch_address varchar(200) NOT NULL,
  create_dt date DEFAULT NULL
);

alter table accounts add FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

create TABLE account_transactions (
  transaction_id varchar(200) NOT NULL PRIMARY KEY,
  account_number bigint NOT NULL,
  customer_id int NOT NULL,
  transaction_dt date NOT NULL,
  transaction_summary varchar(200) NOT NULL,
  transaction_type varchar(100) NOT NULL,
  transaction_amt int NOT NULL,
  closing_balance int NOT NULL,
  create_dt date DEFAULT NULL
);

alter table account_transactions add FOREIGN KEY (account_number) REFERENCES accounts(account_number);
alter table account_transactions add FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

create TABLE loans (
  loan_number int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  customer_id int NOT NULL,
  start_dt date NOT NULL,
  loan_type varchar(100) NOT NULL,
  total_loan int NOT NULL,
  amount_paid int NOT NULL,
  outstanding_amount int NOT NULL,
  create_dt date DEFAULT NULL
);

alter table loans add FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

create TABLE cards (
  card_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  card_number varchar(100) NOT NULL,
  customer_id int NOT NULL,
  card_type varchar(100) NOT NULL,
  total_limit int NOT NULL,
  amount_used int NOT NULL,
  available_amount int NOT NULL,
  create_dt date DEFAULT NULL
);

alter table cards add FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

create TABLE notice_details (
  notice_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  notice_summary varchar(200) NOT NULL,
  notice_details varchar(500) NOT NULL,
  notic_beg_dt date NOT NULL,
  notic_end_dt date DEFAULT NULL,
  create_dt date DEFAULT NULL,
  update_dt date DEFAULT NULL
);

create TABLE contact_messages (
  contact_id varchar(50) NOT NULL PRIMARY KEY,
  contact_name varchar(50) NOT NULL,
  contact_email varchar(100) NOT NULL,
  subject varchar(500) NOT NULL,
  message varchar(2000) NOT NULL,
  create_dt date DEFAULT NULL
);

insert into users (username, password, enabled)
values ('happy', '12345', '1');

insert into authorities (username, authority)
values ('happy', 'write');

insert into customer (name, email, mobile_number, pwd, role, create_dt)
 values ('Happy','happy@example.com','9876548337', '$2a$10$kCGSajJz0.Gqo3toXVzD7umrqR2MR.YQmZFiNbQi/OmsQrG.r1cH6', 'admin', sysdate());

insert into accounts (customer_id, account_number, account_type, branch_address, create_dt)
 values (1, 186576453434, 'Savings', '123 Main Street, New York', sysdate());

insert into account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, create_dt)  values (UUID(), 186576453434, 1, sysdate()-7, 'Coffee Shop', 'Withdrawal', 30,34500,sysdate()-7);

insert into account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, create_dt)  values (UUID(), 186576453434, 1, sysdate()-6, 'Uber', 'Withdrawal', 100,34400,sysdate()-6);

insert into account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, create_dt)  values (UUID(), 186576453434, 1, sysdate()-5, 'Self Deposit', 'Deposit', 500,34900,sysdate()-5);

insert into account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, create_dt)  values (UUID(), 186576453434, 1, sysdate()-4, 'Ebay', 'Withdrawal', 600,34300,sysdate()-4);

insert into account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, create_dt)  values (UUID(), 186576453434, 1, sysdate()-2, 'OnlineTransfer', 'Deposit', 700,35000,sysdate()-2);

insert into account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, create_dt)  values (UUID(), 186576453434, 1, sysdate()-1, 'Amazon.com', 'Withdrawal', 100,34900,sysdate()-1);

insert into loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
 values ( 1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

insert into loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
 values ( 1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

insert into loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
 values ( 1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

insert into loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
 values ( 1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

insert into cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
 values ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, sysdate());

insert into cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
 values ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, sysdate());

insert into cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
 values ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, sysdate());

insert into notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
values ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately',
'2020-10-14', '2021-11-30', sysdate(), null);

insert into notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
values ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
'2020-10-14', '2021-12-05', sysdate(), null);

insert into notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
values ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
'2020-10-14', '2021-12-01', sysdate(), null);

insert into notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
values ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
'2020-10-14', '2021-12-08', sysdate(), null);

insert into notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
values ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
'2020-10-14', '2021-11-28', sysdate(), null);

insert into notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
values ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
'2020-10-14', '2021-12-31', sysdate(), null);
