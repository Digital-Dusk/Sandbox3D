// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class SplashScreen extends JFrame
{
    private JPanel contentPane;
    private static SplashScreen frame;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    SplashScreen.access$0(new SplashScreen());
                    SplashScreen.frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public SplashScreen() {
        this.setResizable(false);
        this.setTitle("LectureStream Desktop");
        this.setUndecorated(true);
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 580, 343);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(SplashScreen.class.getResource("/assets/splash.png")));
        label.setBounds(0, 0, 580, 343);
        this.contentPane.add(label);
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }
    
    public static void shadow() {
        SplashScreen.frame.setVisible(false);
    }
    
    static /* synthetic */ void access$0(final SplashScreen frame) {
        SplashScreen.frame = frame;
    }
}
