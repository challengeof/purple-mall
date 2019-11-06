package net.caidingke.common.T;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import net.caidingke.common.mapper.JsonMapper;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * @author bowen
 */
public class WechatUtils {

    private static boolean initialized = false;

    private static void initialize() {
        if (initialized) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    public static WxOpenGId decrypt(String sessionKey, String encryptedData, String ivStr) throws Exception {
        initialize();
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(Base64.decodeBase64(ivStr)));
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES"), params);
        String openGid = new String(PKCS7Encoder.decode(cipher.doFinal(Base64.decodeBase64(encryptedData))), StandardCharsets.UTF_8);
        return JsonMapper.fromJson(openGid, WxOpenGId.class);
    }

    public static WxRes getWxCode(String code, String appId, String secret) {
        String code2session = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        String url = String.format(code2session, appId, secret, code);
        HttpResponse response = HttpRequest.get(url).send();
        String body = response.body();
        return JsonMapper.fromJson(body, WxRes.class);
    }

    public static class WxRes {
        private String openid;
        private String session_key;
        private String unionid;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getSession_key() {
            return session_key;
        }

        public void setSession_key(String session_key) {
            this.session_key = session_key;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }
    }

    public static class WxOpenGId {
        public String getOpenGId() {
            return openGId;
        }

        public void setOpenGId(String openGId) {
            this.openGId = openGId;
        }

        private String openGId;

    }

    public static void main(String[] args) throws Exception {
        // String data = "";
        // String iv = "";
        // WxRes wxRes = getWxCode("", "wx17f444ee74bc113d", "803ba684c3feeea06544432d606bc067");
        // String sessionKey = wxRes.getSession_key();
        // System.out.println(sessionKey);
        // WxOpenGId wxOpenGId = decrypt(sessionKey, data, iv);
        // System.out.println(wxOpenGId.getOpenGId());

        HttpResponse response = HttpRequest.get("http://baidu.com").send();
        System.out.println("tt");
    }

}
