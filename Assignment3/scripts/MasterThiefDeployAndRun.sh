echo "Transfering data to the master thief node."
sshpass -f password ssh sd306@l040101-ws08.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws08.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirMasterThief.zip sd306@l040101-ws08.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the master thief node."
sshpass -f password ssh sd306@l040101-ws08.ua.pt 'cd test/MuseumHeist ; unzip -uq dirMasterThief.zip'
echo "Executing program at the master thief node."
sshpass -f password ssh sd306@l040101-ws08.ua.pt 'cd test/MuseumHeist/dirMasterThief ; ./mstthief_com_d.sh'