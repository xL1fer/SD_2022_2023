xterm  -T "RMI registry" -hold -e "cd $HOME/test ; ./set_rmiregistry_alt.sh" &
sleep 5
xterm  -T "Registry" -hold -e "cd $HOME/test/MuseumHeist/dirRegistry ; ./registry_com_alt.sh" &
sleep 5
xterm  -T "Information Repository" -hold -e "cd $HOME/test/MuseumHeist/dirInformationRepos ; ./infrepos_com_alt.sh" &
xterm  -T "Concentration Site" -hold -e "cd $HOME/test/MuseumHeist/dirConcentrationSite ; ./consite_com_alt.sh" &
xterm  -T "Collection Site" -hold -e "cd $HOME/test/MuseumHeist/dirCollectionSite ; ./colsite_com_alt.sh" &
xterm  -T "Assault Party 1" -hold -e "cd $HOME/test/MuseumHeist/dirAssaultParty ; ./asparty1_com_alt.sh" &
xterm  -T "Assault Party 2" -hold -e "cd $HOME/test/MuseumHeist/dirAssaultParty ; ./asparty2_com_alt.sh" &
xterm  -T "Museum" -hold -e "cd $HOME/test/MuseumHeist/dirMuseum ; ./museum_com_alt.sh" &
sleep 5
xterm  -T "Ordinary Thieves" -hold -e "cd $HOME/test/MuseumHeist/dirOrdinaryThieves ; ./ordthieves_com_alt.sh" &
sleep 5
xterm  -T "Master Thief" -hold -e "cd $HOME/test/MuseumHeist/dirMasterThief ; ./mstthief_com_alt.sh" &
