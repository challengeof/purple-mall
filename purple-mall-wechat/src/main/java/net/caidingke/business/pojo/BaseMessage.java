package net.caidingke.business.pojo;

import java.io.Serializable;

/**
 * @author bowen
 */
public abstract class BaseMessage implements Serializable {

    protected String CreateTime;
    protected String MsgType;
    protected String FromUserName;
    protected String ToUserName;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }
}
