CODEBASE="file:///"$HOME"/test/MuseumHeist/dirMasterThief/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     MasterThief.Main localhost 22357
