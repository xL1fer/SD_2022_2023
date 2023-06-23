echo "Transfering data to the museum node."
sshpass -f password ssh sd306@l040101-ws07.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd306@l040101-ws07.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirMuseum.zip sd306@l040101-ws07.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the museum node."
sshpass -f password ssh sd306@l040101-ws07.ua.pt 'cd test/MuseumHeist ; unzip -uq dirMuseum.zip'
echo "Executing program at the museum node."
sshpass -f password ssh sd306@l040101-ws07.ua.pt 'cd test/MuseumHeist/dirMuseum ; java Museum.Main 22355'