echo "Transfering data to the assault party 2 node."
sshpass -f password ssh sd306@l040101-ws02.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws02.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirAssaultParty.zip sd306@l040101-ws02.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the assault party 2 node."
sshpass -f password ssh sd306@l040101-ws02.ua.pt 'cd test/MuseumHeist ; unzip -uq dirAssaultParty.zip'
echo "Executing program at the assault party 2 node."
sshpass -f password ssh sd306@l040101-ws02.ua.pt 'cd test/MuseumHeist/dirAssaultParty ; ./asparty2_com_d.sh sd306'