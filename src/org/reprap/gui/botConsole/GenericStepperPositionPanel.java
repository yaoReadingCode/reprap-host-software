/*
 * !!!!!
 * NOTE: PLEASE ONLY EDIT THIS USING THE NETBEANS IDE 6.0.1 OR HIGHER!!!!
 * !!!!!
 * 
 * ... an .xml file is associated with this class. Cheers.
 * 
 * GenericStepperPositionPanel.java
 * 
 * Created on 27 March 2008, 16:54
 */

package org.reprap.gui.botConsole;

/**
 *
 * @author  en0es
 */

import java.io.IOException;
import javax.swing.JOptionPane;
import org.reprap.Preferences;
import org.reprap.comms.Communicator;
import org.reprap.comms.snap.SNAPAddress;
import org.reprap.devices.GenericStepperMotor;

public class GenericStepperPositionPanel extends javax.swing.JPanel {
    
    /** Creates new form GenericStepperPositionPanel */
    public GenericStepperPositionPanel() {
        
        try {
            setupComms(); 
        }
        catch (Exception e) {
            System.out.println("Failure trying to initialise steppers in botConsole: " + e);
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
      
        initComponents();
        axisLabel.setText(axis);
        
        try {
            motor.setPosition(0);
        } catch (Exception ex) {
            deactivateMotorPanel();  
            return;
        }
    }
    
    private void deactivateMotorPanel() {
            motor.dispose();
            motor = null;
            axisLabel.setEnabled(false);
            currentPositionLabel.setEnabled(false);
            endButton.setEnabled(false);
            homeButton.setEnabled(false);
            jLabel3.setEnabled(false);
            stepDownButton1.setEnabled(false);
            stepUpButton.setEnabled(false);
            targetPositionField.setEnabled(false);
    }
    
    private Communicator communicator;
    private int motorID;
    private String axis;
    private GenericStepperMotor motor;

    private int fastSpeed;
    private double motorStepsPerMM;
    private double axisLength;
    private double nudgeSize;
        
    private void setupComms() throws IOException {
        
        communicator = org.reprap.Main.getCommunicator();
        motorID = BotConsoleFrame.getMotorID();

        switch(motorID)
        {
        case 1:
                axis = "X";
                axisLength = 160; // TODO: Replace with Prefs when Java3D parameters work for small wv's.
                break;
        case 2:
                axis = "Y";
                axisLength = 160; // TODO: Replace with Prefs when Java3D parameters work for small wv's.
                break;
        case 3:
                axis = "Z";
                axisLength = 80; // TODO: Replace with Prefs when Java3D parameters work for small wv's.
                break;
        default:
                axis = "X";
                System.err.println("StepperPanel - dud axis id:" + motorID);
        }
        
        int address = Preferences.loadGlobalInt(axis + "Axis" + "Address");
        
        motor = new GenericStepperMotor(communicator, new SNAPAddress(address), Preferences.getGlobalPreferences(), motorID);

        motorStepsPerMM = Preferences.loadGlobalDouble(axis + "AxisScale(steps/mm)");
	
//      TODO: Activate this code when the Java3D parameters allow a small enough working volume. Currently I get a black screen.
//      axisLength = Preferences.loadGlobalDouble("Working" + axis + "(mm)");

    }

    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        axisLabel = new javax.swing.JLabel();
        currentPositionLabel = new javax.swing.JLabel();
        targetPositionField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        stepDownButton1 = new javax.swing.JButton();
        stepUpButton = new javax.swing.JButton();
        homeButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();

        axisLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        axisLabel.setText("X");

        currentPositionLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        currentPositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        currentPositionLabel.setText("(?)");
        currentPositionLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        currentPositionLabel.setMaximumSize(new java.awt.Dimension(50, 15));
        currentPositionLabel.setPreferredSize(new java.awt.Dimension(50, 15));

        targetPositionField.setColumns(4);
        targetPositionField.setFont(targetPositionField.getFont().deriveFont((float)12));
        targetPositionField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        targetPositionField.setText("0");
        targetPositionField.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel3.setText("mm");

        stepDownButton1.setText("<");
        stepDownButton1.setEnabled(false);
        stepDownButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepDownButton1ActionPerformed(evt);
            }
        });

        stepUpButton.setText(">");
        stepUpButton.setEnabled(false);
        stepUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepUpButtonActionPerformed(evt);
            }
        });

        homeButton.setText("Home");
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });

        endButton.setText("End");
        endButton.setEnabled(false);
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(axisLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(targetPositionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stepDownButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stepUpButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(homeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(axisLabel)
                .addComponent(currentPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(targetPositionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3)
                .addComponent(stepDownButton1)
                .addComponent(stepUpButton)
                .addComponent(homeButton)
                .addComponent(endButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private boolean axisBeenHomed = false;
    
    public boolean hasAxisBeenHomed() {
        return axisBeenHomed;
    }
    
    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        homeAxis();
    }//GEN-LAST:event_homeButtonActionPerformed

    public void homeAxis() {
        try {
            motor.homeReset(fastSpeed);
            currentPositionLabel.setText("(Home)");
            axisBeenHomed = true;
            targetPositionField.setEnabled(true);
            stepDownButton1.setEnabled(true);
            stepUpButton.setEnabled(true);
            endButton.setEnabled(true);
            targetPositionField.setText("0");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Could not home motor: " + ex);
        }
    }
    
    double newTargetAfterNudge;
    
    private void stepUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepUpButtonActionPerformed
        
        System.out.println(nudgeSize);
        newTargetAfterNudge = getTargetPositionInMM() + nudgeSize;
        targetPositionField.setText("" + round(newTargetAfterNudge, 2));
        moveToTarget();
        
}//GEN-LAST:event_stepUpButtonActionPerformed

    private void stepDownButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepDownButton1ActionPerformed

        System.out.println(nudgeSize);
        double newTargetAfterNudge = getTargetPositionInMM() - nudgeSize;
        targetPositionField.setText("" + round(newTargetAfterNudge, 2));
        moveToTarget();

}//GEN-LAST:event_stepDownButton1ActionPerformed

    public double round(double Rval, int r2dp) {
        double p = (Double)Math.pow(10,r2dp);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (double)tmp/p;
    }
    
    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
        targetPositionField.setText("" + axisLength);
        try {
            motor.seek(fastSpeed, getTargetPositionInSteps());
            setCurrentPositionLabelsToTargetPosition();
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, axis + " motor could not seek: " + ex);
        }
    }//GEN-LAST:event_endButtonActionPerformed
    
    public void setSpeed(int speed) {
        fastSpeed = speed;
    }
    
    public double getTargetPositionInMM() {
        double targetMM = Double.parseDouble(targetPositionField.getText());
        if (targetMM > axisLength) {
            targetMM = axisLength;
            targetPositionField.setText("" + targetMM);
        }
        if (targetMM < 0) {
            targetMM = 0;
            targetPositionField.setText("" + targetMM);
        }
        return targetMM;
    }
    
    private int getTargetPositionInSteps() {
        double targetMM = getTargetPositionInMM();
        return (int)Math.round(targetMM * motorStepsPerMM);
    }
    
    public void moveToTarget() {
        try {
            motor.seek(fastSpeed, getTargetPositionInSteps());
            setCurrentPositionLabelsToTargetPosition();
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, axis + " motor could not seek: " + ex);
        }
    }
    
    public void moveToTargetBlocking() {
        try {
            motor.seekBlocking(fastSpeed, getTargetPositionInSteps());
            setCurrentPositionLabelsToTargetPosition();
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, axis + " motor could not block: " + ex);
        }
    }
    
    public void setCurrentPositionLabelsToTargetPosition() {
        currentPositionLabel.setText("(" + getTargetPositionInMM() + ")");
         
    }
    
    public void setNudgeSize(double size) {
        nudgeSize = size;
    }
    
    public void setTargetPositionField(int coord) {
        if (axisBeenHomed) targetPositionField.setText("" + coord);
        else JOptionPane.showMessageDialog(null, "You must home the " + axis + " axis first!");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel axisLabel;
    private javax.swing.JLabel currentPositionLabel;
    private javax.swing.JButton endButton;
    private javax.swing.JButton homeButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton stepDownButton1;
    private javax.swing.JButton stepUpButton;
    private javax.swing.JTextField targetPositionField;
    // End of variables declaration//GEN-END:variables

    
}
