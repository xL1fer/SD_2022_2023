echo "Transfering data to the RMIregistry node."
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'mkdir -p Public/classes/ServerInterfaces'
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'rm -rf Public/classes/ServerInterfaces/*'
sshpass -f password scp dirRMIRegistry.zip sd306@l040101-ws10.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'cd test/MuseumHeist ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'cd test/MuseumHeist/dirRMIRegistry ; cp ServerInterfaces/*.class /home/sd306/Public/classes/ServerInterfaces ; cp set_rmiregistry_d.sh /home/sd306'
echo "Executing program at the RMIregistry node."
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'cd Public/classes/ServerInterfaces ; chmod o+r *'
sshpass -f password ssh sd306@l040101-ws10.ua.pt './set_rmiregistry_d.sh sd306'
