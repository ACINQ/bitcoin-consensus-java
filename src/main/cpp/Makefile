CPPFLAGS := -fPIC -O3 -g -Wall -Werror -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
UNAME_P := $(shell uname -p)
ifeq ($(UNAME_P),x86_64)
	ARCH := 64
else
	ARCH := 32
endif

NAME := bitcoin-consensus-wrapper$(ARCH)

lib: lib$(NAME).so

lib$(NAME).so: fr_acinq_bitcoin_ConsensusWrapper.o
	$(CC) -shared -Wl,-soname,lib$(NAME).so $^ -o $@ -lstdc++ -L$(BITCOIN_HOME)/src/.libs -lbitcoinconsensus

clean:
	$(RM) *.o *.so*
