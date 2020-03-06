package net.caidingke.business.utils;

import com.thoughtworks.xstream.XStream;

/**
 * @author bowen
 */
public final class XmlHelper {

    public static XStream getInstance() {
        XStream xstream = new XStream();
        xstream.ignoreUnknownElements();
        xstream.setMode(XStream.NO_REFERENCES);
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypesByWildcard(new String[]{
                "net.caidingke.**"
        });

        xstream.setClassLoader(Thread.currentThread().getContextClassLoader());
        return xstream;
    }

}
