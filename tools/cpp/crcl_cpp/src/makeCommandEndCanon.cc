#include "crcl_cpp/CRCLCommandInstanceClasses.hh"
#include "crcl_cpp/CRCLCommandsClasses.hh"

int main(int argc, char * argv[])
{
  XmlVersion * versionIn;
  XmlHeaderForCRCLCommandInstance * headerIn;
  CRCLCommandInstanceType * CRCLCommandInstanceIn;
  EndCanonType * EndCanonCommand;
  CRCLCommandInstanceFile * CRCLCommandInstanceFileIn;
  int k;
  char outStr[1024];

  versionIn = new XmlVersion(true);
  headerIn = new XmlHeaderForCRCLCommandInstance;
  CRCLCommandInstanceIn = new CRCLCommandInstanceType;
  CRCLCommandInstanceFileIn =
    new CRCLCommandInstanceFile(versionIn, headerIn, CRCLCommandInstanceIn);
  headerIn->location =
    new SchemaLocation("xsi", "../xmlSchemas/CRCLCommandInstance.xsd", false);
  EndCanonCommand = new EndCanonType;
  EndCanonCommand->Name = 0;
  EndCanonCommand->printTypp = true;
  EndCanonCommand->CommandID = new XmlPositiveInteger("3");
  CRCLCommandInstanceIn->Name = 0;
  CRCLCommandInstanceIn->CRCLCommand = EndCanonCommand;
  k = 0;
  CRCLCommandInstanceFileIn->printSelf(outStr, &k);
  printf("%s\n", outStr);
  printf("%d\n", k);
  return 0;
}
