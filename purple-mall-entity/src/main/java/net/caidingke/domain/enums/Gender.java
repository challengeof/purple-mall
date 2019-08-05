package net.caidingke.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bowen
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum Gender {
    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(2, "女"),
    /**
     * 未知 微信等第三方平台最开始的用户没有限制性别。
     */
    UNKNOWN(3, "未知");

    private Integer value;
    private String name;

    public static Gender find(int type) {
        return Arrays.stream(Gender.values()).filter(ageLabel -> ageLabel.value == type).findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        System.out.println("啊！！啊啊啊！！啊bbb！！！".replaceAll("[^\u4E00-\u9FA5]", ""));
    }
}
