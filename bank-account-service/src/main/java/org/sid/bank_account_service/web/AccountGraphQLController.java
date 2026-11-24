package org.sid.bank_account_service.web;

import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.entities.Customer;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.sid.bank_account_service.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class AccountGraphQLController {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @QueryMapping
    public List<BankAccount> accountList() {
        return bankAccountRepository.findAll();
    }

    @QueryMapping
    public BankAccount bankAccount(@Argument String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
    }

    @QueryMapping
    public List<Customer> customers() {
        return customerRepository.findAll();
    }

    @MutationMapping
    public BankAccount addBankAccount(@Argument BankAccountRequest bankAccount) {
        BankAccount account = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .creationDate(new Date())
                .balance(bankAccount.balance())
                .currency(bankAccount.currency())
                .type(bankAccount.type())
                .build();
        return bankAccountRepository.save(account);
    }

    @MutationMapping
    public BankAccount updateBankAccount(@Argument String id, @Argument BankAccountRequest bankAccount) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
        if (bankAccount.balance() != 0) account.setBalance(bankAccount.balance());
        if (bankAccount.currency() != null) account.setCurrency(bankAccount.currency());
        if (bankAccount.type() != null) account.setType(bankAccount.type());
        return bankAccountRepository.save(account);
    }

    @MutationMapping
    public Boolean deleteBankAccount(@Argument String id) {
        bankAccountRepository.deleteById(id);
        return true;
    }
}

// Record pour recevoir les données de mutation proprement
record BankAccountRequest(double balance, String currency, org.sid.bank_account_service.enums.AccountType type) {}