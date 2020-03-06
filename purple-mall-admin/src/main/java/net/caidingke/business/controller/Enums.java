package net.caidingke.business.controller;

import net.caidingke.api.BrandService;
import net.caidingke.domain.Brand;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * @author bowen
 */
public enum Enums {

    sub() {
        @Override
        public String process(String message) {
            Brand brand = brandService.findById(1L);
            return brand.getName();
        }
    };

    public abstract String process(String message);

    public BrandService brandService;

    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @Component
    public static class EnumsServiceInjector {
        @Reference(version = "1.0.0")
        private  BrandService brandService;

        @PostConstruct
        public void postConstruct() {
            for (Enums rt : EnumSet.allOf(Enums.class)) {
                rt.setBrandService(brandService);
            }
        }
    }
}
