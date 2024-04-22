package de.dhbw.ase.user.in;

import de.dhbw.ase.user.out.UserOut;

public interface UserIn {

    void setOutput(Object o);

    String getLastLine(Object o);

    String waitForNextLine(Object o);

    void stop(UserOut userOut);
}
