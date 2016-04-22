

// Import all the Java types we will need.
var urls = java.lang.reflect.Array.newInstance(java.net.URL, 1);
urls[0] = new java.net.URL("file:crcl4java-utils-1.3-jar-with-dependencies.jar");
var classloader = new java.net.URLClassLoader(urls);
var CRCLSocket = classloader.loadClass("crcl.utils.CRCLSocket");
var CRCLPosemath = classloader.loadClass("crcl.utils.CRCLPosemath");
var CRCLCommandInstanceType = classloader.loadClass("crcl.base.CRCLCommandInstanceType");
var InitCanonType = classloader.loadClass("crcl.base.InitCanonType");
var MoveToType = classloader.loadClass("crcl.base.MoveToType");
var GetStatusType = classloader.loadClass("crcl.base.GetStatusType");
var PoseType = classloader.loadClass("crcl.base.PoseType");
var PointType = classloader.loadClass("crcl.base.PointType");
var VectorType = classloader.loadClass("crcl.base.VectorType");
var BigInteger = java.math.BigInteger;

function pose(pt,xaxis,zaxis) {
    var pose = PoseType.newInstance();
    pose.setPoint(pt);
    pose.setXAxis(xaxis);
    pose.setZAxis(zaxis);
    return pose;
}

function point(x,y,z) {
    var pt = PointType.newInstance();
    pt.setX(java.math.BigDecimal.valueOf(x));
    pt.setY(java.math.BigDecimal.valueOf(y));
    pt.setZ(java.math.BigDecimal.valueOf(z));
    return pt;
}

function vector(i,j,k) {
    var v= VectorType.newInstance();
    v.setI(java.math.BigDecimal.valueOf(i));
    v.setJ(java.math.BigDecimal.valueOf(j));
    v.setK(java.math.BigDecimal.valueOf(k));
    return v;
}

var parmtypes = java.lang.reflect.Array.newInstance(java.lang.Class, 1);
parmtypes[0]=java.net.Socket;
var constargs = java.lang.reflect.Array.newInstance(java.lang.Object, 1);
constargs[0]=new java.net.Socket("localhost", 64444);
var socket = CRCLSocket.getConstructor(parmtypes).newInstance(constargs);
// Create an instance to wrap all commands.
print("s=" + socket);
var instance = CRCLCommandInstanceType.newInstance();
print("instance=" + instance);

var init = InitCanonType.newInstance();
init.setCommandID(BigInteger.valueOf(7));
instance.setCRCLCommand(init);
socket.writeCommand(instance);

// Create and send MoveTo command.
var moveTo = MoveToType.newInstance();
moveTo.setCommandID(BigInteger.valueOf(8));
var pose = pose(point(0.65,0.05,0.15),vector(1,0,0),vector(0,0,1));
moveTo.setEndPosition(pose);
moveTo.setMoveStraight(false);
instance.setCRCLCommand(moveTo);
socket.writeCommand(instance);
print("moveTo="+moveTo);

// Create and send getStatus request.
var getStat = GetStatusType.newInstance();
getStat.setCommandID(BigInteger.valueOf(9));
instance.setCRCLCommand(getStat);
socket.writeCommand(instance);

// Read status from server
var stat = socket.readStatus();
print("stat="+stat);

// Print out the status details.
var cmdStat = stat.getCommandStatus();
var IDback = cmdStat.getCommandID();
print("Status:");
print("CommandID = " + IDback);
print("State = " + cmdStat.getCommandState());
pt = stat.getPoseStatus().getPose().getPoint();
print("pt = " + pt.getX() + "," + pt.getY() + "," + pt.getZ());
var jst = stat.getJointStatuses();
if(jst != null) {
    var l = jst.getJointStatus();
    print("Joints:");
    for (index=0; index<l.size(); index++) {
        var js = l.get(index);
        print("Num=" + js.getJointNumber() + " Pos=" + js.getJointPosition());
    }
}

print("Closing socket");
socket.close();
