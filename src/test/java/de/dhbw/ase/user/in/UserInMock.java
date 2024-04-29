package de.dhbw.ase.user.in;

import de.dhbw.ase.user.out.UserOut;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class UserInMock implements UserIn {

    private final Deque<String> inputs;


    public UserInMock(List<String> inputs) {
        this.inputs = new LinkedList<>(inputs);
    }

    @Override
    public void setOutput(Object o) {

    }

    @Override
    public String getLastLine(Object o) {
        return inputs.poll();
    }

    @Override
    public String waitForNextLine(Object o) {
        return getLastLine(o);
    }

    @Override
    public void stop(UserOut userOut) {

    }
}