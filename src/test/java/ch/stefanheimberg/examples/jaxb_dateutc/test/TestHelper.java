/*
 * Copyright 2015 Stefan Heimberg <kontakt@stefanheimberg.ch>.
 * 
 * All rights reserved.
 */
package ch.stefanheimberg.examples.jaxb_dateutc.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import org.junit.Assert;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class TestHelper {

    public static byte[] getResourceAsByteArray(Package resourcePackage, String resourceName) throws IOException {
        final String packagePath = resourcePackage.getName().replaceAll("\\.", "/");
        return getResourceAsByteArray(packagePath + "/" + resourceName);
    }

    public static byte[] getResourceAsByteArray(String resourcePath) throws IOException {
        final InputStream is = TestHelper.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            Assert.fail(MessageFormat.format("Could not load resource {0} from classpath", resourcePath));
        }
        return toByteArray(is);
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        final byte[] buffer = new byte[8192];

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }

        return baos.toByteArray();
    }

    public static void assertEqualsIgnoreLinefeed(final byte[] expectedBytes, final byte[] actualBytes, final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(expectedBytes, charsetName).replaceAll("(\r\n|\r)", "\n");
        final String actual = new String(actualBytes, charsetName).replaceAll("(\r\n|\r)", "\n");

        Assert.assertEquals(actual, expected);
    }

    private TestHelper() {
    }

}
