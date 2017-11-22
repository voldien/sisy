package org.sisy;

/**
 * @author Valdemar Lindberg
 */
public class Debug {

    private static boolean enabled = false;

    /**
     *
     * @param enabled
     */
    public static void setEnabled(boolean enabled){
        Debug.enabled = true;
    }

    /**
     *
     * @param ex
     */
    public static void printStack(Exception ex){
        if(enabled)
            ex.printStackTrace();
    }
}
