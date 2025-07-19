package model.service;

import lombok.Getter;
import model.entity.Customer;
import model.repository.CustomerRepository;


import java.util.List;

public class CustomerService implements Service<Customer> {
    @Getter
    private static CustomerService service = new CustomerService();

    private CustomerService() {

    }
    @Override
    public void save(Customer customer) throws Exception {
        try (CustomerRepository customerRepository = new CustomerRepository()) {
            customerRepository.save(customer);
        }

    }

    @Override
    public void edit(Customer customer) throws Exception {
        try (CustomerRepository customerRepository = new CustomerRepository()) {
            if (customerRepository.findById(customer.getId()) != null) {
                customerRepository.save(customer);
            } else {
                throw new Exception("Customer not found");
            }
        }

    }

    @Override
    public void delete(int id) throws Exception {
        try (CustomerRepository customerRepository = new CustomerRepository()) {
            if (customerRepository.findById(id) != null) {
                customerRepository.delete(id);
            } else {
                throw new Exception("Customer not found");
            }
        }
    }


    @Override
    public List<Customer> findAll() throws Exception {
        try (CustomerRepository customerRepository = new CustomerRepository()) {
            return customerRepository.findAll();
        }

    }

    @Override
    public Customer findById(int id) throws Exception {
        try (CustomerRepository customerRepository = new CustomerRepository()) {
            return customerRepository.findById(id);
        }

    }

    public List<Customer> findByNameAndFamily(String name, String family) throws Exception {
        try (CustomerRepository customerRepository = new CustomerRepository()) {
            return customerRepository.findByNameAndFamily(name, family);
        }
    }
}
