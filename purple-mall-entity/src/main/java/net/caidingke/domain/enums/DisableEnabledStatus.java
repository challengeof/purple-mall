package net.caidingke.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bowen
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum DisableEnabledStatus {
    ENABLED(0, "启用"),
    DISABLE(1, "停用");
    private Integer type;
    private String name;
}
