package com.csit214.utils;

import com.csit214.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherCodeGenerator {

    @Autowired
    private VoucherRepository voucherRepository;
    private static final String prefix = "VOUCHER";

    public String genVoucherCode() {
        int counter = voucherRepository.findAll().size();
        return prefix + counter;
    }
}
