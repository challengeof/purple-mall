package net.caidingke.business.enums;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.business.pojo.ReceiveEventMessage;
import net.caidingke.business.pojo.SendTextMessage;
import net.caidingke.business.utils.XmlHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author bowen
 */
@Slf4j
public enum WechatEvent {
    /**
     * 微信事件
     */
    subscribe() {
        @Override
        public String process(ReceiveEventMessage message) {
            String custom = message.getEventKey();
            String content = "终于等到你\uD83E\uDD17欢迎关注越野部落。";
            if (Objects.equals(custom, "qrscene_1902") || Objects.equals("1902", custom)) {
                content = "<a data-miniprogram-appid='wx17f444ee74bc113d' data-miniprogram-path='/page/tabbar/index/index?custom=true' href='http://www.wiclan.cn'>终于等到你\uD83E\uDD17欢迎关注越野部落。</a>";

            }
            return build(message, content);
        }
    },
    unsubscribe() {
        @Override
        public String process(ReceiveEventMessage message) {
            return StringUtils.EMPTY;
        }
    },
    CLICK() {
        @Override
        public String process(ReceiveEventMessage message) {
            return StringUtils.EMPTY;
        }
    },
    VIEW() {
        @Override
        public String process(ReceiveEventMessage message) {
            return StringUtils.EMPTY;
        }
    },
    SCAN() {
        @Override
        public String process(ReceiveEventMessage message) {
            return subscribe.process(message);
        }
    },
    LOCATION() {
        @Override
        public String process(ReceiveEventMessage message) {
            return StringUtils.EMPTY;
        }
    };

    public abstract String process(ReceiveEventMessage message);

    private static String build(ReceiveEventMessage message, String content) {
        SendTextMessage textMessage = new SendTextMessage();
        textMessage.setFromUserName(message.getToUserName());
        textMessage.setToUserName(message.getFromUserName());
        textMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        textMessage.setMsgType("text");
        textMessage.setContent(content);
        XStream xStream = XmlHelper.getInstance();
        xStream.processAnnotations(SendTextMessage.class);
        String xml = xStream.toXML(textMessage);
        log.info("from xml {}", xml);
        return xml;
    }

}
