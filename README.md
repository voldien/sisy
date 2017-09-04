# sisy #
------
The *sisy* program is simple command line tool for encrypt/decrypting file. Where it it provides a simple set of command line options. Where the minimum amount of argument required is only the file path. Where all the settings of the program can overriden by the command line options.

*Features*
* Compression - All files has the option of being compressed before encrypted.
* Password - All cryptographic symmetric block cipher supports a human password for creating the required cipher key.
* Verbose logging - Each file will by default be verbosed

# Motivation #
----
The program was created in order to create a simple file encryption/decryption tool for the command line. Where each encrypted file contains encrypted meta data. This allows the user from having to explicity input all the parametered requried in order to take advantages of 'mode of operation'. It also contains a signature for checking if the file was encrypted with the sisy program.

# Prerequisites #
----
The only prerequisite for the program is that the program was written for *java 8*. This implies that older version of *java* many not work as accordinly.

All the library dependency is the *java* and *javax* libraries. These are however commonly installed along the JDK (Java Development Kit).

# Installation #
----

The following section covers how to compile from the source code and install the program onto the system. ( This may change in the future when project will migrate to either to the *ant* or *maven* project management software). However the make file will not be removed once the migrated has been performed.

## Linux ##
The program can be installed with the following commands.
```
make
make install
```
It will compile and create a jar file. Where the jar file be installed on the system under the */usr/share/java* directory. Where a bash script will be installed in the */usr/bin* directory. This script will create a executable sisy file. Where the script will forward the user argument to the java program. This will the system to use the *sisy* program as any other command line program in Linux.

# Contributing #
Please read the [CONTRIBUTING.md](CONTRIBUTING) for more details of how you can contriubute.

## License ##

This project is licensed under the GPL+3 License - see the [LICENSE](LICENSE) file for details.

