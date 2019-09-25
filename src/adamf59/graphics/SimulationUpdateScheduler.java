// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.graphics;

import edu.ufl.digitalworlds.j4k.DepthMap;
import adamf59.kinect.Kinect;

public class SimulationUpdateScheduler implements Runnable
{
    public static boolean enabled;
    
    static {
        SimulationUpdateScheduler.enabled = true;
    }
    
    @Override
    public void run() {
        while (true) {
            if (SimulationUpdateScheduler.enabled) {
                final DepthMap depthMap = Kinect.getFrame();
                new DepthFrameBuffer(depthMap, 100);
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
