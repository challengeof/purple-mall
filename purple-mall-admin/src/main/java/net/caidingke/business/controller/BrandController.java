package net.caidingke.business.controller;

import lombok.extern.slf4j.Slf4j;
import net.caidingke.api.BrandService;
import net.caidingke.api.OrderService;
import net.caidingke.base.BasicController;
import net.caidingke.common.AbstractLottery;
import net.caidingke.common.result.Result;
import net.caidingke.common.result.ResultPage;
import net.caidingke.domain.Brand;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author bowen
 */
@RestController
@Slf4j
public class BrandController extends BasicController {

    @Reference(version = "1.0.0")
    private BrandService brandService;

    // @Reference(version = "1.0.0")
    // private OrderService orderService;

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
        System.out.println("bbddd");
        Brand brand = brandService.findById(id);
        // String str = orderService.findById(id);
        return ok(brand);
    }

    @GetMapping("/mq")
    public Result sendMessage(String message) {
        SendResult sendResult = rocketMQTemplate.syncSend("springTopic", message);
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", "springTopic", sendResult);
        return ok();
    }

    // @GetMapping("/order")
    // public Result findO() {
    //     String str = orderService.findById(1L);
    //     System.out.println(str);
    //     return ok(str);
    // }

    @PostMapping("/lottery")
    public Result testLottery(@RequestBody LotteryParams params) {
        return ok(AbstractLottery.lottery(params.getCandidate(), params.getPrizeQuantity(), params.isFair()));
    }

    @GetMapping("/time")
    public Result redis() {
        return ok(LocalDateTime.now());
    }

    // public static void main(String[] args) throws ExecutionException, InterruptedException {
    //     CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    //         int i = 1 / 0;
    //         return 100;
    //     });
    //     future.join();
    //     // future.get();
    // }
    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }
    public static void main(String[] args) throws Exception {
        T t = new T();
        System.out.println(t.hashCode());
        //查看字节序
        System.out.println(ByteOrder.nativeOrder());

        //打印当前jvm信息
        System.out.println(VM.current().details());
        System.out.println("======");
        String classLayout = ClassLayout.parseInstance(t).toPrintable();
        System.out.println(classLayout);

    }

    public static class T {

    }
}

