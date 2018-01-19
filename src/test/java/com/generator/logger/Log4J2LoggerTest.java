package com.generator.logger;

import org.junit.Before;
import org.junit.Test;

public class Log4J2LoggerTest
{
    private Log4J2Logger logger;

    @Before
    public void setUp() throws Exception
    {
        logger = new Log4J2Logger();
    }
    @Test
    public void testWhenLoggerCalledThenMessagesLogged() throws Exception
    {
        logger.execute();
    }
}