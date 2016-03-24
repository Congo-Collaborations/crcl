package com.github.wshackle.crcl4java.vaadin.webapp;

import com.vaadin.annotations.Push;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import crcl.base.ActuateJointType;
import crcl.base.ActuateJointsType;
import crcl.base.CRCLCommandInstanceType;
import crcl.base.CRCLCommandType;
import crcl.base.CRCLProgramType;
import crcl.base.CRCLStatusType;
import crcl.base.CommandStateEnumType;
import crcl.base.CommandStatusType;
import crcl.base.EndCanonType;
import crcl.base.GetStatusType;
import crcl.base.InitCanonType;
import crcl.base.JointSpeedAccelType;
import crcl.base.JointStatusType;
import crcl.base.JointStatusesType;
import crcl.base.MessageType;
import crcl.base.MiddleCommandType;
import crcl.base.MoveToType;
import crcl.base.PointType;
import crcl.base.PoseType;
import crcl.base.SetEndEffectorType;
import crcl.base.SetTransSpeedType;
import crcl.base.StopConditionEnumType;
import crcl.base.StopMotionType;
import crcl.base.TransSpeedRelativeType;
import crcl.base.VectorType;
import crcl.utils.CRCLPosemath;
import crcl.utils.CRCLSocket;
import crcl.utils.CRCLException;
import crcl.utils.ProgramPlotter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import rcs.posemath.PmCartesian;
import rcs.posemath.PmException;
import rcs.posemath.PmPose;
import rcs.posemath.PmRpy;
import rcs.posemath.Posemath;

/**
 *
 */
@Theme("default_theme")
@Widgetset("com.github.wshackle.crcl4java.vaadin.webapp.Crcl4JavaWidgetset")
@Push
public class CrclClientUI extends UI implements Consumer<CommonInfo> {

    private static final List<Consumer<CommonInfo>> programInfoListeners = new ArrayList<>();

    public static void addProgramInfoListener(Consumer<CommonInfo> l) {
        programInfoListeners.add(l);
    }

    public static void removeProgramInfoListener(Consumer<CommonInfo> l) {
        programInfoListeners.add(l);
    }

    private final static File REMOTE_PROGRAM_DIR = new File(System.getProperty("user.home"), ".crcl4java.programs");

    private static CommonInfo commonInfo = CommonInfo.Default(REMOTE_PROGRAM_DIR.list());

    public static void setCommonInfo(CommonInfo newCommonInfo) {
        updateImages(newCommonInfo, commonInfo);
        commonInfo = newCommonInfo;
        for (Consumer<CommonInfo> l : programInfoListeners) {
            l.accept(commonInfo);
        }
    }

    static private ProgramPlotter sidePlotter = new ProgramPlotter(ProgramPlotter.View.SIDE);
    static private ProgramPlotter overheadPlotter = new ProgramPlotter(ProgramPlotter.View.OVERHEAD);

    static final File sideImageDir;
    static final File overheadImageDir;
    private static int imgcount = 0;

    private static PoseType lastGlobalPoseInImage = null;

    public static void updateImages(CommonInfo newCommonInfo, CommonInfo oldCommonInfo) {
        if (null != newCommonInfo) {
            if (null != newCommonInfo.getCurrentProgram()) {
                boolean newPose
                        = globalCurrentPose != null
                        && (lastGlobalPoseInImage == null
                        || CRCLPosemath.diffPosesTran(globalCurrentPose, lastGlobalPoseInImage) > 2.0);
                if (newCommonInfo.getCurrentProgram() != oldCommonInfo.getCurrentProgram()
                        || newCommonInfo.getProgramIndex() != oldCommonInfo.getProgramIndex()
                        || newPose) {
                    try {
                        imgcount++;
                        String imgCountString = String.format("%06d", imgcount);
                        if (null != globalCurrentPose) {
                            sidePlotter.setCurrentPoint(globalCurrentPose.getPoint());
                        }
                        BufferedImage sideImage = sidePlotter.plotProgram(newCommonInfo.getCurrentProgram(),
                                newCommonInfo.getProgramIndex());
                        ImageIO.write(sideImage, "jpg", new File(sideImageDir, "side" + currentDateString() + imgCountString + ".jpg"));
                        if (null != globalCurrentPose) {
                            overheadPlotter.setCurrentPoint(globalCurrentPose.getPoint());
                        }
                        BufferedImage overheadImage = overheadPlotter.plotProgram(newCommonInfo.getCurrentProgram(),
                                newCommonInfo.getProgramIndex());
                        ImageIO.write(overheadImage, "jpg", new File(overheadImageDir, "overhead" + currentDateString() + imgCountString + ".jpg"));
                        if (null != globalCurrentPose) {
                            lastGlobalPoseInImage = CRCLPosemath.copy(globalCurrentPose);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private CRCLSocket socket;
    private Thread updateThread;
    private Thread monitorThread;
    private long lastUpdateTime = 0;

    private ByteArrayOutputStream recieverOutputStream;
    private static Map<String, Resource> browserMap;
//    private static final Resource defaultBrowserResource
//            = new ExternalResource("https://github.com/usnistgov/crcl");
    private static final Resource defaultBrowserResource
            = new StreamResource(
                    () -> new ByteArrayInputStream(("<html><body>" + "Message Panel" + "</body></html>").getBytes()),
                    System.currentTimeMillis() + "msg.html");
    private boolean running = false;
    private boolean skip_wait_for_done = false;
    private final BrowserFrame browser = new BrowserFrame("Message", defaultBrowserResource);
    private final TextField hostField = new TextField("Host");
    private final TextField portField = new TextField("Port");
    private final Button disconnectButton = new Button("Disconnect");
    private final Button connectButton = new Button("Connect");
    private final Button initButton = new Button("Init/Reset");
    final Button runButton = new Button("Run from Start");
    final Button continueButton = new Button("Continue from current");

    private final Table remoteProgramTable = new Table("Remote Programs");
    private final Button remoteProgramLoadButton = new Button("Load Selected Remote Program");
    private final Button remoteProgramDownloadButton = new Button("Download Selected Remote Program");
    private Resource remoteProgramResource = null;
    private FileDownloader remoteProgramDownloader = null;
    private final Table progTable = new Table("Program");
    private final Label programIndexLabel = new Label("Program Index : " + commonInfo.getProgramIndex());
    private final Label cmdIdLbl = new Label("Command ID :" + String.format("%10s", "0"));
    private final Label stateLbl = new Label("State : UNKNOWN");
    private final Label stateDescriptionLbl = new Label("");
    private String lastDescription = "";
    private final Label statusIdLbl = new Label("Status ID :" + String.format("%10s", "0"));
    private final Table posCurrentTable = new Table("Current Position");
    private final Table rotCurrentTable = new Table("Current Rotation");
    private final Table posProgramTable = new Table("Program Position");
    private final Table rotProgramTable = new Table("Program Rotation");
    private final CheckBox editProgramPosCheckbox = new CheckBox("Edit Program Position");
    private final Button currentToProgamButton = new Button("Copy Current Position to Program");
    private final Button modifyProgramPositionButton = new Button("Modify Program Position");
    private final Resource defaultOverheadImageResource = new ThemeResource("overhead.jpg");
    private final Image overHeadImage = new Image("Overhead", defaultOverheadImageResource);
    private final Resource defaultSideImageResource = new ThemeResource("side.jpg");
    private final Image sideImage = new Image("Side", defaultSideImageResource);
    private static File tempDir = null;
    private final JogButton speedMinusJB = new JogButton(" Speed-");
    private final JogButton speedPlusJB = new JogButton(" Speed+");
    private final Label speedJogLabel = new Label(" Speed: " + String.format("%+6.1f ", speedFraction * 100.0) + " % ");
    private final Button speed10Button = new Button("10% Speed");
    private final Button speed50Button = new Button("50% Speed");
    private final Button speed100Button = new Button("100% Speed");
    private final JogButton jointSpeedMinusJB = new JogButton(" Speed-");
    private final JogButton jointSpeedPlusJB = new JogButton(" Speed+");
    private final Label jointSpeedJogLabel = new Label(" Speed: " + String.format("%+6.1f ", speedFraction * 100.0) + " % ");
    private final Button jointSpeed10Button = new Button("10% Speed");
    private final Button jointSpeed50Button = new Button("50% Speed");
    private final Button jointSpeed100Button = new Button("100% Speed");

    private final Button jogIncPoint1Button = new Button(".1 mm");
    private final Button jogInc1Button = new Button("1 mm");
    private final Button jogInc10Button = new Button("10 mm");
    private final Button jogInc100Button = new Button("100 mm");
    private final Button jogInc1000Button = new Button("1m");
    private final Label jogIncLabel = new Label(" Increment: " + String.format("%+6.1f ", jogWorldTransInc) + " mm");
    final ProgressBar jogIncProgressBar = new ProgressBar(0.0f);

    private final Button jointJogIncPoint1Button = new Button(".1 deg");
    private final Button jointJogInc1Button = new Button("1 deg");
    private final Button jointJogInc10Button = new Button("10 deg");
    private final Button jointJogInc100Button = new Button("100 deg");
    public BigDecimal jointJogIncrement = BigDecimal.TEN;

    private final Label jointJogIncLabel = new Label(" Increment: " + String.format("%+6.1f ", jointJogIncrement.doubleValue()) + " deg");
    final ProgressBar jointJogIncProgressBar = new ProgressBar(0.0f);

    private final Button openGripperButton = new Button("Open Gripper");
    private final Button closeGripperButton = new Button("Close Gripper");
    private final Button recordPointButton = new Button("Record Point");

    private final JogButton xPlusJB = new JogButton(" X+ ");
    private final JogButton xMinusJB = new JogButton(" X- ");
    private final Label xJogLabel = new Label(" X: " + String.format("%+6.1f ", 0.0) + " mm ");
    private final JogButton yPlusJB = new JogButton(" Y+ ");
    private final JogButton yMinusJB = new JogButton(" Y- ");
    private final Label yJogLabel = new Label(" Y: " + String.format("%+6.1f ", 0.0) + " mm ");
    private final JogButton zPlusJB = new JogButton(" Z+ ");
    private final JogButton zMinusJB = new JogButton(" Z- ");
    private final Label zJogLabel = new Label(" Z: " + String.format("%+6.1f ", 0.0) + " mm ");
    private final JogButton rollPlusJB = new JogButton(" Roll+ ");
    private final JogButton rollMinusJB = new JogButton(" Roll- ");
    private final Label rollJogLabel = new Label(" Roll: " + String.format("%+6.1f ", 0.0) + " mm ");
    private final JogButton pitchPlusJB = new JogButton(" Pitch+ ");
    private final JogButton pitchMinusJB = new JogButton(" Pitch- ");
    private final Label pitchJogLabel = new Label(" Pitch: " + String.format("%+6.1f ", 0.0) + " mm ");
    private final JogButton yawPlusJB = new JogButton(" Yaw+ ");
    private final JogButton yawMinusJB = new JogButton(" Yaw- ");
    private final Label yawJogLabel = new Label(" Yaw: " + String.format("%+6.1f ", 0.0) + " mm ");
    private final JogButton jogJointPlusButtons[] = new JogButton[]{
        new JogButton("Joint1 +"),
        new JogButton("Joint2 +"),
        new JogButton("Joint3 +"),
        new JogButton("Joint4 +"),
        new JogButton("Joint5 +"),
        new JogButton("Joint6 +"),};
    private final JogButton jogJointMinusButtons[] = new JogButton[]{
        new JogButton("Joint1 -"),
        new JogButton("Joint2 -"),
        new JogButton("Joint3 -"),
        new JogButton("Joint4 -"),
        new JogButton("Joint5 -"),
        new JogButton("Joint6 -"),};
    private final Label jogJointLabels[] = new Label[]{
        new Label("Joint1 : " + String.format("%+6.1f ", 0.0)),
        new Label("Joint2 : " + String.format("%+6.1f ", 0.0)),
        new Label("Joint3 : " + String.format("%+6.1f ", 0.0)),
        new Label("Joint4 : " + String.format("%+6.1f ", 0.0)),
        new Label("Joint5 : " + String.format("%+6.1f ", 0.0)),
        new Label("Joint6 : " + String.format("%+6.1f ", 0.0)),};
    private final HorizontalLayout jogJointLines[] = new HorizontalLayout[6];
    private final CRCLCommandInstanceType instance = new CRCLCommandInstanceType();
    private final Table transformPos1Table = new Table("First Live Position");
    private final Button setPos1CurrentButton = new Button("Set First Live Postion to Current Live Position");
    private final Table transformPos2Table = new Table("Second Live Position");
    private final Button setPos2CurrentButton = new Button("Set Second Postion to Current Live Position");
    private final Table programPos1Table = new Table("First Program Position");
    private final Button setPos1ProgramButton = new Button("Set First Program Postion to Selected Program Line");
    private final Table programPos2Table = new Table("Second Program Position");
    private final Button setPos2ProgramButton = new Button("Set Second Program Postion to Selected Program Line");
    private final Table transformTable = new Table("Computed Transform");
    private final Table point1Errors = new Table("Point 1 Errors");
    private final Table point2Errors = new Table("Point 2 Errors");
    private final Button compute2PointTransformButton = new Button("Compute Transform with Both Points");
    private final Button computePoint1TransformButton = new Button("Compute Transform with Point 1 Only");
    private final Button computePoint2TransformButton = new Button("Compute Transform with Point 2 Only");
    private final Label transformStatusLabel = new Label("Transform Notes:");
    private final Button transformProgramButton = new Button("Apply Transform To Program");
    private final Button flipXAxisButton = new Button("Flip X Axis");
    private final Label statusLabel = new Label("Status: UNITIALIZED");
    private final Queue<MiddleCommandType> cmdQueue = new LinkedList<>();
    private final Slider minXSlider = new Slider();

    static {
        String tempDirProp = System.getProperty("temp.dir");
        if (tempDirProp != null && tempDirProp.length() > 0) {
            tempDir = new File(tempDirProp);
            if (!tempDir.exists()
                    || !tempDir.isDirectory()
                    || !tempDir.canWrite()) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, "Can't use tempDir from property temp.dir " + tempDir);
                tempDir = null;
            }
        }
        if (null == tempDir) {
            try {
                File testTempFile = File.createTempFile("test", "suffix");
                tempDir = testTempFile.getParentFile();
            } catch (IOException ex) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("tempDir = " + tempDir);
        sideImageDir = new File(tempDir, "simserver/side");
        sideImageDir.mkdirs();
        System.out.println("sideImageDir = " + sideImageDir);
        for (File f : sideImageDir.listFiles()) {
            System.out.println("f = " + f);
            f.delete();
        }
        overheadImageDir = new File(tempDir, "simserver/overhead");
        overheadImageDir.mkdirs();
        System.out.println("overheadImageDir = " + overheadImageDir);
        for (File f : overheadImageDir.listFiles()) {
            System.out.println("f = " + f);
            f.delete();
        }
        browserMap = new HashMap<>();
    }

    private String recordPointsProgramName = null;
    private CRCLProgramType recordPointsProgram = null;

    public void recordCurrentPoint() {
        if (null == recordPointsProgram) {
            recordPointsProgram = new CRCLProgramType();
            InitCanonType initcmd = new InitCanonType();
            initcmd.setCommandID(BigInteger.ONE);
            recordPointsProgram.setInitCanon(initcmd);
            EndCanonType endcmd = new EndCanonType();
            endcmd.setCommandID(BigInteger.valueOf(2));
            recordPointsProgram.setEndCanon(endcmd);
        }
        MoveToType moveToCmd = new MoveToType();
        PoseType pose = new PoseType();
        PointType pt = new PointType();
        pt.setX(currentPoint.getX());
        pt.setY(currentPoint.getY());
        pt.setZ(currentPoint.getZ());
        pose.setPoint(pt);
        VectorType xAxis = new VectorType();
        xAxis.setI(currentPose.getXAxis().getI());
        xAxis.setJ(currentPose.getXAxis().getJ());
        xAxis.setK(currentPose.getXAxis().getK());
        pose.setXAxis(xAxis);
        VectorType zAxis = new VectorType();
        zAxis.setI(currentPose.getZAxis().getI());
        zAxis.setJ(currentPose.getZAxis().getJ());
        zAxis.setK(currentPose.getZAxis().getK());
        pose.setZAxis(zAxis);
        moveToCmd.setEndPosition(pose);
        moveToCmd.setCommandID(BigInteger.valueOf(recordPointsProgram.getMiddleCommand().size() + 1));
        recordPointsProgram.getMiddleCommand().add(moveToCmd);
        recordPointsProgram.getEndCanon().setCommandID(BigInteger.valueOf(recordPointsProgram.getMiddleCommand().size() + 2));
    }

    public void recordAndSaveCurrentPoint() {
        recordCurrentPoint();
        saveRecordedPointsProgram();
    }

    public void saveRecordedPointsProgram() {
        if (null == recordPointsProgramName) {
            recordPointsProgramName = "recordedPoints." + currentDateString() + ".xml";
        }
        try (FileOutputStream fos = new FileOutputStream(new File(REMOTE_PROGRAM_DIR, recordPointsProgramName))) {
            CRCLSocket tmpsocket = socket;
            if (null == tmpsocket) {
                tmpsocket = new CRCLSocket();
            }
            final CRCLSocket tmpsocketf = tmpsocket;
            fos.write(tmpsocketf.programToPrettyDocString(recordPointsProgram, true).getBytes());
        } catch (IOException | CRCLException | JAXBException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCommonInfo(CommonInfo.withRemotePrograms(commonInfo, REMOTE_PROGRAM_DIR.list()));
    }

    public void openGripper() {
        try {
            SetEndEffectorType seeCmd = new SetEndEffectorType();
            seeCmd.setSetting(BigDecimal.ONE);
            BigInteger nextId = lastCmdIdSent.and(BigInteger.ONE);
            seeCmd.setCommandID(nextId);
            sendCommand(seeCmd);
            this.recordCurrentPoint();
            recordPointsProgram.getMiddleCommand().add(seeCmd);
            saveRecordedPointsProgram();
        } catch (CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeGripper() {
        try {
            SetEndEffectorType seeCmd = new SetEndEffectorType();
            seeCmd.setSetting(BigDecimal.ZERO);
            BigInteger nextId = lastCmdIdSent.and(BigInteger.ONE);
            seeCmd.setCommandID(nextId);
            sendCommand(seeCmd);
            this.recordCurrentPoint();
            recordPointsProgram.getMiddleCommand().add(seeCmd);
            saveRecordedPointsProgram();
        } catch (CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadRemotePrograms() {
        this.running = false;
        remoteProgramTable.removeAllItems();
        String remotePrograms[] = commonInfo.getRemotePrograms();
        if (null != remotePrograms) {
            for (int i = 0; i < remotePrograms.length; i++) {
                String remoteProgram = remotePrograms[i];
                Item oldItem = remoteProgramTable.getItem(i);
                if (null == oldItem) {
                    remoteProgramTable.addItem(new Object[]{remoteProgram}, i);
                } else {
                    Property prop = oldItem.getItemProperty("File");
                    prop.setValue(remoteProgram);
                }
            }
        }
    }

    private int getSelectedProgramLine() {
        Object id = progTable.firstItemId();
        while (id != null && id != progTable.lastItemId()) {
            if (progTable.isSelected(id)) {
                return (int) id;
            }
            id = progTable.nextItemId(id);
        }
        return -1;
    }

    private PoseType getSelectedProgramPose() {
        if (null != commonInfo) {
            if (null != commonInfo.getCurrentProgram()) {
                CRCLProgramType program = commonInfo.getCurrentProgram();
                final int program_index = getSelectedProgramLine();
                if (program_index > 0 && program_index <= program.getMiddleCommand().size()) {
                    MiddleCommandType cmd = program.getMiddleCommand().get(program_index);
                    if (cmd instanceof MoveToType) {
                        MoveToType moveToCmd = (MoveToType) cmd;
                        return moveToCmd.getEndPosition();
                    }
                }
            }
        }
        return null;
    }

    private PointType getSelectedProgramPoint() {
        PoseType pose = getSelectedProgramPose();
        if (null != pose) {
            return pose.getPoint();
        }
        return null;
    }

    private String selectedRemoteProgramFilename = null;

    private void loadSelectedRemoteProgram() {
        try {
            running = false;
            CRCLSocket tmpsocket = socket;
            if (null == tmpsocket) {
                tmpsocket = new CRCLSocket();
            }
            final CRCLSocket tmpsocketf = tmpsocket;
            String filename = this.selectedRemoteProgramFilename;
            File f = new File(REMOTE_PROGRAM_DIR, filename);
            remoteProgramResource = new FileResource(f);
            remoteProgramDownloader.setFileDownloadResource(remoteProgramResource);
            String progContents = new String(Files.readAllBytes(f.toPath()));
            CRCLProgramType prog = tmpsocketf.stringToProgram(progContents, false);
            setCommonInfo(CommonInfo.withNewProgram(commonInfo, REMOTE_PROGRAM_DIR.list(), filename, prog));
        } catch (IOException | CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private volatile boolean insideMySynAccessCount = false;

    private synchronized void mySyncAccess(Runnable r) {
        if (insideMySynAccessCount) {
            System.err.println("mySyncAccess called recursively");
            Thread.dumpStack();
        }
        try {
            insideMySynAccessCount = true;
            CrclClientUI.this.access(r);
        } catch (Throwable ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
            r.run();
        } finally {
            insideMySynAccessCount = false;
        }
    }

    private CommonInfo prevCommonInfo = CommonInfo.Default(REMOTE_PROGRAM_DIR.list());

    @Override
    @SuppressWarnings("unchecked")
    public void accept(CommonInfo t) {
        CRCLSocket tmpsocket = socket;
        try {
            if (null == tmpsocket) {
                tmpsocket = new CRCLSocket();
            }
            final CRCLSocket tmpsocketf = tmpsocket;
            commonInfo = t;
            final CRCLProgramType newProgram = commonInfo.getCurrentProgram();
            String currentFileName = commonInfo.getCurrentFileName();
            final CRCLProgramType oldProgram = prevCommonInfo.getCurrentProgram();
            final String oldProgramFileName = prevCommonInfo.getCurrentFileName();
            final boolean programIsNew = newProgram != oldProgram && !currentFileName.equals(oldProgramFileName);
            if (newProgram != oldProgram) {
                System.out.println("programIsNew = " + programIsNew);
                System.out.println("currentFileName = " + currentFileName);
                System.out.println("oldProgramFileName = " + oldProgramFileName);
            }
            String remotePrograms[] = commonInfo.getRemotePrograms();
            final String oldRemotePrograms[] = prevCommonInfo.getRemotePrograms();
            final boolean remoteProgramsNew = remotePrograms != null
                    && remotePrograms != oldRemotePrograms
                    && (oldRemotePrograms == null || remotePrograms.length != oldRemotePrograms.length);
            final int program_index = commonInfo.getProgramIndex();
            mySyncAccess(() -> {
                programIndexLabel.setValue("Program Index :" + commonInfo.getProgramIndex());
                if (programIsNew) {
                    progTable.removeAllItems();
                    try {
                        for (int i = 0; i < newProgram.getMiddleCommand().size(); i++) {
                            String tableCommandString = tmpsocketf.commandToSimpleString(newProgram.getMiddleCommand().get(i));
                            progTable.addItem(new Object[]{i, tableCommandString}, i);
                            Item item = progTable.getItem(i);
                            item.<String>getItemProperty("Command").setValue(tableCommandString);
                        }
                    } catch (ParserConfigurationException | SAXException | IOException ex) {
                        Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Item item = progTable.getItem(program_index);
                    if (null != item) {
                        programIndexLabel.setValue("Program Index :" + program_index);
                        progTable.select(program_index);
                        progTable.setCurrentPageFirstItemId(program_index);
                    }
                }
                PoseType selectedProgramPose = getSelectedProgramPose();
                if (selectedProgramPose != null) {
                    loadPointToTable(selectedProgramPose.getPoint(), posProgramTable);
                    loadPoseToRotTable(selectedProgramPose, rotProgramTable);
                }
                if (remoteProgramsNew) {
                    loadRemotePrograms();
                }
                continueButton.setEnabled(commonInfo.getProgramIndex() > 1);
                if (commonInfo.getTransformInfo() != prevCommonInfo.getTransformInfo()) {
                    TransformInfo prevTransformInfo = prevCommonInfo.getTransformInfo();
                    TransformInfo currentTransformInfo = commonInfo.getTransformInfo();
                    if (prevTransformInfo.getA1() != currentTransformInfo.getA1()) {
                        loadPointToTable(currentTransformInfo.getA1(), programPos1Table);
                    }
                    if (prevTransformInfo.getA2() != currentTransformInfo.getA2()) {
                        loadPointToTable(currentTransformInfo.getA2(), programPos2Table);
                    }
                    if (prevTransformInfo.getB1() != currentTransformInfo.getB1()) {
                        loadPointToTable(currentTransformInfo.getB1(), transformPos1Table);
                    }
                    if (prevTransformInfo.getB2() != currentTransformInfo.getB2()) {
                        loadPointToTable(currentTransformInfo.getB2(), transformPos2Table);
                    }
                }
                prevCommonInfo = commonInfo;
            });
            updateStatusLabel();
            checkImageDirs();
        } catch (CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class JogView extends VerticalLayout implements View {

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
        }

    }

    public class MainView extends HorizontalLayout implements View {

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
        }

    }

    private PmCartesian getPmPointFromTable(Table tbl) {
        PmCartesian pt = new PmCartesian();
        Item xItem = tbl.getItem(0);
        Property xProp = xItem.getItemProperty(VALUE_ITEM_PROPERTY);
        pt.x = (double) xProp.getValue();
        Item yItem = tbl.getItem(1);
        Property yProp = yItem.getItemProperty(VALUE_ITEM_PROPERTY);
        pt.y = (double) yProp.getValue();
        Item zItem = tbl.getItem(2);
        Property zProp = zItem.getItemProperty(VALUE_ITEM_PROPERTY);
        pt.z = (double) zProp.getValue();
        return pt;
    }

    public static final String VALUE_ITEM_PROPERTY = "Value";
    PmPose transformPm = null;
    PoseType transformPose = null;

    private void compute2PointTransform() {
        try {
            PmCartesian a1 = getPmPointFromTable(programPos1Table);
            PmCartesian a2 = getPmPointFromTable(programPos2Table);
            PmCartesian b1 = getPmPointFromTable(transformPos1Table);
            PmCartesian b2 = getPmPointFromTable(transformPos2Table);
            transformPm = CRCLPosemath.compute2DPmTransform(a1, a2, b1, b2);
            transformPose = CRCLPosemath.toPose(transformPm);
            loadPoseToTable(transformPose, transformTable);
            updateTransformErrors();

        } catch (PmException | CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTransformErrors() throws PmException {
        PmCartesian a1 = getPmPointFromTable(programPos1Table);
        PmCartesian b1 = getPmPointFromTable(transformPos1Table);
        updatePointErrors(a1, b1, point1Errors);
        PmCartesian a2 = getPmPointFromTable(programPos2Table);
        PmCartesian b2 = getPmPointFromTable(transformPos2Table);
        updatePointErrors(a2, b2, point2Errors);
        double point1_xy_distance = a1.distance(b1.x, b1.y);
        double point1_z_distance = b1.z - a1.z;
        double point2_xy_distance = a2.distance(b2.x, b2.y);
        double point2_z_distance = b2.z - a2.z;
        double xy_dist_difference = point1_xy_distance - point2_xy_distance;
        double z_dist_difference = point1_xy_distance - point2_xy_distance;
        double rotation = Math.toDegrees(Posemath.toRot(transformPm.rot).s);
        transformStatusLabel.setValue(
                String.format("Transform Notes: point1_xy_distance=%.3f, point2_xy_distance=%.3f, xy_dist_difference=%.3f, "
                        + " point1_z_distance=%.3f, point2_z_distance=%.3f, z_dist_difference=%.3f, "
                        + "rotation=%.1f degrees",
                        point1_xy_distance,
                        point2_xy_distance,
                        xy_dist_difference,
                        point1_z_distance,
                        point2_z_distance,
                        z_dist_difference,
                        rotation
                ));
    }

    public void updatePointErrors(PmCartesian a, PmCartesian b, Table errorsTable) throws PmException {
        PmCartesian b1Recompute = new PmCartesian();
        Posemath.pmPoseCartMult(transformPm, a, b1Recompute);
        PmCartesian point1Error = new PmCartesian();
        Posemath.pmCartCartSub(b, b1Recompute, point1Error);
        loadPointToTable(CRCLPosemath.toPointType(point1Error), errorsTable);
    }

    private void computePoint1Transform() {
        try {
            PmCartesian a1 = getPmPointFromTable(programPos1Table);
            PmCartesian b1 = getPmPointFromTable(transformPos1Table);
            transformPm = CRCLPosemath.compute2DPmTransform(a1, a1, b1, b1);
            transformPose = CRCLPosemath.toPose(transformPm);
            loadPoseToTable(transformPose, transformTable);
            updateTransformErrors();
        } catch (PmException | CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void computePoint2Transform() {
        try {
            PmCartesian a2 = getPmPointFromTable(programPos2Table);
            PmCartesian b2 = getPmPointFromTable(transformPos2Table);
            transformPm = CRCLPosemath.compute2DPmTransform(a2, a2, b2, b2);
            transformPose = CRCLPosemath.toPose(transformPm);
            loadPoseToTable(transformPose, transformTable);
            updateTransformErrors();
        } catch (PmException | CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static private String currentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'at'HHmm");
        return sdf.format(new Date());
    }

    private void transformProgram() {
        try {
            CRCLSocket tmpsocket = socket;
            if (null == tmpsocket) {
                tmpsocket = new CRCLSocket();
            }
            final CRCLSocket tmpsocketf = tmpsocket;
            transformPose = getPoseFromTable(transformTable);
            if (null == transformPose || null == commonInfo.getCurrentProgram()) {
                return;
            }
            String newProgName = commonInfo.getCurrentFileName();
            if (newProgName.endsWith(".xml")) {
                newProgName = newProgName.substring(0, newProgName.length() - 4);
            }
            int transformedIndex = newProgName.indexOf(".transformed");
            if (transformedIndex > 0) {
                newProgName = newProgName.substring(0, transformedIndex);
            }
            newProgName += ".transformed." + currentDateString() + ".xml";
            CRCLProgramType newProgram = CRCLPosemath.transformProgram(transformPose, commonInfo.getCurrentProgram());
            try (FileOutputStream fos = new FileOutputStream(new File(REMOTE_PROGRAM_DIR, newProgName))) {
                fos.write(tmpsocketf.programToPrettyDocString(newProgram, true).getBytes());
            } catch (IOException | JAXBException ex) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            setCommonInfo(CommonInfo.withNewProgram(commonInfo, REMOTE_PROGRAM_DIR.list(), newProgName, newProgram));
        } catch (CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void flipXAxis() {
        try {
            CRCLSocket tmpsocket = socket;
            if (null == tmpsocket) {
                tmpsocket = new CRCLSocket();
            }
            final CRCLSocket tmpsocketf = tmpsocket;
            if (null == commonInfo.getCurrentProgram()) {
                return;
            }
            String newProgName = commonInfo.getCurrentFileName();
            if (newProgName.endsWith(".xml")) {
                newProgName = newProgName.substring(0, newProgName.length() - 4);
            }
            int flippedIndex = newProgName.indexOf(".flipped");
            if (flippedIndex > 0) {
                newProgName = newProgName.substring(0, flippedIndex);
            }
            newProgName += ".flipped." + currentDateString() + ".xml";
            CRCLProgramType newProgram = CRCLPosemath.flipXAxis(commonInfo.getCurrentProgram());
            try (FileOutputStream fos = new FileOutputStream(new File(REMOTE_PROGRAM_DIR, newProgName))) {
                fos.write(tmpsocketf.programToPrettyDocString(newProgram, true).getBytes());
            } catch (IOException | JAXBException ex) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            setCommonInfo(CommonInfo.withNewProgram(commonInfo, REMOTE_PROGRAM_DIR.list(), newProgName, newProgram));
        } catch (CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startRun() {
        if (null == socket) {
            connect();
        }
        CRCLProgramType program = commonInfo.getCurrentProgram();
        if (null != socket && null != program && program.getMiddleCommand().size() > 0) {
            try {
                if (commonInfo.getProgramIndex() < 1) {
                    CRCLCommandInstanceType instance = new CRCLCommandInstanceType();
                    instance.setCRCLCommand(program.getInitCanon());
                    if (null == instance.getCRCLCommand().getCommandID()) {
                        instance.getCRCLCommand().setCommandID(BigInteger.ONE);
                    }
                    socket.writeCommand(instance);
                    lastCmdIdSent = instance.getCRCLCommand().getCommandID();
                } else {
                    skip_wait_for_done = true;
                }
                running = true;
            } catch (CRCLException ex) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setTransformA1(PointType a1) {
        setCommonInfo(CommonInfo.withTransformInfoA1(commonInfo, a1));
    }

    public void setTransformA2(PointType a2) {
        setCommonInfo(CommonInfo.withTransformInfoA2(commonInfo, a2));
    }

    public void setTransformB1(PointType b1) {
        setCommonInfo(CommonInfo.withTransformInfoB1(commonInfo, b1));
    }

    public void setTransformB2(PointType b2) {
        setCommonInfo(CommonInfo.withTransformInfoB2(commonInfo, b2));
    }

    private void currentToProgram() {
        loadPointToTable(currentPoint, posProgramTable);
        loadPoseToRotTable(currentPose, rotProgramTable);
    }

    private void saveNewProgram(CRCLProgramType newProgram) throws CRCLException {
        CRCLSocket tmpsocket = socket;
        if (null == tmpsocket) {
            tmpsocket = new CRCLSocket();
        }
        final CRCLSocket tmpsocketf = tmpsocket;
        transformPose = getPoseFromTable(transformTable);
        if (null == transformPose || null == commonInfo.getCurrentProgram()) {
            return;
        }
        String newProgName = commonInfo.getCurrentFileName();
        if (newProgName.endsWith(".xml")) {
            newProgName = newProgName.substring(0, newProgName.length() - 4);
        }
        int modifiedIndex = newProgName.indexOf(".modified");
        if (modifiedIndex > 0) {
            newProgName = newProgName.substring(0, modifiedIndex);
        }
        newProgName += ".modified." + currentDateString() + ".xml";
        try (FileOutputStream fos = new FileOutputStream(new File(REMOTE_PROGRAM_DIR, newProgName))) {
            fos.write(tmpsocketf.programToPrettyDocString(newProgram, true).getBytes());
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCommonInfo(CommonInfo.withNewProgram(commonInfo, REMOTE_PROGRAM_DIR.list(), newProgName, newProgram));
    }

    private void modifySelectedProgramPose() {
        if (null == commonInfo.getCurrentProgram()) {
            alert("No Program Selected to Modify.");
            return;
        }
        final int program_index = commonInfo.getProgramIndex();
        if (program_index < 0 || program_index >= commonInfo.getCurrentProgram().getMiddleCommand().size()) {
            alert("Selected program index invalid.");
            return;
        }
        CRCLProgramType newProgram = CRCLPosemath.copy(commonInfo.getCurrentProgram());
        MiddleCommandType cmd = newProgram.getMiddleCommand().get(commonInfo.getProgramIndex());
        if (cmd instanceof MoveToType) {
            try {
                MoveToType moveToCmd = (MoveToType) cmd;
                PoseType newPose = new PoseType();
                PointType pt = CRCLPosemath.toPointType(getPmPointFromTable(posProgramTable));
                newPose.setPoint(pt);
                readPoseFromRotTable(newPose, rotProgramTable);
                moveToCmd.setEndPosition(newPose);
                newProgram.getMiddleCommand().set(program_index, cmd);
                saveNewProgram(newProgram);
            } catch (CRCLException ex) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert("Selected program command is of wrong type:" + cmd.getClass());
            return;
        }
    }

    public void alert(String msg) {
        mySyncAccess(() -> {
            Notification n = new Notification(msg);
            n.setDelayMsec(5000);
            n.show(Page.getCurrent());
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void init(VaadinRequest vaadinRequest) {
        System.out.println("init(" + vaadinRequest + ")");
        addProgramInfoListener(this);
        final VerticalLayout navLayout = new VerticalLayout();
        navLayout.setSpacing(true);
        final HorizontalLayout topStatusLine = new HorizontalLayout();
        topStatusLine.setSpacing(true);
        topStatusLine.addComponent(cmdIdLbl);
        topStatusLine.addComponent(stateLbl);
        topStatusLine.addComponent(stateDescriptionLbl);
        topStatusLine.addComponent(statusIdLbl);
        navLayout.addComponent(topStatusLine);
        final HorizontalLayout navButtons = new HorizontalLayout();
        navButtons.setSpacing(true);
        final Button mainNavButton = new Button("Main");
        navButtons.addComponent(mainNavButton);
        final Button jogWorldNavButton = new Button("Jog World");
        navButtons.addComponent(jogWorldNavButton);
        final Button jogJointNavButton = new Button("Jog Joint");
        navButtons.addComponent(jogJointNavButton);
        final Button remoteProgramsNavButton = new Button("Remote Programs");
        navButtons.addComponent(remoteProgramsNavButton);
        final Button transformNavButton = new Button("Transform");
        navButtons.addComponent(transformNavButton);
        navLayout.addComponent(navButtons);
        Panel panel = new Panel();
        navLayout.addComponent(panel);
        final MainView mainLayout = new MainView();
        final HorizontalLayout jogJointLayout = new HorizontalLayout();
        final HorizontalLayout jogWorldLayout = new HorizontalLayout();
        final VerticalLayout jogWorldLeftLayout = new VerticalLayout();
        final VerticalLayout jogWorldRightLayout = new VerticalLayout();
        final VerticalLayout jogJointLeftLayout = new VerticalLayout();
        final VerticalLayout jogJointRightLayout = new VerticalLayout();
        final VerticalLayout remoteProgramsLayout = new VerticalLayout();
        final VerticalLayout transformLayout = new VerticalLayout();
        final VerticalLayout tranformGroupSetupLayout = new VerticalLayout();

        mainLayout.setSpacing(true);
        jogJointLayout.setSpacing(true);
        jogWorldLayout.setSpacing(true);

        jogJointNavButton.addClickListener(l -> panel.setContent(jogJointLayout));
        jogWorldNavButton.addClickListener(l -> panel.setContent(jogWorldLayout));
        mainNavButton.addClickListener(l -> panel.setContent(mainLayout));
        remoteProgramsNavButton.addClickListener(l -> panel.setContent(remoteProgramsLayout));
        transformNavButton.addClickListener(l -> panel.setContent(transformLayout));
        
        tranformGroupSetupLayout.setSpacing(true);
        minXSlider.setHeight("200px");
        minXSlider.setWidth("600px");
        tranformGroupSetupLayout.addComponent(minXSlider);
        panel.setContent(mainLayout);
        mainLayout.setMargin(true);
        setContent(navLayout);
        final VerticalLayout leftLayout = new VerticalLayout();
        mainLayout.addComponent(leftLayout);
        final VerticalLayout middleLayout = new VerticalLayout();
        mainLayout.addComponent(middleLayout);
        middleLayout.setMargin(true);
        setupPosTable(posCurrentTable);
        setupRotTable(rotCurrentTable);

        HorizontalLayout imageLine = new HorizontalLayout();
        imageLine.setSpacing(true);
        imageLine.addComponent(overHeadImage);
        imageLine.addComponent(sideImage);
        middleLayout.addComponent(imageLine);
        HorizontalLayout posRotateLine = new HorizontalLayout();
        posRotateLine.setSpacing(true);
        posRotateLine.addComponent(posCurrentTable);
        posRotateLine.addComponent(rotCurrentTable);
        middleLayout.addComponent(posRotateLine);

        setupPosTable(posProgramTable);
        setupRotTable(rotProgramTable);

        HorizontalLayout posProgramRotateLine = new HorizontalLayout();
        posProgramRotateLine.setSpacing(true);
        posProgramRotateLine.addComponent(posProgramTable);
        posProgramRotateLine.addComponent(rotProgramTable);

        middleLayout.addComponent(posProgramRotateLine);
        editProgramPosCheckbox.addValueChangeListener((Property.ValueChangeEvent event) -> {
            posProgramTable.setEditable(editProgramPosCheckbox.getValue());
            rotProgramTable.setEditable(editProgramPosCheckbox.getValue());
        });
        middleLayout.addComponent(editProgramPosCheckbox);
        currentToProgamButton.addClickListener(e -> currentToProgram());
        middleLayout.addComponent(currentToProgamButton);
        modifyProgramPositionButton.addClickListener(e -> modifySelectedProgramPose());
        middleLayout.addComponent(modifyProgramPositionButton);
        middleLayout.addComponent(programIndexLabel);
        HorizontalLayout hostPortLine = new HorizontalLayout();
        hostPortLine.setSpacing(true);
        hostField.setValue("localhost");
        hostPortLine.addComponent(hostField);
        portField.setValue("64444");
        hostPortLine.addComponent(portField);
        leftLayout.addComponent(hostPortLine);
        disconnectButton.setEnabled(false);
        final HorizontalLayout connectDisconnectLayout = new HorizontalLayout();
        connectDisconnectLayout.addComponent(disconnectButton);
        connectDisconnectLayout.addComponent(connectButton);
        navButtons.addComponent(initButton);
        leftLayout.addComponent(connectDisconnectLayout);
        final Upload uploadProgram = new Upload("Upload Program", new Upload.Receiver() {

            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                recieverOutputStream = new ByteArrayOutputStream();
                return recieverOutputStream;
            }
        });
        remoteProgramTable.addContainerProperty("File", String.class, null);
        remoteProgramTable.setWidth("500px");
        remoteProgramTable.setHeight("460px");
        remoteProgramTable.setMultiSelect(false);
        remoteProgramTable.setSelectable(true);
        loadRemotePrograms();
        remoteProgramLoadButton.addClickListener(e -> loadSelectedRemoteProgram());

        progTable.addContainerProperty("Index", Integer.class, null);
        progTable.addContainerProperty("Command", String.class, null);
        progTable.setWidth("500px");
        progTable.setHeight("460px");
        progTable.setMultiSelect(false);
        progTable.setSelectable(true);

        progTable.addItemClickListener(e -> setCommonInfo(CommonInfo.withProgramIndex(commonInfo, (int) e.getItemId())));
        
        VerticalLayout transformSetupLayout = new VerticalLayout();
        VerticalLayout transformGroupLayout = new VerticalLayout();
        Panel transformPanel = new Panel();
        Button transformSetupNavButton = new Button("Setup");
        Button transformGroupNavButton = new Button("Group");
        HorizontalLayout transformNavButtonLayout = new HorizontalLayout();
        transformNavButtonLayout.setSpacing(true);
        transformNavButtonLayout.addComponent(transformSetupNavButton);
        transformNavButtonLayout.addComponent(transformGroupNavButton);
        transformLayout.addComponent(transformNavButtonLayout);
        transformLayout.addComponent(transformPanel);
        
        transformSetupNavButton.addClickListener(l -> transformPanel.setContent(transformSetupLayout));
        transformGroupNavButton.addClickListener(l -> transformPanel.setContent(transformGroupLayout));
        
        GridLayout posGridLayout = new GridLayout(2, 2);
        posGridLayout.setSpacing(true);
        VerticalLayout livePos1VLayout = new VerticalLayout();

        setupPosTable(transformPos1Table);
        livePos1VLayout.addComponent(transformPos1Table);
        setPos1CurrentButton.addClickListener(e -> setTransformB1(currentPoint));
        livePos1VLayout.addComponent(setPos1CurrentButton);
        posGridLayout.addComponent(livePos1VLayout, 0, 0);

        VerticalLayout livePos2VLayout = new VerticalLayout();
        setupPosTable(transformPos2Table);
        livePos2VLayout.addComponent(transformPos2Table);
        setPos2CurrentButton.addClickListener(e -> setTransformB2(currentPoint));
        livePos2VLayout.addComponent(setPos2CurrentButton);
        posGridLayout.addComponent(livePos2VLayout, 1, 0);

        VerticalLayout programPos1VLayout = new VerticalLayout();

        setupPosTable(programPos1Table);
        programPos1VLayout.addComponent(programPos1Table);
        setPos1ProgramButton.addClickListener(e -> setTransformA1(getSelectedProgramPoint()));

        programPos1VLayout.addComponent(setPos1ProgramButton);
        posGridLayout.addComponent(programPos1VLayout, 0, 1);

        VerticalLayout programPos2VLayout = new VerticalLayout();

        setupPosTable(programPos2Table);
        programPos2VLayout.addComponent(programPos2Table);
        setPos2ProgramButton.addClickListener(e -> setTransformA2(getSelectedProgramPoint()));
        programPos2VLayout.addComponent(setPos2ProgramButton);
        posGridLayout.addComponent(programPos2VLayout, 1, 1);
        transformSetupLayout.addComponent(posGridLayout);

        setupTransformTable(transformTable);
        setupPosTable(point1Errors);
        setupPosTable(point2Errors);

        HorizontalLayout transformButtonsLine = new HorizontalLayout();
        transformButtonsLine.setSpacing(true);
        compute2PointTransformButton.addClickListener(e -> compute2PointTransform());
        transformButtonsLine.addComponent(compute2PointTransformButton);
        computePoint1TransformButton.addClickListener(e -> computePoint1Transform());
        transformButtonsLine.addComponent(computePoint1TransformButton);
        computePoint2TransformButton.addClickListener(e -> computePoint2Transform());
        transformButtonsLine.addComponent(computePoint2TransformButton);

        transformSetupLayout.addComponent(transformButtonsLine);
        transformSetupLayout.addComponent(transformStatusLabel);
        HorizontalLayout outputTablesLine = new HorizontalLayout();
        outputTablesLine.setSpacing(true);
        outputTablesLine.addComponent(transformTable);
        outputTablesLine.addComponent(point1Errors);
        outputTablesLine.addComponent(point2Errors);
        transformSetupLayout.addComponent(outputTablesLine);

        CheckBox editable = new CheckBox("Editable", false);
        editable.addValueChangeListener(valueChange
                -> transformTable.setEditable(editable.getValue()));
        transformSetupLayout.addComponent(editable);
        HorizontalLayout applyLine = new HorizontalLayout();

        applyLine.addComponent(transformProgramButton);
        transformProgramButton.addClickListener(e -> transformProgram());
        applyLine.addComponent(flipXAxisButton);
        flipXAxisButton.addClickListener(e -> flipXAxis());
        transformSetupLayout.addComponent(applyLine);
        transformPanel.setContent(transformSetupLayout);
        runButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                setCommonInfo(CommonInfo.withProgramIndex(commonInfo, 0));
                startRun();
            }
        });
        continueButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                setCommonInfo(CommonInfo.withProgramIndex(commonInfo, 0));
                startRun();
            }
        });

        progTable.addItemClickListener((ItemClickEvent event) -> {
            String cmd = event.getItem().getItemProperty("Command").getValue().toString();
            int spaceindex = cmd.indexOf(' ');
            if (spaceindex > 0) {
                cmd = cmd.substring(0, spaceindex);
            }
            Resource r = browserMap.get(cmd);
            if (null != r) {
                mySyncAccess(() -> {
                    browser.setSource(r);
                });
            } else {
                mySyncAccess(() -> {
                    browser.setSource(defaultBrowserResource);
                });
            }
        });
        uploadProgram.addSucceededListener(new Upload.SucceededListener() {

            @Override
            public void uploadSucceeded(Upload.SucceededEvent event) {
                if (null != recieverOutputStream) {

                    String string = recieverOutputStream.toString();
                    if (null != string && string.length() > 0) {
                        try {
                            REMOTE_PROGRAM_DIR.mkdirs();
                            try (FileOutputStream fos = new FileOutputStream(new File(REMOTE_PROGRAM_DIR, event.getFilename()))) {
                                fos.write(string.getBytes());
                            } catch (IOException ex) {
                                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            CRCLSocket tmpsocket = socket;
                            if (null == tmpsocket) {
                                tmpsocket = new CRCLSocket();
                            }
                            CRCLProgramType newProgram = tmpsocket.stringToProgram(string, false);
                            setCommonInfo(CommonInfo.withNewProgram(commonInfo, REMOTE_PROGRAM_DIR.list(), event.getFilename(), newProgram));
                            running = false;

                        } catch (CRCLException ex) {
                            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        connectButton.addClickListener((ClickEvent event) -> {
            connect();
        });
        initButton.addClickListener((ClickEvent event) -> {
            try {
                sendInit();
                cmdQueue.clear();
                SetTransSpeedType setSpeedCmd = new SetTransSpeedType();
                TransSpeedRelativeType relSpeed = new TransSpeedRelativeType();
                relSpeed.setFraction(BigDecimal.valueOf(speedFraction));
                setSpeedCmd.setTransSpeed(relSpeed);
                cmdQueue.offer(setSpeedCmd);
                running = false;
            } catch (CRCLException ex) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        disconnectButton.addClickListener((ClickEvent event) -> {
            disconnect();
        });
        leftLayout.addComponent(uploadProgram);
        final HorizontalLayout runStopLayout = new HorizontalLayout();

        runStopLayout.addComponent(runButton);
        runStopLayout.addComponent(continueButton);
        final Button stepButton = new Button("One Step");

        stepButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    if (null == socket) {
                        connect();
                    }
                    runOneProgramStep(commonInfo.getProgramIndex());
                } catch (CRCLException ex) {
                    Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        runStopLayout.addComponent(stepButton);

        final Button stopButton = new Button("Stop");

        stopButton.addClickListener(
                new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                running = false;
                stopMotion();
            }
        }
        );
        navButtons.addComponent(stopButton);
        leftLayout.addComponent(runStopLayout);
        remoteProgramTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                try {
                    Item selectedItem = event.getItem();
                    CrclClientUI.this.selectedRemoteProgramFilename = selectedItem.getItemProperty("File").getValue().toString();
                    String filename = CrclClientUI.this.selectedRemoteProgramFilename;
                    File f = new File(REMOTE_PROGRAM_DIR, filename);
                    System.out.println("f = " + f);
                    System.out.println("f.exists() = " + f.exists());
                    System.out.println("f.getCanonicalPath() = " + f.getCanonicalPath());
                    remoteProgramResource = new FileResource(f);
                    if (null == remoteProgramDownloader) {
                        remoteProgramDownloader = new FileDownloader(remoteProgramResource);
                        remoteProgramDownloader.extend(remoteProgramDownloadButton);
                        remoteProgramDownloadButton.setEnabled(true);
                    } else {
                        remoteProgramDownloader.setFileDownloadResource(remoteProgramResource);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        remoteProgramsLayout.addComponent(remoteProgramTable);
        HorizontalLayout remoteButtonsLine = new HorizontalLayout();
        remoteProgramDownloadButton.setEnabled(false);
        remoteButtonsLine.addComponent(remoteProgramLoadButton);

        remoteButtonsLine.addComponent(remoteProgramDownloadButton);
        remoteProgramsLayout.addComponent(remoteButtonsLine);
        leftLayout.addComponent(progTable);

        final HorizontalLayout xLine = new HorizontalLayout();
        xLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        xLine.addComponent(xMinusJB);
        xMinusJB.addMouseDownConsumer(med -> curJogState = JogState.X_MINUS);
        xMinusJB.addMouseUpConsumer(med -> stopMotion());
        xPlusJB.addMouseDownConsumer(med -> curJogState = JogState.X_PLUS);
        xPlusJB.addMouseUpConsumer(med -> stopMotion());

        xLine.addComponent(xPlusJB);
        xLine.addComponent(xJogLabel);
        jogWorldLeftLayout.addComponent(xLine);

        final HorizontalLayout yLine = new HorizontalLayout();
        yLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        yLine.addComponent(yMinusJB);
        yMinusJB.addMouseDownConsumer(med -> curJogState = JogState.Y_MINUS);
        yMinusJB.addMouseUpConsumer(med -> stopMotion());
        yPlusJB.addMouseDownConsumer(med -> curJogState = JogState.Y_PLUS);
        yPlusJB.addMouseUpConsumer(med -> stopMotion());

        yLine.addComponent(yPlusJB);
        yLine.addComponent(yJogLabel);
        jogWorldLeftLayout.addComponent(yLine);

        final HorizontalLayout zLine = new HorizontalLayout();
        zLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        zLine.addComponent(zMinusJB);
        zMinusJB.addMouseDownConsumer(med -> curJogState = JogState.Z_MINUS);
        zMinusJB.addMouseUpConsumer(med -> stopMotion());
        zPlusJB.addMouseDownConsumer(med -> curJogState = JogState.Z_PLUS);
        zPlusJB.addMouseUpConsumer(med -> stopMotion());

        zLine.addComponent(zPlusJB);
        zLine.addComponent(zJogLabel);
        jogWorldLeftLayout.addComponent(zLine);

        final HorizontalLayout rollLine = new HorizontalLayout();
        rollLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        rollLine.addComponent(rollMinusJB);
        rollMinusJB.addMouseDownConsumer(med -> curJogState = JogState.ROLL_MINUS);
        rollMinusJB.addMouseUpConsumer(med -> stopMotion());
        rollPlusJB.addMouseDownConsumer(med -> curJogState = JogState.ROLL_PLUS);
        rollPlusJB.addMouseUpConsumer(med -> stopMotion());

        rollLine.addComponent(rollPlusJB);
        rollLine.addComponent(rollJogLabel);
        jogWorldLeftLayout.addComponent(rollLine);

        final HorizontalLayout pitchLine = new HorizontalLayout();
        pitchLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        pitchLine.addComponent(pitchMinusJB);
        pitchMinusJB.addMouseDownConsumer(med -> curJogState = JogState.PITCH_MINUS);
        pitchMinusJB.addMouseUpConsumer(med -> stopMotion());
        pitchPlusJB.addMouseDownConsumer(med -> curJogState = JogState.PITCH_PLUS);
        pitchPlusJB.addMouseUpConsumer(med -> stopMotion());

        pitchLine.addComponent(pitchPlusJB);
        pitchLine.addComponent(pitchJogLabel);
        jogWorldLeftLayout.addComponent(pitchLine);

        final HorizontalLayout yawLine = new HorizontalLayout();
        yawLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        yawLine.addComponent(yawMinusJB);
        yawMinusJB.addMouseDownConsumer(med -> curJogState = JogState.YAW_MINUS);
        yawMinusJB.addMouseUpConsumer(med -> stopMotion());
        yawPlusJB.addMouseDownConsumer(med -> curJogState = JogState.YAW_PLUS);
        yawPlusJB.addMouseUpConsumer(med -> stopMotion());

        yawLine.addComponent(yawPlusJB);
        yawLine.addComponent(yawJogLabel);
        jogWorldLeftLayout.addComponent(yawLine);
        jogWorldLayout.addComponent(jogWorldLeftLayout);

        final HorizontalLayout speedLine = new HorizontalLayout();
        speedLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        speedLine.addComponent(speedMinusJB);
        speedMinusJB.addMouseDownConsumer(med -> curJogState = JogState.SPEED_MINUS);
        speedMinusJB.addMouseUpConsumer(med -> stopMotion());
        speedPlusJB.addMouseDownConsumer(med -> curJogState = JogState.SPEED_PLUS);
        speedPlusJB.addMouseUpConsumer(med -> stopMotion());
        speedLine.addComponent(speedPlusJB);
        speedLine.addComponent(speedJogLabel);
        jogWorldRightLayout.setSpacing(true);
        jogWorldRightLayout.addComponent(speedLine);

        final HorizontalLayout speedButtonLine = new HorizontalLayout();
        speedButtonLine.setSpacing(true);
        speed10Button.addClickListener(e -> sendSetSpeed(0.1));
        speedButtonLine.addComponent(speed10Button);
        speed50Button.addClickListener(e -> sendSetSpeed(0.5));
        speedButtonLine.addComponent(speed50Button);
        speed100Button.addClickListener(e -> sendSetSpeed(1.0));
        speedButtonLine.addComponent(speed100Button);
        jogWorldRightLayout.addComponent(speedButtonLine);

        final HorizontalLayout incrementButtonLine = new HorizontalLayout();
        incrementButtonLine.setSpacing(true);
        incrementButtonLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        jogIncPoint1Button.addClickListener(e -> {
            worldAngleIncrementRad = Math.toRadians(0.1);
            jogWorldTransInc = BigDecimal.valueOf(0.1);
            jogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jogWorldTransInc) + " mm");
        });
        incrementButtonLine.addComponent(jogIncPoint1Button);
        jogInc1Button.addClickListener(e -> {
            worldAngleIncrementRad = Math.toRadians(1.0);
            jogWorldTransInc = BigDecimal.ONE;
            jogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jogWorldTransInc) + " mm");
        });
        incrementButtonLine.addComponent(jogInc1Button);
        jogInc10Button.addClickListener(e -> {
            worldAngleIncrementRad = Math.toRadians(10.0);
            jogWorldTransInc = BigDecimal.TEN;
            jogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jogWorldTransInc) + " mm");
        });
        incrementButtonLine.addComponent(jogInc10Button);
        jogInc100Button.addClickListener(e -> {
            worldAngleIncrementRad = Math.toRadians(100.0);
            jogWorldTransInc = BigDecimal.valueOf(100.0);
            jogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jogWorldTransInc) + " mm,");
        });
        incrementButtonLine.addComponent(jogInc100Button);
        jogInc1000Button.addClickListener(e -> {
            worldAngleIncrementRad = Math.toRadians(1000.0);
            jogWorldTransInc = BigDecimal.valueOf(1000.0);
            jogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jogWorldTransInc.doubleValue() / 1000.0) + "m");
        });
        incrementButtonLine.addComponent(jogInc1000Button);
        jogWorldRightLayout.addComponent(incrementButtonLine);

        HorizontalLayout jogIncLine = new HorizontalLayout();
        jogIncLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        jogIncLine.setSpacing(true);
        jogIncLine.addComponent(jogIncLabel);
        jogIncLine.addComponent(jogIncProgressBar);
        jogWorldRightLayout.addComponent(jogIncLine);

        final HorizontalLayout buttonLine = new HorizontalLayout();
        buttonLine.setSpacing(true);
        openGripperButton.addClickListener(e -> openGripper());
        buttonLine.addComponent(openGripperButton);
        closeGripperButton.addClickListener(e -> closeGripper());
        buttonLine.addComponent(closeGripperButton);
        recordPointButton.addClickListener(e -> recordAndSaveCurrentPoint());
        buttonLine.addComponent(recordPointButton);
        jogWorldRightLayout.addComponent(buttonLine);
        jogWorldLayout.addComponent(jogWorldRightLayout);

        for (int i = 0; i < jogJointLines.length; i++) {
            final int jointIndex = i;
            HorizontalLayout hl = new HorizontalLayout();
            jogJointLines[i] = hl;
            hl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            JogButton jmButton = jogJointMinusButtons[i];
            jmButton.addMouseDownConsumer(med -> {
                curJogState = JogState.JOINT_MINUS;
                jogJointNumber = jointIndex;
            });
            jmButton.addMouseUpConsumer(med -> stopMotion());
            hl.addComponent(jmButton);
            JogButton jpButton = jogJointPlusButtons[i];
            jpButton.addMouseDownConsumer(med -> {
                curJogState = JogState.JOINT_PLUS;
                jogJointNumber = jointIndex;
            });
            jpButton.addMouseUpConsumer(med -> stopMotion());
            hl.addComponent(jpButton);
            hl.addComponent(jogJointLabels[i]);
            jogJointLeftLayout.addComponent(jogJointLines[i]);
        }
        jogJointLayout.addComponent(jogJointLeftLayout);

        final HorizontalLayout jointSpeedLine = new HorizontalLayout();
        jointSpeedLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        jointSpeedLine.addComponent(jointSpeedMinusJB);
        jointSpeedMinusJB.addMouseDownConsumer(med -> curJogState = JogState.SPEED_MINUS);
        jointSpeedMinusJB.addMouseUpConsumer(med -> stopMotion());
        jointSpeedPlusJB.addMouseDownConsumer(med -> curJogState = JogState.SPEED_PLUS);
        jointSpeedPlusJB.addMouseUpConsumer(med -> stopMotion());
        jointSpeedLine.addComponent(jointSpeedPlusJB);
        jointSpeedLine.addComponent(jointSpeedJogLabel);
        jogJointRightLayout.setSpacing(true);
        jogJointRightLayout.addComponent(jointSpeedLine);

        final HorizontalLayout jointSpeedButtonLine = new HorizontalLayout();
        jointSpeedButtonLine.setSpacing(true);
        jointSpeed10Button.addClickListener(e -> sendSetSpeed(0.1));
        jointSpeedButtonLine.addComponent(jointSpeed10Button);
        jointSpeed50Button.addClickListener(e -> sendSetSpeed(0.5));
        jointSpeedButtonLine.addComponent(jointSpeed50Button);
        jointSpeed100Button.addClickListener(e -> sendSetSpeed(1.0));
        jointSpeedButtonLine.addComponent(jointSpeed100Button);
        jogJointRightLayout.addComponent(jointSpeedButtonLine);

        final HorizontalLayout jointIncrementButtonLine = new HorizontalLayout();
        jointIncrementButtonLine.setSpacing(true);
        jointIncrementButtonLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        jointJogIncPoint1Button.addClickListener(e -> {
            jointJogIncrement = BigDecimal.valueOf(0.1);
            jointJogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jointJogIncrement.doubleValue()) + " deg");
        });
        jointIncrementButtonLine.addComponent(jointJogIncPoint1Button);
        jointJogInc1Button.addClickListener(e -> {
            jointJogIncrement = BigDecimal.ONE;
            jointJogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jointJogIncrement.doubleValue()) + " deg");
        });
        jointIncrementButtonLine.addComponent(jointJogInc1Button);
        jointJogInc10Button.addClickListener(e -> {
            jointJogIncrement = BigDecimal.TEN;
            jointJogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jointJogIncrement.doubleValue()) + " deg");
        });
        jointIncrementButtonLine.addComponent(jointJogInc10Button);
        jointJogInc100Button.addClickListener(e -> {
            jointJogIncrement = BigDecimal.valueOf(100.0);
            jointJogIncLabel.setValue(" Increment: " + String.format("%+6.1f ", jointJogIncrement.doubleValue()) + " deg");
        });
        jointIncrementButtonLine.addComponent(jointJogInc100Button);
        jogJointRightLayout.addComponent(jointIncrementButtonLine);

        HorizontalLayout jointJogIncLine = new HorizontalLayout();
        jointJogIncLine.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        jointJogIncLine.setSpacing(true);
        jointJogIncLine.addComponent(jointJogIncLabel);
        jointJogIncLine.addComponent(jointJogIncProgressBar);
        jogJointRightLayout.addComponent(jointJogIncLine);

        jogJointLayout.addComponent(jogJointRightLayout);
        navLayout.addComponent(statusLabel);
        connect();
    }

    public void setupRotTable(Table rotTable) throws UnsupportedOperationException {
        rotTable.addContainerProperty("Axis", String.class, null);
        rotTable.addContainerProperty("I", Double.class, null);
        rotTable.addContainerProperty("J", Double.class, null);
        rotTable.addContainerProperty("K", Double.class, null);
        rotTable.addItem(new Object[]{"X", 1.0, 0.0, 0.0}, "X");
        rotTable.addItem(new Object[]{"Z", 0.0, 0.0, 1.0}, "Z");
        rotTable.setWidth("220px");
        rotTable.setHeight("120px");
    }

    private void updateStatusLabel() {
        StringBuffer sb = new StringBuffer("Status: ");
        sb.append("connected=");
        sb.append(socket != null && socket.isConnected());
        sb.append(STATUS_SEPERATOR);
        sb.append("program_running=");
        sb.append(running);
        sb.append(STATUS_SEPERATOR);
        sb.append("program_index=");
        sb.append(commonInfo.getProgramIndex());
        sb.append(STATUS_SEPERATOR);
        sb.append("programFile=");
        sb.append(commonInfo.getCurrentFileName());
        sb.append(STATUS_SEPERATOR);
        if (null != lastCmdIdSent) {
            sb.append("lastCmdIdSent=");
            sb.append(lastCmdIdSent);
            sb.append(STATUS_SEPERATOR);
        }
        if (stat != null) {
            CommandStatusType cmdStat = stat.getCommandStatus();
            if (null != cmdStat) {
                sb.append("statusId=");
                sb.append(cmdStat.getStatusID());
                sb.append(STATUS_SEPERATOR);
                sb.append("commandID=");
                sb.append(cmdStat.getCommandID());
                sb.append(STATUS_SEPERATOR);
                sb.append("state=");
                sb.append(cmdStat.getCommandState());
                sb.append(STATUS_SEPERATOR);
                sb.append("description=");
                sb.append(cmdStat.getStateDescription());
                sb.append(STATUS_SEPERATOR);
            }
        }
        statusLabel.setValue(sb.toString());
    }
    public static final String STATUS_SEPERATOR = ", ";

    private int disconnectCount = 0;

    public void disconnect() {
        try {
            System.out.println("disconnect() called. " + disconnectCount);
            disconnectCount++;
            if (null != updateThread) {
                updateThread.interrupt();
                try {
                    updateThread.join(50);
                } catch (InterruptedException ex) {
                }
                updateThread = null;
            }
            if (null != monitorThread) {
                monitorThread.interrupt();
                try {
                    monitorThread.join(50);
                } catch (InterruptedException ex) {
                }
                monitorThread = null;
            }
            if (null != socket) {
                socket.close();
                socket = null;
            }
            stat = null;
            running = false;
            mySyncAccess(() -> {
                connectButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                updateStateLabels(CommandStateEnumType.CRCL_ERROR);
                stateDescriptionLbl.setValue("DISCONNECTED from CRCL Server");
            });
            updateStatusLabel();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void setupPosTable(Table tbl) throws UnsupportedOperationException {
        tbl.addContainerProperty("Axis", String.class, null);
        tbl.addContainerProperty(VALUE_ITEM_PROPERTY, Double.class, null);
        tbl.addItem(new Object[]{"X", 0.0}, 0);
        tbl.addItem(new Object[]{"Y", 0.0}, 1);
        tbl.addItem(new Object[]{"Z", 0.0}, 2);
        tbl.setWidth("220px");
        tbl.setHeight("160px");
    }

    public void setupTransformTable(Table tbl) throws UnsupportedOperationException {
        tbl.addContainerProperty("Axis", String.class, null);
        tbl.addContainerProperty(VALUE_ITEM_PROPERTY, Double.class, null);
        tbl.addItem(new Object[]{"X", 0.0}, 0);
        tbl.addItem(new Object[]{"Y", 0.0}, 1);
        tbl.addItem(new Object[]{"Z", 0.0}, 2);
        tbl.addItem(new Object[]{"X I", 1.0}, 3);
        tbl.addItem(new Object[]{"X J", 0.0}, 4);
        tbl.addItem(new Object[]{"X K", 0.0}, 5);
        tbl.addItem(new Object[]{"Z I", 0.0}, 6);
        tbl.addItem(new Object[]{"Z J", 0.0}, 7);
        tbl.addItem(new Object[]{"Z K", 1.0}, 8);
        tbl.setWidth("300px");
        tbl.setHeight("450px");
    }

    public void stopMotion() {
        try {
            curJogState = JogState.NONE;
            StopMotionType stopMotionCmd = new StopMotionType();
            stopMotionCmd.setStopCondition(StopConditionEnumType.IMMEDIATE);
            BigInteger nextId = lastCmdIdSent.add(BigInteger.ONE);
            stopMotionCmd.setCommandID(nextId);
            sendCommand(stopMotionCmd);
            cmdQueue.clear();
        } catch (CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            curJogState = JogState.NONE;
        }
    }

    public void sendCommand(CRCLCommandType cmd) throws CRCLException {
        if (cmd.getCommandID() == null) {
            BigInteger nextId = lastCmdIdSent.add(BigInteger.ONE);
            cmd.setCommandID(nextId);
        }
        if (null != instance && null != socket) {
            instance.setCRCLCommand(cmd);
            socket.writeCommand(instance);
            lastCmdIdSent = instance.getCRCLCommand().getCommandID();
        }
    }

    @Override
    public void close() {
        try {
            System.out.println("close() called.");
            running = false;
            prevCommonInfo = CommonInfo.Default(new String[]{});
            disconnect();
        } finally {
            System.out.println("super.close() called.");
            super.close();
            System.out.println("close() returning.");
        }
    }

    public static double speedFraction = 0.1;

    public void sendSetSpeed(double fraction) {
        try {
            speedFraction = fraction;
            SetTransSpeedType setSpeedCmd = new SetTransSpeedType();
            TransSpeedRelativeType relSpeed = new TransSpeedRelativeType();
            relSpeed.setFraction(BigDecimal.valueOf(speedFraction));
            setSpeedCmd.setTransSpeed(relSpeed);
            sendCommand(setSpeedCmd);
            mySyncAccess(() -> {
                speedJogLabel.setValue(" Speed: " + String.format("%+6.1f ", speedFraction * 100) + " % ");
                jointSpeedJogLabel.setValue(" Speed: " + String.format("%+6.1f ", speedFraction * 100) + " % ");
            });
        } catch (CRCLException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int connectCount = -1;

    @SuppressWarnings("unchecked")
    private void connect() {
        try {
            System.out.println("connect() called. " + connectCount);
            connectCount = disconnectCount;
            if (null != socket) {
                socket.close();
            }
            if (null != updateThread) {
                updateThread.interrupt();
                try {
                    updateThread.join();
                } catch (InterruptedException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
            socket = new CRCLSocket(hostField.getValue(), Integer.valueOf(portField.getValue()));
            mySyncAccess(() -> {
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
            });
            cmdQueue.clear();
            running = false;
            updateThread = new Thread(() -> {
                pollForStatus();
            }, "pollForStatus" + this.getSession());
            updateThread.start();
            monitorThread = new Thread(() -> {
                monitorConnection();
            }, "monitorConnection" + this.getSession());
            monitorThread.start();
            this.accept(commonInfo);
            updateStatusLabel();
        } catch (IOException | CRCLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            updateStateLabels(CommandStateEnumType.CRCL_ERROR);
            stateDescriptionLbl.setValue("Connect failed : " + ex);
        }
    }

    public void sendInit() throws CRCLException {
        InitCanonType initCmd = new InitCanonType();
        BigInteger nextId = lastCmdIdSent.add(BigInteger.ONE);
        initCmd.setCommandID(nextId);
        sendCommand(initCmd);
    }

    enum JogState {
        NONE,
        X_MINUS, X_PLUS,
        Y_MINUS, Y_PLUS,
        Z_MINUS, Z_PLUS,
        ROLL_MINUS, ROLL_PLUS,
        PITCH_MINUS, PITCH_PLUS,
        YAW_MINUS, YAW_PLUS,
        JOINT_MINUS, JOINT_PLUS,
        SPEED_MINUS, SPEED_PLUS;
    }

    private int jogJointNumber = -1;
    private JogState curJogState = JogState.NONE;
    private JogState prevJogState = JogState.NONE;
    private CRCLStatusType stat = null;

    private void monitorConnection() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(300);
                long diff = System.currentTimeMillis() - lastUpdateTime;
                if (diff > 500) {
                    access(() -> {
                        updateStateLabels(CommandStateEnumType.CRCL_ERROR);
                        stateDescriptionLbl.setValue("TIMEOUT Communicating with CRCL Server : " + diff + " ms");
                    });
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pollForStatus() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(200);
                CRCLCommandInstanceType instance = new CRCLCommandInstanceType();
                GetStatusType getStatus = new GetStatusType();
                getStatus.setCommandID(BigInteger.ONE);
                instance.setCRCLCommand(getStatus);
                socket.writeCommand(instance);
                stat = socket.readStatus();
                lastUpdateTime = System.currentTimeMillis();
                updateStatusLabel();
                final int program_index = commonInfo.getProgramIndex();
                final CRCLProgramType program = commonInfo.getCurrentProgram();
                if (running
                        && null != program
                        && null != stat
                        && null != stat.getCommandStatus()
                        && program_index < program.getMiddleCommand().size()
                        && (skip_wait_for_done
                        || (stat.getCommandStatus().getCommandState() == CommandStateEnumType.CRCL_DONE
                        && (program_index < 1 || program_index + 1 <= stat.getCommandStatus().getCommandID().intValue())))) {
                    runOneProgramStep(program_index + 1);
                    cmdQueue.clear();
                }
                if (stat.getCommandStatus().getCommandID().compareTo(lastCmdIdSent) >= 0
                        && stat.getCommandStatus().getCommandState() == CommandStateEnumType.CRCL_DONE) {
                    MiddleCommandType cmd = cmdQueue.poll();
                    if (null != cmd) {
                        BigInteger id = lastCmdIdSent.add(BigInteger.ONE);
                        cmd.setCommandID(id);
                        instance.setCRCLCommand(cmd);
                        socket.writeCommand(instance);
                        lastCmdIdSent = id;
                    }
                }
                access(() -> {
                    updateUIComponents(stat);
                });
            }
        } catch (CRCLException ex) {
            if (disconnectCount <= connectCount) {
                Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
                updateStateLabels(CommandStateEnumType.CRCL_ERROR);
                stateDescriptionLbl.setValue(ex.toString());
                System.err.println("connectCount = " + connectCount);
                System.err.println("disconnectCount = " + disconnectCount);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void refresh(VaadinRequest request) {
        if (getPushConnection() != null && getPushConnection().isConnected()) {
            getPushConnection().disconnect();
        }
        super.refresh(request);
    }

    private PointType currentPoint = null;

    @SuppressWarnings("unchecked")
    private void loadPointToTable(PointType pt, Table tbl) {
        if (null == pt) {
            return;
        }
        if (null != pt.getX()) {
            Item it = tbl.getItem(0);
            if (null != it) {
                Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                if (null != ip) {
                    ip.setValue(pt.getX().doubleValue());
                }
            }
        }
        if (null != pt.getY()) {
            Item it = tbl.getItem(1);
            if (null != it) {
                Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                if (null != ip) {
                    ip.setValue(pt.getY().doubleValue());
                }
            }
        }
        if (null != pt.getZ()) {
            Item it = tbl.getItem(2);
            if (null != it) {
                Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                if (null != ip) {
                    ip.setValue(pt.getZ().doubleValue());
                }
            }
        }
    }

    private PoseType getPoseFromTable(Table tbl) {
        PoseType pose = new PoseType();

        PmCartesian cart = getPmPointFromTable(tbl);
        VectorType xAxis = new VectorType();
        xAxis.setI(new BigDecimal(tbl.getItem(3).getItemProperty(VALUE_ITEM_PROPERTY).getValue().toString()));
        xAxis.setJ(new BigDecimal(tbl.getItem(4).getItemProperty(VALUE_ITEM_PROPERTY).getValue().toString()));
        xAxis.setK(new BigDecimal(tbl.getItem(5).getItemProperty(VALUE_ITEM_PROPERTY).getValue().toString()));
        VectorType zAxis = new VectorType();
        zAxis.setI(new BigDecimal(tbl.getItem(6).getItemProperty(VALUE_ITEM_PROPERTY).getValue().toString()));
        zAxis.setJ(new BigDecimal(tbl.getItem(7).getItemProperty(VALUE_ITEM_PROPERTY).getValue().toString()));
        zAxis.setK(new BigDecimal(tbl.getItem(8).getItemProperty(VALUE_ITEM_PROPERTY).getValue().toString()));
        pose.setPoint(CRCLPosemath.toPointType(cart));
        pose.setXAxis(xAxis);
        pose.setZAxis(zAxis);
        return pose;
    }

    @SuppressWarnings("unchecked")
    private void loadPoseToTable(PoseType pose, Table tbl) {
        if (null == pose) {
            return;
        }
        PointType pt = pose.getPoint();
        loadPointToTable(pt, tbl);
        VectorType xAxis = pose.getXAxis();
        if (null != xAxis) {
            if (null != xAxis.getI()) {
                Item it = tbl.getItem(3);
                if (null != it) {
                    Property<Double> ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                    if (null != ip) {
                        ip.setValue(xAxis.getI().doubleValue());
                    }
                }
            }
            if (null != xAxis.getJ()) {
                Item it = tbl.getItem(4);
                if (null != it) {
                    Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                    if (null != ip) {
                        ip.setValue(xAxis.getJ().doubleValue());
                    }
                }
            }
            if (null != xAxis.getK()) {
                Item it = tbl.getItem(5);
                if (null != it) {
                    Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                    if (null != ip) {
                        ip.setValue(xAxis.getK().doubleValue());
                    }
                }
            }
        }
        VectorType zAxis = pose.getZAxis();
        if (null != zAxis) {
            if (null != zAxis.getI()) {
                Item it = tbl.getItem(6);
                if (null != it) {
                    Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                    if (null != ip) {
                        ip.setValue(zAxis.getI().doubleValue());
                    }
                }
            }
            if (null != zAxis.getJ()) {
                Item it = tbl.getItem(7);
                if (null != it) {
                    Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                    if (null != ip) {
                        ip.setValue(zAxis.getJ().doubleValue());
                    }
                }
            }
            if (null != zAxis.getK()) {
                Item it = tbl.getItem(8);
                if (null != it) {
                    Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                    if (null != ip) {
                        ip.setValue(zAxis.getK().doubleValue());
                    }
                }
            }
        }
    }
    private static BigDecimal jogWorldTransInc = BigDecimal.valueOf(100.0);
    private static BigDecimal jogWorldRotInc = BigDecimal.valueOf(100.0);

    private static float computeFraction(BigDecimal goal, BigDecimal current, BigDecimal inc) {
        return (float) (Math.abs(inc.doubleValue() - Math.abs(goal.doubleValue() - current.doubleValue())) / inc.doubleValue());
    }

    private PoseType currentPose = null;
    private static PoseType globalCurrentPose = null;

    private BigDecimal jointJogSpeed = BigDecimal.valueOf(5.0);

    @SuppressWarnings("unchecked")
    private void updateUIComponents(final CRCLStatusType stat) {
        try {
            CommandStatusType cst = stat.getCommandStatus();
            if (null != cst) {
                cmdIdLbl.setValue("Command ID: " + String.format("%10s", cst.getCommandID()));
                updateStateLabels(cst.getCommandState());

                String description = cst.getStateDescription();
                if (null == description) {
                    description = "";
                }
                if (cst.getCommandState() == CommandStateEnumType.CRCL_ERROR) {
                    if (description.length() > 1 && !description.equals(lastDescription)) {
                        new Notification(description).show(Page.getCurrent());
                        lastDescription = description;
                    }
                }
                if (description.length() > 40) {
                    description = description.substring(0, 36) + " ...";
                }
                stateDescriptionLbl.setValue(description);
                statusIdLbl.setValue("Status ID: " + cst.getStatusID());
            }
            PoseType pose = CRCLPosemath.getPose(stat);
            if (null != pose) {
                this.currentPose = pose;
                globalCurrentPose = this.currentPose;
                updateImages(commonInfo, commonInfo);
                PointType pt = pose.getPoint();
                if (null != pt) {
                    this.currentPoint = pt;
                    if (cst.getCommandState() == CommandStateEnumType.CRCL_DONE
                            && cst.getCommandID().compareTo(lastCmdIdSent) >= 0) {
                        prevJogState = JogState.NONE;
                        MoveToType moveToCmd = new MoveToType();
                        ActuateJointsType actuateJointsCmd = new ActuateJointsType();
                        PoseType endPos = new PoseType();
                        endPos.setPoint(new PointType());
                        endPos.setXAxis(new VectorType());
                        endPos.setZAxis(new VectorType());
                        moveToCmd.setEndPosition(endPos);
                        moveToCmd.getEndPosition().getPoint().setX(pose.getPoint().getX());
                        moveToCmd.getEndPosition().getPoint().setY(pose.getPoint().getY());
                        moveToCmd.getEndPosition().getPoint().setZ(pose.getPoint().getZ());
                        moveToCmd.getEndPosition().getXAxis().setI(pose.getXAxis().getI());
                        moveToCmd.getEndPosition().getXAxis().setJ(pose.getXAxis().getJ());
                        moveToCmd.getEndPosition().getXAxis().setK(pose.getXAxis().getK());
                        moveToCmd.getEndPosition().getZAxis().setI(pose.getZAxis().getI());
                        moveToCmd.getEndPosition().getZAxis().setJ(pose.getZAxis().getJ());
                        moveToCmd.getEndPosition().getZAxis().setK(pose.getZAxis().getK());
                        BigInteger nextId;
                        switch (curJogState) {
                            case X_MINUS:
                                moveToCmd.getEndPosition().getPoint().setX(pose.getPoint().getX().subtract(jogWorldTransInc));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                                break;

                            case X_PLUS:
                                moveToCmd.getEndPosition().getPoint().setX(pose.getPoint().getX().add(jogWorldTransInc));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                                break;

                            case Y_MINUS:
                                moveToCmd.getEndPosition().getPoint().setY(pose.getPoint().getY().subtract(jogWorldTransInc));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                                break;

                            case Y_PLUS:
                                moveToCmd.getEndPosition().getPoint().setY(pose.getPoint().getY().add(jogWorldTransInc));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                                break;

                            case Z_MINUS:
                                moveToCmd.getEndPosition().getPoint().setZ(pose.getPoint().getZ().subtract(jogWorldTransInc));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                                break;

                            case Z_PLUS:
                                moveToCmd.getEndPosition().getPoint().setZ(pose.getPoint().getZ().add(jogWorldTransInc));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                                break;

                            case ROLL_MINUS: {
                                PmRpy rpy = CRCLPosemath.toPmRpy(pose);
                                rpy.r -= worldAngleIncrementRad;
                                moveToCmd.setEndPosition(CRCLPosemath.toPoseType(CRCLPosemath.toPmCartesian(pt), rpy));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                            }
                            break;

                            case ROLL_PLUS: {
                                PmRpy rpy = CRCLPosemath.toPmRpy(pose);
                                rpy.r += worldAngleIncrementRad;
                                moveToCmd.setEndPosition(CRCLPosemath.toPoseType(CRCLPosemath.toPmCartesian(pt), rpy));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                            }
                            break;

                            case PITCH_MINUS: {
                                PmRpy rpy = CRCLPosemath.toPmRpy(pose);
                                rpy.p -= worldAngleIncrementRad;
                                moveToCmd.setEndPosition(CRCLPosemath.toPoseType(CRCLPosemath.toPmCartesian(pt), rpy));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                            }
                            break;

                            case PITCH_PLUS: {
                                PmRpy rpy = CRCLPosemath.toPmRpy(pose);
                                rpy.p += worldAngleIncrementRad;
                                moveToCmd.setEndPosition(CRCLPosemath.toPoseType(CRCLPosemath.toPmCartesian(pt), rpy));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                            }
                            break;

                            case YAW_MINUS: {
                                PmRpy rpy = CRCLPosemath.toPmRpy(pose);
                                rpy.y -= worldAngleIncrementRad;
                                moveToCmd.setEndPosition(CRCLPosemath.toPoseType(CRCLPosemath.toPmCartesian(pt), rpy));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                            }
                            break;

                            case YAW_PLUS: {
                                PmRpy rpy = CRCLPosemath.toPmRpy(pose);
                                rpy.y += worldAngleIncrementRad;
                                moveToCmd.setEndPosition(CRCLPosemath.toPoseType(CRCLPosemath.toPmCartesian(pt), rpy));
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                moveToCmd.setCommandID(nextId);
                                sendCommand(moveToCmd);
                            }
                            break;

                            case JOINT_MINUS: {
                                for (JointStatusType js : stat.getJointStatuses().getJointStatus()) {
                                    ActuateJointType actuateJoint = new ActuateJointType();
                                    actuateJoint.setJointNumber(js.getJointNumber());
                                    actuateJoint.setJointPosition(js.getJointPosition());
                                    if (jogJointNumber == js.getJointNumber().intValue() - 1) {
                                        actuateJoint.setJointPosition(js.getJointPosition().subtract(jointJogIncrement));
                                    }
                                    JointSpeedAccelType jas = new JointSpeedAccelType();
                                    jas.setJointSpeed(jointJogSpeed);
                                    actuateJoint.setJointDetails(jas);
                                    actuateJointsCmd.getActuateJoint().add(actuateJoint);
                                }
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                actuateJointsCmd.setCommandID(nextId);
                                sendCommand(actuateJointsCmd);
                            }
                            break;

                            case JOINT_PLUS: {
                                for (JointStatusType js : stat.getJointStatuses().getJointStatus()) {
                                    ActuateJointType actuateJoint = new ActuateJointType();
                                    actuateJoint.setJointNumber(js.getJointNumber());
                                    actuateJoint.setJointPosition(js.getJointPosition());
                                    if (jogJointNumber == js.getJointNumber().intValue() - 1) {
                                        actuateJoint.setJointPosition(js.getJointPosition().add(jointJogIncrement));
                                    }
                                    JointSpeedAccelType jas = new JointSpeedAccelType();
                                    jas.setJointSpeed(jointJogSpeed);
                                    actuateJoint.setJointDetails(jas);
                                    actuateJointsCmd.getActuateJoint().add(actuateJoint);
                                }
                                nextId = lastCmdIdSent.add(BigInteger.ONE);
                                actuateJointsCmd.setCommandID(nextId);
                                sendCommand(actuateJointsCmd);
                            }
                            break;

                            case SPEED_MINUS: {
                                if (speedFraction > 0.01) {
                                    speedFraction -= 0.01;
                                    SetTransSpeedType setSpeedCmd = new SetTransSpeedType();
                                    TransSpeedRelativeType relSpeed = new TransSpeedRelativeType();
                                    relSpeed.setFraction(BigDecimal.valueOf(speedFraction));
                                    setSpeedCmd.setTransSpeed(relSpeed);
                                    nextId = lastCmdIdSent.add(BigInteger.ONE);
                                    setSpeedCmd.setCommandID(nextId);
                                    sendCommand(setSpeedCmd);
                                    speedJogLabel.setValue(" Speed: " + String.format("%+6.1f ", speedFraction * 100) + " % ");
                                    jointSpeedJogLabel.setValue(" Speed: " + String.format("%+6.1f ", speedFraction * 100) + " % ");
                                }
                            }
                            break;

                            case SPEED_PLUS: {
                                if (speedFraction < .99) {
                                    speedFraction += 0.01;
                                    SetTransSpeedType setSpeedCmd = new SetTransSpeedType();
                                    TransSpeedRelativeType relSpeed = new TransSpeedRelativeType();
                                    relSpeed.setFraction(BigDecimal.valueOf(speedFraction));
                                    setSpeedCmd.setTransSpeed(relSpeed);
                                    nextId = lastCmdIdSent.add(BigInteger.ONE);
                                    setSpeedCmd.setCommandID(nextId);
                                    sendCommand(setSpeedCmd);
                                    speedJogLabel.setValue(" Speed: " + String.format("%+6.1f ", speedFraction * 100) + " % ");
                                    jointSpeedJogLabel.setValue(" Speed: " + String.format("%+6.1f ", speedFraction * 100) + " % ");
                                }
                            }
                            break;

                            case NONE:
                                break;
                        }
                        if (curJogState != JogState.NONE
                                && curJogState != JogState.SPEED_MINUS
                                && curJogState != JogState.SPEED_PLUS) {
                            prevMoveTo = moveToCmd;
                            prevActuateJoints = actuateJointsCmd;
                            prevJogState = curJogState;
                            curJogState = JogState.NONE;
                            jogIncProgressBar.setValue(0.0f);
                            jointJogIncProgressBar.setValue(0.0f);
                        }
                    } else {
                        switch (prevJogState) {
                            case X_MINUS:
                            case X_PLUS:
                                if (null != prevMoveTo) {
                                    float fraction
                                            = computeFraction(prevMoveTo.getEndPosition().getPoint().getX(), pt.getX(), jogWorldTransInc);
                                    jogIncProgressBar.setValue(fraction);
                                }
                                break;

                            case Y_MINUS:
                            case Y_PLUS:
                                if (null != prevMoveTo) {
                                    float fraction
                                            = computeFraction(prevMoveTo.getEndPosition().getPoint().getY(), pt.getY(), jogWorldTransInc);
                                    jogIncProgressBar.setValue(fraction);
                                }
                                break;

                            case Z_MINUS:
                            case Z_PLUS:
                                if (null != prevMoveTo) {
                                    float fraction
                                            = computeFraction(prevMoveTo.getEndPosition().getPoint().getZ(), pt.getZ(), jogWorldTransInc);
                                    jogIncProgressBar.setValue(fraction);
                                }
                                break;

                            case JOINT_MINUS:
                            case JOINT_PLUS:
                                if (null != prevActuateJoints) {
                                    BigDecimal curPosition = null;
                                    BigDecimal goalPosition = null;
                                    BigInteger jointNumber = BigInteger.valueOf(jogJointNumber + 1); //jogJointNumber;//prevActuateJoints.getActuateJoint().get(0).getJointNumber();
                                    for (int i = 0; i < stat.getJointStatuses().getJointStatus().size(); i++) {
                                        JointStatusType js = stat.getJointStatuses().getJointStatus().get(i);
                                        if (js.getJointNumber().compareTo(jointNumber) == 0) {
                                            curPosition = js.getJointPosition();
                                            break;
                                        }
                                    }
                                    for (int i = 0; i < prevActuateJoints.getActuateJoint().size(); i++) {
                                        ActuateJointType aj = prevActuateJoints.getActuateJoint().get(i);
                                        if (aj.getJointNumber().compareTo(jointNumber) == 0) {
                                            goalPosition = aj.getJointPosition();
                                            break;
                                        }
                                    }
                                    if (null != curPosition && goalPosition != null) {
                                        float fraction
                                                = computeFraction(goalPosition,
                                                        curPosition, jointJogIncrement);

                                        jointJogIncProgressBar.setValue(fraction);
                                    }
                                }
                                break;
                        }
                    }
                    if (null != pt.getX()) {
                        Item it = posCurrentTable.getItem(0);
                        if (null != it) {
                            Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                            if (null != ip) {
                                ip.setValue(pt.getX().doubleValue());
                            }
                        }
                        xJogLabel.setValue(" X: " + String.format("%+6.1f ", pt.getX().doubleValue()) + " mm ");
                    }
                    if (null != pt.getY()) {
                        Item it = posCurrentTable.getItem(1);
                        if (null != it) {
                            Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                            if (null != ip) {
                                ip.setValue(pt.getY().doubleValue());
                            }
                        }
                        yJogLabel.setValue(" Y: " + String.format("%+6.1f ", pt.getY().doubleValue()) + " mm ");
                    }
                    if (null != pt.getZ()) {
                        Item it = posCurrentTable.getItem(2);
                        if (null != it) {
                            Property ip = it.getItemProperty(VALUE_ITEM_PROPERTY);
                            if (null != ip) {
                                ip.setValue(pt.getZ().doubleValue());
                            }
                        }
                        zJogLabel.setValue(" Z: " + String.format("%+6.1f ", pt.getZ().doubleValue()) + " mm ");
                    }
                    PmRpy rpy = CRCLPosemath.toPmRpy(pose);
                    rollJogLabel.setValue(" Roll: " + String.format("%+6.1f ", Math.toDegrees(rpy.r)) + " degrees ");
                    pitchJogLabel.setValue(" Pitch: " + String.format("%+6.1f ", Math.toDegrees(rpy.p)) + " degrees  ");
                    yawJogLabel.setValue(" Yaw: " + String.format("%+6.1f ", Math.toDegrees(rpy.y)) + " degrees  ");
                    JointStatusesType jointStatuses = stat.getJointStatuses();
                    if (null != jointStatuses) {
                        for (JointStatusType js : jointStatuses.getJointStatus()) {
                            jogJointLabels[js.getJointNumber().intValue() - 1].setValue("Joint" + js.getJointNumber() + " " + String.format("%+6.1f ", js.getJointPosition().doubleValue()));
                        }
                    }
                }
                loadPoseToRotTable(pose, rotCurrentTable);
            }
            checkImageDirs();
        } catch (CRCLException | Property.ReadOnlyException | PmException ex) {
            Logger.getLogger(CrclClientUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStateLabels(CommandStateEnumType state) {
        stateLbl.setValue("State: " + state);
        for (CommandStateEnumType stateToRemove : CommandStateEnumType.values()) {
            if (!stateToRemove.equals(state)) {
                stateLbl.removeStyleName(stateToRemove.toString());
                statusLabel.removeStyleName(stateToRemove.toString());
                stateDescriptionLbl.removeStyleName(stateToRemove.toString());
            }
        }
        stateLbl.addStyleName(state.toString());
        statusLabel.addStyleName(state.toString());
        stateDescriptionLbl.addStyleName(state.toString());
    }

    public void loadPoseToRotTable(PoseType pose, Table rotTable) throws Property.ReadOnlyException {
        if (null != pose) {
            VectorType xAxis = pose.getXAxis();
            if (null != xAxis) {
                Item xItem = rotTable.getItem("X");
                setAxisItem(xAxis, xItem);
            }
            VectorType zAxis = pose.getZAxis();
            if (null != zAxis) {
                Item zItem = rotTable.getItem("Z");
                setAxisItem(zAxis, zItem);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void setAxisItem(VectorType xAxis, Item xItem) throws Property.ReadOnlyException {
        BigDecimal I = xAxis.getI();
        if (null != I) {
            xItem.getItemProperty("I").setValue(I.doubleValue());
        }
        BigDecimal J = xAxis.getJ();
        if (null != J) {
            xItem.getItemProperty("J").setValue(J.doubleValue());
        }
        BigDecimal K = xAxis.getK();
        if (null != K) {
            xItem.getItemProperty("K").setValue(K.doubleValue());
        }
    }

    @SuppressWarnings("unchecked")
    public void readPoseFromRotTable(PoseType pose, Table rotTable) throws Property.ReadOnlyException {
        if (null == pose) {
            throw new IllegalArgumentException("pose may not be null");
        }
        VectorType xAxis = pose.getXAxis();
        if (null == xAxis) {
            xAxis = new VectorType();
            pose.setXAxis(xAxis);
        }
        Item xItem = rotTable.getItem("X");
        xAxis.setI(new BigDecimal(xItem.getItemProperty("I").getValue().toString()));
        xAxis.setJ(new BigDecimal(xItem.getItemProperty("J").getValue().toString()));
        xAxis.setK(new BigDecimal(xItem.getItemProperty("K").getValue().toString()));
        VectorType zAxis = pose.getZAxis();
        if (null == zAxis) {
            zAxis = new VectorType();
            pose.setZAxis(zAxis);
        }
        Item zItem = rotTable.getItem("Z");
        zAxis.setI(new BigDecimal(zItem.getItemProperty("I").getValue().toString()));
        zAxis.setJ(new BigDecimal(zItem.getItemProperty("J").getValue().toString()));
        zAxis.setK(new BigDecimal(zItem.getItemProperty("K").getValue().toString()));
    }

    public void checkImageDirs() {
        try {
            if (null != tempDir) {
                File dirOverhead = overheadImageDir;
                long max_last_modified = 0;
                File max_last_modified_File = null;
                if (dirOverhead.exists()) {
                    for (File f : dirOverhead.listFiles()) {
                        long last_modified = f.lastModified();
                        if (max_last_modified < last_modified) {
                            max_last_modified = last_modified;
                            max_last_modified_File = f;
                        }
                    }
                    for (File f : dirOverhead.listFiles()) {
                        long last_modified = f.lastModified();
                        if (max_last_modified > last_modified + 2000) {
                            f.delete();
                        }
                    }
                    if (null != max_last_modified_File) {
                        final Resource res = new FileResource(max_last_modified_File);
                        CrclClientUI.this.access(() -> {
                            try {
                                overHeadImage.setSource(res);
                                overHeadImage.markAsDirty();
                            } catch (Exception e) {
                                LOGGER.log(Level.SEVERE, null, e);
                            }
                        });
                    }
                }
                File dirSide = sideImageDir;
                max_last_modified = 0;
                max_last_modified_File = null;
                if (dirSide.exists()) {
                    for (File f : dirSide.listFiles()) {
                        long last_modified = f.lastModified();
                        if (max_last_modified < last_modified) {
                            max_last_modified = last_modified;
                            max_last_modified_File = f;
                        }
                    }
                    for (File f : dirSide.listFiles()) {
                        long last_modified = f.lastModified();
                        if (max_last_modified > last_modified + 2000) {
                            f.delete();
                        }
                    }
                    if (null != max_last_modified_File) {
                        final Resource res = new FileResource(max_last_modified_File);
                        CrclClientUI.this.access(() -> {
                            try {
                                sideImage.setSource(res);
                                sideImage.markAsDirty();
                            } catch (Exception e) {
                                LOGGER.log(Level.SEVERE, null, e);
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    public static double worldAngleIncrementRad = Math.toRadians(30.0);

    private MoveToType prevMoveTo = null;
    private ActuateJointsType prevActuateJoints = null;
    private BigInteger lastCmdIdSent = BigInteger.ONE;

    @SuppressWarnings("unchecked")
    private void runOneProgramStep(final int new_program_index) throws CRCLException {
        final int program_index = commonInfo.getProgramIndex();
        final CRCLProgramType program = commonInfo.getCurrentProgram();
        instance.setCRCLCommand(program.getMiddleCommand().get(program_index));
        instance.getCRCLCommand().setCommandID(BigInteger.valueOf(program_index + 2));
        lastCmdIdSent = instance.getCRCLCommand().getCommandID();
        socket.writeCommand(instance);

        setCommonInfo(CommonInfo.withProgramIndex(commonInfo, new_program_index));
        skip_wait_for_done = false;
        if (new_program_index < program.getMiddleCommand().size() && new_program_index >= 0) {
            if (program.getMiddleCommand().get(new_program_index) instanceof crcl.base.MessageType) {
                MessageType msg = (MessageType) program.getMiddleCommand().get(new_program_index);
                final String msgString = msg.getMessage();
                mySyncAccess(() -> {
                    Notification n = new Notification("Program Paused to Show Message. Review the message to the right, then click Run to continue.");
                    n.setDelayMsec(5000);
                    n.show(Page.getCurrent());
                    if (msgString.startsWith("http:") || msgString.startsWith("https:")) {
                        browser.setSource(new ExternalResource(msgString));
                    } else {
                        browser.setSource(new StreamResource(
                                () -> new ByteArrayInputStream(("<html><body>" + msgString + "</body></html>").getBytes()),
                                System.currentTimeMillis() + "msg.html"));
                    }
                    running = false;
                    setCommonInfo(CommonInfo.withProgramIndex(commonInfo, new_program_index + 1));
                });
                running = false;
            }
        }
        mySyncAccess(() -> {
            Item item = progTable.getItem(program_index);
            if (null != item) {
                programIndexLabel.setValue("Program Index :" + program_index);
                progTable.select(program_index);
                progTable.setCurrentPageFirstItemId(program_index);
            }
        });
    }

    @Override
    public void detach() {
        System.out.println("detach() called.");
        removeProgramInfoListener(this);
        disconnect();
        super.detach();
    }

    private static final Logger LOGGER = Logger.getLogger(CrclClientUI.class
            .getName());

    @WebServlet(urlPatterns = "/*", name = "CrclClientUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CrclClientUI.class, productionMode = false)
    public static class CrclClientUIServlet extends VaadinServlet {
    }
}
