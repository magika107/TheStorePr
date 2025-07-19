package model.service;

import lombok.Getter;
import model.entity.Bank;
import model.repository.BankRepository;

import java.util.List;

public class BankService implements Service<Bank> {
    @Getter
    private static BankService service = new BankService();

    private BankService() {

    }

    @Override
    public void save(Bank bank) throws Exception {
        try (BankRepository bankRepository = new BankRepository()) {
            bankRepository.save(bank);
        }
    }

    @Override
    public void edit(Bank bank) throws Exception {
        try (BankRepository bankRepository = new BankRepository()) {
            if (bankRepository.findById(bank.getId()) != null) {
                bankRepository.save(bank);
            } else {
                throw new Exception("Bank with ID " + bank.getId() + " not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (BankRepository bankRepository = new BankRepository()) {
            if (bankRepository.findById(id) != null) {
                bankRepository.delete(id);
            } else {
                throw new Exception("Bank with ID " + id + " not found");
            }
        }
    }

    @Override
    public List<Bank> findAll() throws Exception {
        try (BankRepository bankRepository = new BankRepository()) {
            return bankRepository.findAll();
        }
    }

    @Override
    public Bank findById(int id) throws Exception {
        try (BankRepository bankRepository = new BankRepository()) {
            return bankRepository.findById(id);
        }
    }
}

