package org.sisy;

/**
 * Option associated with class GetOpt
 * for long option and map it to short
 * option.
 *
 * @author Valdemar Lindberg
 *
 */
public class Option {

    private int shortopt;
    private String longopt;

    /**
     * @param lopt long option.
     * @param sopt short option.
     */
    public Option(String lopt, int sopt) {
        assert lopt != null;
        shortopt = sopt;
        longopt = lopt;
    }

    /**
     * Get short option.
     *
     * @return short option.
     */
    public int getShortopt() {
        return shortopt;
    }

    /**
     * Get long option.
     *
     * @return non-null string .
     */
    public String getLongopt() {
        return longopt;
    }
}