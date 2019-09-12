package net.caidingke.business.component.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bowen
 */
@Getter
@Setter
public class BookQuery extends Query implements Serializable {
    private static final long serialVersionUID = -3713580498312791941L;
    private int status;
    private String keywords;
    private long minCreateTime;
    private long maxCreateTime;
    private Set<Integer> statusSet = new HashSet<>();

}
