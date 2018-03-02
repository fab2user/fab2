package eu.cehj.cdb2.common.service;

import javax.xml.bind.annotation.XmlRootElement;

import eu.chj.cdb2.common.Data;

/**
 * Only purpose is to wrap xml jaxb message inside an XmlRootElement.
 *
 */
@XmlRootElement
public class CdbPushMessage {

    private Data data;

    public Data getData() {
        return this.data;
    }

    public void setData(final Data data) {
        this.data = data;
    }
}
