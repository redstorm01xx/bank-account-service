package org.sid.bank_account_service.services;

import org.sid.bank_account_service.dtos.BankAccountRequestDTO;
import org.sid.bank_account_service.dtos.BankAccountResponseDTO;
import java.util.List;

public interface BankAccountService {
    BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);
    List<BankAccountResponseDTO> listAccounts();
    BankAccountResponseDTO getAccountById(String id);
    BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO);
    void deleteAccount(String id);
}