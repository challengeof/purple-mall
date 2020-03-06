package net.caidingke.business.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bowen
 */
@Getter
@Setter
@XStreamAlias("xml")
public class ReceiveEventMessage extends AbstractEventMessage {

    private String Ticket;

    @Override
    public String toString() {
        return "ReceiveEventMessage{" +
                "Ticket='" + Ticket + '\'' +
                ", Event='" + Event + '\'' +
                ", EventKey='" + EventKey + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", ToUserName='" + ToUserName + '\'' +
                '}';
    }
}
