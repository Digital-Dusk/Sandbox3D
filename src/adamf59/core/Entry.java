// 
// Decompiled by Procyon v0.5.36
// 

package adamf59.core;

import adamf59.graphics.Setup;
import adamf59.graphics.SplashScreen;

public class Entry
{
    public static void main(final String[] args) {
        System.out.println("==============Sandbox 3D==============");
        System.out.println("Version R2019.9A | Released on 9/10/19 | Coverage: 100% :)");
        System.out.println("Developed by Adam Frank. Licenced by Digital Dusk. All Rights Reserved.");
        System.out.println("This is open source software! Source code and license information is available at github.com/adamf59.");
        System.out.println("======================================");
        SplashScreen.main(null);
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        SplashScreen.shadow();
        Setup.main(null);
    }
}
