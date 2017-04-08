package com.example.ankhiya.findsimilarimages.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;

/**
 * Created by ankhiya on 3/18/17.
 * <p>
 * this class provides you Hash of any file.
 * To check whether two files are same or not you can compare by checking there hash
 * </p>
 */

public class ImageHash {

    public static String md5ForFile(String filePath) {
        String md5 = "";
        try {
            byte[] bytes = new byte[1024];
            int read = 0;
            InputStream is = new FileInputStream(filePath); // IO
            MessageDigest digest = MessageDigest.getInstance("MD5"); // MessageDigest
            while ((read = is.read(bytes)) != -1) {
                digest.update(bytes, 0, read);
            }
            byte[] messageDigest = digest.digest();
            md5 = convertHashToString(messageDigest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    private static String convertHashToString(byte[] md5Bytes) {
        String returnVal = "";
        for (int i = 0; i < md5Bytes.length; i++) {
            returnVal += Integer.toString(( md5Bytes[i] & 0xff ) + 0x100, 16).substring(1);
        }
        return returnVal.toUpperCase();
    }

    public static boolean isFileContentTypeImage(Context context, String filePath) {
        boolean imageType = false;
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (type != null && type.contains("image")) {
            imageType = true;
        }
        return imageType;
    }
}
