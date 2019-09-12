package net.caidingke.common.code.qrcode;

import com.google.common.io.Files;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.common.mapper.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author bowen
 * @date 2019-08-15 16:05
 */
@Slf4j
public class WxCode {

    private static final String URL = "https://api.weixin.qq.com/wxa/getwxacode?access_token=%s";
    private static final String UUU = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=%s";

    private Map<String, Object> params;

    /**
     * 接口调用凭证
     */
    private String accessToken;

    public static WxCode of(String accessToken) {
        return new WxCode(accessToken);
    }


    public WxCode(String accessToken) {
        Objects.requireNonNull(accessToken, "access token must not be null");
        this.params = new HashMap<>(5);
        this.accessToken = accessToken;
    }


    /**
     * 扫码进入的小程序页面路径，最大长度 128 字节，不能为空；
     * 对于小游戏，可以只传入 query 部分，来实现传参效果，
     * 如：传入 "?foo=bar"，即可在 wx.getLaunchOptionsSync 接口中的 query 参数获取到 {foo:"bar"}。
     */
    public WxCode path(String path) {
        this.params.put("path", path);
        return this;
    }

    /**
     * 二维码的宽度，单位 px。最小 280px，最大 1280px
     * default 430
     */
    public WxCode width(int width) {
        this.params.put("width", width);
        return this;
    }

    /**
     * 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
     * default false
     */
    public WxCode autoColor(boolean autoColor) {
        this.params.put("auto_color", autoColor);
        return this;
    }

    /**
     * auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     */
    private Map<String, String> lineColor;

    public WxCode lineColor(Map<String, String> lineColor) {
        this.params.put("line_color", lineColor);
        return this;
    }

    /**
     * 是否需要透明底色，为 true 时，生成透明底色的小程序码
     * default false
     */
    public WxCode isHyaline(boolean isHyaline) {
        this.params.put("is_hyaline", isHyaline);
        return this;
    }

    public String getWxCode() throws IOException {
        Objects.requireNonNull(params.get("path"), "path must not be null");
        HttpResponse send = HttpRequest.post(String.format(URL, this.accessToken)).body(JsonMapper.toJson(params)).send();
        String body = send.body();
        Map<String, String> result = JsonMapper.toMapWithType(body, String.class, String.class);
        if (result == null) {

        }
        byte[] bodyBytes = send.bodyBytes();
        Files.write(bodyBytes, new File("123456.png"));
        return body;
    }

    public static void main(String[] args) throws IOException {
        WxCode.of("24_lkpXkV7q3y2sX_1uLDV9epyGVAKZCx_sKyE5NUlVXs5bbHeWo9Pcbuh91yWFsaecKcLHoEDpi1zvELONdh3k-nmO_M0hP10tr2kOyggjTwzRxzj4n4PTSBZdrGNRPsVmD6kohPMIrJmTnblfKVQeAIAQBW")
                .path("?a=b")
                .width(200)
                .getWxCode();
    }

}
