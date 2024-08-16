rep = classes

clean:
	rm -Rf classes/zombicide
	rm -Rf docs

classpath:
	export CLASSPATH="src:classes:test:junit-console.jar"

doc:
	javadoc -sourcepath src -subpackages zombicide -d docs

cls:
	javac src/**/*.java -d classes

util:
	javac src/zombicide/util/*.java -d $(rep)

item:
	javac src/zombicide/item/*.java -d $(rep)

actor:
	javac src/zombicide/actor/*.java -d $(rep)

zone:
	javac src/zombicide/zone/*.java -d $(rep)

board:
	javac src/zombicide/board/*.java -d $(rep)

tests:
	javac -classpath junit-console.jar:classes test/zombicide/*.java
	javac -classpath junit-console.jar:classes test/zombicide/actor/**/*.java
	javac -classpath junit-console.jar:classes test/zombicide/actor/zombie/*.java
	javac -classpath junit-console.jar:classes test/zombicide/actor/survivor/**/*.java
	javac -classpath junit-console.jar:classes test/zombicide/item/**/*.java
	javac -classpath junit-console.jar:classes test/zombicide/util/*.java
	javac -classpath junit-console.jar:classes test/zombicide/zone/**/*.java
	javac -classpath junit-console.jar:classes test/zombicide/zone/room/*.java
	javac -classpath junit-console.jar:classes test/zombicide/zone/street/*.java

	java -jar junit-console.jar -classpath test:classes -scan-classpath

livrable2.jar:
	jar cvfe jar/livrable2.jar zombicide.Livrable2 -C classes zombicide

livrable2:
	java -jar jar/livrable2.jar

livrable3.jar:
	jar cvfe jar/livrable3.jar zombicide.Livrable3 -C classes zombicide

livrable3:
	java -jar jar/livrable3.jar

zombicide.jar: cls
	jar cvfe jar/zombicide.jar zombicide.SurvivorMain -C classes zombicide

zombicide-interactive.jar: cls
    jar cvfe jar/zombicide-interactive.jar zombicide.SurvivorInteractiveMain -C classes zombicide

jars: zombicide.jar zombicide-interactive.jar