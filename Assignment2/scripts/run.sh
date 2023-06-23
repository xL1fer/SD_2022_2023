xterm  -T "Information Repository" -hold -e "cd $HOME/test/MuseumHeist/dirInformationRepos ; java InformationRepository.Main 22354" &
xterm  -T "Concentration Site" -hold -e "cd $HOME/test/MuseumHeist/dirConcentrationSite ; java ConcentrationSite.Main 22353" &
xterm  -T "Collection Site" -hold -e "cd $HOME/test/MuseumHeist/dirCollectionSite ; java CollectionSite.Main 22352" &
xterm  -T "Assault Party 1" -hold -e "cd $HOME/test/MuseumHeist/dirAssaultParty ; java AssaultParty.Main 22350 1" &
xterm  -T "Assault Party 2" -hold -e "cd $HOME/test/MuseumHeist/dirAssaultParty ; java AssaultParty.Main 22351 2" &
xterm  -T "Museum" -hold -e "cd $HOME/test/MuseumHeist/dirMuseum ; java Museum.Main 22355" &
sleep 3
xterm  -T "Ordinary Thieves" -hold -e "cd $HOME/test/MuseumHeist/dirOrdinaryThieves ; java OrdinaryThief.Main localhost 22354 localhost 22353 localhost 22352 localhost 22350 localhost 22351 localhost 22355" &
sleep 1
xterm  -T "Master Thief" -hold -e "cd $HOME/test/MuseumHeist/dirMasterThief ; java MasterThief.Main localhost 22354 localhost 22353 localhost 22352" &