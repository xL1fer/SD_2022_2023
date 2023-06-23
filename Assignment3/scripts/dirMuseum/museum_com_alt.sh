CODEBASE="file:///"$HOME"/test/MuseumHeist/dirMuseum/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     Museum.Main 22355 localhost 22357
