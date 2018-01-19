package com.generator.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4J2Logger {

    private static final Logger LOGGER = LogManager.getLogger(Log4J2Logger.class);

    public void execute() {
        LOGGER.debug("This is a debug log message");
        LOGGER.info("This is an info log message");
        LOGGER.warn("This is a warn log message");
        LOGGER.error("This is an error log message");
        LOGGER.fatal("This is a fatal log message");
    }
}
