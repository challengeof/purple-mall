package net.caidingke.business.controller;

import lombok.extern.slf4j.Slf4j;
import net.caidingke.api.BrandService;
import net.caidingke.base.BasicController;
import net.caidingke.common.result.Result;
import net.caidingke.common.result.ResultPage;
import net.caidingke.domain.Brand;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;

/**
 * @author bowen
 */
@RestController
@Slf4j
public class BrandController extends BasicController {

    @Reference(version = "1.0.0")
    private BrandService brandService;

    private final RocketMQTemplate rocketMQTemplate;

    public BrandController(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @GetMapping("/brands")
    public ResultPage<Brand> findBrand(@RequestParam String name, @RequestParam Integer status,
            @RequestParam int page, @RequestParam int pageSize) {
        return brandService.findBrand(name, status, page, pageSize);
    }

    @PostMapping("/brand")
    public Result createBrand(Brand brand) {
        brandService.createBrand(brand);
        return ok();
    }

    @PutMapping("/brand")
    public Result updateBrand(Long id, Brand brand) {
        brandService.updateBrand(id, brand);
        return ok();
    }

    @GetMapping("/brand")
    public Result<Brand> findById(@RequestParam Long id) throws Exception {
        Brand brand = brandService.findById(id);
        return ok(brand);
    }

    @GetMapping("/mq")
    public Result sendMessage(String message) {
        SendResult sendResult = rocketMQTemplate.syncSend("springTopic", message);
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", "springTopic", sendResult);
        return ok();
    }
}
