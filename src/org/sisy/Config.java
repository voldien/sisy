package org.sisy;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The class is responsible for containing
 * all configuration of the program. Where
 * configuration can be added and modified.
 *
 * @author Valdemar Lindberg
 */
public class Config {

    public Config() {
        iconfig = new Hashtable<>();
        sconfig = new Hashtable<>();
        saconfig = new Hashtable<>();
        loadDefaultConfig();
    }

    /**
     * Set integer.
     *
     * @param key
     * @param value
     */
    public void setInt(String key, int value) {
        this.iconfig.put(key, value);
    }

    /**
     * Set boolean.
     *
     * @param key
     * @param bool
     */
    public void setBoolean(String key, boolean bool) {
        this.iconfig.put(key, bool ? 1 : 0);
    }

    /**
     * Set string.
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        this.sconfig.put(key, value);
    }

    /**
     * Set string array.
     *
     * @param key
     * @param values
     */
    public void setStrings(String key, String[] values) throws Exception {
        saconfig.put(key, values);
    }

    /**
     * @param key
     * @return
     * @throws IllegalArgumentException if no entry exists.
     */
    public int getInt(String key) {
        if (!iconfig.containsKey(key))
            throw new IllegalArgumentException(String.format("'%s' is not a valid configuration key", key));

        return iconfig.get(key).intValue();
    }

    /**
     * Get boolean value.
     *
     * @param key
     * @return boolean
     * @throws IllegalArgumentException if no entry exists.
     */
    public boolean getBoolean(String key) {
        if (!iconfig.containsKey(key))
            throw new IllegalArgumentException(String.format("'%s' is not a valid configuration key", key));

        return iconfig.get(key).byteValue() != 0;
    }

    /**
     * Get string array.
     *
     * @param key
     * @return non-null string array.
     * @throws IllegalArgumentException if no entry exists.
     */
    public String[] getStrings(String key) {
        if (!saconfig.containsKey(key))
            throw new IllegalArgumentException(String.format("'%s' is not a valid configuration key", key));

        return saconfig.get(key);
    }

    /**
     * Get string by key.
     *
     * @param key
     * @return non-null string.
     * @throws IllegalArgumentException if no entry exists.
     */
    public String getString(String key) {
        if (!sconfig.containsKey(key))
            throw new IllegalArgumentException(String.format("'%s' is not a valid configuration key", key));

        return sconfig.get(key);
    }

    private Hashtable<String, String> sconfig;
    private Hashtable<String, String[]> saconfig;
    private Hashtable<String, Integer> iconfig;

    /**
     * Load default configuration.
     */
    private void loadDefaultConfig() {

        setString("cipher", "aes");
        setInt("keysize", 128);
        setString("password", "");
        setBoolean("compression", false);
        setBoolean("encrypt", true);
        setString("recursion", "");
        setString("output", "");
        setBoolean("pipe", false);
    }

    /**
     * Create configuration object instance.
     *
     * @param argv program string array.
     * @return non-null configuration object.
     * @throws Exception
     */
    public static Config createConfig(String[] argv) throws Exception {

		/*	Valid options.  */
        final Option[] longoption = new Option[]{
                new Option("version", 'v'),
                new Option("verbose", 'V'),
                new Option("cipher", 'c'),
                new Option("keyspace", 'k'),
                new Option("password", 'p'),
                new Option("recursive", 'r'),
                new Option("decrypt", 'd'),
                new Option("output", 'o'),
                new Option("compression", 'C'),
                new Option("pipe", 'P'),
        };

		/*  */
        Config config = new Config();
        ArrayList<String> filearray = new ArrayList<>();
        String[] files;
        String shortopt = "vrc:dPC:k:p:o:";
        int c;

		/*	The program can't run no option specified.  */
        if (argv.length < 1)
            throw new Exception("requires at least one argument");

        /*	Parse user argument option.	*/
        while ((c = GetOpt.getOptLong(argv, shortopt, longoption)) != -1) {
            switch (c) {
                case 'v':
                    System.out.write(String.format("version %s.\n", Program.getVersion()).getBytes());
                    System.exit(0);
                    break;
                case 'c':
                    config.setString("cipher", GetOpt.getArgument());
                    break;
                case 'k':
                    config.setInt("keysize", Integer.valueOf(GetOpt.getArgument()));
                    break;
                case 'p':
                    config.setString("password", GetOpt.getArgument());
                    break;
                case 'd':
                    config.setBoolean("encrypt", false);
                    break;
                case 'C':
                    config.setBoolean("compression", true);
                    break;
                case 'o':
                    config.setString("output", GetOpt.getArgument());
                    break;
                case 'r':
                    config.setString("recursive", GetOpt.getArgument());
                    break;
                case 'P':
                    config.setBoolean("pipe", true);
                default:
                    filearray.add(GetOpt.getArgument());
                    break;
            }
        }

		/*  Set files.  */
        files = new String[filearray.size()];
        filearray.toArray(files);
        config.setStrings("files", files);

        return config;
    }

}
