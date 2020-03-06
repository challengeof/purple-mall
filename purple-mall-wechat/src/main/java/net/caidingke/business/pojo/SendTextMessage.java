package net.caidingke.business.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author bowen
 */
@XStreamAlias("xml")
public class SendTextMessage extends AbstractEventMessage {

    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "SendTextMessage{" +
                "Content='" + Content + '\'' +
                ", Event='" + Event + '\'' +
                ", EventKey='" + EventKey + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", ToUserName='" + ToUserName + '\'' +
                '}';
    }
}
