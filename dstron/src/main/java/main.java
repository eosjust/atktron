import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Response;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) {
        ScheduledExecutorService executor1 = Executors.newSingleThreadScheduledExecutor();
        final Random random=new Random();
        executor1.scheduleWithFixedDelay(new Runnable() {
            public void run() {

                int block = random.nextInt(24888200);
                JSONObject params=new JSONObject();
                params.put("num",block);
                Response response = Http.post3("https://api.trongrid.io/wallet/getblockbynum",params.toJSONString(), Header.create(),5000);

                System.out.println(response.getContent());
            }
        },100,1000, TimeUnit.MILLISECONDS);
    }
}
