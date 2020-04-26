package net.caidingke.common.code.qrcode;

import com.google.common.base.Splitter;
import com.google.common.io.Files;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.common.mapper.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author bowen
 * @date 2019-08-15 16:05
 */
@Slf4j
public class WxCode {

    private static final String URL = "https://api.weixin.qq.com/wxa/getwxacode?access_token=%s";
    private static final String UUU = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=%s";
    private static final String UUUU = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
    private static final String aa = "https://api.weixin.qq.com/wxa/getwxacode?access_token=%s";

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

    public String getWxCode(String filename) throws IOException {
        Objects.requireNonNull(params.get("path"), "path must not be null");
        HttpResponse send = HttpRequest.post(String.format(UUU, this.accessToken)).body(JsonMapper.toJson(params)).send();
        String body = send.body();
        Map<String, String> result = JsonMapper.toMapWithType(body, String.class, String.class);
        if (result == null) {

        }
        byte[] bodyBytes = send.bodyBytes();
        Files.write(bodyBytes, new File(filename+".png"));
        return body;
    }

    public static void main(String[] args) throws IOException {
        // String str = "V10002_3,V10003_3,V10004_3,V10005_3,V10006_3,V10007_3,V10008_3,V10009_3,V10010_3,V10011_3,V10012_3,V10013_3,V10014_3,V10015_3,V10016_3,V10017_3,V10018_3,V10019_3,V10020_3,V10021_3,V10022_3,V10023_3,V10024_3,V10025_3,V10026_3,V10027_3,V10028_3,V10029_3,V10030_3,V10031_3";
        // List<String> list = Splitter.on(",").splitToList(str);
        // for (String s : list) {
        String s = "V10032_3";
            String filename = Splitter.on("_").splitToList(s).get(0);
            String page = String.format("page/activty/markteActive/middleware/middleware?scene=%s", s);
            WxCode.of("32_TYxEZbQIO1bCWjaZgvktWNk9z-sIpak2X0q8RGsMISDwecEUqraYDJzwi8Y_agapidmQa9bZazQ_sie5NulcoqSiNsDValMtjA0aTNuZFq4AxYQYgZ3zQ4yqC4UTKcKqhiZo5nXbuUm86XydFQMiAHAPAW")
                    // .path("page/services/realGoodsServe/productDetails/productDetails?goodsId=161")
                    .path(page)
                    .width(660)
                    .getWxCode(filename);
        // }

    }
}
