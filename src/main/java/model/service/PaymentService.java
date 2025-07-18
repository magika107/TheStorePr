package model.service;

import model.entity.Payment;
import model.repository.PaymentRepository;

import java.util.Collections;
import java.util.List;

public class PaymentService implements Service<Payment>{
    @Override
    public void save(Payment payment) throws Exception {
        try(PaymentRepository paymentRepository = new PaymentRepository()){
            paymentRepository.save(payment);
        }
    }

    @Override
    public void edit(Payment payment) throws Exception {
        try(PaymentRepository paymentRepository = new PaymentRepository()){
            if(paymentRepository.findById(payment.getId()) != null){
                paymentRepository.edit(payment);
            }else {
                throw new Exception("Payment not found");
            }
        }

    }

    @Override
    public void delete(int id) throws Exception {
        try(PaymentRepository paymentRepository = new PaymentRepository()){
            if(paymentRepository.findById(id) != null) {
                paymentRepository.delete(id);
            }else {
                throw new Exception("Payment not found");
            }
        }

    }

    @Override
    public List<Payment> findAll() throws Exception {
        try(PaymentRepository paymentRepository = new PaymentRepository()){
            return paymentRepository.findAll();
        }

    }

    @Override
    public Payment findById(int id) throws Exception {
        try(PaymentRepository paymentRepository = new PaymentRepository()){
            return paymentRepository.findById(id);
        }

    }
    public Payment findByPaymentTime(String paymentTime) throws Exception {
        try(PaymentRepository paymentRepository = new PaymentRepository()){
            return paymentRepository.findByPaymentTime(paymentTime);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
