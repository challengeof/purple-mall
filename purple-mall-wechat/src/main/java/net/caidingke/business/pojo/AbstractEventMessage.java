package net.caidingke.business.pojo;

/**
 * @author bowen
 */
public abstract class AbstractEventMessage extends BaseMessage {
    protected String Event;
    protected String EventKey;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
