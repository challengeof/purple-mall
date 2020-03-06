package net.caidingke.business.controller;

import net.caidingke.business.service.EbeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bowen
 */
@RestController
public class EbeanController {

    @Autowired
    private EbeanService ebeanService;

    @GetMapping("/ref")
    public void ref(Long id) {
        ebeanService.ref(id);
    }
}
