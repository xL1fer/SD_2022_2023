CODEBASE="file:///"$HOME"/test/MuseumHeist/dirRegistry/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     Registry.Main 22356 localhost 22357
