echo "Transfering data to the information repository node."
sshpass -f password ssh sd306@l040101-ws06.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws06.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirInformationRepos.zip sd306@l040101-ws06.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the information repository node."
sshpass -f password ssh sd306@l040101-ws06.ua.pt 'cd test/MuseumHeist ; unzip -uq dirInformationRepos.zip'
echo "Executing program at the server information repository."
sshpass -f password ssh sd306@l040101-ws06.ua.pt 'cd test/MuseumHeist/dirInformationRepos ; ./infrepos_com_d.sh sd306'