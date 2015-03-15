UNAME := $(shell uname)
UNAME_P := $(shell uname -p)

ifeq ($(UNAME_P),x86_64)
	ARCH := 64
else
	ARCH := 32
endif

NAME := bitcoin-consensus-wrapper$(ARCH)

CPPFLAGS := -fPIC -O3 -g -Wall -Werror -I$(JAVA_HOME)/include
LDFLAGS := -lstdc++ -L$(BITCOIN_HOME)/src/.libs -lbitcoinconsensus
ifeq ($(UNAME),Linux)
	CPPFLAGS += -I$(JAVA_HOME)/include/linux
	LDFLAGS += -shared -Wl,-soname,lib$(NAME).so
	TARGET = lib$(NAME).so
else ifeq ($(UNAME),Darwin)
	CPPFLAGS += -I$(JAVA_HOME)/include/darwin
	LDFLAGS += -dynamiclib
	TARGET = lib$(NAME).dylib
endif


lib: $(TARGET)

$(TARGET): fr_acinq_bitcoin_ConsensusWrapper.o
	$(CC) $(LDFLAGS) $^ -o $@ 

clean:
	$(RM) *.o *.so*
