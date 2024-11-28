package com.example.ormculinaryacadamy.Dao.Custom;

import com.example.ormculinaryacadamy.Dao.CrudDao;
import com.example.ormculinaryacadamy.Entity.Payment;

import java.io.IOException;

public interface PaymentDao extends CrudDao<Payment> {
    String getCurrentId() throws IOException;

}
