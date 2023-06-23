echo "Checking possible open ports PIDs..."
echo "...ws01"
sshpass -f password ssh sd306@l040101-ws01.ua.pt 'lsof -ti'
echo "...ws02"
sshpass -f password ssh sd306@l040101-ws02.ua.pt 'lsof -ti'
echo "...ws03"
sshpass -f password ssh sd306@l040101-ws03.ua.pt 'lsof -ti'
echo "...ws05"
sshpass -f password ssh sd306@l040101-ws05.ua.pt 'lsof -ti'
echo "...ws06"
sshpass -f password ssh sd306@l040101-ws06.ua.pt 'lsof -ti'
echo "...ws07"
sshpass -f password ssh sd306@l040101-ws07.ua.pt 'lsof -ti'
echo "...ws08"
sshpass -f password ssh sd306@l040101-ws08.ua.pt 'lsof -ti'
echo "...ws09"
sshpass -f password ssh sd306@l040101-ws09.ua.pt 'lsof -ti'
echo "...ws10"
sshpass -f password ssh sd306@l040101-ws10.ua.pt 'lsof -ti'