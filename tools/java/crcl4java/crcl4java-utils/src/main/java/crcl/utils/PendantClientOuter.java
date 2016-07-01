/*
 * This software is public domain software, however it is preferred
 * that the following disclaimers be attached.
 * Software Copywrite/Warranty Disclaimer
 * 
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of their
 * official duties. Pursuant to title 17 Section 105 of the United States
 * Code this software is not subject to copyright protection and is in the
 * public domain.
 *
 *  This software is experimental. NIST assumes no responsibility whatsoever for its use by other
 * parties, and makes no guarantees, expressed or implied, about its
 * quality, reliability, or any other characteristic. We would appreciate
 * acknowledgement if the software is used. This software can be
 * redistributed and/or modified freely provided that any derivative works
 * bear some notice that they are derived from it, and any modified
 * versions bear some notice that they have been modified.
 * 
 */
package crcl.utils;

import crcl.base.CRCLProgramType;
import crcl.base.MiddleCommandType;
import crcl.base.PoseType;
import java.io.File;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public interface PendantClientOuter extends PendantClientMenuOuter {

    public void showMessage(String s);

    public void showMessage(Throwable t);

    public boolean showDebugMessage(String s);

    public String getHost();

    public int getPort();

    public void finishDisconnect();

    public void finishConnect();

    public void finishSetStatus();

    public void checkXmlQuery(CRCLSocket crclSocket);

    public void stopPollTimer();

    public void checkPollSelected();

    public void showCurrentProgramLine(int line);

    public void showLastProgramLineExecTimeMillisDists(long millis, double dist, boolean result);

    public void finishOpenXmlProgramFile(File f, CRCLProgramType program, boolean addRecent);

    public CRCLProgramType editProgram(CRCLProgramType program);

    public boolean checkUserText(String text);

    public boolean isMonitoringHoldingObject();

    public void setExpectedHoldingObject(boolean x);

    public MiddleCommandType getCurrentProgramCommand();

    public PoseType getCurrentPose();

    public CRCLProgramType getProgram();

    public File getLastOpenedProgramFile();

    public void setProgram(CRCLProgramType program) throws JAXBException;

    public void saveXmlProgramFile(File f) throws JAXBException, CRCLException;
}
