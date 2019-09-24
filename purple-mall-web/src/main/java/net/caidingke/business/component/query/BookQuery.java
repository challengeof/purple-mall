package net.caidingke.business.component.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bowen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BookQuery extends Query implements Serializable {
    private static final long serialVersionUID = -3713580498312791941L;
    private int status;
    private String keywords;
    private long minCreateTime;
    private long maxCreateTime;
    private Set<Integer> statusSet = new HashSet<>();

}
