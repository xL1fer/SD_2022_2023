echo "Compiling source code."
javac */*.java
echo "Distributing intermediate code to the different execution environments."
echo "  Information Repository"
rm -rf dirInformationRepos
mkdir -p dirInformationRepos dirInformationRepos/InformationRepository \
         dirInformationRepos/CommInfra
cp InformationRepository/InformationRepository.class InformationRepository/InformationRepositoryClientProxy.class InformationRepository/InformationRepositoryInterface.class InformationRepository/Main.class InformationRepository/SimulationInput.class dirInformationRepos/InformationRepository
cp CommInfra/ConfigsOffsets.class CommInfra/Message.class CommInfra/MessageException.class CommInfra/MessageType.class CommInfra/ServerCom.class dirInformationRepos/CommInfra
echo "  Concentration Site"
rm -rf dirConcentrationSite
mkdir -p dirConcentrationSite dirConcentrationSite/ConcentrationSite \
         dirConcentrationSite/CommInfra
cp ConcentrationSite/ConcentrationSite.class ConcentrationSite/ConcentrationSiteClientProxy.class ConcentrationSite/ConcentrationSiteInterface.class ConcentrationSite/Main.class ConcentrationSite/SimulationInput.class dirConcentrationSite/ConcentrationSite
cp CommInfra/ConfigsOffsets.class CommInfra/Message.class CommInfra/MessageException.class CommInfra/MessageType.class CommInfra/ServerCom.class dirConcentrationSite/CommInfra
echo "  Collection Site"
rm -rf dirCollectionSite
mkdir -p dirCollectionSite dirCollectionSite/CollectionSite \
         dirCollectionSite/CommInfra
cp CollectionSite/CollectionSite.class CollectionSite/CollectionSiteClientProxy.class CollectionSite/CollectionSiteInterface.class CollectionSite/Main.class CollectionSite/SimulationInput.class dirCollectionSite/CollectionSite
cp CommInfra/ConfigsOffsets.class CommInfra/Message.class CommInfra/MessageException.class CommInfra/MessageType.class CommInfra/ServerCom.class dirCollectionSite/CommInfra
echo "  Assault Party"
rm -rf dirAssaultParty
mkdir -p dirAssaultParty dirAssaultParty/AssaultParty \
         dirAssaultParty/CommInfra
cp AssaultParty/AssaultParty.class AssaultParty/AssaultPartyClientProxy.class AssaultParty/AssaultPartyInterface.class AssaultParty/Main.class AssaultParty/SimulationInput.class dirAssaultParty/AssaultParty
cp CommInfra/ConfigsOffsets.class CommInfra/Message.class CommInfra/MessageException.class CommInfra/MessageType.class CommInfra/ServerCom.class dirAssaultParty/CommInfra
echo "  Museum"
rm -rf dirMuseum
mkdir -p dirMuseum dirMuseum/Museum \
         dirMuseum/CommInfra
cp Museum/Museum.class Museum/MuseumClientProxy.class Museum/MuseumInterface.class Museum/Main.class Museum/SimulationInput.class Museum/Room.class dirMuseum/Museum
cp CommInfra/ConfigsOffsets.class CommInfra/Message.class CommInfra/MessageException.class CommInfra/MessageType.class CommInfra/ServerCom.class dirMuseum/CommInfra
echo "  Master Thief"
rm -rf dirMasterThief
mkdir -p dirMasterThief dirMasterThief/MasterThief \
         dirMasterThief/CommInfra
cp MasterThief/CollectionSiteStub.class MasterThief/ConcentrationSiteStub.class MasterThief/InformationRepositoryStub.class MasterThief/Main.class MasterThief/MasterThief.class MasterThief/MasterThiefStates.class MasterThief/SimulationInput.class MasterThief/Thief.class dirMasterThief/MasterThief
cp CommInfra/ClientCom.class CommInfra/ConfigsOffsets.class CommInfra/Message.class CommInfra/MessageException.class CommInfra/MessageType.class dirMasterThief/CommInfra
echo "  Ordinary Thieves"
rm -rf dirOrdinaryThieves
mkdir -p dirOrdinaryThieves dirOrdinaryThieves/OrdinaryThief \
         dirOrdinaryThieves/CommInfra
cp OrdinaryThief/AssaultPartyStub.class OrdinaryThief/CollectionSiteStub.class OrdinaryThief/ConcentrationSiteStub.class OrdinaryThief/InformationRepositoryStub.class OrdinaryThief/Main.class OrdinaryThief/MuseumStub.class OrdinaryThief/OrdinaryThief.class OrdinaryThief/OrdinaryThiefStates.class OrdinaryThief/SimulationInput.class OrdinaryThief/Thief.class dirOrdinaryThieves/OrdinaryThief
cp CommInfra/ClientCom.class CommInfra/ConfigsOffsets.class CommInfra/Message.class CommInfra/MessageException.class CommInfra/MessageType.class dirOrdinaryThieves/CommInfra
echo "Compressing execution environments."
echo "  Information Repository"
rm -f  dirInformationRepos.zip
zip -rq dirInformationRepos.zip dirInformationRepos
echo "  Concentration Site"
rm -f  dirConcentrationSite.zip
zip -rq dirConcentrationSite.zip dirConcentrationSite
echo "  Collection Site"
rm -f  dirCollectionSite.zip
zip -rq dirCollectionSite.zip dirCollectionSite
echo "  Assault Party"
rm -f  dirAssaultParty.zip
zip -rq dirAssaultParty.zip dirAssaultParty
echo "  Museum"
rm -f  dirMuseum.zip
zip -rq dirMuseum.zip dirMuseum
echo "  Master Thief"
rm -f  dirMasterThief.zip
zip -rq dirMasterThief.zip dirMasterThief
echo "  Ordinary Thieves"
rm -f  dirOrdinaryThieves.zip
zip -rq dirOrdinaryThieves.zip dirOrdinaryThieves
echo "Deploying and decompressing execution environments."
mkdir -p $HOME/test/MuseumHeist
rm -rf $HOME/test/MuseumHeist/*
cp dirInformationRepos.zip $HOME/test/MuseumHeist
cp dirConcentrationSite.zip $HOME/test/MuseumHeist
cp dirCollectionSite.zip $HOME/test/MuseumHeist
cp dirAssaultParty.zip $HOME/test/MuseumHeist
cp dirMuseum.zip $HOME/test/MuseumHeist
cp dirMasterThief.zip $HOME/test/MuseumHeist
cp dirOrdinaryThieves.zip $HOME/test/MuseumHeist
cd $HOME/test/MuseumHeist
unzip -q dirInformationRepos.zip
unzip -q dirConcentrationSite.zip
unzip -q dirCollectionSite.zip
unzip -q dirAssaultParty.zip
unzip -q dirMuseum.zip
unzip -q dirMasterThief.zip
unzip -q dirOrdinaryThieves.zip