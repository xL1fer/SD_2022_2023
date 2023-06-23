echo "Transfering data to the ordinary thieves node."
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirOrdinaryThieves.zip sd306@l040101-ws09.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the ordinary thieves node."
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'cd test/MuseumHeist ; unzip -uq dirOrdinaryThieves.zip'
echo "Executing program at the ordinary thieves node."
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'cd test/MuseumHeist/dirOrdinaryThieves ; java OrdinaryThief.Main l040101-ws06.ua.pt 22354 l040101-ws05.ua.pt 22353 l040101-ws03.ua.pt 22352 l040101-ws01.ua.pt 22350 l040101-ws02.ua.pt 22351 l040101-ws07.ua.pt 22355'