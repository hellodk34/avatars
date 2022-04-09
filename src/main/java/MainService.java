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
    private static String serverUrl = "https://sdn.geekzu.org/avatar/";

    private static MockNeat mockNeat;

    private static Integer pixel = null;

    static {
        mockNeat = MockNeat.secure();
    }

    private String getNowTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        int argsLength = args.length;
        if (argsLength != 3 && argsLength != 4) {
            System.out.println("Usage: java -jar /path/to/app.jar SAVE_FOLDER DOWNLOAD_NUMBER TYPE PIXEL" + System.lineSeparator() + System.lineSeparator() +
                    "Example: java -jar d:\\app.jar d:\\aaa 10 identicon 240" + System.lineSeparator() + System.lineSeparator() +
                    "SAVE_FOLDER: the folder path you want to store these images" + System.lineSeparator() +
                    "DOWNLOAD_NUMBER: number you want to save to local disk" + System.lineSeparator() +
                    "TYPE: identicon | retro | robohash" + System.lineSeparator() +
                    "  identicon: a geometric pattern based on an email hash" + System.lineSeparator() +
                    "  retro: awesome generated, 8-bit arcade-style pixelated faces" + System.lineSeparator() +
                    "  robohash: a generated robot with different colors, faces, etc" + System.lineSeparator() +
                    "PIXEL(optional): a number between 1 and 2048, default is 80 if you did not specify this parameter"
            );
            return;
        }
        String folder = args[0];
        Integer LOOP_COUNT = Integer.parseInt(args[1]);
        String type = args[2];

        if (argsLength == 4) {
            pixel = Integer.parseInt(args[3]);
            if (pixel < 1 || pixel > 2048) {
                System.out.println("PIXEL should be between 1 and 2048.");
                return;
            }
        }
        MainService mainService = new MainService();
        Date now = new Date();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (seq < LOOP_COUNT) {
                    mainService.foo(folder, seq, type, pixel);
                    ++seq;
                } else {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, now, period);
    }

    private void foo(String folder, Integer seq, String type, Integer pixel) {
        Path path = Paths.get(folder);
        StringBuilder sb = new StringBuilder();
        String nowTime = getNowTime();
        sb.append(path).append("/").append(nowTime).append(".png");
        File file = new File(sb.toString());
        HttpRequest request = HttpRequest.get(apiUrl(type, pixel));
        HttpResponse response = request.timeout(40000).execute();
        response.writeBody(file);
        System.out.println("Current output file sequence is " + (seq + 1) + ", fileName is " + nowTime + ".png");
    }

    private String apiUrl(String type, Integer pixel) {
        String fakeEmail = mockNeat.emails().val().trim().toLowerCase();
        String result = DigestUtils.md5Hex(fakeEmail).toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(serverUrl)
                .append(result)
                .append("?d=")
                .append(type);
        if (pixel != null) {
            sb.append("&s=")
                    .append(pixel);
        }
        return sb.toString();
    }
}
