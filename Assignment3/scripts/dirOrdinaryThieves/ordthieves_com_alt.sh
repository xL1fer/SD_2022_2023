CODEBASE="file:///"$HOME"/test/MuseumHeist/dirOrdinaryThieves/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     OrdinaryThief.Main localhost 22357
