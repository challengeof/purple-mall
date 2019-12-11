package net.caidingke.impl;

import net.caidingke.api.OrderService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author bowen
 */
@Service(version = "1.0.0")
public class OrderServiceImpl implements OrderService {
    @Override
    public String findById(Long id) {
        System.out.println("id");
        return "id" + id;
    }
}
