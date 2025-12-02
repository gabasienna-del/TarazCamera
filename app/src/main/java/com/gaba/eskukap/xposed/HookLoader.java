package com.gaba.eskukap.xposed;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class HookLoader implements IXposedHookLoadPackage {
    private static final String META_PATH = "/sdcard/CamYan/selected.meta";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        try {
            File meta = new File(META_PATH);
            if (!meta.exists()) {
                XposedBridge.log("CamYan: meta not found for package: " + lpparam.packageName);
                return;
            }
            byte[] pathBytes = new byte[(int) meta.length()];
            try (InputStream pis = new FileInputStream(meta)) {
                pis.read(pathBytes);
            }
            String imgPath = new String(pathBytes, "UTF-8").trim();
            File img = new File(imgPath);
            if (!img.exists()) {
                XposedBridge.log("CamYan: selected image not found: " + imgPath);
                return;
            }
            byte[] imgBytes = new byte[(int) img.length()];
            try (InputStream is = new FileInputStream(img)) {
                int read = 0;
                while (read < imgBytes.length) {
                    int r = is.read(imgBytes, read, imgBytes.length - read);
                    if (r < 0) break;
                    read += r;
                }
            }
            XposedBridge.log("CamYan: loaded image bytes size=" + imgBytes.length + " for package=" + lpparam.packageName);
        } catch (Throwable t) {
            XposedBridge.log("CamYan: HookLoader error: " + t.getMessage());
        }
    }
}
