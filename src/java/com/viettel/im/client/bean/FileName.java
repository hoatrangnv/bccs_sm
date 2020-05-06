package com.viettel.im.client.bean;

/**
 *
 * @author kdvt_thinhdd4
 */
public class FileName {

    private String fullPath;
    private char pathSeparator, extensionSeparator;

    public FileName(String str, char sep, char ext) {
        fullPath = str;
        pathSeparator = sep;
        extensionSeparator = ext;
    }

    public String extension() {
        int dot = fullPath.lastIndexOf(extensionSeparator);
        return fullPath.substring(dot + 1);
    }

    public String getFileName() { // gets filename with extension
        int sep = fullPath.lastIndexOf(pathSeparator);
        // tutm1 fix loi dau \ doi voi local
        if (sep < 0 && pathSeparator == '/') {
            sep = fullPath.lastIndexOf('\\');
        }
        if (sep < 0 && pathSeparator == '\\') {
            sep = fullPath.lastIndexOf("/");
        }
        // end tutm1
        return fullPath.substring(sep + 1);
    }

    public String getFileNameWithoutExt() { // gets filename without extension
        int dot = fullPath.lastIndexOf(extensionSeparator);
        int sep = fullPath.lastIndexOf(pathSeparator);
        // tutm1 fix loi dau \ doi voi local
        if (sep < 0 && pathSeparator == '/') {
            sep = fullPath.lastIndexOf('\\');
        }
        if (sep < 0 && pathSeparator == '\\') {
            sep = fullPath.lastIndexOf("/");
        }
        // end tutm1
        return fullPath.substring(sep + 1, dot);
    }

    public String path() {
        int sep = fullPath.lastIndexOf(pathSeparator);
        // tutm1 fix loi dau \ doi voi local
        if (sep < 0 && pathSeparator == '/') {
            sep = fullPath.lastIndexOf('\\');
        }
        if (sep < 0 && pathSeparator == '\\') {
            sep = fullPath.lastIndexOf("/");
        }
        // end tutm1
        return fullPath.substring(0, sep);
    }
}
