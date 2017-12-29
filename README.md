# sisy #
------
The *sisy* program is simple command line tool for encrypt/decrypting file. It provides a simple set of command line options. Where the minimum amount of argument required is only the file path. All the settings of the program can be overridden by the command line options.

## Features ##
* Compression - All files has the option of being compressed before being encrypted.
* Password - All cryptographic symmetric block cipher supports a human password for creating the required cipher key.
* Verbose logging - Each file will by default be verbosed.

The program works by encrypting a sequence of bytes into *sisy-blocks* that are block of data, containing a meta header and encrypted/compressed block of data. That implies that a file for example will be divided into multiple sisy-blocks in consecutive order.
The *meta header* inside each block contains information of what cipher; data and header size; and IV (initialization vector) and along with other information that is necessary for decrypting and decompressing the block.

# Motivation #

The program was created in order to create a simple file encryption/decryption tool for the command line. Where each encrypted file contains blocks of encrypted data. This allows the user from having to explicitly input all the parameters required in order to take advantages of ['mode of operation'](https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation). It also contains a signature for checking if the file were encrypted with the sisy program. It also makes it easier for adding support for streaming with secure pipe or a VPN type of pipe.

Additional intention of the project is that the design of the *sisy* can easily be incorporated used as library. See the UML design of the project for more information. ()[]. This is done by creating multiple subsystem of the program. Where each subsystem is responsible for a subset of the functionality of the program only.

# Examples #
The following section covers how the use the *sisy* program, where will create an encrypted file:
```
sisy rand.txt
```

See more information in sisy(1) for all possible command line options along other types of information about the usages of the program.

# Prerequisites #

The only prerequisite for the program is that the program was written for *java 8*. This implies that older version of *java* many not work as accordingly.

All the library dependency is the *java* and *javax* libraries. These are however commonly installed along the JDK (Java Developmenst Kit).

# Installation #

The following section covers how to compile from the source code and install the program onto the system. ( This may change in the future when project will migrate to either to the *ant* or *maven* project management software). However the make file will not be removed once the migrated has been performed.

## Linux ##
The program can be installed with the following commands.
```
make
make install
```
It will compile and create a jar file. Where the jar file be installed on the system under the */usr/share/java* directory. Where a bash script will be installed in the */usr/bin* directory. This script will create a executable sisy file. Where the script will forward the user argument to the java program. This will make system to use the *sisy* program as any other command line program in Linux.

# Documentation #

# Contributing #
Please read the [CONTRIBUTING](CONTRIBUTING.md) for more details of how you can contribute.

## License ##

This project is licensed under the GPL+3 License - see the [LICENSE](LICENSE) file for details.

