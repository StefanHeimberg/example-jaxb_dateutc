/*
 * Copyright 2015 Stefan Heimberg <kontakt@stefanheimberg.ch>.
 * 
 * All rights reserved.
 */
package ch.stefanheimberg.examples.jaxb_dateutc.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class SchemaHelper {

    public static Schema getSchema(final String schemaLocation) throws SAXException {
        if (null == schemaLocation) {
            throw new IllegalArgumentException("the schema location is required for xml validation. method parameter schemaLocation");
        }
        final URL schemaURL = SchemaHelper.class.getResource(schemaLocation);
        if (null == schemaURL) {
            throw new NullPointerException("the schema could not be found on classpath.");
        }
        final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return sf.newSchema(schemaURL);
    }

    public static void validateXml(final Schema schema, final byte[] xmlBytes) throws SAXException {
        validateXml(schema, new ByteArrayInputStream(xmlBytes));
    }

    public static void validateXml(final Schema schema, final InputStream inputStream) throws SAXException {
        if (null == schema) {
            throw new IllegalArgumentException("the schema is required for xml validation. method parameter schema");
        }
        if (null == inputStream) {
            throw new IllegalArgumentException("the inputStream is required for xml validation. method parameter inputStream");
        }
        try {
            // creating a Validator instance
            final Validator validator = schema.newValidator();

            // preparing the XML file as a SAX source
            final SAXSource source = new SAXSource(new InputSource(inputStream));

            // validating the SAX source against the schema
            validator.validate(source);
        } catch (final IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private SchemaHelper() {
    }

}
