
#include "crclj.h"
#include <iostream>
#include <stdlib.h>

using namespace crclj;
using namespace crclj::crcl::base;
using namespace crclj::crcl::utils;
using namespace crclj::java::math;
using namespace crclj::java::util;

using namespace std;

int main(int argc, const char **argv) {

    try {
        // Connect to the server
        CRCLSocket s("localhost", 64444);
        
        // Create an instance to wrap all commands.
        CRCLCommandInstanceType instance;

        // Create and send init command.
        InitCanonType initCmd;
        initCmd.setCommandID(BigInteger::valueOf(7));
        instance.setCRCLCommand(initCmd);
        s.writeCommand(instance);

        // Create and send MoveTo command.
        MoveToType moveTo;
        moveTo.setCommandID(BigInteger::valueOf(8));
        PoseType pose;
        PointType pt;
        pt.setX(BigDecimal::valueOf(0.6));
        pt.setY(BigDecimal::valueOf(0.1));
        pt.setZ(BigDecimal::valueOf(0.1));
        PrintObject("pt.getX()=",pt.getX());
        PrintObject("BigDecimal::valueOf(0.6)=",BigDecimal::valueOf(0.6));
        pose.setPoint(pt);
        VectorType xAxis;
        xAxis.setI(BigDecimal::getONE());
        xAxis.setJ(BigDecimal::getZERO());
        xAxis.setK(BigDecimal::getZERO());
        pose.setXAxis(xAxis);
        VectorType zAxis;
        zAxis.setI(BigDecimal::getZERO());
        zAxis.setJ(BigDecimal::getZERO());
        zAxis.setK(BigDecimal::getONE());
        pose.setZAxis(zAxis);
        moveTo.setEndPosition(pose);
        moveTo.setMoveStraight(false);
        instance.setCRCLCommand(moveTo);
        s.writeCommand(instance, JNI_TRUE);

        BigInteger IDback= BigInteger::getONE();
        CommandStatusType cmdStat;

        do {
            GetStatusType getStat;
            getStat.setCommandID(BigInteger::valueOf(9));
            instance.setCRCLCommand(getStat);
            s.writeCommand(instance);

            CRCLStatusType stat = s.readStatus();
            cmdStat = stat.getCommandStatus();
            IDback = cmdStat.getCommandID();
            PrintObject("Command ID=", IDback);
            PrintObject("stat=", stat);
            PrintObject("cmdStat.getCommandState()=",cmdStat.getCommandState());
            pose = stat.getPoseStatus().getPose();
            PrintObject("pose=", pose);
            pt = pose.getPoint();
            PrintObject("X:", pt.getX());
            PrintObject("Y:", pt.getY());
            PrintObject("Z:", pt.getZ());
            JointStatusesType jst = stat.getJointStatuses();
            if (jst.jthis != NULL) {
                List l = jst.getJointStatus();
                for (int i = 0; i < l.size(); i++) {
                    JointStatusType elem;
                    elem = JointStatusType::cast(l.get(i));
                    PrintObject("Joint Number :", elem.getJointNumber());
                    PrintObject("Joint Position :", elem.getJointPosition());
                }
            }
        } while (!IDback.equals(moveTo.getCommandID()) || cmdStat.getCommandState().equals(CommandStateEnumType::getCRCL_WORKING()));
        cout << " End of C++ main() reached. " << endl;
    } catch (jthrowable t) {
        PrintJThrowable("Exception Thrown : ", t);
        exit(1);
    }
}