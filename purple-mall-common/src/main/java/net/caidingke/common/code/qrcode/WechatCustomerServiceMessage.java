package net.caidingke.common.code.qrcode;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.caidingke.common.mapper.JsonMapper;

import java.io.Serializable;

/**
 * @author bowen
 */
public class WechatCustomerServiceMessage implements Serializable {

    @JsonProperty("touser")
    private String toUser;
    @JsonProperty("msgtype")
    private String msgType;
    @JsonProperty("piniprogrampage")
    private Piniprogrampage piniprogrampage;

    private Text text;

    public static class Piniprogrampage {
        @JsonProperty("thumb_media_id")
        private String thumbMediaId;
        private String title;
        @JsonProperty("appid")
        private String miniProgramAppId;
        @JsonProperty("pagepath")
        private String miniProgramPagePath;

        public String getThumbMediaId() {
            return thumbMediaId;
        }

        public void setThumbMediaId(String thumbMediaId) {
            this.thumbMediaId = thumbMediaId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMiniProgramAppId() {
            return miniProgramAppId;
        }

        public void setMiniProgramAppId(String miniProgramAppId) {
            this.miniProgramAppId = miniProgramAppId;
        }

        public String getMiniProgramPagePath() {
            return miniProgramPagePath;
        }

        public void setMiniProgramPagePath(String miniProgramPagePath) {
            this.miniProgramPagePath = miniProgramPagePath;
        }

    }

    public static class Text {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Piniprogrampage getPiniprogrampage() {
        return piniprogrampage;
    }

    public void setPiniprogrampage(Piniprogrampage piniprogrampage) {
        this.piniprogrampage = piniprogrampage;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public static void main(String[] args) {
        WechatCustomerServiceMessage wechatCustomerServiceMessage = new WechatCustomerServiceMessage();
        wechatCustomerServiceMessage.setMsgType("miniprogrampage");
        wechatCustomerServiceMessage.setToUser(":");
        Piniprogrampage piniprogrampage = new Piniprogrampage();
        piniprogrampage.setTitle("title");
        piniprogrampage.setMiniProgramAppId("appid");
        piniprogrampage.setMiniProgramPagePath("path");
        piniprogrampage.setThumbMediaId("mei");
        wechatCustomerServiceMessage.setPiniprogrampage(piniprogrampage);
        System.out.println(JsonMapper.toJson(wechatCustomerServiceMessage));


    }
}
