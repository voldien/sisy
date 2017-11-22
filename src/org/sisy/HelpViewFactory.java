package org.sisy;

/**
 * Responsible for displaying comprehensive
 * information for how use the program and what
 * supported option.
 *
 * It was written for non
 * Unix based system, for which does not use
 * man page for manual, see sisy(1) for a more
 * comprehensive documentation.
 *
 * @author Valdemar Lindberg
 */
public class HelpViewFactory {

    /**
     * Get console help view.
     * @return non-null terminated string.
     * @throws NullPointerException if version is null.
     */
    public static String createConsoleHelpViewString(String version){
        if(version == null)
            throw new NullPointerException();

        String consolehelp = String.join("\n",
                String.format("sisy console - version: %s", version),
                "---------------------------------------------------",
                "Usage:",
                "\tsisy <argument> [-p,--password] [-q|--quite]",
                "",
                "",
                "Options:",
                "",
                "\t-v / --version",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-V / --verbose",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-h / --help",
                "\t\tDisplay help information for using the program.",
                "",
                "\t-q / --quite",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-c / --cipher",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-k / --keyspace",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-p / --password",
                "\t\t Set the password used for encrypting and decrypting the files.",
                "",
                "\t-r / --recursive",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-o / --output",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t -D / --directory",
                "\t\t Set the output directory.",
                "",
                "\t-r / --compression",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-P / --pipe",
                "\t\tDisplay version only from the program and exit.",
                "",
                "\t-D / --directory",
                "\t\t Set the output directory.",
                "\t-b / --block-size",
                "\t\t Set sub block size in bytes.",
                "",
                ""
                );

        return consolehelp;
    }
}
