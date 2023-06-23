echo "Killing possible open ports."
sshpass -f password ssh sd306@l040101-ws01.ua.pt 'kill $(lsof -ti:22350)'
sshpass -f password ssh sd306@l040101-ws02.ua.pt 'kill $(lsof -ti:22351)'
sshpass -f password ssh sd306@l040101-ws03.ua.pt 'kill $(lsof -ti:22352)'
sshpass -f password ssh sd306@l040101-ws05.ua.pt 'kill $(lsof -ti:22353)'
sshpass -f password ssh sd306@l040101-ws06.ua.pt 'kill $(lsof -ti:22354)'
sshpass -f password ssh sd306@l040101-ws07.ua.pt 'kill $(lsof -ti:22355)'