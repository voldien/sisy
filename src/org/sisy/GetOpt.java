package org.sisy;

/**
 * Get option from program argument.
 * Its based on how the getopt in c
 * is used.
 *
 * @author Valdemar Lindberg
 */
public class GetOpt {

    /**
     * Internal variable for reading the opt.
     */
    private static String optarg;
    private static int optind = 0;
    private static int optlongind = 0;

    /**
     * Get option by parsing the argument vector.
     *
     * @param argv       string array of argument.
     * @param shortopt   supported short option with the same standard as in getopt.
     * @param longoption long option that maps to short option.
     * @return short option code, -1 if no more to read.
     * @throws Exception if argument is invalid.
     * @throws NullPointerException if argument is invalid.
     */
    public static int getOptLong(String[] argv, String shortopt, Option[] longoption) throws Exception {

        if(argv == null)
            throw new NullPointerException("Parameter argv cannot be a null reference.");
        if(shortopt == null)
            throw new NullPointerException("Parameter shortopt cannot be a null reference.");

        /*	Continue in till all argument has been read. */
        while (optind < argv.length) {

            String arg = argv[optind++];

			/*	Init.	*/
            optarg = null;

			/*	Long option.	*/
            if (arg.startsWith("--")) {
                String larg = arg.substring(2);

                /*  */
                if (larg.contains("=")) {
                    optarg = larg.substring(larg.indexOf("=") + 1);
                    larg = larg.substring(0, larg.indexOf("=")).replace("\"", "");
                }

                /*  */
                for(int i = 0; i < longoption.length; i++){
                    if (longoption[i].getLongopt().compareTo(larg) == 0) {
                        optlongind = i;
                        return longoption[i].getShortopt();
                    }
                }

                throw new Exception(String.format("Invalid option, %s", arg));
            }
            /*	Short option.*/
            else if (arg.startsWith("-")) {
                String sarg = arg.substring(1, 2);

				/*	Exist as valid*/
                if (shortopt.contains(arg.substring(1))) {
					/*	Check if next is argument or not.	*/
                    if (shortopt.contains(sarg + ":")) {
                        optarg = argv[optind++];
                    }

					/*	return option code.	*/
                    return sarg.charAt(0);

                } else
                    throw new Exception(String.format("Invalid option, %s", arg));
            }
            optarg = arg;
            return 0;
        }

        /*  End of file.    */
        return -1;
    }

    /**
     *  Reset the getopt.
     */
    public static void reset(){
       optarg = null;
       optind = 0;
       optlongind = 0;
    }

    /**
     * Get current index of in the option.
     * @return non negative index.
     */
    public static int getIndex(){
        return optind;
    }

    /**
     *
     * @return
     */
    public static int getLongOptIndex(){
        return optlongind;
    }

    /**
     * Get argument from previous invoke getOptLong method.
     *
     * @return non-null terminated string if previous option had argument.
     */
    public static String getArgument() {
        return optarg;
    }
}
