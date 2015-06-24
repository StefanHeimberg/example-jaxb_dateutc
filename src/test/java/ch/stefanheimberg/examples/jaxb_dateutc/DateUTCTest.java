/*
 * Copyright 2015 Stefan Heimberg <kontakt@stefanheimberg.ch>.
 * 
 * All rights reserved.
 */
package ch.stefanheimberg.examples.jaxb_dateutc;

import ch.stefanheimberg.examples.jaxb_dateutc.test.JaxbHelper;
import ch.stefanheimberg.examples.jaxb_dateutc.test.TestHelper;
import ch.stefanheimberg.ns.jaxb_dateutc.Example;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class DateUTCTest {

    private static final String EXAMPLE_XML = "example.xml";
    private static final String EXAMPLE_XSD = "/example.xsd";
    
    @Test
    public void test() throws IOException, JAXBException, SAXException {
        byte[] xmlBytes = TestHelper.getResourceAsByteArray(EXAMPLE_XML);
        final Example example = JaxbHelper.unmarshall(xmlBytes, Example.class, EXAMPLE_XSD);
        
        assertNotNull(example);
        assertEquals("2015-05-19 13:37:40", toString(example.getDate()));
        assertEquals("2015-05-19 13:37:40", toString(example.getDateUTC()));
    }
    
    private String toString(final XMLGregorianCalendar calendar) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.toGregorianCalendar().getTime());
    }

}
