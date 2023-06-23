echo "Transfering data to the collection site node."
sshpass -f password ssh sd306@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirCollectionSite.zip sd306@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the collection site node."
sshpass -f password ssh sd306@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirCollectionSite.zip'
echo "Executing program at the collection site node."
sshpass -f password ssh sd306@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirCollectionSite ; ./colsite_com_d.sh sd306'