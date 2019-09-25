// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.graphics;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class ResizableRender extends JFrame
{
    private JPanel contentPane;
    public static JLabel preview;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final ResizableRender frame = new ResizableRender();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public ResizableRender() {
        this.setTitle("Resizable Rendering");
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 520, 421);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(new BorderLayout(0, 0));
        ResizableRender.preview = new JLabel("");
        this.contentPane.add(ResizableRender.preview);
    }
    
    public static void injectFrame(final BufferedImage img) {
        final Image dimg = img.getScaledInstance(ResizableRender.preview.getWidth(), ResizableRender.preview.getHeight(), 4);
        ResizableRender.preview.setIcon(new ImageIcon(dimg));
    }
}
