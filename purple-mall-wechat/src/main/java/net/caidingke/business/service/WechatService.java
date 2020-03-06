package net.caidingke.business.service;

import com.thoughtworks.xstream.XStream;
import net.caidingke.business.enums.WechatEvent;
import net.caidingke.business.pojo.ReceiveEventMessage;
import net.caidingke.business.utils.XmlHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author bowen
 */
@Service
public class WechatService {

    private static final Logger LOG = LoggerFactory.getLogger(WechatService.class);
    private static final String EVENT = "event";

    public String receiveMpMessage(HttpServletRequest request) {
        try (InputStream in = request.getInputStream()) {
            XStream xStream = XmlHelper.getInstance();
            xStream.processAnnotations(ReceiveEventMessage.class);
            ReceiveEventMessage message = (ReceiveEventMessage) xStream.fromXML(in);
            LOG.info("from xml {}", message);
            if (Objects.equals(message.getMsgType(), EVENT)) {
                return WechatEvent.valueOf(message.getEvent()).process(message);
            }
        } catch (Exception e) {
            // ignore just print
            LOG.error(e.getMessage(), e);
        }
        return StringUtils.EMPTY;

    }

}
