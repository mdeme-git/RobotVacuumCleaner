# Begin makefile
JFLAGS = -g
JC = javac
JVM = java
dir = -d

compil:
	$(JC) $(JFLAGS) $(dir) bin ./src/*/*.java
run:
	$(JVM) ./bin/Main.class
clean:
	rm -r ./bin/*

