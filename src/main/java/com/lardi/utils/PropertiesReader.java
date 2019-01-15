package com.lardi.utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger log = Logger.getLogger(PropertiesReader.class);

    private static InputArgumentsReader argumentsReader = new InputArgumentsReader();

    private static Properties properties;
    private static InputStream input;

    static {
        loadResourceBundle();
    }

    public PropertiesReader() {

    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    public static Properties getProperties() {
        return properties;
    }

    private static void loadResourceBundle() {
        try {
            String configurationPath = null;
            if (argumentsReader != null) {
                configurationPath = argumentsReader.getRuntimeArgument("-Dlardi.conf");
            }
            if (configurationPath != null) {
                log.info(configurationPath);
                input = new FileInputStream(configurationPath);
                properties = new Properties();
                properties.load(input);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


