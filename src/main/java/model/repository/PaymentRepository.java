package model.repository;

import model.entity.Payment;

import java.util.Collections;
import java.util.List;

public class PaymentRepository implements Repository<Payment>{
    @Override
    public void save(Payment payment) throws Exception {

    }

    @Override
    public void edit(Payment payment) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<Payment> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Payment findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
