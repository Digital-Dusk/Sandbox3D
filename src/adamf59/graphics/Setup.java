// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.graphics;

import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextPane;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import adamf59.kinect.Kinect;
import javax.swing.UIManager;
import org.opencv.core.Core;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Setup extends JFrame
{
    private JPanel contentPane;
    private JButton btnFinish;
    private JButton btnCalibrate;
    public static JLabel previewPane;
    public static JLabel lblHighestRecordedMountain;
    public static JLabel lblLowestRecordedValley;
    public static JLabel lblSeaLevel;
    public static JLabel lblRangeaboveseaLevel;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final Setup frame = new Setup();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Setup() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Kinect.init();
        this.setTitle("Sandbox3D - Setup");
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 650, 615);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JLabel lblWelcomeToSandboxd = new JLabel("Welcome to Sandbox3D!");
        lblWelcomeToSandboxd.setFont(new Font("Segoe UI Historic", 1, 15));
        lblWelcomeToSandboxd.setBounds(10, 11, 618, 32);
        this.contentPane.add(lblWelcomeToSandboxd);
        final JTextPane txtpnInOrderTo = new JTextPane();
        txtpnInOrderTo.setText("In order to get started, we'll need to perform some setup. Please follow the following instructions:\r\n\tIn your sandbox:\t\r\n\t\t1. Build the highest possible mountain with the sand.\r\n\t\t2. Clear out the lowest possible valley.\r\n\t\t3. Click the \"calibrate\" button and review the preview topographical map.\r\n\t\t4. Recomplete steps 1-3 as necessary.");
        txtpnInOrderTo.setEditable(false);
        txtpnInOrderTo.setBounds(10, 54, 628, 102);
        this.contentPane.add(txtpnInOrderTo);
        (this.btnCalibrate = new JButton("Calibrate")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                Setup.this.btnCalibrate.setText("Re-Calibrate");
                Setup.this.btnFinish.setEnabled(true);
                new DepthFrameBuffer(Kinect.getFrame(), 0);
                new DepthFrameBuffer(Kinect.getFrame(), 1);
            }
        });
        this.btnCalibrate.setBounds(10, 167, 628, 23);
        this.contentPane.add(this.btnCalibrate);
        (Setup.lblHighestRecordedMountain = new JLabel("Highest Recorded Mountain:")).setFont(new Font("Tahoma", 1, 11));
        Setup.lblHighestRecordedMountain.setBounds(10, 201, 221, 14);
        this.contentPane.add(Setup.lblHighestRecordedMountain);
        (Setup.lblLowestRecordedValley = new JLabel("Lowest Recorded Valley:")).setFont(new Font("Tahoma", 1, 11));
        Setup.lblLowestRecordedValley.setBounds(241, 201, 221, 14);
        this.contentPane.add(Setup.lblLowestRecordedValley);
        (Setup.lblSeaLevel = new JLabel("Sea Level:")).setBounds(10, 220, 221, 14);
        this.contentPane.add(Setup.lblSeaLevel);
        (Setup.lblRangeaboveseaLevel = new JLabel("Range-Above-Sea Level:")).setBounds(241, 220, 221, 14);
        this.contentPane.add(Setup.lblRangeaboveseaLevel);
        (Setup.previewPane = new JLabel("preview will appear here")).setHorizontalAlignment(0);
        Setup.previewPane.setBounds(10, 245, 618, 292);
        this.contentPane.add(Setup.previewPane);
        (this.btnFinish = new JButton("Finish Setup")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                Setup.this.setVisible(false);
                ControlPanel.main(null);
                ResizableRender.main(null);
            }
        });
        this.btnFinish.setEnabled(false);
        this.btnFinish.setBounds(10, 539, 618, 23);
        this.contentPane.add(this.btnFinish);
        final JLabel lblAdamFrank = new JLabel("Adam Frank 2019. Open Source! | github.com/adamf59");
        lblAdamFrank.setHorizontalAlignment(0);
        lblAdamFrank.setBounds(0, 571, 638, 14);
        this.contentPane.add(lblAdamFrank);
    }
    
    public static void computeSetupParameter(final float closest, final float farthest) {
        DepthFrameBuffer.dist_highest_mountian = closest;
        DepthFrameBuffer.dist_lowest_valley = farthest;
        DepthFrameBuffer.dist_sea_level = (farthest + closest) / 2.0f;
        DepthFrameBuffer.dist_range_above_sea_level = (closest - DepthFrameBuffer.dist_sea_level) * -1.0f;
        DepthFrameBuffer.dist_score_increment = 544.0f / DepthFrameBuffer.dist_range_above_sea_level;
        DepthFrameBuffer.frame_center_x = (float)(ControlPanel.myKinect.getDepthWidth() / 2);
        DepthFrameBuffer.frame_center_y = (float)(ControlPanel.myKinect.getDepthHeight() / 2);
        System.out.println("----------:");
        System.out.println("Setup Parameters: ");
        System.out.println("Highest: " + closest);
        System.out.println("Lowest: " + farthest);
        System.out.println("Sea Level: " + DepthFrameBuffer.dist_sea_level);
        System.out.println("Range-ASL: " + DepthFrameBuffer.dist_range_above_sea_level);
        System.out.println("Score Increment: " + DepthFrameBuffer.dist_score_increment);
        System.out.println("Center Coordinites: " + DepthFrameBuffer.frame_center_x + ", " + DepthFrameBuffer.frame_center_y);
        System.out.println("----------:");
        Setup.lblHighestRecordedMountain.setText("Highest Recorded Mountain: " + DepthFrameBuffer.dist_highest_mountian);
        Setup.lblLowestRecordedValley.setText("Lowest Recorded Valley: " + DepthFrameBuffer.dist_lowest_valley);
        Setup.lblSeaLevel.setText("Sea Level: " + DepthFrameBuffer.dist_sea_level);
        Setup.lblRangeaboveseaLevel.setText("Range-Above-Sea Level: " + DepthFrameBuffer.dist_range_above_sea_level);
    }
}
