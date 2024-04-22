package de.dhbw.ase.user.in;

import java.io.InputStream;

public class ConsoleIn implements UserIn {

    private Object output;


    public ConsoleIn(InputStream in) {

    }

    @Override
    public void setOutput(Object o) {
        output = o;
    }


    @Override
    public String getLastLine(Object o) {
        if (o != output) {
            throw new IllegalStateException("Output where already redirected");
        }
        return null;
    }

    Object getOutput() {
        return output;
    }
}
