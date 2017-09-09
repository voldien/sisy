#!/bin/bash


# Versions
MAJOR := 0
MINOR := 1
PATCH := 0
STATE := pa
VERSION := $(MAJOR).$(MINOR)$(STATE)$(PATCH)
# Utilities
RM := rm -f
CP := cp
MKDIR := mkdir -p
CHMOD := chmod
# Directories
DESTDIR ?=
PREFIX ?= /usr
INSTALL_LOCATION=$(DESTDIR)$(PREFIX)
# Compiler
JCC := javac
JAR := jar
# Sources
SRCS = $(wildcard src/org/sisy/*.java)

# Target
TARGET:= sisy

all : $(TARGET)
	@echo -n "Done!\n"

$(TARGET) : manifest
	$(JCC) $(SRCS) -d ./build
	$(MKDIR) build/jar
	$(JAR) cvfm build/jar/$@.jar $^ -C build/ org/sisy/

manifest :
	echo "Main-Class: org.sisy.Program" > $@

install: $(TARGET)
	$(CP) sisy $(INSTALL_LOCATION)/bin/sisy
	$(CHMOD) +x $(INSTALL_LOCATION)/bin/sisy
	$(MKDIR) $(INSTALL_LOCATION)/share/sisy/
	$(CP) build/jar/$(TARGET).jar $(INSTALL_LOCATION)/share/sisy/
	$(CP) sisy.bc $(INSTALL_LOCATION)/share/bash-completion/completions/sisy
	$(CP) sisy.1 $(INSTALL_LOCATION)/share/man/man1

clean :
	$(RM) *.class

distribution:
	$(RM) -r $(TARGET)-$(VERSION)
	$(MKDIR) $(TARGET)-$(VERSION)
	$(CP) -r src Makefile README.md LICENSE *.1 sisy.bc $(TARGET)-$(VERSION)
	tar cf - $(TARGET)-$(VERSION) | gzip -c > $(TARGET)-$(VERSION).tar.gz
	$(RM) -r $(TARGET)-$(VERSION)

.PHONY: all install distribution clean debug manifest

