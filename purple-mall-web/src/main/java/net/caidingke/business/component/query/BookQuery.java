package net.caidingke.business.component.query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bowen
 */
@Getter
@Setter
public class BookQuery implements Serializable {

    private static final long serialVersionUID = -3713580498312791941L;
    private String sortField;
    private String sortOrder;
    private int status;
    private String keywords;
    private int start;
    private int limit;
    private long minCreateTime;
    private long maxCreateTime;
    private Set<Integer> statusSet = new HashSet<>();
}
