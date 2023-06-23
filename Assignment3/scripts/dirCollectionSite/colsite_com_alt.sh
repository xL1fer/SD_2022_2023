CODEBASE="file:///"$HOME"/test/MuseumHeist/dirCollectionSite/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     CollectionSite.Main 22352 localhost 22357
