CODEBASE="file:///"$HOME"/test/MuseumHeist/dirAssaultParty/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     AssaultParty.Main 22351 localhost 22357 2
