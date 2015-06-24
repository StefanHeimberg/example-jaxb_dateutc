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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class DateUTCTest {

    private static final String EXAMPLE_XML = "example.xml";
    private static final String EXAMPLE_XSD = "/example.xsd";
    
    @BeforeClass
    public static void beforeClass() {
        System.setProperty("user.timezone", "Europe/Zurich");
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
    }
    
    @Test
    public void test() throws IOException, JAXBException, SAXException {
        final byte[] xmlBytes = TestHelper.getResourceAsByteArray(EXAMPLE_XML);
        final Example example = JaxbHelper.unmarshall(xmlBytes, Example.class, EXAMPLE_XSD);
        
        assertNotNull(example);
        
        assertEquals("2015-05-19 13:37:40 Europe/Zurich", formatDateTimeZone(example.getDateLocal()));
        assertEquals("2015-05-19 13:37:40 GMT+02:00", formatDateTimeZone(example.getDateLocalBerlin()));
        assertEquals("2015-05-19 11:37:40 GMT+00:00", formatDateTimeZone(example.getDateUTC()));
    }
    
    private String formatDateTimeZone(final XMLGregorianCalendar calendar) {
        final GregorianCalendar gc = calendar.toGregorianCalendar();
        
        final Date dateTime = gc.getTime();
        final TimeZone timeZone = gc.getTimeZone();
        
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        
        return sdf.format(dateTime) + " " + timeZone.getID();
    }

}
