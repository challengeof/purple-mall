package net.caidingke.test;

import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.base.BasicController;
import net.caidingke.common.result.Result;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author bowen
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class BaiDuOcrController extends BasicController {

    private static final String SECRET_KEY = "mQLELRG8YikZkznUa039SIw34CfbxVE6";

    private static final String API_KEY = "kLY4aSGeGoUojSZxEdryKG9v";

    private static final String APP_ID = "18654383";


    @PostMapping(value = "/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException, JSONException {
        byte[] image = file.getBytes();
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");

        String idCardSide = "front";

        JSONObject res = client.idcard(image, idCardSide, options);
        System.out.println(res.toString(2));
        return ok("nihao");
    }

}
