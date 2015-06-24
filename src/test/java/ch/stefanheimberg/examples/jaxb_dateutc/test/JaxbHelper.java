/*
 * Copyright 2015 Stefan Heimberg <kontakt@stefanheimberg.ch>.
 * 
 * All rights reserved.
 */
package ch.stefanheimberg.examples.jaxb_dateutc.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class JaxbHelper {

    public static <T> T unmarshall(byte[] xmlBytes, Class<T> type, final String schemaLocation) throws JAXBException, SAXException {
        final Schema schema;
        // schema validierung nur aktiveren wenn schemLocation angegeben wurde.
        if (null == schemaLocation) {
            schema = null;
        } else {
            schema = SchemaHelper.getSchema(schemaLocation);
        }
        return unmarshall(xmlBytes, type, schema);
    }

    public static <T> T unmarshall(byte[] xmlBytes, Class<T> type, final Schema schema) throws JAXBException {
        final JAXBContext c = JAXBContext.newInstance(type);

        final Unmarshaller um = c.createUnmarshaller();

        // schema validierung nur aktiveren wenn schemLocation angegeben wurde.
        if (null != schema) {
            um.setSchema(schema);
        }

        final ByteArrayInputStream bais = new ByteArrayInputStream(xmlBytes);
        return type.cast(um.unmarshal(bais));
    }

    public static byte[] marshall(Object jaxbObject, final String schemaLocation) throws JAXBException, SAXException {
        final Schema schema;
        // schema validierung nur aktiveren wenn schemLocation angegeben wurde.
        if (null == schemaLocation) {
            schema = null;
        } else {
            schema = SchemaHelper.getSchema(schemaLocation);
        }
        return marshall(jaxbObject, schema);
    }

    public static byte[] marshall(Object jaxbObject, final Schema schema) throws JAXBException {
        final JAXBContext c = JAXBContext.newInstance(jaxbObject.getClass());

        final Marshaller m = c.createMarshaller();

        // schema validierung nur aktiveren wenn schemLocation angegeben wurde.
        if (null != schema) {
            m.setSchema(schema);
        }

        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        m.marshal(jaxbObject, baos);

        return baos.toByteArray();
    }

    private JaxbHelper() {
    }

}
