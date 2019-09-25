// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.graphics;

import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Font;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import org.opencv.core.Core;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import adamf59.kinect.VideoPanel;
import adamf59.kinect.Kinect;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class ControlPanel extends JFrame
{
    private JPanel contentPane;
    public static Kinect myKinect;
    VideoPanel main_panel;
    JPanel p_root;
    boolean didKinectInit;
    public static JLabel dmap_dim;
    public static JLabel cmap_dim;
    public static JLabel previewSimulation;
    public static JLabel lblZeroDistance;
    public static JProgressBar processorProgress;
    public static JLabel lblProcessorReadout;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final ControlPanel frame = new ControlPanel();
                    final Thread t = new Thread(new SimulationUpdateScheduler());
                    t.start();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public ControlPanel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        this.p_root = new JPanel(new BorderLayout());
        this.didKinectInit = false;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.setTitle("Sandbox3D | R2019.9A ");
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 1090, 762);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JLabel lblSandboxdByAdam = new JLabel("Sandbox3D by Adam Frank");
        lblSandboxdByAdam.setFont(new Font("Trebuchet MS", 0, 40));
        lblSandboxdByAdam.setBounds(10, 11, 476, 47);
        this.contentPane.add(lblSandboxdByAdam);
        final JButton btnNewButton = new JButton("Start Simulation");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                SimulationUpdateScheduler.enabled = true;
            }
        });
        btnNewButton.setBounds(10, 69, 224, 23);
        this.contentPane.add(btnNewButton);
        final JButton btnStopSimulation = new JButton("Stop Simulation");
        btnStopSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimulationUpdateScheduler.enabled = false;
            }
        });
        btnStopSimulation.setBounds(262, 69, 224, 23);
        this.contentPane.add(btnStopSimulation);
        final JLabel RA = new JLabel("Visible Preview");
        RA.setBackground(Color.LIGHT_GRAY);
        RA.setHorizontalAlignment(0);
        RA.setFont(new Font("Trebuchet MS", 1, 20));
        RA.setBounds(10, 297, 507, 23);
        this.contentPane.add(RA);
        final JPanel visibleViewerPanel = new JPanel();
        visibleViewerPanel.setBounds(10, 331, 507, 387);
        this.contentPane.add(visibleViewerPanel);
        visibleViewerPanel.setLayout(new BorderLayout(0, 0));
        this.GUIsetup(this.p_root);
        visibleViewerPanel.add(this.p_root, "Center");
        ControlPanel.myKinect.setNearMode(true);
        final JLabel lblKinectSettings = new JLabel("Statistics:");
        lblKinectSettings.setHorizontalAlignment(0);
        lblKinectSettings.setFont(new Font("Trebuchet MS", 0, 20));
        lblKinectSettings.setBackground(Color.LIGHT_GRAY);
        lblKinectSettings.setBounds(10, 103, 476, 23);
        this.contentPane.add(lblKinectSettings);
        final JSeparator separator = new JSeparator();
        separator.setBounds(10, 137, 476, 2);
        this.contentPane.add(separator);
        final JLabel lblDepthMapDimensions = new JLabel("Depth Map Dimensions:");
        lblDepthMapDimensions.setBounds(10, 150, 121, 14);
        this.contentPane.add(lblDepthMapDimensions);
        (ControlPanel.dmap_dim = new JLabel("ERR NO KINECT?")).setBounds(134, 150, 347, 14);
        this.contentPane.add(ControlPanel.dmap_dim);
        final JLabel lblColorMapDimensions = new JLabel("Color Map Dimensions:");
        lblColorMapDimensions.setBounds(10, 175, 121, 14);
        this.contentPane.add(lblColorMapDimensions);
        (ControlPanel.cmap_dim = new JLabel("ERR NO KINECT?")).setBounds(134, 175, 342, 14);
        this.contentPane.add(ControlPanel.cmap_dim);
        final JLabel lblProcessorStatus = new JLabel("Processor Status:");
        lblProcessorStatus.setBounds(10, 200, 121, 14);
        this.contentPane.add(lblProcessorStatus);
        (ControlPanel.processorProgress = new JProgressBar()).setBounds(100, 200, 224, 14);
        this.contentPane.add(ControlPanel.processorProgress);
        (ControlPanel.lblProcessorReadout = new JLabel("IDLE")).setBounds(334, 200, 177, 14);
        this.contentPane.add(ControlPanel.lblProcessorReadout);
        (ControlPanel.previewSimulation = new JLabel("")).setBounds(527, 331, 531, 387);
        this.contentPane.add(ControlPanel.previewSimulation);
        final JLabel lblSimulationPreview = new JLabel("Simulation Preview");
        lblSimulationPreview.setHorizontalAlignment(0);
        lblSimulationPreview.setFont(new Font("Trebuchet MS", 1, 20));
        lblSimulationPreview.setBackground(Color.LIGHT_GRAY);
        lblSimulationPreview.setBounds(527, 297, 531, 23);
        this.contentPane.add(lblSimulationPreview);
        final JButton btnZeroDepthField = new JButton("Set Sea Level");
        btnZeroDepthField.setEnabled(false);
        btnZeroDepthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                DepthFrameBuffer.setFieldZero(Kinect.getZeroDistance());
            }
        });
        btnZeroDepthField.setBounds(744, 34, 121, 23);
        this.contentPane.add(btnZeroDepthField);
        final JCheckBox chckbxApplyBlurring = new JCheckBox("Apply Blurring to Simulation");
        chckbxApplyBlurring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("SimOptions: Updated USE_BLURRING to " + chckbxApplyBlurring.isSelected());
                DepthFrameBuffer.useBlurring = chckbxApplyBlurring.isSelected();
            }
        });
        chckbxApplyBlurring.setBounds(888, 95, 170, 23);
        this.contentPane.add(chckbxApplyBlurring);
        final JCheckBox chckbxShowTopographicalLines = new JCheckBox("Show Contour Lines");
        chckbxShowTopographicalLines.setEnabled(false);
        chckbxShowTopographicalLines.setBounds(744, 121, 131, 23);
        this.contentPane.add(chckbxShowTopographicalLines);
        (ControlPanel.lblZeroDistance = new JLabel("Currently set to: " + DepthFrameBuffer.dist_sea_level)).setForeground(Color.MAGENTA);
        ControlPanel.lblZeroDistance.setBounds(875, 38, 183, 14);
        this.contentPane.add(ControlPanel.lblZeroDistance);
        final JLabel lblSimulationOptions = new JLabel("Simulation Options:");
        lblSimulationOptions.setHorizontalAlignment(0);
        lblSimulationOptions.setFont(new Font("Trebuchet MS", 0, 20));
        lblSimulationOptions.setBackground(Color.LIGHT_GRAY);
        lblSimulationOptions.setBounds(716, 11, 342, 23);
        this.contentPane.add(lblSimulationOptions);
        final JCheckBox chckbxApplySmoothing = new JCheckBox("Apply Smoothing");
        chckbxApplySmoothing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                DepthFrameBuffer.applySmoothing = chckbxApplySmoothing.isSelected();
                System.out.println("SimOptions: Updated USE_SMOOTHING to " + chckbxApplySmoothing.isSelected());
            }
        });
        chckbxApplySmoothing.setBounds(744, 95, 142, 23);
        this.contentPane.add(chckbxApplySmoothing);
        ControlPanel.dmap_dim.setText(String.valueOf(ControlPanel.myKinect.getDepthWidth()) + " x " + ControlPanel.myKinect.getDepthHeight());
        ControlPanel.cmap_dim.setText(String.valueOf(ControlPanel.myKinect.getColorWidth()) + " x " + ControlPanel.myKinect.getColorHeight());
        final JLabel label = new JLabel((String)null);
        label.setBounds(194, 222, 292, 14);
        this.contentPane.add(label);
        final JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Basic Score-Based Topography Coloration" }));
        comboBox.setBounds(800, 63, 261, 20);
        this.contentPane.add(comboBox);
        final JLabel lblAlgorithm = new JLabel("Algorithm:");
        lblAlgorithm.setBounds(744, 66, 64, 14);
        this.contentPane.add(lblAlgorithm);
    }
    
    public void GUIsetup(final JPanel p_root) {
        System.out.println("Initializing Kinect viewer panels");
        this.main_panel = new VideoPanel();
        ControlPanel.myKinect.setViewer(this.main_panel);
        p_root.add((Component)this.main_panel, "Center");
        this.didKinectInit = true;
    }
}
