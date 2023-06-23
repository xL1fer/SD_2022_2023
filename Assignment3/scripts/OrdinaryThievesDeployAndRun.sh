echo "Transfering data to the ordinary thieves node."
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirOrdinaryThieves.zip sd306@l040101-ws09.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the ordinary thieves node."
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'cd test/MuseumHeist ; unzip -uq dirOrdinaryThieves.zip'
echo "Executing program at the ordinary thieves node."
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'cd test/MuseumHeist/dirOrdinaryThieves ; ./ordthieves_com_d.sh'