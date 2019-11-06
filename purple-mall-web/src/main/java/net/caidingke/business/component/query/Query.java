package net.caidingke.business.component.query;

import lombok.Data;

/**
 * basic query
 *
 * @author bowen
 */
@Data
public class Query {
    protected int start;
    protected int limit;
    protected String sortField;
    protected String sortOrder;

}
