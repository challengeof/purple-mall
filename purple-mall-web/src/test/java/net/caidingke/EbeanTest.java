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
        ebean.insert();
        Ebean ebean1 = Ebean.find.query().where().eq("name", "1").findOne();
        System.out.println(ebean1.getVersion());
        ebean1.setName("2");
        ebean1.update();
        Ebean ebean3 = Ebean.find.query().where().eq("name", "2").findOne();
        System.out.println(ebean3.getVersion());
    }

    @Test
    public void testV() {

        Ebean ebean = Ebean.find.query().where().eq("name", "1").findOne();
        System.out.println(ebean.getName());
    }


}
