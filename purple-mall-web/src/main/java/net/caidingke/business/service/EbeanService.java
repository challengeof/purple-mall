package net.caidingke.business.service;

import net.caidingke.domain.Customer;
import org.springframework.stereotype.Service;

/**
 * @author bowen
 */
@Service
public class EbeanService {


    public void ref(Long id) {
        Customer customer = Customer.find.ref(id);
        System.out.println("ref");
    }
}
