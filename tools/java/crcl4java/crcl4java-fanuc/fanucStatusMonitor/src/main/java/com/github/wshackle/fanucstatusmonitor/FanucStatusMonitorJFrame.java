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
package com.github.wshackle.fanucstatusmonitor;

import com.github.wshackle.fanuc.robotserver.IAlarm;
import com.github.wshackle.fanuc.robotserver.IAlarms;
import com.github.wshackle.fanuc.robotserver.IRobot2;
import com.github.wshackle.fanuc.robotserver.IRobotErrorInfo;
import com.github.wshackle.fanuc.robotserver.IVar;
import com.github.wshackle.fanuc.robotserver.IVars;
import com.github.wshackle.fanuc.robotserver.events.IAlarmNotify;
import com.github.wshackle.fanuc.robotserver.events.IRobotEvents;
import com4j.Com4jObject;
import com4j.EventCookie;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shackle
 */
public class FanucStatusMonitorJFrame extends javax.swing.JFrame {

    /**
     * Creates new form FanucStatusMonitorJFrame
     */
    public FanucStatusMonitorJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldHost = new javax.swing.JTextField();
        jToggleButtonConnect = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNewAlarms = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldLastReset = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldLastUpdate = new javax.swing.JTextField();
        jTextFieldNumUpdates = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaExceptions = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableOldAlarms = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Host:");

        jTextFieldHost.setText("129.6.78.111");

        jToggleButtonConnect.setText("Connect");
        jToggleButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonConnectActionPerformed(evt);
            }
        });

        jTableNewAlarms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableNewAlarms);

        jLabel2.setText("Last Reset: ");

        jTextFieldLastReset.setEditable(false);

        jLabel3.setText("Last Update:");

        jLabel4.setText("Num Updates:");

        jTextFieldLastUpdate.setEditable(false);

        jTextFieldNumUpdates.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Old Alarms:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Exceptions:");

        jTextAreaExceptions.setColumns(20);
        jTextAreaExceptions.setRows(5);
        jScrollPane2.setViewportView(jTextAreaExceptions);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Alarms:");

        jTableOldAlarms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableOldAlarms);

        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldHost, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButtonConnect)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNumUpdates)
                                    .addComponent(jTextFieldLastUpdate)
                                    .addComponent(jTextFieldLastReset)))
                            .addComponent(jScrollPane2))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonConnect)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldLastReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldLastUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldNumUpdates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void disconnect() {
        if (null != monitorThread) {
            synchronized (monitorThread) {
                if (null != monitorThread) {
                    try {
                        monitorThread.interrupt();
                        monitorThread.join(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FanucStatusMonitorJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        monitorThread = null;
                    }
                }
            }
        }
    }

    private String remoteRobotHost = "129.6.78.111";

    ;

    /**
     * Get the value of remoteRobotHost
     *
     * @return the value of remoteRobotHost
     */
    public String getRemoteRobotHost() {
        return remoteRobotHost;
    }

    /**
     * Set the value of remoteRobotHost
     *
     * @param remoteRobotHost new value of remoteRobotHost
     */
    public void setRemoteRobotHost(String remoteRobotHost) {
        this.remoteRobotHost = remoteRobotHost;
    }

    private Map<String,IVar> flatMap(String path, IVars ivars) {
        Map<String,IVar> map = new TreeMap<>();
        for (Com4jObject c4jo : ivars) {
            IVars internalIvars = c4jo.queryInterface(IVars.class);
            if(null != internalIvars) {
                String vname = internalIvars.varName();
                if(!vname.endsWith("]") || vname.endsWith("[1]")) {
                    map.putAll(flatMap(path+internalIvars.varName()+".",internalIvars));
                }
                continue;
            }
            try {
                IVar iv = c4jo.queryInterface(IVar.class);
                if (null != iv && iv.isInitialized()) {
                    map.put(path + iv.varName(), iv);
                }
            } catch (Exception e) {
            }
        }
        return map;
    }
    
    private void update() {
        IRobot2 robot = null;
        EventCookie robot_ec = null;
        EventCookie alarms_ec = null;
        try {
            final String host = remoteRobotHost;

            int last_reset_count = 0;
            int num_updates = 0;
            while (!Thread.currentThread().isInterrupted()) {
                if (null != robot) {
                    robot.dispose();
                    robot = null;
                }
                if (null != robot_ec) {
                    robot_ec.close();
                    robot_ec = null;
                }
                if (null != alarms_ec) {
                    alarms_ec.close();
                    alarms_ec = null;
                }
                robot = com.github.wshackle.fanuc.robotserver.ClassFactory.createFRCRobot();
                robot_ec = robot.advise(IRobotEvents.class, new IRobotEvents() {
                    @Override
                    public void error(IRobotErrorInfo error) {
                        System.out.println("error = " + error);
                    }

                    @Override
                    public void disconnect() {
                        System.out.println("robot disconnect");
                    }

                    @Override
                    public void connect() {
                    }
                });
                robot.connect(host);
                if (!robot.isConnected()) {
                    System.err.println("failed to connect to robot");
                    java.awt.EventQueue.invokeLater(() -> {
                        jTextAreaExceptions.append("Failed to connect to robot!\n");
                        jToggleButtonConnect.setSelected(false);
                        disconnect();
                    });
                    break;
                }
                IAlarms alarms = robot.alarms();
                int new_reset_count = reset_count;
                if (last_reset_count != new_reset_count) {
                    alarms.reset();
                    last_reset_count = new_reset_count;
                    continue;
                }
                alarms_ec = alarms.advise(IAlarmNotify.class, new IAlarmNotify() {
                    @Override
                    public void alarmNotify(Com4jObject alarm) {
                        System.out.println("alarm = " + alarm);
                    }
                });

                List<IAlarm> alarmList = new LinkedList<>();
                for (Com4jObject c4jo : alarms) {
                    IAlarm alarm = c4jo.queryInterface(IAlarm.class);
                    if (null != alarm) {
                        alarmList.add(alarm);
                    }
                }
                num_updates++;
                final Date lastResetDate = alarmList.stream()
                        .filter(a -> a.errorClass() == 1)
                        .map(IAlarm::timeStamp)
                        .max(Date::compareTo)
                        .get();
                List<IAlarm> newAlarms = alarmList.stream()
                        .filter(a -> a.errorClass() != 1)
                        .filter(a -> a.timeStamp().after(lastResetDate))
                        .collect(Collectors.toList());
                Map<String, List<IAlarm>> msgMap = newAlarms.stream()
                        .collect(Collectors.groupingBy(IAlarm::errorMessage));
                List<Object[]> l = msgMap.values().stream()
                        .filter(l2 -> !l2.isEmpty())
                        .map(l3 -> l3.stream().max((a1, a2) -> a1.timeStamp().compareTo(a2.timeStamp())).get())
                        .map(a -> new Object[]{a.timeStamp(), a.errorMnemonic(), a.errorMessage(), a.causeMessage()})
                        .collect(Collectors.toList());
                final Object[][] data = l.toArray(new Object[l.size()][]);
                List<IAlarm> oldAlarms = alarmList.stream()
                        .filter(a -> a.errorClass() != 1)
                        .filter(a -> a.timeStamp().before(lastResetDate))
                        .collect(Collectors.toList());
                final int nu = num_updates;
                List<Object[]> oldDataList = oldAlarms.stream()
                        .map(a -> new Object[]{a.timeStamp(), a.errorMnemonic(), a.errorMessage(), a.causeMessage()})
                        .collect(Collectors.toList());
                final Object[][] olddata = oldDataList.toArray(new Object[oldDataList.size()][]);
                java.awt.EventQueue.invokeLater(() -> {
                    jTableNewAlarms.setModel(new DefaultTableModel(data, new Object[]{"timestamp", "error", "message", "cause"}));
                    jTableOldAlarms.setModel(new DefaultTableModel(olddata, new Object[]{"timestamp", "error", "message", "cause"}));
                    jTextFieldLastReset.setText(lastResetDate.toString());
                    jTextFieldNumUpdates.setText("" + nu);
                    jTextFieldLastUpdate.setText(new Date().toString());
                });
                Thread.sleep(1000);
            }
        } catch (com4j.ComException ce) {
            jTextAreaExceptions.append(ce.getMessage() + "\n");
            Logger.getLogger(FanucStatusMonitorJFrame.class.getName()).log(Level.SEVERE, null, ce);
        } catch (InterruptedException ex) {
            Logger.getLogger(FanucStatusMonitorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != alarms_ec) {
                alarms_ec.close();
            }
            if (null != robot_ec) {
                robot_ec.close();
            }
            if (null != robot) {
                robot.dispose();
            }
        }
    }
    private Thread monitorThread = null;

    @Override
    protected void finalize() throws Throwable {
        this.disconnect();
    }

    public void connect() {
        this.disconnect();
        this.setRemoteRobotHost(this.jTextFieldHost.getText());
        monitorThread = new Thread(this::update, "fanucMonitor");
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    private void jToggleButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonConnectActionPerformed
        if (this.jToggleButtonConnect.isSelected()) {
            this.connect();
        } else {
            this.disconnect();
        }
    }//GEN-LAST:event_jToggleButtonConnectActionPerformed

    private volatile int reset_count = 0;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        reset_count++;
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FanucStatusMonitorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FanucStatusMonitorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FanucStatusMonitorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FanucStatusMonitorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FanucStatusMonitorJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableNewAlarms;
    private javax.swing.JTable jTableOldAlarms;
    private javax.swing.JTextArea jTextAreaExceptions;
    private javax.swing.JTextField jTextFieldHost;
    private javax.swing.JTextField jTextFieldLastReset;
    private javax.swing.JTextField jTextFieldLastUpdate;
    private javax.swing.JTextField jTextFieldNumUpdates;
    private javax.swing.JToggleButton jToggleButtonConnect;
    // End of variables declaration//GEN-END:variables
}