package com.lardi.util;

import com.lardi.utils.PropertiesReader;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropertiesReaderTest {

    private static final Logger log = Logger.getLogger(PropertiesReaderTest.class);

    @Test
    public void getPropertyTest() {
        String property = PropertiesReader.getProperty("contact.service.json.file");
        assertEquals(property, "/json");
    }

    @Test
    public void getPropertiesTest() {
        Properties properties = PropertiesReader.getProperties();
        assertTrue(properties != null);
    }

}
