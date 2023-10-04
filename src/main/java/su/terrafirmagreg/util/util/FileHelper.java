package su.terrafirmagreg.util.util;

import java.io.Closeable;
import java.io.IOException;

public class FileHelper {

    private FileHelper() {

    }

    public static void closeSilently(Closeable closeable) {

        if (closeable != null) {

            try {
                closeable.close();
            } catch (IOException e) {
                //
            }
        }
    }

}
