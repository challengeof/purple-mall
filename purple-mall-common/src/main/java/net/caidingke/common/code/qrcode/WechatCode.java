package net.caidingke.common.code.qrcode;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.common.mapper.JsonMapper;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author bowen
 */
@Getter
@Setter
public class WechatCode {

    private static final String URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
    private static final String TICKET = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";
    private static final String TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static final String UPLOAD = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";
    private static final String SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    /**
     * 接口调用凭证
     */
    private String accessToken;

    public static WechatCode of(String accessToken) {
        return new WechatCode(accessToken);
    }

    public WechatCode(String accessToken) {
        Objects.requireNonNull(accessToken, "access token must not be null");
        this.accessToken = accessToken;
    }

    // public static String getAccessToken() {
    //     String url = String.format(TOKEN, "wx17f444ee74bc113d", "803ba684c3feeea06544432d606bc067");
    //     HttpResponse send = HttpRequest.get(url).send().charset("utf-8");
    //     String body = send.body();
    //     Map<Object, Object> result = JsonMapper.toMapWithType(body, Object.class, Object.class);
    //     String accessToken = String.valueOf(result.get("access_token"));
    //     System.out.println(accessToken);
    //     return accessToken;
    // }

    public String getTicket() throws IOException {
        String json = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 1902}}}";
        HttpResponse send = HttpRequest.post(String.format(URL, this.accessToken)).body(json).send();
        String body = send.body();
        Map<String, String> result = JsonMapper.toMapWithType(body, String.class, String.class);
        String ticket = null;
        if (result != null) {
            ticket = result.get("ticket");
        }
        return ticket;

    }

    public void getWechatCode(String ticket) throws IOException {
        HttpResponse send = HttpRequest.get(String.format(TICKET, ticket)).send();
        if (send.statusCode() == 200) {
            byte[] bodyBytes = send.bodyBytes();
            Files.write(bodyBytes, new File("1234567.png"));
        }
    }

    public static void uploadMedia(String accessToken, String type, File file) {
        String url = String.format(UPLOAD, accessToken, type);
        HttpRequest request = HttpRequest.post(url);
        request.contentType("multipart/form-data");
        request.charset("utf-8");
        request.form("file", file);
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        System.out.println(respJson);
    }

    public static void send(String accessToken,WechatCustomerServiceMessage message) {
        String url = String.format(SEND, accessToken);
        HttpRequest request = HttpRequest.post(url);
        request.charset("utf-8");
        request.body(JsonMapper.toJson(message));
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        System.out.println(respJson);
    }

    public static void sendMessageNew(String accessToken) {
        Map<String, Value> data = ImmutableMap.of("character_string2", new Value("dingdanbianhao"),"thing6",new Value("线路名称"),"time3",new Value("15:01"),"date4",new Value("15:01"),"thing8",new Value("温馨提示"));
        Message message = Message.builder().wechatAppId("11111").page("/page/page/index").touser("oEEv74qYAbMO6Zyd2iwQiibbzNrQ").template_id("pEhurn_mEUFlO5Zh8wS3ZuLAQQRxUJ7viuP-_OSxN3s").data(data).build();
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s", accessToken);
        HttpResponse response = HttpRequest.post(url).charset("utf-8").bodyText(JsonMapper.toJson(message)).send();
        System.out.println(response);
    }

    public static void sendMessage() {

    }

    public static void main(String[] args) throws IOException {

        // String str = getAccessToken();
        // System.out.println(str);
        // sendMessageNew("31_MN74FcU7JmE-gJFdBQqb05blaEJ4rni2mRK2xV1WRg56IArwSysRnHmSMSivqKmVh98MDY0gGOtxIjazZosoX3BdgPJRmuLAN8DMBCPGuCU-V-Zrxv-ZPmACFuaFngB4dKCbEShWBJBByHmhBMQdAFAWHL");
        // String accessToken = WechatCode.getAccessToken();
        // String token = "29_mybcf7DUtGLhy_sNtsw8z0JughoACLU_jO1jkNm29aoGxJxEwt3Q8PCGD4WuvJZkVLC0xyS-bS1NILPebrqfxtXpLBOIGTB9bTj8M34ZE5bLkjYxFxoAJmQZ9_o9EzIJw6ypn0WRyp8p5BZRQVOdACAZEX";
        // File file = new File("cc.png");
        // uploadMedia(token, "image", file);
        // String mediaId = "Y_t1SdghyBZf9k48QvHc82gkVaf1S3Ilujck5JAxXIAoqNnG6o6tBV0sPYdz9wvI";
        // send(token, build(mediaId));
        // WechatCode wechatCode = WechatCode.of(accessToken);
        // String ticket = wechatCode.getTicket();
        // wechatCode.getWechatCode(ticket);

        // String url = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN ", "28_UY8sCG5nFj-9q7yTd9H58Zhgv4yE8W3ZBh_YZohloQSEIdMFjFP8981nXYl2fRaAAQodbQhV3KrjRLHDbuHVi8L7V2Lze94WW93TOU0aLewhaBkv2XXDdxx1lk_UoJNok79AcXvx4OQX34FZFQIiAIAJUM", "obMvw1P_K65JwxjY5WYNg3ofKaEo");
        // HttpResponse send = HttpRequest.get(url).send().charset("utf-8");
        // String str = send.bodyText();
        // System.out.println(str);
        // System.out.println(accessToken);
        // String accessToken = "29_xn3WyEjMrgdeQwXpQO_GTpymK07mM-MKVq5szQht6e1lae0jzLV1OCsxgWvC6cVE7LKZSYY26kWhR3nz1qYWyLp2yMjF8tw6doEW3Ys2OtD84fmHUYdUnqtTClcB1Rpiih8xpZRb3IOspiQPCTHeAIAJAO";
        // sendMessageNew(accessToken);

    }

    private static WechatCustomerServiceMessage build(String mediaId) {
        WechatCustomerServiceMessage wechatCustomerServiceMessage = new WechatCustomerServiceMessage();
        wechatCustomerServiceMessage.setMsgType("miniprogrampage");
        wechatCustomerServiceMessage.setToUser("obMvw1P_K65JwxjY5WYNg3ofKaEo");
        WechatCustomerServiceMessage.Text text = new WechatCustomerServiceMessage.Text();
        // String content = "<a data-miniprogram-appid='wx17f444ee74bc113d' data-miniprogram-path='/page/tabbar/index/index?custom=true' href='http://www.wiclan.cn'>原生态，好风景</a>";
        // text.setContent(content);
        // wechatCustomerServiceMessage.setText(text);
        WechatCustomerServiceMessage.Piniprogrampage piniprogrampage = new WechatCustomerServiceMessage.Piniprogrampage();
        piniprogrampage.setTitle("title");
        piniprogrampage.setMiniProgramAppId("wx17f444ee74bc113d");
        piniprogrampage.setMiniProgramPagePath("page/tabbar/index/index?custom=true");
        piniprogrampage.setThumbMediaId(mediaId);
        wechatCustomerServiceMessage.setPiniprogrampage(piniprogrampage);
        return wechatCustomerServiceMessage;
    }

    @Getter
    @Setter
    @Builder
    public static class Message {
        private String page;
        private String template_id;
        private String touser;
        private Map<String,Value> data;
        private String access_token;
        private String wechatAppId;


    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Value {
        private String value;
    }
}
