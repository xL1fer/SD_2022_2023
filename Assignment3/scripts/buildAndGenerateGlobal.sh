echo "Compiling source code."
javac */*.java
echo "Distributing intermediate code to the different execution environments."
echo "  RMI registry"
rm -rf dirRMIRegistry/ServerInterfaces
mkdir -p dirRMIRegistry/ServerInterfaces
cp ServerInterfaces/*.class dirRMIRegistry/ServerInterfaces
echo "  Register Remote Objects"
rm -rf dirRegistry/Registry dirRegistry/ServerInterfaces
mkdir -p dirRegistry/Registry dirRegistry/ServerInterfaces
cp Registry/Main.class dirRegistry/Registry
cp Registry/RegisterRemoteObject.class dirRegistry/Registry
cp ServerInterfaces/Register.class dirRegistry/ServerInterfaces
echo "  Information Repository"
rm -rf dirInformationRepos/InformationRepository dirInformationRepos/ServerInterfaces dirInformationRepos/CommInfra
mkdir -p dirInformationRepos dirInformationRepos/InformationRepository dirInformationRepos/ServerInterfaces dirInformationRepos/CommInfra
cp InformationRepository/InformationRepository.class InformationRepository/Main.class InformationRepository/SimulationInput.class dirInformationRepos/InformationRepository
cp ServerInterfaces/Register.class ServerInterfaces/InformationRepositoryInterface.class dirInformationRepos/ServerInterfaces
cp CommInfra/ConfigsOffsets.class dirInformationRepos/CommInfra
echo "  Concentration Site"
rm -rf dirConcentrationSite/ConcentrationSite dirConcentrationSite/ServerInterfaces dirConcentrationSite/CommInfra
mkdir -p dirConcentrationSite dirConcentrationSite/ConcentrationSite dirConcentrationSite/ServerInterfaces dirConcentrationSite/CommInfra
cp ConcentrationSite/ConcentrationSite.class ConcentrationSite/Main.class ConcentrationSite/SimulationInput.class dirConcentrationSite/ConcentrationSite
cp ServerInterfaces/Register.class ServerInterfaces/ConcentrationSiteInterface.class dirConcentrationSite/ServerInterfaces
cp CommInfra/ConfigsOffsets.class dirConcentrationSite/CommInfra
echo "  Collection Site"
rm -rf dirCollectionSite/CollectionSite dirCollectionSite/ServerInterfaces dirCollectionSite/CommInfra
mkdir -p dirCollectionSite dirCollectionSite/CollectionSite dirCollectionSite/ServerInterfaces dirCollectionSite/CommInfra
cp CollectionSite/CollectionSite.class CollectionSite/Main.class CollectionSite/SimulationInput.class dirCollectionSite/CollectionSite
cp ServerInterfaces/Register.class ServerInterfaces/CollectionSiteInterface.class dirCollectionSite/ServerInterfaces
cp CommInfra/ConfigsOffsets.class dirCollectionSite/CommInfra
echo "  Assault Party"
rm -rf dirAssaultParty/AssaultParty dirAssaultParty/ServerInterfaces dirAssaultParty/CommInfra
mkdir -p dirAssaultParty dirAssaultParty/AssaultParty dirAssaultParty/ServerInterfaces dirAssaultParty/CommInfra
cp AssaultParty/AssaultParty.class AssaultParty/Main.class AssaultParty/SimulationInput.class dirAssaultParty/AssaultParty
cp ServerInterfaces/Register.class ServerInterfaces/AssaultPartyInterface.class dirAssaultParty/ServerInterfaces
cp CommInfra/ConfigsOffsets.class dirAssaultParty/CommInfra
echo "  Museum"
rm -rf dirMuseum/Museum dirMuseum/ServerInterfaces dirMuseum/CommInfra
mkdir -p dirMuseum dirMuseum/Museum dirMuseum/ServerInterfaces dirMuseum/CommInfra
cp Museum/Museum.class Museum/Main.class Museum/SimulationInput.class Museum/Room.class dirMuseum/Museum
cp ServerInterfaces/Register.class ServerInterfaces/MuseumInterface.class dirMuseum/ServerInterfaces
cp CommInfra/ConfigsOffsets.class dirMuseum/CommInfra
echo "  Master Thief"
rm -rf dirMasterThief/MasterThief dirMasterThief/ServerInterfaces dirMasterThief/CommInfra
mkdir -p dirMasterThief dirMasterThief/MasterThief dirMasterThief/ServerInterfaces dirMasterThief/CommInfra
cp MasterThief/MasterThief.class MasterThief/Main.class MasterThief/MasterThiefStates.class MasterThief/Thief.class MasterThief/SimulationInput.class dirMasterThief/MasterThief
cp ServerInterfaces/Register.class ServerInterfaces/InformationRepositoryInterface.class ServerInterfaces/CollectionSiteInterface.class ServerInterfaces/ConcentrationSiteInterface.class dirMasterThief/ServerInterfaces
cp CommInfra/ConfigsOffsets.class dirMasterThief/CommInfra
echo "  Ordinary Thieves"
rm -rf dirOrdinaryThieves/OrdinaryThief dirOrdinaryThieves/ServerInterfaces dirOrdinaryThieves/CommInfra
mkdir -p dirOrdinaryThieves dirOrdinaryThieves/OrdinaryThief dirOrdinaryThieves/ServerInterfaces dirOrdinaryThieves/CommInfra
cp OrdinaryThief/OrdinaryThief.class OrdinaryThief/Main.class OrdinaryThief/OrdinaryThiefStates.class OrdinaryThief/Thief.class OrdinaryThief/SimulationInput.class dirOrdinaryThieves/OrdinaryThief
cp ServerInterfaces/Register.class ServerInterfaces/InformationRepositoryInterface.class ServerInterfaces/MuseumInterface.class ServerInterfaces/AssaultPartyInterface.class ServerInterfaces/CollectionSiteInterface.class ServerInterfaces/ConcentrationSiteInterface.class dirOrdinaryThieves/ServerInterfaces
cp CommInfra/ConfigsOffsets.class dirOrdinaryThieves/CommInfra
echo "Compressing execution environments."
echo "  RMI registry"
rm -f  dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry
echo "  Register Remote Objects"
rm -f  dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry
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
