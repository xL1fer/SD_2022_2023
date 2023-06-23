CODEBASE=http://l040101-ws10.ua.pt/$1/classes/
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     AssaultParty.Main 22351 l040101-ws10.ua.pt 22357 2
