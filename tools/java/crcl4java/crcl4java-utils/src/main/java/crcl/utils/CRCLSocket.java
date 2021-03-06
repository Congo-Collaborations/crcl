/* 
 * This is public domain software, however it is preferred
 * that the following disclaimers be attached.
 * 
 * Software Copywrite/Warranty Disclaimer
 * 
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of their
 * official duties. Pursuant to title 17 Section 105 of the United States
 * Code this software is not subject to copyright protection and is in the
 * public domain. This software is experimental.
 * NIST assumes no responsibility whatsoever for its use by other
 * parties, and makes no guarantees, expressed or implied, about its
 * quality, reliability, or any other characteristic. We would appreciate
 * acknowledgment if the software is used. This software can be
 * redistributed and/or modified freely provided that any derivative works
 * bear some notice that they are derived from it, and any modified
 * versions bear some notice that they have been modified.
 * 
 */
package crcl.utils;

import crcl.base.CRCLCommandInstanceType;
import crcl.base.CRCLCommandType;
import crcl.base.CRCLProgramType;
import crcl.base.CRCLStatusType;
import crcl.base.CommandStateEnumType;
import crcl.base.CommandStatusType;
import crcl.base.GetStatusType;
import crcl.base.JointStatusType;
import crcl.base.JointStatusesType;
import crcl.base.ObjectFactory;
import crcl.base.PointType;
import crcl.base.PoseType;
import crcl.base.VectorType;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/*
 * 
 * NOTE: Comments beginning with {@literal @} or {@literal >>>} are used by Checker Framework Comments
 * beginning with {@literal @} must have no spaces in the comment or Checker will ignore
 * it.
 *
 * See http://types.cs.washington.edu/checker-framework for null pointer
 * checks. This file can be compiled without the Checker Framework, but using
 * the framework allows potential NullPointerExceptions to be found.
 */
 /*>>>
import org.checkerframework.checker.nullness.qual.*;
 */
/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov> }
 */
public class CRCLSocket implements AutoCloseable {

    public static final int DEFAULT_PORT = 64444;

    static final public UnaryOperator<String> addCRCLToState = new UnaryOperator<String>() {

        @Override
        public String apply(String t) {
            return addCRCLToStatePriv(t);
        }

    };

    public static File getCrclSchemaDirFile() {
        return crclSchemaDirFile;
    }

    /*@Nullable*/
    private static CRCLSocket utilSocket = null;

    public static void setUtilSocket(CRCLSocket newUtilSocket) {
        utilSocket = newUtilSocket;
    }

    public static CRCLSocket getUtilSocket() {
        if (null != utilSocket) {
            return utilSocket;
        }
        return (utilSocket = new CRCLSocket());
    }

    final public Socket getSocket() {
        if (null != socketChannel) {
            return socketChannel.socket();
        }
        return socket;
    }

    public static interface CRCLSocketConsumer {

        public void accept(CRCLSocket socket);
    }

    public static void
            runSimpleServer(int port, CRCLSocketConsumer consumer) {

    }

    @Override
    public String toString() {
        Socket socket = getSocket();
        return "CRCLSocket(" + ((socket == null) ? "null" : socket.getRemoteSocketAddress() + ")");
    }

    /**
     * Read a CRCL Program from a File with the given file.
     *
     * @param f File to read
     * @return CRCLProgram read from file.
     * @throws CRCLException file is not valid CRCLProgram
     * @throws IOException unable to read from file
     */
    public static CRCLProgramType readProgramFile(File f) throws CRCLException, IOException {
        return readProgramFile(f.toPath());
    }

    /**
     * Read a CRCL Program from a File with the given path.
     *
     * @param p Path to file to read
     * @return CRCLProgram read from file.
     * @throws CRCLException file is not valid CRCLProgram
     * @throws IOException unable to read from file
     */
    public static CRCLProgramType readProgramFile(Path p) throws CRCLException, IOException {
        if (null == utilSocket) {
            utilSocket = new CRCLSocket();
        }
        CRCLSocket cs = utilSocket;
        String str = new String(Files.readAllBytes(p));
        synchronized (cs) {
            return cs.stringToProgram(str, true);
        }
    }

    /**
     * Read a CRCL Program from a File with the given filename.
     *
     * @param filename name of file to read
     * @return CRCLProgram read from file.
     * @throws CRCLException file is not valid CRCLProgram
     * @throws IOException unable to read from file
     */
    public static CRCLProgramType readProgramFile(String filename) throws CRCLException, IOException {
        return readProgramFile(Paths.get(filename));
    }

    static final public UnaryOperator<String> removeCRCLFromState = new UnaryOperator<String>() {

        @Override
        public String apply(String t) {
            return removeCRCLFromStatePriv(t);
        }
    };
    final public static String statusHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<CRCLStatus\n"
            + "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "  xsi:noNamespaceSchemaLocation=\"../xmlSchemas/CRCLStatus.xsd\">";
    final public static String cmdHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<CRCLCommandInstance\n"
            + "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "  xsi:noNamespaceSchemaLocation=\"../xmlSchemas/CRCLCommandInstance.xsd\">";
    final public static String progHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<CRCLProgram\n"
            + "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "  xsi:noNamespaceSchemaLocation=\"../xmlSchemas/CRCLProgramInstance.xsd\">";
    final public static boolean DEFAULT_JAXB_FRAGMENT = true;

    /*@Nullable*/
//    private static Schema defaultCmdSchema = null;
    private static File defaultStatSchemaFiles[] = null;
    private static File defaultCmdSchemaFiles[] = null;
    private static File defaultProgramSchemaFiles[] = null;

    public static synchronized Schema filesToCmdSchema(File fa[]) throws CRCLException {
        if (null == fa) {
            return null;
        }
        fa = reorderCommandSchemaFiles(Arrays.copyOf(fa, fa.length));
        defaultCmdSchemaFiles = fa;
        return filesToSchema(fa);
    }

    public static synchronized Schema getDefaultCmdSchema() throws CRCLException {
        return filesToCmdSchema(defaultCmdSchemaFiles);
    }

    public static synchronized File[] getDefaultCmdSchemaFiles() {
        if (null == defaultCmdSchemaFiles || defaultCmdSchemaFiles.length < 1) {
            return new File[0];
        }
        return Arrays.copyOf(defaultCmdSchemaFiles, defaultCmdSchemaFiles.length);
    }

    public static synchronized File[] getDefaultProgramSchemaFiles() {
        if (null == defaultProgramSchemaFiles || defaultProgramSchemaFiles.length < 1) {
            return new File[0];
        }
        return Arrays.copyOf(defaultProgramSchemaFiles, defaultProgramSchemaFiles.length);
    }

    /*@Nullable*/
//    private static Schema defaultProgramSchema = null;
    public static synchronized Schema filesToStatSchema(File fa[]) throws CRCLException {
        if (null == fa) {
            return null;
        }
        fa = reorderStatSchemaFiles(Arrays.copyOf(fa, fa.length));
        defaultStatSchemaFiles = fa;
        return filesToSchema(fa);
    }

    public static synchronized Schema getDefaultStatSchema() throws CRCLException {
        return filesToStatSchema(defaultStatSchemaFiles);
    }

    public static synchronized Schema filesToProgramSchema(File fa[]) throws CRCLException {
        if (null == fa) {
            return null;
        }
        fa = reorderProgramSchemaFiles(Arrays.copyOf(fa, fa.length));
        defaultProgramSchemaFiles = fa;
        return filesToSchema(fa);
    }

    public static synchronized Schema getDefaultProgramSchema() throws CRCLException {
        return filesToProgramSchema(defaultProgramSchemaFiles);
    }

    /*@Nullable*/
//    private static Schema defaultStatSchema = null;

    /*@Nullable*/
    private static File commandXsdFile = null;

    public static File getCommandXsdFile() {
        return commandXsdFile;
    }

    private static final Logger LOGGER = Logger.getLogger(CRCLSocket.class.getName());
    private final static File statSchemasFile = new File(System.getProperty("user.home"),
            ".crcljava_stat_schemas.txt");
    private final static File cmdSchemasFile = new File(System.getProperty("user.home"),
            ".crcljava_cmd_schemas.txt");
    private final static File programSchemasFile = new File(System.getProperty("user.home"),
            ".crcljava_program_schemas.txt");
    final static public boolean DEFAULT_APPEND_TRAILING_ZERO = false;
    final static public boolean DEFAULT_RANDOM_PACKETING = false;
    private static final File crclSchemaDirFile;
    private static boolean resourcesCopied = false;

    static {
        File startFile = new File(System.getProperty("user.home"));
        crclSchemaDirFile = new File(startFile, "crclXmlSchemas");
    }

    private static String addCRCLToStatePriv(String in) {
        return in.replaceAll("<CommandState>Working</CommandState>", "<CommandState>WORKING</CommandState>")
                .replaceAll("<CommandState>Done</CommandState>", "<CommandState>DONE</CommandState>")
                .replaceAll("<CommandState>Error</CommandState>", "<CommandState>ERROR</CommandState>")
                .replaceAll("<CommandState>Ready</CommandState>", "<CommandState>CRCL_Ready</CommandState>");
    }

    private static String removeCRCLFromStatePriv(String in) {
        return in.replaceAll("<CommandState>WORKING</CommandState>", "<CommandState>Working</CommandState>")
                .replaceAll("<CommandState>DONE</CommandState>", "<CommandState>Done</CommandState>")
                .replaceAll("<CommandState>ERROR</CommandState>", "<CommandState>Error</CommandState>")
                .replaceAll("<CommandState>CRCL_Ready</CommandState>", "<CommandState>Ready</CommandState>");
    }

    /*@Nullable*/
    static public File generateSchema(Class<?> clss) throws CRCLException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clss);
            CRCLSchemaOutputResolver sor = new CRCLSchemaOutputResolver();
            jaxbContext.generateSchema(sor);
            return sor.getFile();
        } catch (JAXBException | IOException ex) {
            throw new CRCLException(ex);
        }
    }

    /*@Nullable*/
    public static JointStatusType getJointStatus(CRCLStatusType _status, int i) {
        BigInteger bi = BigInteger.valueOf(i);
        return getJointStatus(_status, bi);
    }

    /*@Nullable*/
    public static JointStatusType getJointStatus(CRCLStatusType _status, BigInteger bi) {
        if (null == _status) {
            return null;
        }
        JointStatusesType jsst = _status.getJointStatuses();
        if (null == jsst) {
            return null;
        }
        List<JointStatusType> jsl = jsst.getJointStatus();
        for (JointStatusType js : jsl) {
            if (js.getJointNumber().equals(bi)) {
                return js;
            }
        }
        return null;
    }

    private static String vectorToDebugString(final VectorType v) {
        return v == null ? "null" : v.toString() + " { "
                + "I=" + v.getI() + ","
                + "J=" + v.getJ() + ","
                + "K=" + v.getK() + " } ";
    }

    private static String pointToDebugString(/*@Nullable*/final PointType p) {
        return p == null ? "null" : p.toString() + " { "
                + "X=" + p.getX() + ","
                + "Y=" + p.getY() + ","
                + "Z=" + p.getZ() + " } ";
    }

    private static String poseToDebugString(/*@Nullable*/final PoseType p) {
        return p == null ? "null" : p.toString() + " { "
                + "Point=" + pointToDebugString(p.getPoint()) + ","
                + "XAxis=" + vectorToDebugString(p.getXAxis()) + ","
                + "ZAxis=" + vectorToDebugString(p.getZAxis()) + " } ";
    }

    private static String commandStatToDebugString(/*@Nullable*/final CommandStatusType c) {
        return c == null ? "null" : c.toString() + " { "
                + "CommandId=" + c.getCommandID() + ","
                + "CommandState=" + c.getCommandState() + ","
                + "StatusId=" + c.getStatusID() + " } ";
    }

    private static String jointStatusToDebugString(/*@Nullable*/final JointStatusType j) {
        return j == null ? "null" : j.toString() + " { "
                + "JointNumber=" + j.getJointNumber() + ","
                + "Position=" + j.getJointPosition() + ","
                + "Velocity=" + j.getJointVelocity() + ","
                + "TorqueOrForce=" + j.getJointTorqueOrForce()
                + " } ";
    }

    private static String jointStatusListToDebugString(final List<JointStatusType> l) {
        StringBuilder sb = new StringBuilder();
        Iterator<JointStatusType> it = l.iterator();
        while (it.hasNext()) {
            JointStatusType jst = it.next();
            sb.append(CRCLSocket.jointStatusToDebugString(jst));
            if (it.hasNext()) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    private static String jointStatusesToDebugString(final JointStatusesType j) {
        return j == null ? "null" : j.toString() + " { "
                + "JointStatus=" + jointStatusListToDebugString(j.getJointStatus()) + " } ";
    }

    public static String statToDebugString(final CRCLStatusType stat) {
        return stat == null ? "null" : stat.toString() + " { "
                + "CommandStatus=" + commandStatToDebugString(stat.getCommandStatus()) + ","
                + "Pose=" + poseToDebugString(CRCLPosemath.getPose(stat)) + ","
                + "JointStatuses=" + jointStatusesToDebugString(stat.getJointStatuses()) + " } ";
    }

    public static void clearSchemas() {
        if (null != programSchemasFile && programSchemasFile.exists()) {
            boolean deleted = programSchemasFile.delete();
            if (!deleted) {
                Logger.getLogger(CRCLSocket.class.getName()).warning(programSchemasFile + " not deleted");
            }
        }
        if (null != cmdSchemasFile && cmdSchemasFile.exists()) {
            boolean deleted = cmdSchemasFile.delete();
            if (!deleted) {
                Logger.getLogger(CRCLSocket.class.getName()).warning(cmdSchemasFile + " not deleted");
            }
        }
        if (null != statSchemasFile && statSchemasFile.exists()) {
            boolean deleted = statSchemasFile.delete();
            if (!deleted) {
                Logger.getLogger(CRCLSocket.class.getName()).warning(statSchemasFile + " not deleted");
            }
        }
        if (null != crclSchemaDirFile && crclSchemaDirFile.exists()) {
            boolean deleted = crclSchemaDirFile.delete();
            if (!deleted) {
                Logger.getLogger(CRCLSocket.class.getName()).warning(crclSchemaDirFile + " not deleted");
            }
        }
    }

    private static final File[] EMPTY_FILE_ARRAY = new File[0];

    public static File[] findSchemaFiles() {
        copySchemaResources();
        File files[] = crclSchemaDirFile.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".xsd");
            }
        });
        if (files != null) {
            return files;
        }
        return EMPTY_FILE_ARRAY;
    }

    protected static void copySchemaResources() {
        if (resourcesCopied) {
            return;
        }
        boolean made_directory = crclSchemaDirFile.mkdirs();
        Logger.getLogger(CRCLSocket.class.getName()).log(Level.FINEST, crclSchemaDirFile + "mkdirs() returned" + made_directory);
        copyResourcesToFiles(crclSchemaDirFile,
                "CRCLCommandInstance.xsd",
                "CRCLCommands.xsd",
                "CRCLProgramInstance.xsd",
                "DataPrimitives.xsd",
                "CRCLStatus.xsd");
        resourcesCopied = true;
    }

    private static String getVersion(String resourceName) {
        try {
            InputStream is
                    = CRCLStatusType.class.getClassLoader().getResourceAsStream(resourceName);
            Document doc
                    = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            Element el = doc.getDocumentElement();
            String version = el.getAttribute("version");
            return version;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static Map<String, String> getSchemaVersions(String... resourcNames) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < resourcNames.length; i++) {
            String resourcName = resourcNames[i];
            map.put(resourcName, getVersion(resourcName));
        }
        return map;
    }

    public static Map<String, String> getSchemaVersions() {
        return getSchemaVersions(
                "CRCLCommandInstance.xsd",
                "CRCLCommands.xsd",
                "CRCLProgramInstance.xsd",
                "DataPrimitives.xsd",
                "CRCLStatus.xsd");
    }

    static private void copyInputStreamToFile(InputStream is, File f) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(f)) {
            while (true) {
                byte buf[] = new byte[4096];
                int bytes_read = is.read(buf);
                if (bytes_read < 1) {
                    return;
                }
                fos.write(buf, 0, bytes_read);
            }
        }
    }

    /**
     *
     * @param dirFile the value of dirFile
     * @param names the value of names
     */
    private static void copyResourcesToFiles(File dirFile, String... names) {
        boolean made_directory = dirFile.mkdirs();
        Logger.getLogger(CRCLSocket.class.getName()).log(Level.FINEST, dirFile + "mkdirs() returned" + made_directory);
        ClassLoader classLoader = CRCLStatusType.class.getClassLoader();
        if (null != classLoader) {
            for (String name : names) {
                try {
                    File f = new File(dirFile, name);
                    if (f.exists() && (System.currentTimeMillis() - f.lastModified()) < 60000) {
                        continue;
                    }
                    InputStream resourceStream = classLoader.getResourceAsStream(name);
                    if (resourceStream != null) {
                        copyInputStreamToFile(
                                resourceStream,
                                f);

                    }
                } catch (MalformedURLException ex) {
                    LOGGER.log(Level.SEVERE, "Bad resource name " + name, ex);
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Failed to copy resource " + name, ex);
                }
            }
        }
    }

    public static List<File> reorderStatSchemaFiles(List<File> fl) {
        Collections.sort(fl, new Comparator<File>() {

            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        int statIndex = -1;
        for (int i = 0; i < fl.size(); i++) {
            if (fl.get(i).getName().contains("Status")) {
                statIndex = i;
                break;
            }
        }
        if (statIndex > 0 && statIndex < fl.size()) {
            File f = fl.remove(statIndex);
            fl.add(0, f);
        }
        return fl;
    }

    public static File[] reorderStatSchemaFiles(File fa[]) {
        if (null == fa || fa.length < 1) {
            return EMPTY_FILE_ARRAY;
        }
        List<File> fl = new ArrayList<>();
        fl.addAll(Arrays.asList(fa));
        List<File> newList = reorderStatSchemaFiles(fl);
        if (null != newList) {
            /*@Nullable*/
            File files[] = newList.toArray(EMPTY_FILE_ARRAY);
            if (null != files) {
                File newFiles[] = (/*@NonNull*/File[]) files;
                return newFiles;
            }
        }
        return EMPTY_FILE_ARRAY;
    }

    public static void main(String[] args) {
        try {
            try {
                System.out.println(CommandStateEnumType.class.getField("CRCL_WORKING").get(null));
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            String intname = int.class.getName();
            System.out.println("intname = " + intname);
            InputStream is
                    = CRCLStatusType.class.getClassLoader().getResourceAsStream("CRCLStatus.xsd");
            System.out.println("is = " + is);
//                StringBuilder sb = new StringBuilder();
//                try(BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//                    String line = null;
//                    while(null != (line = br.readLine())) {
//                        sb.append(line);
//                    }
//                }
//                String input = sb.toString();
//                System.out.println("input = " + input);
            Document doc
                    = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            System.out.println("doc = " + doc);
            Element el = doc.getDocumentElement();
            System.out.println("el = " + el);
            String version = el.getAttribute("version");
            NamedNodeMap map = doc.getAttributes();
            System.out.println("map = " + map);
//                String version = map.getNamedItem("version").getTextContent();
            System.out.println("version = " + version);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Schema filesToSchema(File fa[]) throws CRCLException {
        try {
            Source sources[] = new Source[fa.length];
            for (int i = 0; i < sources.length; i++) {
                sources[i] = new StreamSource(fa[i]);
            }
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            return schemaFactory.newSchema(sources);
        } catch (SAXException ex) {
            throw new CRCLException(ex);
        }
    }

    public static File[] readStatSchemaFiles(File schemaListFile) {
        if (schemaListFile.exists() && System.currentTimeMillis() - schemaListFile.lastModified() > 60000) {
            boolean deleted = schemaListFile.delete();
            if (!deleted) {
                Logger.getLogger(CRCLSocket.class.getName()).warning(schemaListFile + " not deleted");
            }
            saveStatSchemaFiles(schemaListFile, findSchemaFiles());
        } else if (!schemaListFile.exists()) {
            saveStatSchemaFiles(schemaListFile, findSchemaFiles());
        }
        if (!schemaListFile.exists()) {
//            showMessage("Could not find CRCL Schema xsd files.");
            return EMPTY_FILE_ARRAY;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(schemaListFile));
            String line = null;
            List<File> fl = new ArrayList<>();
            while (null != (line = br.readLine())) {
                fl.add(new File(line.trim()));
            }
            fl = reorderStatSchemaFiles(fl);
            return fl.toArray(new File[fl.size()]);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to read " + schemaListFile, ex);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (Exception exx) {
                }
            }
        }
        return EMPTY_FILE_ARRAY;
    }

    public static void saveProgramSchemaFiles(File schemaListFile, File fa[]) {
        if (null == fa) {
            return;
        }
        fa = reorderProgramSchemaFiles(fa);
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(schemaListFile));
            for (int i = 0; i < fa.length; i++) {
                ps.println(fa[i].getCanonicalPath());
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Can not read " + schemaListFile, ex);
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (Exception exx) {
                }
            }
        }
    }

    public static void saveStatSchemaFiles(File schemaListFile, File fa[]) {
        if (null == fa) {
            return;
        }
        fa = reorderStatSchemaFiles(fa);
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(schemaListFile));
            for (int i = 0; i < fa.length; i++) {
                ps.println(fa[i].getCanonicalPath());
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Can not write " + schemaListFile, ex);
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (Exception exx) {
                }
            }
        }
    }

    /*@NonNull*/
    private static <T> T[] toNonNullArray(List<T> list,
            T inArray[], /*@NonNull*/ T altArray[]) {
        if (null == list) {
            return altArray;
        }
        /*@Nullable*/ T nullableArray[] = list.toArray(inArray);
        if (nullableArray == null) {
            return altArray;
        }
        return (/*@NonNull*/T[]) nullableArray;
    }

    public static File[] reorderCommandSchemaFiles(File[] fa) {
        if (null == fa || fa.length < 1) {
            return EMPTY_FILE_ARRAY;
        }
        List<File> fl = new ArrayList<>();
        fl.addAll(Arrays.asList(fa));
        List<File> newList = reorderCommandSchemaFiles(fl);
        if (newList != null) {
            return toNonNullArray(newList, fa, EMPTY_FILE_ARRAY);
        }
        return EMPTY_FILE_ARRAY;
    }

    public static List<File> reorderCommandSchemaFiles(List<File> fl) {
        Collections.sort(fl, new Comparator<File>() {

            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        int cmdInstanceIndex = -1;
        for (int i = 0; i < fl.size(); i++) {
            if (fl.get(i).getName().contains("CommandInstance")) {
                cmdInstanceIndex = i;
                break;
            }
        }
        if (cmdInstanceIndex > 0 && cmdInstanceIndex < fl.size()) {
            File f = fl.remove(cmdInstanceIndex);
            CRCLSocket.commandXsdFile = f;
            fl.add(0, f);
        }
        return fl;
    }

    public static File[] reorderProgramSchemaFiles(File[] fa) {
        if (null == fa) {
            return EMPTY_FILE_ARRAY;
        }
        List<File> fl = new ArrayList<>();
        fl.addAll(Arrays.asList(fa));
        return toNonNullArray(fl, fa, EMPTY_FILE_ARRAY);
    }

    public static List<File> reorderProgramSchemaFiles(List<File> fl) {
        Collections.sort(fl, new Comparator<File>() {

            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        int cmdInstanceIndex = -1;
        for (int i = 0; i < fl.size(); i++) {
            if (fl.get(i).getName().contains("ProgramInstance")) {
                cmdInstanceIndex = i;
                break;
            }
        }
        if (cmdInstanceIndex > 0 && cmdInstanceIndex < fl.size()) {
            File f = fl.remove(cmdInstanceIndex);
            CRCLSocket.commandXsdFile = f;
            fl.add(0, f);
        }
        return fl;
    }

    public static File[] readCmdSchemaFiles(File schemasListFile) {
        if (schemasListFile.exists() && System.currentTimeMillis() - schemasListFile.lastModified() > 60000) {
            boolean deleted = schemasListFile.delete();
            if (!deleted) {
                Logger.getLogger(CRCLSocket.class.getName()).warning(schemasListFile + " not deleted");
            }
            saveCmdSchemaFiles(schemasListFile, findSchemaFiles());
        } else if (!schemasListFile.exists()) {
            saveCmdSchemaFiles(schemasListFile, findSchemaFiles());
        }
        if (!schemasListFile.exists()) {
//            showMessage("Could not find CRCL Schema xsd files.");
            return EMPTY_FILE_ARRAY;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(schemasListFile));
            String line = null;
            List<File> fl = new ArrayList<>();
            while (null != (line = br.readLine())) {
                fl.add(new File(line.trim()));
            }
            fl = reorderCommandSchemaFiles(fl);
            return toNonNullArray(fl, EMPTY_FILE_ARRAY, EMPTY_FILE_ARRAY);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Can not read " + schemasListFile, ex);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (Exception exx) {
                }
            }
        }
        return EMPTY_FILE_ARRAY;
    }

    public static File[] readProgramSchemaFiles(File schemasListFile) {
        if (!schemasListFile.exists()) {
            saveProgramSchemaFiles(schemasListFile, findSchemaFiles());
        }
        if (!schemasListFile.exists()) {
//            showMessage("Could not find CRCL Schema xsd files.");
            return EMPTY_FILE_ARRAY;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(schemasListFile));
            String line = null;
            List<File> fl = new ArrayList<>();
            while (null != (line = br.readLine())) {
                fl.add(new File(line.trim()));
            }
            fl = reorderProgramSchemaFiles(fl);
            return fl.toArray(new File[fl.size()]);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Can not read " + schemasListFile, ex);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (Exception exx) {
                }
            }
        }
        return EMPTY_FILE_ARRAY;
    }

    public static void saveCmdSchemaFiles(File schemasListFile, File fa[]) {
        if (null == fa) {
            return;
        }
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(schemasListFile));
            for (int i = 0; i < fa.length; i++) {
                ps.println(fa[i].getCanonicalPath());
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Can not write " + schemasListFile, ex);
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (Exception exx) {
                }
            }
        }
    }

    private SocketChannel socketChannel;

    /**
     * Get the value of socketChannel
     *
     * @return the value of socketChannel
     */
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    /**
     * Set the value of socketChannel
     *
     * @param socketChannel new value of socketChannel
     */
    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    private UnaryOperator<String> statusStringInputFilter = STRING_IDENTITY_OPERATOR;
    private UnaryOperator<String> statusStringOutputFilter = STRING_IDENTITY_OPERATOR;
    private boolean jaxbFragment = DEFAULT_JAXB_FRAGMENT;
    /*@Nullable*/ private final Socket socket;

    /*@Nullable*/ private String lastStatusString = null;
    /*@Nullable*/ private String lastCommandString = null;
    /*@Nullable*/ private String lastProgramString = null;

    /*@Nullable*/ private Schema cmdSchema = null;
    /*@Nullable*/ private Schema programSchema = null;
    /*@Nullable*/ private Schema statSchema = null;
    /*@NonNull*/ protected final Marshaller m_cmd;
    /*@NonNull*/ protected final Unmarshaller u_cmd;
    /*@NonNull*/ protected final Marshaller m_prog;
    /*@NonNull*/ protected final Unmarshaller u_prog;
    /*@NonNull*/ protected final Marshaller m_stat;
    /*@NonNull*/ protected final Unmarshaller u_stat;
    private String readInProgressString = "";

    /*@Nullable*/ private BufferedInputStream bufferedInputStream = null;
    private boolean useBufferedInputStream = true;
    /*@Nullable*/ private SAXSource exiCommandInSaxSource = null;
    /*@Nullable*/ private SAXSource exiStatusInSaxSource = null;
    private final ObjectFactory objectFactory
            = new ObjectFactory();
    public boolean appendTrailingZero = DEFAULT_APPEND_TRAILING_ZERO;
    public boolean randomPacketing = DEFAULT_RANDOM_PACKETING;
    /*@Nullable*/ private Random random = null;
    public int rand_seed = 12345;
    /*@Nullable*/ private String last_xml_version_header = null;
    /*@Nullable*/ private String last_orig_first_tag = null;
    private boolean replaceHeader;

    public CRCLSocket() {
        this.socket = null;
    }

    // Instance initializer called by all constructors , but not seperately callable.
    {
        try {
            ClassLoader cl = crcl.base.ObjectFactory.class.getClassLoader();
            if (null == cl) {
                throw new RuntimeException("crcl.base.ObjectFactory.class.getClassLoader() returned null");
            }
            final /*@NonNull*/ ClassLoader nnCl = (/*@NonNull*/ClassLoader) cl;
            JAXBContext context = JAXBContext.newInstance("crcl.base", nnCl);
            assert null != context : "@AssumeAssertion(nullness)";
            u_cmd = context.createUnmarshaller();
            m_cmd = context.createMarshaller();
//            cmdSchema = getDefaultCmdSchema();
//            if (null != cmdSchema) {
//                u_cmd.setSchema(cmdSchema);
//                m_cmd.setSchema(cmdSchema);
//            }
            u_stat = context.createUnmarshaller();
            m_stat = context.createMarshaller();
//            statSchema = getDefaultStatSchema();
//            if (null != statSchema) {
//                u_stat.setSchema(statSchema);
//                m_stat.setSchema(statSchema);
//            }
            u_prog = context.createUnmarshaller();
            m_prog = context.createMarshaller();
//            programSchema = getDefaultProgramSchema();
//            if (null != programSchema) {
//                u_prog.setSchema(programSchema);
//                m_prog.setSchema(programSchema);
//            }

            bufferedInputStream = null;
        } catch (JAXBException ex) {
            LOGGER.log(Level.SEVERE, "", ex);
            throw new RuntimeException(ex);
        }
    }

    public CRCLSocket(/*@Nullable*/Socket socket) {
        this.socket = socket;
    }

    public CRCLSocket(/*@Nullable*/SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.socket = socketChannel.socket();
    }

    public CRCLSocket(String hostname, int port) throws CRCLException, IOException {
        this(new Socket(hostname, port));
    }

    public boolean isConnected() {
        return getSocket().isConnected();
    }

    public int getLocalPort() {
        if (null != socketChannel) {
            return socketChannel.socket().getLocalPort();
        }
        if (null == this.socket) {
            return -1;
        }
        return this.socket.getLocalPort();
    }

    public int getPort() {
        if (null != socketChannel) {
            return socketChannel.socket().getLocalPort();
        }
        if (null == this.socket) {
            return -1;
        }
        return this.socket.getPort();
    }

    /*@Nullable*/
    public InetAddress getInetAddress() {
        if (null != socketChannel) {
            return socketChannel.socket().getInetAddress();
        }
        if (null == this.socket) {
            return null;
        }
        return this.socket.getInetAddress();
    }

    /**
     * Get the value of statusStringInputFilter
     *
     * @return the value of statusStringInputFilter
     */
    public UnaryOperator<String> getStatusStringInputFilter() {
        return statusStringInputFilter;
    }

    /**
     * Set the value of statusStringInputFilter
     *
     * @param statusStringInputFilter new value of statusStringInputFilter
     */
    public void setStatusStringInputFilter(UnaryOperator<String> statusStringInputFilter) {
        this.statusStringInputFilter = statusStringInputFilter;
    }

    /**
     * Get the value of statusStringOutputFilter
     *
     * @return the value of statusStringOutputFilter
     */
    public UnaryOperator<String> getStatusStringOutputFilter() {
        return statusStringOutputFilter;
    }

    /**
     * Set the value of statusStringOutputFilter
     *
     * @param statusStringOutputFilter new value of statusStringOutputFilter
     */
    public void setStatusStringOutputFilter(UnaryOperator<String> statusStringOutputFilter) {
        this.statusStringOutputFilter = statusStringOutputFilter;
    }

//    public static String DEFAULT_STATUS_NO_NAMESPACE_JAXB_SCHEMA_LOCATION = "../xmlSchemas/CRCLStatus.xsd";
//    public static String DEFAULT_COMMAND_NO_NAMESPACE_JAXB_SCHEMA_LOCATION = ".../xmlSchemas/CRCLCommandInstance.xsd";
//    public static String DEFAULT_PROGRAM_NO_NAMESPACE_JAXB_SCHEMA_LOCATION = "../xmlSchemas/CRCLProgramInstance.xsd";
//    public static String DEFAULT_JAXB_SCHEMA_LOCATION = "http://www.w3.org/2001/XMLSchema-instance";
    /**
     * Get the value of jaxbFragment
     *
     * @return the value of jaxbFragment
     */
    public boolean isJaxbFragment() {
        return jaxbFragment;
    }

    /**
     * Set the value of jaxbFragment
     *
     * @param jaxbFragment new value of jaxbFragment
     */
    public void setJaxbFragment(boolean jaxbFragment) {
        this.jaxbFragment = jaxbFragment;
    }

    /**
     * Get the value of lastStatusString
     *
     * @return the value of lastStatusString or null if no status has been
     * read/converted.
     */
    public /*@Nullable*/ String getLastStatusString() {
        return lastStatusString;
    }

    /**
     * Get the value of lastCommandString
     *
     * @return the value of lastCommandString or null if no command has been
     * read/converted.
     */
    public /*@Nullable*/ String getLastCommandString() {
        return lastCommandString;
    }

    /**
     * Get the value of cmdSchema
     *
     * @return the value of cmdSchema
     */
    public /*@Nullable*/ Schema getProgramSchema() {
        return programSchema;
    }

    /**
     * Set the value of cmdSchema
     *
     * @param programSchema new value of cmdSchema
     */
    public void setProgramSchema(/*@Nullable*/Schema programSchema) {
        this.programSchema = programSchema;
        if (null != programSchema) {
            if (null != this.m_prog) {
                setUnmarshallerSchema(u_prog, programSchema);
            }
            if (null != this.u_prog) {
                this.u_prog.setSchema(programSchema);
            }
        }
    }

    /**
     * Get the value of cmdSchema
     *
     * @return the value of cmdSchema
     */
    public /*@Nullable*/ Schema getCmdSchema() {
        return cmdSchema;
    }

    /**
     * Set the value of cmdSchema
     *
     * @param cmdSchema new value of cmdSchema
     */
    public void setCmdSchema(Schema cmdSchema) {
        this.cmdSchema = cmdSchema;
        if (null != cmdSchema) {
            if (null != m_cmd) {
                m_cmd.setSchema(cmdSchema);
            }
            if (null != u_cmd) {
                u_cmd.setSchema(cmdSchema);
            }
        }
    }

    /**
     * Get the value of statSchema
     *
     * @return the value of statSchema
     */
    public /*@Nullable*/ Schema getStatSchema() {
        return statSchema;
    }

    /**
     * Set the value of statSchema
     *
     * @param statSchema new value of statSchema
     */
    public void setStatSchema(Schema statSchema) {
        this.statSchema = statSchema;
        if (null != statSchema) {
            if (null != m_stat) {
                m_stat.setSchema(statSchema);
            }
            if (null != u_stat) {
                u_stat.setSchema(statSchema);
            }
        }
    }

    @Override
    public void close() throws IOException {

        exiCommandInSaxSource = null;
        if (null != bufferedInputStream) {
            bufferedInputStream.close();
            bufferedInputStream = null;
        }
        if (null != socketChannel) {
            socketChannel.close();
        }

        if (null == socket) {
            return;
        }

        try {
            socket.shutdownInput();
        } catch (IOException iOException) {
        }

        try {
            socket.shutdownOutput();
        } catch (IOException iOException) {
        }

        try {
            socket.close();
        } catch (IOException iOException) {
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }

    public String getReadInProgressString() {
        return this.readInProgressString;
    }

//    public String readUntilEndTagOld(final String tag, final InputStream is) throws IOException {
//        byte ba1[] = new byte[1];
//        String rips = "";
//        final String endTagStartString = "</" + tag;
//        final String startTag = "<" + tag;
//        boolean insideStartTag = false;
//        boolean startTag_found = false;
//        boolean endTag_started = false;
//        String str = "";
//        String skipped_str = "";
//        StringBuilder skipped_str_sb = new StringBuilder();
//        synchronized (is) {
//            while (ba1[0] != '>' || !endTag_started && !Thread.currentThread().isInterrupted()) {
//                int bytes_read = is.read(ba1);
//                if (bytes_read != 1) {
//                    Level lvl = rips.length() > 0 ? Level.SEVERE : Level.FINE;
//                    final int brF = bytes_read;
//                    final String ripsF = rips;
//                    LOGGER.log(lvl, "CRCLSocket.readUntilEndTag({0}): read returned {1} before end of tag was found. str = {2}", new Object[]{tag, brF, ripsF});
//                    throw new SocketException("socket closed after read returned:" + bytes_read);
//                }
//                if (ba1[0] == 0) {
//                    continue;
//                }
//                rips += new String(ba1);
//                if (ba1[0] == '>' && !endTag_started && insideStartTag) {
//                    if (rips.endsWith("/>")) {
//                        break;
//                    }
//                    insideStartTag = false;
//                }
//                this.readInProgressString = rips;
//                if (!startTag_found) {
//                    while (rips.length() > 0
//                            && !rips.startsWith(startTag.substring(0, Math.min(rips.length(), startTag.length())))) {
////                        skipped_str += rips.substring(0, 1);
//                        if(LOGGER.isLoggable(Level.FINER)) {
//                            skipped_str_sb.append(rips.substring(0, 1));
//                        }
//                        rips = rips.substring(1);
//                        this.readInProgressString = rips;
//                    }
//                    if (rips.startsWith(startTag)) {
//                        startTag_found = true;
//                        insideStartTag = true;
//                    }
//                } else if (!endTag_started) {
//                    endTag_started = rips.endsWith(endTagStartString);
//                }
//            }
//            str = rips;
//            rips = "";
//            this.readInProgressString = rips;
//        }
//        final String threadName = Thread.currentThread().getName();
//        skipped_str = skipped_str_sb.toString();
//        final String skipped_str_f = skipped_str;
//        LOGGER.log(Level.FINER, "readUntilEndTag({0}) called with skipped_str=\"{1}\"  from Thread: {2}", new Object[]{tag, skipped_str_f, threadName});
//        return str;
//    }
    public String readUntilEndTag(final String tag, final InputStream is) throws IOException {
        String rips = "";
        final String endTagStartString = "</" + tag;
        final String startTag = "<" + tag;
        StringBuilder sb = new StringBuilder();
        StringBuilder skipped = new StringBuilder();
        synchronized (is) {
            byte lastbyte = 0;
            lastbyte = readUntilMatch(startTag, is, skipped);
            sb.append(startTag);
            lastbyte = readUntilMatch(">", is, sb);
            sb.append(">");
            if (lastbyte != '/') {
                readUntilMatch(endTagStartString, is, sb);
                sb.append(endTagStartString);
                readUntilMatch(">", is, sb);
                sb.append(">");
            }
        }

        final String str = sb.toString();
        return str;
    }

    private byte readUntilMatch(String toMatch, final InputStream is, StringBuilder sb) throws IOException {
        byte[] tagBytes;
        byte[] tagBytesPrep;
        tagBytes = toMatch.getBytes();
        tagBytesPrep = new byte[tagBytes.length];
        int tagBytesPrepped = 0;
        byte lastbyte = 0;
        while (tagBytesPrepped < tagBytesPrep.length) {
            int readret = is.read(tagBytesPrep, tagBytesPrepped, (tagBytesPrep.length - tagBytesPrepped));
            if (readret > 0) {
                tagBytesPrepped += readret;
                for (int i = 0; i < tagBytesPrepped; i++) {
                    if (tagBytesPrep[i] != tagBytes[i]) {
                        while (i < tagBytesPrepped - 1 && tagBytesPrep[i + 1] != tagBytes[0]) {
                            i++;
                        }
                        sb.append(new String(tagBytesPrep, 0, (i + 1)));
                        lastbyte = tagBytesPrep[i];
                        if (i < tagBytesPrepped - 1) {
                            System.arraycopy(tagBytesPrep, i + 1, tagBytesPrep, 0, tagBytesPrepped - i - 1);
                            tagBytesPrepped -= (i + 1);
                        } else {
                            tagBytesPrepped = 0;
                        }
                        i = 0;
                        break;
                    }
                }
            } else if (readret < 0) {
                throw new EOFException("is(" + is + ").read(tagBytesPrep(" + Arrays.toString(tagBytesPrep) + "), tagBytesPrepped(" + tagBytesPrepped + "), (" + (tagBytesPrep.length - tagBytesPrepped) + "))) returned " + readret + ": sb=" + sb);
            }
        }
        return lastbyte;
    }

    @SuppressWarnings("nullness")
    private void setUnmarshallerSchema(/*@Nullable*/Unmarshaller u, /*@Nullable*/ Schema schema) {
        // we need to be able to set the schema to null but since 
        // the setSchema method is not annotated there is no way 
        // for a nullness checker to know this is safe.
        if (null != u) {
            u.setSchema(schema);
        }
    }

    private String unparsedCommandString = "";

    /**
     * Parse a string that may contain multiple or partial XML
     * CRCLCommandInstances, and return a list of those commands. Partial
     * commands are saved and used as a prefix to the string passed in the next
     * call to this function.
     *
     * @param s String that may contain multiple XML CRCLCommandInstances
     * @return list of CRCLCommandInstances from strings so far.
     * @throws CRCLException if the string is invalid
     */
    public List<CRCLCommandInstanceType> parseMultiCommandString(String s) throws CRCLException {
        return parseMultiCommandString(s, false);
    }

    /**
     * Parse a string that may contain multiple or partial XML
     * CRCLCommandInstances, and return a list of those commands. Partial
     * commands are saved and used as a prefix to the string passed in the next
     * call to this function.
     *
     * @param s String that may contain multiple XML CRCLCommandInstances
     * @param validate validate each instance against the schema.
     * @return list of CRCLCommandInstances from strings so far.
     * @throws CRCLException if the string is invalid
     */
    public List<CRCLCommandInstanceType> parseMultiCommandString(String s, boolean validate) throws CRCLException {
        unparsedCommandString += s;
        int index = -1;
        final String endtag = "</CRCLCommandInstance>";
        List<CRCLCommandInstanceType> list = new ArrayList<>();
        while (0 < (index = unparsedCommandString.indexOf(endtag))) {
            int endindex = index + endtag.length();
            String s0 = unparsedCommandString.substring(0, endindex);
            CRCLCommandInstanceType instance = stringToCommand(s0, validate);
            unparsedCommandString = unparsedCommandString.substring(endindex);
            list.add(instance);
        }
        return list;
    }

    private String unparsedStatusString = "";

    /**
     * Parse a string that may contain multiple or partial XML CRCLStatusTypes,
     * and return a list of those status messages. Partial commands are saved
     * and used as a prefix to the string passed in the next call to this
     * function.
     *
     * @param s String that may contain multiple XML CRCLStatusTypes
     * @return list of CRCLStatusTypes from strings so far.
     * @throws CRCLException if the string is invalid
     */
    public List<CRCLStatusType> parseMultiStatusString(String s) throws CRCLException {
        return parseMultiStatusString(s, false);
    }

    /**
     * Parse a string that may contain multiple or partial XML CRCLStatusTypes,
     * and return a list of those status messages. Partial commands are saved
     * and used as a prefix to the string passed in the next call to this
     * function.
     *
     * @param s String that may contain multiple XML CRCLStatusTypes
     * @param validate validate each instance against the schema.
     * @return list of CRCLStatusTypes from strings so far.
     * @throws CRCLException if the string is invalid
     */
    public List<CRCLStatusType> parseMultiStatusString(String s, boolean validate) throws CRCLException {
        unparsedStatusString += s;
        int index = -1;
        final String endtag = "</CRCLStatus>";
        List<CRCLStatusType> list = new ArrayList<>();
        while (0 < (index = unparsedStatusString.indexOf(endtag))) {
            int endindex = index + endtag.length();
            String s0 = unparsedStatusString.substring(0, endindex);
            CRCLStatusType instance = stringToStatus(s0, validate);
            unparsedStatusString = unparsedStatusString.substring(endindex);
            list.add(instance);
        }
        return list;
    }

    public CRCLCommandInstanceType stringToCommand(String str, boolean validate) throws CRCLException {
        this.checkCommandSchema(validate);

        synchronized (u_cmd) {
            try {
                this.lastCommandString = str;
                setUnmarshallerSchema(u_cmd, validate ? cmdSchema : null);
                JAXBElement el = (JAXBElement) u_cmd.unmarshal(new StringReader(str));
                CRCLCommandInstanceType instance
                        = (CRCLCommandInstanceType) el.getValue();
                return instance;
            } catch (JAXBException ex) {
                throw new CRCLException(ex);
            }
        }

    }

    public CRCLCommandInstanceType readCommandFromStream(final InputStream is, boolean validate) throws JAXBException {

        synchronized (u_cmd) {
            setUnmarshallerSchema(u_cmd, validate ? cmdSchema : null);
            JAXBElement el = (JAXBElement) u_cmd.unmarshal(is);
            CRCLCommandInstanceType instance
                    = (CRCLCommandInstanceType) el.getValue();
            return instance;
        }
    }

    public CRCLProgramType stringToProgram(String str, boolean validate) throws CRCLException {
        this.checkProgramSchema(validate);
        try {
            synchronized (u_prog) {
                this.lastProgramString = str;
                setUnmarshallerSchema(u_prog, validate ? programSchema : null);
                JAXBElement el = (JAXBElement) u_prog.unmarshal(new StringReader(str));
                CRCLProgramType prog
                        = (CRCLProgramType) el.getValue();
                return prog;
            }
        } catch (Exception ex) {
            throw new CRCLException(ex);
        }
    }

    public CRCLCommandInstanceType readCommand() throws CRCLException {
        try {
            return readCommand(false);
        } catch (IOException ex) {
            throw new CRCLException(ex);
        }
    }

    public CRCLCommandInstanceType readCommand(boolean validate) throws CRCLException, IOException {
        final String threadName = Thread.currentThread().getName();
        final String str = this.readUntilEndTag("CRCLCommandInstance", getBufferedInputStream());
        if (null == str) {
            throw new EOFException("readUntilEndTag returned null");
        }
        CRCLCommandInstanceType cmd = stringToCommand(str, validate);
        final CRCLCommandType cc = cmd.getCRCLCommand();
        final Level loglevel = (cc instanceof GetStatusType) ? Level.FINER : Level.FINE;
        LOGGER.log(loglevel, "readCommand() returning {0} ID={1}str={2}  called from Thread: {3}", new Object[]{cc, cc.getCommandID(), str, threadName});
        this.lastCommandString = str;
        return cmd;
    }

    public List<CRCLCommandInstanceType> checkForCommands(boolean validate) throws CRCLException {
        try {
            if (null != socketChannel) {
                ByteBuffer bb = ByteBuffer.allocate(4096);
                int bytesread = socketChannel.read(bb);
                String string = new String(bb.array(), 0, bytesread);
                return parseMultiCommandString(string, validate);
            }
            int bytesavail = this.socket.getInputStream().available();
            if (bytesavail > 0) {
                byte buf[] = new byte[bytesavail];
                int bytes_read = this.socket.getInputStream().read(buf);
                if (bytes_read > 0) {
                    String s = new String(buf, 0, bytes_read);
                    return parseMultiCommandString(s, validate);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.emptyList();
    }

    public List<CRCLStatusType> checkForStatusMessages(boolean validate) throws CRCLException {
        try {
            if (null != socketChannel) {
                ByteBuffer bb = ByteBuffer.allocate(4096);
                int bytesread = socketChannel.read(bb);
                String string = new String(bb.array(), 0, bytesread);
                return parseMultiStatusString(string, validate);
            }
            int bytesavail = this.socket.getInputStream().available();
            if (bytesavail > 0) {
                byte buf[] = new byte[bytesavail];
                int bytes_read = this.socket.getInputStream().read(buf);
                if (bytes_read > 0) {
                    String s = new String(buf, 0, bytes_read);
                    return parseMultiStatusString(s, validate);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.emptyList();
    }

    public CRCLStatusType stringToStatus(String str, boolean validate) throws CRCLException {
        this.checkStatusSchema(validate);
        synchronized (u_stat) {
            try {
                if (this.statusStringInputFilter != null) {
                    str = this.statusStringInputFilter.apply(str);
                }
                lastStatusString = str;
                setUnmarshallerSchema(u_stat, validate ? statSchema : null);
                JAXBElement el = (JAXBElement) u_stat.unmarshal(new StringReader(str));
                CRCLStatusType instance
                        = (CRCLStatusType) el.getValue();
                return instance;
            } catch (JAXBException ex) {
                throw new CRCLException(ex);
            }
        }
    }

    public CRCLStatusType readStatusFromStream(final InputStream is, boolean validate) throws JAXBException {
        synchronized (u_stat) {
            setUnmarshallerSchema(u_stat, validate ? statSchema : null);
            JAXBElement el = (JAXBElement) u_stat.unmarshal(is);
            CRCLStatusType instance
                    = (CRCLStatusType) el.getValue();
            return instance;
        }
    }

//    static private <T> /*@NonNull*/ T ToNonNull(/*@Nullable*/T t, String msg) {
//        if (t == null) {
//            throw new RuntimeException(msg);
//        }
//        assert t != null : "@AssumeAssertion(nullness)";
//        return (/*@NonNull*/T) t;
//    }
    public CRCLStatusType
            readStatusFromSaxSource(SAXSource saxSource) throws JAXBException {
        synchronized (u_stat) {
            JAXBElement<CRCLStatusType> el
                    = u_stat.unmarshal(saxSource, CRCLStatusType.class
                    );
            return el.getValue();
        }
    }

    protected InputStream getBufferedInputStream() throws IOException {

        if (null != socketChannel) {
            if (!socketChannel.isBlocking() && socketChannel.isRegistered()) {
                throw new IllegalStateException("Can not use SocketChannel inputStream when set non-blocking and registered with selector. It must be deregistered with the SelectionKey's cancel method.");
            }
            socketChannel = (SocketChannel) socketChannel.configureBlocking(true);
        }
        if (null != bufferedInputStream) {
            return bufferedInputStream;
        }
        Socket socket = getSocket();
        if (null == socket) {
            throw new IllegalStateException("socket is null");
        }
        assert null != socket : "@AssumeAssertion(nullness)";
        if (!useBufferedInputStream) {
            return socket.getInputStream();
        }
        BufferedInputStream newBufferedInputStream = new BufferedInputStream(socket.getInputStream());
        bufferedInputStream = newBufferedInputStream;
        return newBufferedInputStream;
    }

    public CRCLStatusType readStatus() throws CRCLException {
        return readStatus(false);
    }

    public CRCLStatusType readStatus(boolean validate)
            throws CRCLException {
        try {
            this.lastStatusString = this.readUntilEndTag("CRCLStatus", getBufferedInputStream());
            if (null == this.lastStatusString) {
                throw new EOFException("readUntilEndTag returned null");
            }
            return stringToStatus(this.lastStatusString, validate);
        } catch (IOException ex) {
            throw new CRCLException(ex);
        }
    }

    public String commandToString(CRCLCommandType cmd, boolean validate) {
        try {
            if (cmd instanceof CrclCommandWrapper) {
                CrclCommandWrapper wrapper = (CrclCommandWrapper) cmd;
                cmd = wrapper.getWrappedCommand();
            }
            if (null == cmd.getCommandID()) {
                throw new IllegalArgumentException("cmd.getCommandID() must not be null. Use setCommandID(BigInteger.valueOf(...)).");
            }
            CRCLCommandInstanceType instance = new CRCLCommandInstanceType();
            instance.setCRCLCommand(cmd);
            String str = removeHeader(this.commandToString(instance, validate));
            if (str.endsWith("</CRCLCommandInstance>")) {
                str = str.substring(0, str.length() - "</CRCLCommandInstance>".length());
            }
            return str;
        } catch (Exception ex) {
        }
        return "";
    }

    private int checkCommandSchemaCount = 0;

    private void checkCommandSchema(boolean validate) throws CRCLException {
        checkCommandSchemaCount++;
        if (checkCommandSchemaCount > 100) {
            checkCommandSchemaCount = 0;
            cmdSchema = null;
        }
        if (validate) {
            if (null == cmdSchema) {
                Schema defaultCmdSchema = CRCLSocket.getDefaultCmdSchema();
                if (null == defaultCmdSchema) {
                    File fa[] = CRCLSocket.readCmdSchemaFiles(cmdSchemasFile);
                    if (null != fa) {
                        setCmdSchema(CRCLSocket.filesToCmdSchema(fa));
                    }
                } else {
                    setCmdSchema(defaultCmdSchema);
                }
            }
        }
    }

    private int checkStatusSchemaCount = 0;

    private void checkStatusSchema(boolean validate) throws CRCLException {
        checkStatusSchemaCount++;
        if (checkStatusSchemaCount > 100) {
            checkStatusSchemaCount = 0;
            statSchema = null;
        }
        if (validate) {
            if (null == statSchema) {
                Schema defaultStatSchema = CRCLSocket.getDefaultStatSchema();
                if (null == defaultStatSchema) {
                    File fa[] = CRCLSocket.readStatSchemaFiles(statSchemasFile);
                    if (null != fa) {
                        setStatSchema(CRCLSocket.filesToStatSchema(fa));
                    }
                } else {
                    setStatSchema(defaultStatSchema);
                }
            }
        }
    }

    private int checkProgramSchemaCount = 0;

    private void checkProgramSchema(boolean validate) throws CRCLException {
        checkProgramSchemaCount++;
        if (checkProgramSchemaCount > 100) {
            checkProgramSchemaCount = 0;
            programSchema = null;
        }
        if (validate) {
            if (null == programSchema) {
                Schema defaultProgramSchema = CRCLSocket.getDefaultProgramSchema();
                if (null == defaultProgramSchema) {
                    File fa[] = CRCLSocket.readProgramSchemaFiles(programSchemasFile);
                    if (null != fa) {
                        setProgramSchema(CRCLSocket.filesToProgramSchema(fa));
                    }
                } else {
                    setProgramSchema(defaultProgramSchema);
                }
            }
        }
    }

    @SuppressWarnings("nullness")
    private void setMarshallerSchema(Marshaller marshaller, /*@Nullable*/ Schema schema) {
        if (null != marshaller) {
            marshaller.setSchema(schema);
        }
    }

    public String commandToString(CRCLCommandInstanceType cmd, boolean validate) throws CRCLException {
        if (null == cmd.getCRCLCommand()) {
            throw new IllegalArgumentException("cmd.getCRCLCommand() must not be null. Use setCRCLCommand(...).");
        }
        if (null == cmd.getCRCLCommand().getCommandID()) {
            throw new IllegalArgumentException("cmd.getCRCLCommand().getCommandID() must not be null. Use setCommandID(BigInteger.valueOf(...)).");
        }
        JAXBElement<CRCLCommandInstanceType> jaxb_cmd
                = objectFactory.createCRCLCommandInstance(cmd);
        StringWriter sw = new StringWriter();
        String ret = null;
        this.checkCommandSchema(validate);
        synchronized (m_cmd) {
            try {
                setMarshallerSchema(m_cmd, validate ? cmdSchema : null);
                m_cmd.marshal(jaxb_cmd, sw);
                String str = sw.toString();
                if (replaceHeader) {
                    str = removeHeader(str);
                    ret = cmdHeader + str;
                } else {
                    ret = str;
                }
            } catch (JAXBException ex) {
                throw new CRCLException(ex);
            }
        }
        this.lastCommandString = ret;
        return ret;
    }

    public String programToString(CRCLProgramType prog, boolean validate) throws CRCLException {
        JAXBElement<CRCLProgramType> jaxb_prog
                = objectFactory.createCRCLProgram(prog);
        StringWriter sw = new StringWriter();
        this.checkProgramSchema(validate);
        synchronized (m_prog) {
            try {
                setMarshallerSchema(m_prog, validate ? programSchema : null);
                m_prog.marshal(jaxb_prog, sw);
                String str = sw.toString();
                if (replaceHeader) {
                    str = removeHeader(str);
                    this.lastProgramString = progHeader + str;
                } else {
                    this.lastProgramString = str;
                }
            } catch (JAXBException ex) {
                throw new CRCLException(ex);
            }
        }
        return this.lastProgramString;
    }

    public String commandToPrettyString(CRCLCommandType cmd) throws JAXBException, CRCLException {
        CRCLCommandInstanceType instance = new CRCLCommandInstanceType();
        if (cmd instanceof CrclCommandWrapper) {
            CrclCommandWrapper wrapper = (CrclCommandWrapper) cmd;
            cmd = wrapper.getWrappedCommand();
        }
        instance.setCRCLCommand(cmd);
        return commandInstanceToPrettyString(instance, false);
    }

    public String commandToPrettyString(CRCLCommandType cmd, String errorText) {
        if (cmd instanceof CrclCommandWrapper) {
            CrclCommandWrapper wrapper = (CrclCommandWrapper) cmd;
            cmd = wrapper.getWrappedCommand();
        }
        try {
            return commandToPrettyString(cmd);
        } catch (JAXBException | CRCLException ex) {
            Logger.getLogger(CRCLSocket.class.getName()).log(Level.SEVERE, "could not convert cmd=" + cmd, ex);
        }
        return errorText;
    }

    public String commandInstanceToPrettyString(CRCLCommandInstanceType cmd, boolean validate) throws JAXBException, CRCLException {
        if (null == cmd.getCRCLCommand()) {
            throw new IllegalArgumentException("cmd.getCRCLCommand() must not be null. Use setCRCLCommand(...).");
        }
        if (null == cmd.getCRCLCommand().getCommandID()) {
            throw new IllegalArgumentException("cmd.getCRCLCommand().getCommandID() must not be null. Use setCommandID(BigInteger.valueOf(...)).");
        }
        JAXBElement<CRCLCommandInstanceType> jaxb_cmd
                = objectFactory.createCRCLCommandInstance(cmd);
        StringWriter sw = new StringWriter();
        String ret = null;
        this.checkCommandSchema(validate);
        synchronized (m_cmd) {
            setMarshallerSchema(m_cmd, validate ? cmdSchema : null);
            m_cmd.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m_cmd.marshal(jaxb_cmd, sw);
            m_cmd.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            String str = sw.toString();
            if (replaceHeader) {
                str = removeHeader(str);
                ret = cmdHeader + str;
            } else {
                ret = str;
            }
        }
        this.lastCommandString = ret;
        return ret;
    }

    public String commandInstanceToPrettyDocString(CRCLCommandInstanceType cmd, boolean validate) throws JAXBException {
        if (null == cmd.getCRCLCommand()) {
            throw new IllegalArgumentException("cmd.getCRCLCommand() must not be null. Use setCRCLCommand(...).");
        }
        if (null == cmd.getCRCLCommand().getCommandID()) {
            throw new IllegalArgumentException("cmd.getCRCLCommand().getCommandID() must not be null. Use setCommandID(BigInteger.valueOf(...)).");
        }
        JAXBElement<CRCLCommandInstanceType> jaxb_cmd
                = objectFactory.createCRCLCommandInstance(cmd);
        StringWriter sw = new StringWriter();
        String ret = null;
        synchronized (m_cmd) {
            setMarshallerSchema(m_cmd, validate ? cmdSchema : null);
            m_cmd.setProperty(Marshaller.JAXB_FRAGMENT, false);
            m_cmd.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m_cmd.marshal(jaxb_cmd, sw);
            m_cmd.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            m_cmd.setProperty(Marshaller.JAXB_FRAGMENT, jaxbFragment);
            String str = sw.toString();
            if (replaceHeader) {
                str = removeHeader(str);
                ret = cmdHeader + str;
            } else {
                ret = str;
            }
        }
        this.lastCommandString = ret;
        return ret;
    }

    public String programToPrettyString(CRCLProgramType prog, boolean validate) throws CRCLException {
        JAXBElement<CRCLProgramType> jaxb_prog
                = objectFactory.createCRCLProgram(prog);
        StringWriter sw = new StringWriter();
        this.checkProgramSchema(validate);
        synchronized (m_prog) {
            try {
                setMarshallerSchema(m_prog, validate ? programSchema : null);
                m_prog.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                m_prog.marshal(jaxb_prog, sw);
                m_prog.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
                String str = sw.toString();
                if (replaceHeader) {
                    str = removeHeader(str);
                    this.lastProgramString = progHeader + str;
                } else {
                    this.lastProgramString = str;
                }
            } catch (JAXBException ex) {
                throw new CRCLException(ex);
            }
        }
        return this.lastProgramString;
    }

    public String programToPrettyDocString(CRCLProgramType prog, boolean validate) throws JAXBException {
        JAXBElement<CRCLProgramType> jaxb_proj
                = objectFactory.createCRCLProgram(prog);
        StringWriter sw = new StringWriter();
        synchronized (m_prog) {
            setMarshallerSchema(m_prog, validate ? programSchema : null);
            m_prog.setProperty(Marshaller.JAXB_FRAGMENT, false);
            m_prog.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m_prog.marshal(jaxb_proj, sw);
            m_prog.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            m_prog.setProperty(Marshaller.JAXB_FRAGMENT, jaxbFragment);
            String str = sw.toString();
            if (replaceHeader) {
                str = removeHeader(str);
                this.lastProgramString = progHeader + str;
            } else {
                this.lastProgramString = str;
            }
        }
        return this.lastProgramString;
    }

    public void writeCommand(CRCLCommandInstanceType cmd) throws CRCLException {
        writeCommand(cmd, false);
    }

    public synchronized void writeCommand(CRCLCommandInstanceType cmd, boolean validate) throws CRCLException {
        try {
            final CRCLCommandType cc = cmd.getCRCLCommand();
            if (null == cc) {
                throw new IllegalArgumentException("cmd.getCRCLCommand() must not be null. Use setCRCLCommand(...).");
            }
            if (null == cc.getCommandID()) {
                throw new IllegalArgumentException("cmd.getCRCLCommand().getCommandID() must not be null. Use setCommandID(BigInteger.valueOf(...)).");
            }
            final String threadName = Thread.currentThread().getName();
            final Level loglevel = (cc instanceof GetStatusType) ? Level.FINER : Level.FINE;
            LOGGER.log(loglevel, "writeCommand({0} ID={1}) called from Thread: {2}", new Object[]{cc, cc.getCommandID(), threadName});
            final Socket socket = getSocket();
            if (null == socket) {
                throw new IllegalStateException("Internal socket is null.");
            }
            assert null != socket : "@AssumeAssertion(nullable)";
            final String str = commandToString(cmd, validate);
            LOGGER.log(loglevel, "writeCommand({0} ID={1}) with str = {2} called from Thread: {3}", new Object[]{cc, cc.getCommandID(), str, threadName});
            writeWithFill(str);
            this.lastCommandString = str;
        } catch (IOException | InterruptedException ex) {
            throw new CRCLException(ex);
        }
    }

    protected void writePackets(byte ba[]) throws IOException, InterruptedException {
        if (null != socketChannel) {
            writePackets(socketChannel, ba);
        } else {
            writePackets(getSocket().getOutputStream(), ba);
        }
    }

    private void writePackets(SocketChannel channel, byte ba[]) throws IOException, InterruptedException {
        if (!this.randomPacketing) {
            int byteswritten = channel.write(ByteBuffer.wrap(ba));
            while (byteswritten < ba.length) {
                byteswritten += channel.write(ByteBuffer.wrap(ba, byteswritten, ba.length));
                Thread.sleep(20);
            }
        } else {
            if (null == random) {
                random = new Random(rand_seed);
            }
            assert null != random : "@AssumeAssertion(nullable)";
            writeRandomSizedPackets(ba, channel, random);
        }
    }

    private void writePackets(OutputStream os, byte ba[]) throws IOException, InterruptedException {
        if (!this.randomPacketing) {
            os.write(ba);
        } else {
            if (null == random) {
                random = new Random(rand_seed);
            }
            assert null != random : "@AssumeAssertion(nullable)";
            writeRandomSizedPackets(ba, os, random);
        }
    }

    private void writeRandomSizedPackets(byte[] ba, SocketChannel channel, Random rand) throws InterruptedException, IOException {
        int bytes_written = 0;
        while (bytes_written < ba.length) {
            int bytes_to_write = rand.nextInt(ba.length - 1) + 1;
            if (bytes_to_write >= ba.length - bytes_written) {
                bytes_to_write = ba.length - bytes_written;
            }
            int write_ret = channel.write(ByteBuffer.wrap(ba, bytes_written, bytes_to_write));
            bytes_written += write_ret;
            if (bytes_written < ba.length) {
                Thread.sleep(rand.nextInt(150));
            }
        }
    }

    private void writeRandomSizedPackets(byte[] ba, OutputStream os, Random rand) throws InterruptedException, IOException {
        int bytes_written = 0;
        while (bytes_written < ba.length) {
            int bytes_to_write = rand.nextInt(ba.length - 1) + 1;
            if (bytes_to_write >= ba.length - bytes_written) {
                bytes_to_write = ba.length - bytes_written;
            }
            os.write(ba, bytes_written, bytes_to_write);
            bytes_written += bytes_to_write;
            if (bytes_written < ba.length) {
                os.flush();
                Thread.sleep(rand.nextInt(150));
            }
        }
    }

    public void writeWithFill(String str) throws IOException, InterruptedException {
        if (null != socketChannel) {
            if (!appendTrailingZero) {
                this.writePackets(socketChannel, str.getBytes());
            } else {
                int len = str.length();
                byte bytesPlusOne[] = new byte[len + 1];
                System.arraycopy(str.getBytes(), 0, bytesPlusOne, 0, len);
                this.writePackets(socketChannel, bytesPlusOne);
            }
            return;
        }

        final Socket socket = getSocket();
        if (null == socket) {
            throw new IllegalStateException("Internal socket is null.");
        }
        assert null != socket : "@AssumeAssertion(nullable)";
        OutputStream os = socket.getOutputStream();
        synchronized (os) {
            if (!appendTrailingZero) {
                this.writePackets(os, str.getBytes());
            } else {
                int len = str.length();
                byte bytesPlusOne[] = new byte[len + 1];
                System.arraycopy(str.getBytes(), 0, bytesPlusOne, 0, len);
                this.writePackets(os, bytesPlusOne);
            }
            os.flush();
        }
    }

    public void writeProgram(CRCLProgramType prog, boolean validate) throws CRCLException {
        try {
            this.lastProgramString = programToString(prog, validate);
            this.writeWithFill(this.lastProgramString);
        } catch (IOException | InterruptedException ex) {
            throw new CRCLException(ex);
        }
    }

    /**
     * Get the value of replaceHeader
     *
     * @return the value of replaceHeader
     */
    public boolean isReplaceHeader() {
        return replaceHeader;
    }

    /**
     * Set the value of replaceHeader
     *
     * @param replaceHeader new value of replaceHeader
     */
    public void setReplaceHeader(boolean replaceHeader) {
        this.replaceHeader = replaceHeader;
    }

    public String statusToString(CRCLStatusType status, boolean validate) throws CRCLException {

        if (status.getCommandStatus() == null) {
            throw new IllegalArgumentException("status.getCommandStatus()  must not be null. Use setCommandStatus(...)");
        }
        if (status.getCommandStatus().getCommandID() == null) {
            throw new IllegalArgumentException("status.getCommandStatus().getCommandID()  must not be null. Use getCommandStatus().setCommandID(BigInteger.valueOf(...))");
        }
        if (status.getCommandStatus().getStatusID() == null) {
            throw new IllegalArgumentException("status.getCommandStatus().getStatusID()  must not be null. Use getCommandStatus().setStatusID(BigInteger.valueOf(...))");
        }
        JAXBElement<CRCLStatusType> jaxb_status
                = objectFactory.createCRCLStatus(status);
        StringWriter sw = new StringWriter();
        this.checkStatusSchema(validate);
        synchronized (m_stat) {
            setMarshallerSchema(m_stat, validate ? statSchema : null);
            try {
                m_stat.marshal(jaxb_status, sw);
            } catch (Exception ex1) {
                LOGGER.log(Level.SEVERE,
                        "First Exception validate=" + validate + " sw.toString() = " + sw.toString() + ",status=" + statToDebugString(status),
                        ex1);
                sw = new StringWriter();
                try {
                    setMarshallerSchema(m_stat, null);
                    m_stat.marshal(jaxb_status, sw);
                } catch (Exception ex2) {
                    LOGGER.log(Level.SEVERE, "Second Exception", ex2);
                    throw new RuntimeException(ex2);
                }
            }
            String str = sw.toString();
            if (replaceHeader) {
                str = removeHeader(str);
                this.lastStatusString = statusHeader + str;
            } else {
                this.lastStatusString = str;
            }
            if (null != this.statusStringOutputFilter) {
                this.lastStatusString = this.statusStringOutputFilter.apply(this.lastStatusString);
            }
            return this.lastStatusString;
        }
    }

    private String removeHeader(String str) {
        int qgtindex = str.indexOf("?>");
        if (qgtindex > 0) {
            last_xml_version_header = str.substring(0, qgtindex + 2);
            str = str.substring(qgtindex + 2);
        }
        int gtindex = str.indexOf('>');
        if (gtindex > 0) {
            last_orig_first_tag = str.substring(0, gtindex + 1);
            str = str.substring(gtindex + 1);
        }
        return str;
    }

    public String statusToPrettyString(CRCLStatusType status, boolean validate) throws JAXBException {
        JAXBElement<CRCLStatusType> jaxb_status
                = objectFactory.createCRCLStatus(status);
        StringWriter sw = new StringWriter();
        synchronized (m_stat) {
            setMarshallerSchema(m_stat, validate ? statSchema : null);
            m_stat.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m_stat.marshal(jaxb_status, sw);
            m_stat.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            String str = sw.toString();
            if (replaceHeader) {
                str = removeHeader(str);
                this.lastStatusString = statusHeader + str;
            } else {
                this.lastStatusString = str;
            }
            if (null != this.statusStringOutputFilter) {
                this.lastStatusString = this.statusStringOutputFilter.apply(this.lastStatusString);
            }
            return this.lastStatusString;
        }
    }

    public String commandToSimpleString(CRCLCommandInstanceType cmdInstance) throws ParserConfigurationException, SAXException, IOException {
        return commandToSimpleString(cmdInstance.getCRCLCommand());
    }

    public String commandToSimpleString(CRCLCommandType cmd) throws ParserConfigurationException, SAXException, IOException {
        if (cmd instanceof CrclCommandWrapper) {
            CrclCommandWrapper wrapper = (CrclCommandWrapper) cmd;
            cmd = wrapper.getWrappedCommand();
        }
        return commandToSimpleString(cmd, 9, 50);
    }

    public String commandToSimpleString(CRCLCommandType cmd, final int max_fields, final int max_length) throws ParserConfigurationException, SAXException, IOException {
        if (cmd instanceof CrclCommandWrapper) {
            CrclCommandWrapper wrapper = (CrclCommandWrapper) cmd;
            cmd = wrapper.getWrappedCommand();
        }
        String xmlString = this.commandToString(cmd, false);
        DefaultHandler handler = new DefaultHandler() {

            private final StringBuffer buffer = new StringBuffer();
            private int fields = 0;
            private int last_end_buffer_length = 0;

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (fields <= max_fields) {
                    buffer.append(ch, start, length);
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                final int bl = buffer.length();
                if (bl > last_end_buffer_length) {
                    fields++;
                    if (fields < max_fields) {
                        buffer.append(",");
                    } else if (fields == max_fields) {
                        buffer.append(" ...");
                    }
                    last_end_buffer_length = buffer.length();
                }
            }

            @Override
            public String toString() {
                return buffer.toString();
            }

        };
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlreader = parser.getXMLReader();
        xmlreader.setContentHandler(handler);
        xmlreader.parse(new InputSource(new StringReader(xmlString)));
        String cmdName = cmd.getClass().getName();
        int lpindex = cmdName.lastIndexOf('.');
        if (lpindex > 0 && lpindex < cmdName.length() - 1) {
            cmdName = cmdName.substring(lpindex + 1);
        }
        if(cmdName.endsWith("Type")) {
            cmdName = cmdName.substring(0, cmdName.length()-4);
        }
        String content = handler.toString();
        content = content.trim();
        if (content.length() > max_length) {
            content = content.substring(0, (max_length - 4)) + " ...";
        }
        return cmdName + " " + content;
    }

    public void writeStatus(CRCLStatusType status) throws CRCLException {
        writeStatus(status, false);
    }

    public synchronized void writeStatus(CRCLStatusType status, boolean validate)
            throws CRCLException {
        try {
            if (status.getCommandStatus() == null) {
                throw new IllegalArgumentException("status.getCommandStatus()  must not be null. Use setCommandStatus(...)");
            }
            if (status.getCommandStatus().getCommandID() == null) {
                throw new IllegalArgumentException("status.getCommandStatus().getCommandID()  must not be null. Use getCommandStatus().setCommandID(BigInteger.valueOf(...))");
            }
            if (status.getCommandStatus().getStatusID() == null) {
                throw new IllegalArgumentException("status.getCommandStatus().getStatusID()  must not be null. Use getCommandStatus().setStatusID(BigInteger.valueOf(...))");
            }
            final Socket socket = getSocket();
            if (null == socket) {
                throw new IllegalStateException("Internal socket is null.");
            }
            assert null != socket : "@AssumeAssertion(nullable)";
            this.lastStatusString = statusToString(status, validate);
            this.writeWithFill(this.lastStatusString);
        } catch (IOException | InterruptedException ex) {
            throw new CRCLException(ex);
        }
    }

    static public interface UnaryOperator<T> {

        public T apply(T t);
    }

    private static final class IdentityUnaryOperator<T> implements UnaryOperator<T> {

        @Override
        public T apply(T t) {
            return t;
        }
    }

    private static final IdentityUnaryOperator<String> STRING_IDENTITY_OPERATOR
            = new IdentityUnaryOperator<>();

    static private class CRCLSchemaOutputResolver extends SchemaOutputResolver {

        /*@Nullable*/ private File file = null;

        /*@Nullable*/
        public File getFile() {
            return file;
        }

        @Override
        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
            file = new File(suggestedFileName);
            StreamResult result = new StreamResult(file);
            result.setSystemId(file.toURI().toURL().toString());
            return result;
        }

    }

    private static interface Supplier<T> {

        /*@Nullable*/
        public T get();
    }
}
