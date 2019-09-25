// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.kinect;

import javax.media.opengl.GL2;
import edu.ufl.digitalworlds.j4k.VideoFrame;
import edu.ufl.digitalworlds.opengl.OpenGLPanel;

public class VideoPanel extends OpenGLPanel
{
    VideoFrame videoTexture;
    
    public void setup() {
        final GL2 gl = this.getGL2();
        gl.glEnable(2884);
        final float[] light_model_ambient = { 0.3f, 0.3f, 0.3f, 1.0f };
        final float[] light0_diffuse = { 0.9f, 0.9f, 0.9f, 0.9f };
        final float[] light0_direction = { 0.0f, -0.4f, 1.0f, 0.0f };
        gl.glEnable(2977);
        gl.glShadeModel(7425);
        gl.glLightModeli(2897, 0);
        gl.glLightModeli(2898, 0);
        gl.glLightModelfv(2899, light_model_ambient, 0);
        gl.glLightfv(16384, 4609, light0_diffuse, 0);
        gl.glLightfv(16384, 4611, light0_direction, 0);
        gl.glEnable(16384);
        gl.glEnable(2903);
        gl.glEnable(2896);
        gl.glColor3f(0.9f, 0.9f, 0.9f);
        this.videoTexture = new VideoFrame();
        this.background(0.0, 0.0, 0.0);
    }
    
    public void draw() {
        final GL2 gl = this.getGL2();
        this.pushMatrix();
        gl.glDisable(2896);
        gl.glEnable(3553);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        this.videoTexture.use(gl);
        this.translate(0.0, 0.0, -2.2);
        this.rotateZ(180.0);
        this.image(2.6666666666666665, 2.0);
        this.popMatrix();
    }
}
