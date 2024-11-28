package com.example.ormculinaryacadamy.Bo.Custom;

import com.example.ormculinaryacadamy.Bo.SuperBo;
import com.example.ormculinaryacadamy.Dto.PaymentDto;
import com.example.ormculinaryacadamy.Entity.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentBo extends SuperBo {
    boolean savePayment(PaymentDto paymentDto) throws IOException;
    List<Payment> getPaymentList() throws IOException;
}
