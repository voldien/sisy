package org.sisy;

/*
 * Get option from program argument.
 * Its based on how the getopt in c
 * is used.
 *
 * @author Valdemar Lindberg
 */
public class GetOpt {

    /**
     *
     */
    private static String optarg;
    private static int optind = 0;

    /**
     * Get option by parsing the argument vector.
     *
     * @param argv       string array of argument.
     * @param shortopt   supported short option with the same standard as in getopt.
     * @param longoption long option that maps to short option.
     * @return short option code, -1 if no more to read.
     * @throws Exception
     */
    public static int getOptLong(String[] argv, String shortopt, Option[] longoption) throws Exception {

        /*	Continue in till all argument has been read. */
        while (optind < argv.length) {

            String arg = argv[optind++];

			/*	Init.	*/
            optarg = null;

			/*	Long option.	*/
            if (arg.startsWith("--")) {
                String larg = arg.substring(2);

                if (larg.contains("=")) {
                    optarg = larg.substring(larg.indexOf("=") + 1);
                    larg = larg.substring(0, larg.indexOf("=")).replace("\"", "");
                }

                for (Option option : longoption) {
                    if (option.getLongopt().compareTo(larg) == 0) {
                        return option.getShortopt();
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
                    return Integer.valueOf(sarg);

                } else
                    throw new Exception(String.format("Invalid option, %s", arg));
            }
            optarg = arg;
            return 0;
        }

        return -1;
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
