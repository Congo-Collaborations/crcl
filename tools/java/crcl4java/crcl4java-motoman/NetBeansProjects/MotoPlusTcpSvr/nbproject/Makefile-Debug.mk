#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/22c3ddb1/mpFakeLib.o \
	${OBJECTDIR}/_ext/22c3ddb1/mpMain.o \
	${OBJECTDIR}/_ext/22c3ddb1/remoteFunctions.o \
	${OBJECTDIR}/_ext/22c3ddb1/tcpSvr.o \
	${OBJECTDIR}/main.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=-lpthread

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/motoplustcpsvr

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/motoplustcpsvr: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.c} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/motoplustcpsvr ${OBJECTFILES} ${LDLIBSOPTIONS}

${OBJECTDIR}/_ext/22c3ddb1/mpFakeLib.o: /home/shackle/NetBeansProjects/MotoPlusTcpSvr/mpFakeLib.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/22c3ddb1
	${RM} "$@.d"
	$(COMPILE.c) -g -DDO_SWAP=1 -I/home/shackle/motoplus_headers -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/22c3ddb1/mpFakeLib.o /home/shackle/NetBeansProjects/MotoPlusTcpSvr/mpFakeLib.c

${OBJECTDIR}/_ext/22c3ddb1/mpMain.o: /home/shackle/NetBeansProjects/MotoPlusTcpSvr/mpMain.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/22c3ddb1
	${RM} "$@.d"
	$(COMPILE.c) -g -DDO_SWAP=1 -I/home/shackle/motoplus_headers -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/22c3ddb1/mpMain.o /home/shackle/NetBeansProjects/MotoPlusTcpSvr/mpMain.c

${OBJECTDIR}/_ext/22c3ddb1/remoteFunctions.o: /home/shackle/NetBeansProjects/MotoPlusTcpSvr/remoteFunctions.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/22c3ddb1
	${RM} "$@.d"
	$(COMPILE.c) -g -DDO_SWAP=1 -I/home/shackle/motoplus_headers -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/22c3ddb1/remoteFunctions.o /home/shackle/NetBeansProjects/MotoPlusTcpSvr/remoteFunctions.c

${OBJECTDIR}/_ext/22c3ddb1/tcpSvr.o: /home/shackle/NetBeansProjects/MotoPlusTcpSvr/tcpSvr.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/22c3ddb1
	${RM} "$@.d"
	$(COMPILE.c) -g -DDO_SWAP=1 -I/home/shackle/motoplus_headers -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/22c3ddb1/tcpSvr.o /home/shackle/NetBeansProjects/MotoPlusTcpSvr/tcpSvr.c

${OBJECTDIR}/main.o: main.c 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.c) -g -DDO_SWAP=1 -I/home/shackle/motoplus_headers -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/main.o main.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/motoplustcpsvr

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc