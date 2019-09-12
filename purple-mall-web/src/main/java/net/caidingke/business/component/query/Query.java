package net.caidingke.business.component.query;

import lombok.Getter;
import lombok.Setter;

/**
 * basic query
 *
 * @author bowen
 */
@Getter
@Setter
public class Query {
    protected int start;
    protected int limit;
    protected String sortField;
    protected String sortOrder;
}
