import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

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

    private static final String apiUrl = "https://api.prodless.com/avatar.png";
    // 时间间隔 2 秒
    private static final long period = 2 * 1000;

    private static int seq = 0;

    // 用来保存生成的头像的文件夹
    //private static final String folder = "d:\\aaa";

    // 循环多少次，就生成多少头像
    //private static final int LOOP_COUNT = 2;

    private static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        int argsLength = args.length;
        if (argsLength != 2) {
            System.out.println("Usage: java -jar app.jar saveFolder downloadNumber, example: java -jar app.jar d:\\aaa 10");
            return;
        }
        String folder = args[0];
        Integer LOOP_COUNT = Integer.parseInt(args[1]);
        Date now = new Date();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (seq < LOOP_COUNT) {
                    foo(folder, seq);
                    ++seq;
                }
                if (seq >= LOOP_COUNT) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, now, period);
    }

    private static void foo(String folder, Integer seq) {
        Path path = Paths.get(folder);
        StringBuilder sb = new StringBuilder();
        String nowTime = getNowTime();
        sb.append(path)
                .append("/")
                .append(nowTime)
                .append(".png");
        File file = new File(sb.toString());
        HttpRequest request = HttpRequest.get(apiUrl);
        HttpResponse response = request.timeout(40000)
                .execute();
        response.writeBody(file);
        System.out.println("Current output file sequence is " + (seq + 1) + ", fileName is " + nowTime + ".png");
    }
}