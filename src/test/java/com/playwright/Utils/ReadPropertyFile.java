package com.playwright.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import  org.apache.logging.log4j.Logger;


public class ReadPropertyFile {
    private static final Logger logger = LogManager.getLogger(ReadPropertyFile.class);
    /**
     * This method returns the values of given key present in property file
     */

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try(InputStream input = ReadPropertyFile.class.getClassLoader().getResourceAsStream(fileName)){
            if (input == null){
                logger.error("Unable to find the properties file :- {}", fileName);
                throw new RuntimeException("unable to find properties file : "+fileName);
            }
            properties.load(input);
            logger.info("Properties File {} loaded successfully.",fileName);

        } catch (IOException e) {
            logger.error("Failed to load properties file: {}",fileName, e);
            throw new RuntimeException("Failed to load properties file: "+fileName, e);
        }
        return properties;
    }
}
