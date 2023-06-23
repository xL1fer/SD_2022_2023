xterm  -T "RMI registry" -hold -e "./RMIRegistryDeployAndRun.sh" &
sleep 10
xterm  -T "Registry" -hold -e "./RegistryDeployAndRun.sh" &
sleep 10
xterm  -T "Information Repository" -hold -e "./InformationReposDeployAndRun.sh" &
xterm  -T "Concentration Site" -hold -e "./ConcentrationSiteDeployAndRun.sh" &
xterm  -T "Collection Site" -hold -e "./CollectionSiteDeployAndRun.sh" &
xterm  -T "Assault Party 1" -hold -e "./AssaultParty1DeployAndRun.sh" &
xterm  -T "Assault Party 2" -hold -e "./AssaultParty2DeployAndRun.sh" &
xterm  -T "Museum" -hold -e "./MuseumDeployAndRun.sh" &
sleep 25
xterm  -T "Ordinary Thieves" -hold -e "./OrdinaryThievesDeployAndRun.sh" &
sleep 10
xterm  -T "Master Thief" -hold -e "./MasterThiefDeployAndRun.sh" &
