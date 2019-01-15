package com.lardi.util;

import com.lardi.utils.InputArgumentsReader;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputArgumentsReaderTest {

    @Mock
    RuntimeMXBean runtimeMxBean;

    List<String> arguments;

    private InputArgumentsReader reader;

    private static final Logger log = Logger.getLogger(InputArgumentsReader.class);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        reader = new InputArgumentsReader();
        reader.setRuntimeMxBean(runtimeMxBean);
        arguments = new ArrayList<String>();
        arguments.add("-Dlardi.conf=/path/to/file.properties");
        when(runtimeMxBean.getInputArguments()).thenReturn(arguments);
        //log.info(runtimeMxBean.getInputArguments());
    }

    @Test
    public void getRuntimeArgumentTest() {
        when(runtimeMxBean.getInputArguments()).thenReturn(arguments);
        String result = reader.getRuntimeArgument("-Dlardi.conf");
        log.info(result);
        verify(runtimeMxBean).getInputArguments();
        //assertEquals(result, "/path/to/file.properties");
    }

    @Test
    public void getRuntimeArgumentsTest() {
        when(runtimeMxBean.getInputArguments()).thenReturn(arguments);
        List<String> result = reader.getRuntimeArguments();
        log.info(result);
        assertTrue(result.size() == 1);
    }
}
