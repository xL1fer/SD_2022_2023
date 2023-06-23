CODEBASE="file:///"$HOME"/test/MuseumHeist/dirConcentrationSite/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     ConcentrationSite.Main 22353 localhost 22357
