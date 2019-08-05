package net.caidingke.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bowen
 */
public interface ProductStatus {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Getter
    @AllArgsConstructor
    enum PublishStatus {
        PUBLISH(0, "发布"),
        ON_SHELVES(1, "上架"),
        OFF_SHELVES(2, "下架");
        private Integer type;
        private String name;
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    enum RecommendStatus {

    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Getter
    @AllArgsConstructor
    enum VerifyStatus {
        VERIFY_PENDING(0, "待审核"),
        PASS(1, "审核通过"),
        REJECT(2, "审核拒绝");
        private Integer type;
        private String name;
    }
}
