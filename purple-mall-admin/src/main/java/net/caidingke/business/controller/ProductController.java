// package net.caidingke.business.controller;
//
// import net.caidingke.api.ProductService;
// import net.caidingke.base.BasicController;
// import net.caidingke.common.result.Result;
// import net.caidingke.domain.Product;
// import org.apache.dubbo.config.annotation.Reference;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// /**
//  * @author bowen
//  */
// @RestController
// public class ProductController extends BasicController {
//
//     @Reference(version = "1.0.0")
//     private ProductService productService;
//
//     @GetMapping("findbyid")
//     public Result findById(Long id) {
//         Product product = productService.findById(id);
//         return ok();
//     }
//
//     @PostMapping("save")
//     public Result save(String name) {
//         Product product = new Product();
//         product.setName(name);
//         productService.save(product);
//         return ok();
//     }
// }
