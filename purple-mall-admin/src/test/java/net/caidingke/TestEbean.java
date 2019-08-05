package net.caidingke;

import java.util.Optional;
import net.caidingke.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author bowen
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEbean {

    @Test
    public void queryBean() {
        Optional<User> user = User.find.where().username.eq("bowen").findOneOrEmpty();
        System.out.println(user.isPresent());
    }
}
