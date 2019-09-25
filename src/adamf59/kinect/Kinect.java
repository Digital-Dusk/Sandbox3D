// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.kinect;

import adamf59.graphics.ControlPanel;
import edu.ufl.digitalworlds.j4k.DepthMap;
import edu.ufl.digitalworlds.j4k.J4KSDK;

public class Kinect extends J4KSDK
{
    VideoPanel viewer;
    private static DepthMap recentMap;
    
    public Kinect() {
        this.viewer = null;
    }
    
    public void setViewer(final VideoPanel viewer) {
        this.viewer = viewer;
    }
    
    public void onSkeletonFrameEvent(final boolean[] skeleton_tracked, final float[] positions, final float[] orientations, final byte[] joint_status) {
    }
    
    public void onColorFrameEvent(final byte[] color_frame) {
        if (this.viewer == null || this.viewer.videoTexture == null) {
            return;
        }
        this.viewer.videoTexture.update(this.getColorWidth(), this.getColorHeight(), color_frame);
    }
    
    public void onDepthFrameEvent(final short[] depth_frame, final byte[] body_index, final float[] XYZ, final float[] uv) {
        final float[] a = this.getAccelerometerReading();
        final DepthMap map = new DepthMap(this.getDepthWidth(), this.getDepthHeight(), XYZ);
        map.setMaximumAllowedDeltaZ(0.5);
        map.setUV(uv);
        Kinect.recentMap = map;
    }
    
    public static DepthMap getFrame() {
        return Kinect.recentMap;
    }
    
    public static float getZeroDistance() {
        final DepthMap dm = getFrame();
        final int y = 100;
        final int x = 100;
        final int idx = y * dm.getWidth() + x;
        return dm.realZ[idx];
    }
    
    public static void init() {
        System.out.println("Searching for Kinect on USB Bus");
        (ControlPanel.myKinect = new Kinect()).setNearMode(true);
        if (!ControlPanel.myKinect.start(4107)) {
            System.out.println("Failed to discover a valid kinect device. Is one connected? Is the Kinect SDK installed? Is AC power connected?");
        }
        System.out.println("Kinect discovered! Type: Kinect" + ControlPanel.myKinect.getDeviceType());
    }
}
