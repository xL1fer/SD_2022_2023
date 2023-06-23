echo "Transfering data to the concentration site node."
sshpass -f password ssh sd306@l040101-ws05.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws05.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirConcentrationSite.zip sd306@l040101-ws05.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the concentration site node."
sshpass -f password ssh sd306@l040101-ws05.ua.pt 'cd test/MuseumHeist ; unzip -uq dirConcentrationSite.zip'
echo "Executing program at the concentration site node."
sshpass -f password ssh sd306@l040101-ws05.ua.pt 'cd test/MuseumHeist/dirConcentrationSite ; ./consite_com_d.sh sd306'