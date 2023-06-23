CODEBASE="file:///"$HOME"/test/MuseumHeist/dirInformationRepos/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     InformationRepository.Main 22354 localhost 22357
