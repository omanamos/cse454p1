GS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Main.java Classifier.java Trainer.java Utils.java

default: classes

classes: $(CLASSES:.java=.class)

clean: $(RM) *.class
