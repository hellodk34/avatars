import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import net.andreinc.mockneat.MockNeat;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author hellodk
 * @date 2021-09-17 23:36
 */
public class MainService {

    /**
     * 2022-04-08 update: 此 api 已经停止服务，于是改写了这个项目
     */
    // private static final String apiUrl = "https://api.prodless.com/avatar.png";

    // 时间间隔 2 秒
    private static final long period = 2 * 1000;

    private static int seq = 0;

    // gravatar 官方地址是 https://www.gravatar.com/avatar/ 此处使用国内 cdn 加快速度
//    private static String prefix = "https://sdn.geekzu.org/avatar/";
    private static String prefix = "https://gravatar.loli.net/avatar/";

    private static MockNeat mockNeat;

    static {
        mockNeat = MockNeat.secure();
    }

    private static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        int argsLength = args.length;
        if (argsLength != 3) {
            System.out.println("Usage: java -jar app.jar saveFolder downloadNumber, example: java -jar app.jar d:\\aaa 10 TYPE");
            return;
        }
        String folder = args[0];
        Integer LOOP_COUNT = Integer.parseInt(args[1]);
        String type = args[2];
        Date now = new Date();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (seq < LOOP_COUNT) {
                    foo(folder, seq, type);
                    ++seq;
                }
                else {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, now, period);
    }

    private static void foo(String folder, Integer seq, String type) {
        Path path = Paths.get(folder);
        StringBuilder sb = new StringBuilder();
        String nowTime = getNowTime();
        sb.append(path).append("/").append(nowTime).append(".png");
        File file = new File(sb.toString());
        HttpRequest request = HttpRequest.get(apiUrl(type));
        HttpResponse response = request.timeout(40000).execute();
        response.writeBody(file);
        System.out.println("Current output file sequence is " + (seq + 1) + ", fileName is " + nowTime + ".png");
    }

    private static String apiUrl(String type) {
        String fakeEmail = mockNeat.emails().val().trim().toLowerCase();
        String result = DigestUtils.md5Hex(fakeEmail).toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix)
                .append(result)
                .append("?d=")
                .append(type);
        return sb.toString();
    }
}
