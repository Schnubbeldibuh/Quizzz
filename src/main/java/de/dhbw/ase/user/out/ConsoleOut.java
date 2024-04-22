package de.dhbw.ase.user.out;

public class ConsoleOut implements UserOut {
    @Override
    public void printLn(String out) {
        System.out.println(out);
    }
}
