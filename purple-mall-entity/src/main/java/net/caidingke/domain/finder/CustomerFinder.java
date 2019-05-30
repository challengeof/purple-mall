package net.caidingke.domain.finder;

import io.ebean.Finder;
import net.caidingke.domain.Customer;
import net.caidingke.domain.query.QCustomer;

/**
 * @author bowen
 */
public class CustomerFinder extends Finder<Long, Customer> {

    /**
     * Construct using the default EbeanServer.
     */
    public CustomerFinder() {
        super(Customer.class);
    }

    /**
     * Start a new typed query.
     */
    public QCustomer where() {
        return new QCustomer();
    }

}
