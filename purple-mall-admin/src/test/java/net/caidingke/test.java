package net.caidingke;

import net.caidingke.domain.User;
import org.junit.Test;

/**
 * @author bowen
 */
public class test {

    @Test
    public void queryBean() {
        User.find.where().username.eq("bowen").findOneOrEmpty();
    }
}
