package net.caidingke;

import net.caidingke.domain.Ebean;
import org.junit.Test;

/**
 * @author bowen
 */
public class EbeanTest {

    @Test
    public void testVersion() {
        Ebean ebean = new Ebean();
        ebean.setName("1");
        ebean.save();


    }
}
