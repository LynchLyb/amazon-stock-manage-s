import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestJava {

    @Test
    public void test() {
//        try {
//            JSch jsch = new JSch();
//            Session session = jsch.getSession("root", "121.41.112.124", 22);
//            session.setPassword("958293Li.");
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.connect();
//        }catch (Throwable e){
//            log.error("failed", e);
//        }


        System.getenv().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}
