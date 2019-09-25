// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.graphics;

import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.MatOfByte;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import java.awt.Image;
import javax.swing.ImageIcon;
import org.opencv.core.Core;
import org.opencv.core.Size;
import adamf59.alg.SpectrumComputer;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.CvType;
import adamf59.kinect.Kinect;
import org.opencv.core.Scalar;
import org.opencv.core.Mat;
import edu.ufl.digitalworlds.j4k.DepthMap;

public class DepthFrameBuffer
{
    DepthMap dm;
    Mat depthMat;
    public static final int THRESHOLD_XMIN = 120;
    public static final int THRESHOLD_XMAX = 375;
    public static final int THRESHOLD_YMIN = 65;
    public static final int THRESHOLD_YMAX = 260;
    public static float dist_highest_mountian;
    public static float dist_lowest_valley;
    public static float dist_range_above_sea_level;
    public static float dist_sea_level;
    public static float dist_score_increment;
    public static float frame_center_x;
    public static float frame_center_y;
    public static boolean applySmoothing;
    public static boolean useBlurring;
    public static float fieldZeroDistance;
    public static final Scalar _WATER;
    int setupStage;
    
    static {
        DepthFrameBuffer.applySmoothing = true;
        DepthFrameBuffer.useBlurring = true;
        DepthFrameBuffer.fieldZeroDistance = 0.0f;
        _WATER = new Scalar(199.0, 45.0, 14.0);
    }
    
    private void init() {
        final DepthMap dm = Kinect.getFrame();
    }
    
    public DepthFrameBuffer(final DepthMap dm, final int setupStage) {
        this.dm = dm;
        this.setupStage = setupStage;
        if (setupStage == 0) {
            this.setupCalibration();
        }
        else {
            final int width = 255;
            final int height = 195;
            (this.depthMat = new Mat(height, width, CvType.CV_64FC4)).setTo(DepthFrameBuffer._WATER);
            this.createMat();
        }
    }
    
    private void createMat() {
        int inrange = 0;
        if (DepthFrameBuffer.applySmoothing) {
            this.dm.smooth(10);
        }
        int total_iterations = 0;
        float roundMaximum = 0.0f;
        float roundMinimum = 100.0f;
        int xmax = 0;
        int ymax = 0;
        for (int x = 1; x < this.dm.getWidth(); ++x) {
            for (int y = 1; y < this.dm.getHeight(); ++y) {
                ++total_iterations;
                if (this.setupStage != 1) {
                    ControlPanel.processorProgress.setValue((int)Math.round(total_iterations / (double)(this.dm.getHeight() * this.dm.getWidth()) * 100.0));
                    ControlPanel.lblProcessorReadout.setText("Computing matrix #" + total_iterations);
                }
                if (x > 120 && x < 375 && y > 65 && y < 260) {
                    ++inrange;
                    final int idx = y * this.dm.getWidth() + x;
                    final float uncorrectedHeight = this.dm.realZ[idx];
                    if (uncorrectedHeight > DepthFrameBuffer.dist_sea_level) {
                        Imgproc.rectangle(this.depthMat, new Point((double)x, (double)y), new Point((double)x, (double)y), DepthFrameBuffer._WATER, -1);
                    }
                    else if (uncorrectedHeight == 0.0) {
                        SpectrumComputer.computeColor(544);
                    }
                    else {
                        final int score = Math.round(DepthFrameBuffer.dist_score_increment * (DepthFrameBuffer.dist_sea_level - uncorrectedHeight));
                        if (score > 15) {
                            Imgproc.rectangle(this.depthMat, new Point((double)(x - 120), (double)(y - 65)), new Point((double)(x - 120), (double)(y - 65)), SpectrumComputer.computeColor(score), -1);
                        }
                    }
                    if (uncorrectedHeight > roundMaximum) {
                        roundMaximum = uncorrectedHeight;
                        xmax = x;
                        ymax = y;
                    }
                    if (uncorrectedHeight < roundMinimum && uncorrectedHeight != 0.0) {
                        roundMinimum = uncorrectedHeight;
                    }
                }
            }
        }
        if (DepthFrameBuffer.useBlurring) {
            Imgproc.GaussianBlur(this.depthMat, this.depthMat, new Size(5.0, 5.0), 0.0);
        }
        try {
            if (this.setupStage != 1) {
                Imgproc.circle(this.depthMat, new Point(120.0, 65.0), 4, new Scalar(255.0, 0.0, 0.0));
                Imgproc.circle(this.depthMat, new Point(375.0, 260.0), 4, new Scalar(255.0, 0.0, 0.0));
                System.out.println("Adding Rect to ROA");
                Core.flip(this.depthMat, this.depthMat, 1);
                ControlPanel.previewSimulation.setIcon(new ImageIcon(Mat2BufferedImage(this.depthMat)));
                ResizableRender.injectFrame(Mat2BufferedImage(this.depthMat));
                ControlPanel.lblProcessorReadout.setText("Done. Displaying heightmap");
                System.out.println("Thresholded total: " + inrange);
            }
            else {
                Setup.previewPane.setIcon(new ImageIcon(Mat2BufferedImage(this.depthMat)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupCalibration() {
        this.dm.smooth(10);
        int total_iterations = 0;
        float roundMaximum = 0.0f;
        float roundMinimum = 100.0f;
        int xmax = 0;
        int ymax = 0;
        for (int x = 1; x < this.dm.getWidth(); ++x) {
            for (int y = 1; y < this.dm.getHeight(); ++y) {
                ++total_iterations;
                if (x > 120 && x < 375 && y > 65 && y < 260) {
                    final int idx = y * this.dm.getWidth() + x;
                    final float uncorrectedHeight = this.dm.realZ[idx];
                    if (uncorrectedHeight > roundMaximum) {
                        roundMaximum = uncorrectedHeight;
                        xmax = x;
                        ymax = y;
                    }
                    if (uncorrectedHeight < roundMinimum && uncorrectedHeight != 0.0) {
                        roundMinimum = uncorrectedHeight;
                    }
                }
            }
        }
        System.err.println("Round maximum (Farthest) height: " + roundMaximum + " AT " + xmax + ", " + ymax);
        System.err.println("Round minimum (Closest) height: " + roundMinimum);
        Setup.computeSetupParameter(roundMinimum, roundMaximum);
    }
    
    static BufferedImage Mat2BufferedImage(final Mat matrix) throws Exception {
        final MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        final byte[] ba = mob.toArray();
        final BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
        return bi;
    }
    
    public static void setFieldZero(final float distance) {
        ControlPanel.lblZeroDistance.setText("Zero Distance: " + distance);
        DepthFrameBuffer.fieldZeroDistance = distance;
    }
    
    public static float calculateCorrectedHeight(final float uncorrectedHeight, final int x, final int y) {
        return 0.0f;
    }
    
    public static void progressPercentage(final int done, final int total) {
        final int size = 5;
        final String iconLeftBoundary = "[";
        final String iconDone = "=";
        final String iconRemain = ".";
        final String iconRightBoundary = "]";
        if (done > total) {
            throw new IllegalArgumentException();
        }
        final int donePercents = 100 * done / total;
        final int doneLength = size * donePercents / 100;
        final StringBuilder bar = new StringBuilder(iconLeftBoundary);
        for (int i = 0; i < size; ++i) {
            if (i < doneLength) {
                bar.append(iconDone);
            }
            else {
                bar.append(iconRemain);
            }
        }
        bar.append(iconRightBoundary);
        System.out.print("\r" + (Object)bar + " " + donePercents + "%");
        if (done == total) {
            System.out.print("\n");
        }
    }
}
