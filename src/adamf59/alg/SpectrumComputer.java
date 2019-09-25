// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.alg;

import org.opencv.core.Scalar;

public class SpectrumComputer
{
    public static Scalar computeColor(final int score) {
        final int target = score;
        int iterations = 0;
        final int blue = 29;
        int green = 115;
        int red = 29;
        for (int i = 115; i < 239; ++i) {
            ++iterations;
            green = i;
            if (iterations == target) {
                return new Scalar((double)blue, (double)green, (double)red);
            }
        }
        for (int i = 29; i < 239; ++i) {
            ++iterations;
            red = i;
            if (iterations == target) {
                return new Scalar((double)blue, (double)green, (double)red);
            }
        }
        for (int i = 239; i > 29; --i) {
            ++iterations;
            green = i;
            if (iterations == target) {
                return new Scalar((double)blue, (double)green, (double)red);
            }
        }
        return new Scalar((double)blue, (double)green, (double)red);
    }
}
