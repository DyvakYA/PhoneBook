package com.lardi.utils;

import org.apache.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class InputArgumentsReader {

    private static final Logger log = Logger.getLogger(InputArgumentsReader.class);

    private RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

    public List<String> getRuntimeArguments() {
        return runtimeMxBean.getInputArguments();
    }

    public String getRuntimeArgument(String prefix) {
        List<String> arguments = runtimeMxBean.getInputArguments();
        log.info(prefix + " - " + arguments);
        String result = null;
        for (String argument : arguments) {
            if (argument.startsWith(prefix)) {
                String[] output = argument.split("=");
                result = output[1];
            }
        }
        return result;
    }

    public void setRuntimeMxBean(RuntimeMXBean runtimeMxBean) {
        this.runtimeMxBean = runtimeMxBean;
    }
}
