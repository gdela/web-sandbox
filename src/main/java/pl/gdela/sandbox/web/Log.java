package pl.gdela.sandbox.web;

import java.io.PrintStream;

import static java.lang.System.currentTimeMillis;

public final class Log {

    private final static PrintStream out = System.out;

    private static long lastTickTime = 0;

    private Log() { /* non instantiable */ }

    /**
     * Prints dots on the sysout, so it's easy to spot when some loop hanged.
     */
    public static void tick() {
        synchronized (out) {
            if (currentTimeMillis() - lastTickTime >= 100) {
                lastTickTime = currentTimeMillis();
                out.print('.');
            }
        }
    }

    /**
     * Prints message to sysout, works nicely with the dots from {@link #tick()}.
     */
    public static void info(Object message) {
        synchronized (out) {
            if (lastTickTime != 0) {
                out.println();
                lastTickTime = 0;
            }
            out.println(Thread.currentThread().getName() + "> " + message);
        }
    }
}
