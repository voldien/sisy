package org.sisy;

/**
 * @author Valdemar Lindberg
 */
public class Debug {

    private static boolean enabled = false;

    /**
     * Set the debug mode.
     * @param enabled true of false.
     */
    public static void setEnabled(boolean enabled) {
        Debug.enabled = enabled;
    }

    /**
     * Print the stack trace of the
     * exception if debug is enabled.
     * @param ex non null exception
     */
    public static void printStack(Exception ex) {
        if (enabled)
            ex.printStackTrace();
    }
}
