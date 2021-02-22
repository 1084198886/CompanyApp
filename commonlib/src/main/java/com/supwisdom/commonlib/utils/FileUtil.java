package com.supwisdom.commonlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import junit.framework.Assert;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author zzq
 * @version 1.0.1
 * @date 2018/4/16.
 * @desc SD卡文件存储
 */

public class FileUtil {
    private static final String TAG = "FileUtil";
    private static final String ROOT_DIR = "/dcpos";
    private static final String ROOT_FACE_DIR = "/face/";
    private static final String IMG_ROOT_DIR = ROOT_DIR + "/images/";
    private static final String UPGRADE_FILE_DIR = ROOT_DIR + "/upgrade/";
    private static final String LOG_FILE_DIR = ROOT_DIR + "/log/";
    private static final String LOG_FILE_PRE = "log-";
    private static final String CRASH_FILE_DIR = ROOT_DIR + "/crash/";
    private static final String CRASH_FILE_PRE = "crash-";

    public static String getFaceImagePath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + ROOT_FACE_DIR + "headpic");
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            return rootFile.getAbsolutePath() + "/";
        }
        return null;
    }

    public static String getDeepFacephotoPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + ROOT_FACE_DIR + "deepfacephoto");
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            return rootFile.getAbsolutePath() + "/";
        }
        return "";
    }

    public static String getFacephotoPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + ROOT_FACE_DIR + "facephoto");
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            return rootFile.getAbsolutePath() + "/";
        }
        return "";
    }

    public static String getFacetPreviewphotoPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + ROOT_FACE_DIR + "facepreviewphoto");
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            return rootFile.getAbsolutePath() + "/";
        }
        return "";
    }

    /**
     * @param delFileDir 要删除的文件目录
     * @param beforeday  删除 beforeday 天前的
     * @desc 删除日志
     */
    public static void deleteLogFile(File delFileDir, int beforeday) {
        LogUtil.d(TAG, "开始删除日志文件,目录=" + delFileDir.getName() + ",beforeday=" + beforeday);
        if (!delFileDir.exists()) {
            return;
        }
        String logName = "log-" + DateUtil.getDayDateNoFormatBefore(beforeday) + ".txt";
        LogUtil.d(TAG, beforeday + "天前的日志文件名=" + logName);

        File[] files = delFileDir.listFiles();
        for (File file : files) {
            String fullFileName = file.getAbsolutePath();
            String filename = fullFileName.substring(fullFileName.lastIndexOf('/') + 1);
            if (file.isFile() && filename.startsWith("log") && filename.endsWith(".txt")) {
                if (filename.compareTo(logName) < 0) {
                    boolean delRet = file.delete();
                    LogUtil.i(TAG, "删除日志文件:" + filename + ",返回：" + delRet);
                }
            }
        }
    }

    /**
     * @param bitmap
     */
    public static boolean saveBitmap(Bitmap bitmap, String imagePath, int quality, boolean needRecycle) {
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            if (!imageFile.delete()) {
                LogUtil.e(TAG, "saveBitmap 删除原文件失败");
            }
        }
        FileOutputStream fos = null;
        try {
            if (!imageFile.createNewFile()) {
                LogUtil.e(TAG, "创建文件失败");
                return false;
            }

            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.flush();
            if (needRecycle) {
                bitmap.recycle();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "saveBitmap异常:" + e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Bitmap getBitmap(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        return bitmap;
    }

    /**
     * 检测SD卡是否存在
     */
    public static boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    public static String getDestAPKPath() {
        if (!checkSDcard()) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/dc_empdir";
    }


    public static void deleteFile(File file) {
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File f = files[i];
                    deleteFile(f);
                }
                file.delete();
            } else if (file.exists()) {
                file.delete();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取升级文件路径
     *
     * @param fileName
     * @return
     */
    public static File getUpdateFile(String fileName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + UPGRADE_FILE_DIR);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            File retFile = new File(rootFile, fileName);
            if (!retFile.exists()) {
                try {
                    retFile.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return retFile;
        }
        return null;
    }

    public static File getImageFile(String filename) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + IMG_ROOT_DIR);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            if (!CommonUtil.isEmpty(filename)) {
                if (filename.indexOf(".") == -1) {
                    filename += ".jpg";
                }
            }
            File retFile = new File(rootFile, filename);
            return retFile;
        }
        return null;
    }

    public static File getImageRootFile() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + IMG_ROOT_DIR);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            return rootFile;
        }
        return null;
    }

    /**
     * 获取日志记录目录
     */
    public static File getLogFile() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = new File(Environment.getExternalStorageDirectory() + ROOT_DIR + "/log");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

    /**
     * 获取数据库目录
     */
    public static String getDbPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/supwisdom";
        }
        return null;
    }

    /**
     * 获取应用数据库目录
     */
    public static String getAppDataBasePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return context.getFilesDir().getAbsolutePath().replace("files", "") + "databases";
        }
        return null;
    }

    /**
     * 获取日志根目录
     */
    public static File getLogRootFile() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = new File(Environment.getExternalStorageDirectory() + ROOT_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

    /**
     * 目录拷贝
     *
     * @param srcDir
     * @param desDir
     */
    public static void copyDir(File srcDir, File desDir) {
        File[] fileArray = srcDir.listFiles();
        if (fileArray != null) {
            for (File srcFile : fileArray) {
                File desFile = new File(desDir, srcFile.getName());
                if (srcFile.isDirectory()) {
                    desFile.mkdirs();
                    copyDir(srcFile, desFile);
                } else {
                    copyFile(srcFile, desFile);
                }
            }
        }
    }

    /**
     * 文件拷贝
     *
     * @param in
     * @param out
     */
    public static void copyFile(File in, File out) {
        Assert.assertNotNull(in);
        Assert.assertNotNull(out);

        BufferedInputStream fis = null;
        BufferedOutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(in));
            fos = new BufferedOutputStream(new FileOutputStream(out));
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "FileUtil 文件拷贝异常:" + e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e(TAG, "FileUtil 文件拷贝异常:" + e.getMessage());
            }
        }
    }

    public static void removeLogFile(int beforeday) {
        removeFile(LOG_FILE_DIR, LOG_FILE_PRE, beforeday);
    }

    public static void removeCrashFile(int beforeday) {
        removeFile(CRASH_FILE_DIR, CRASH_FILE_PRE, beforeday);
    }

    public static void writeCrashFile(String msg) {
        String pathfile = CRASH_FILE_DIR + CRASH_FILE_PRE + DateUtil.getNowDateNoFormat() + ".txt";
        writeFile(pathfile, msg, true);
    }

    public static void writeLogFile(String msg) {
        String pathfile = LOG_FILE_DIR + LOG_FILE_PRE + DateUtil.getNowDateNoFormat() + ".txt";
        writeFile(pathfile, msg, true);
    }

    private static void writeFile(String targetPath, String msg, boolean append) {
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            File targetFile = new File(Environment.getExternalStorageDirectory() + targetPath);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            try {
                OutputStreamWriter osw = new OutputStreamWriter(
                        new FileOutputStream(targetFile, append), "utf-8");
                try {
                    osw.write(DateUtil.getNow5() + " " + msg);
                    osw.write("</br>\n");
                    osw.flush();
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void removeFile(String targetPath, String prefile, int beforeday) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + targetPath);
            if (!rootFile.exists()) {
                return;
            }
            String logname = prefile + DateUtil.getDayDateNoFormatBefore(beforeday) + ".txt";
            File[] files = rootFile.listFiles();
            for (File file : files) {
                String fullfilename = file.getAbsolutePath();
                String filename = fullfilename.substring(fullfilename.lastIndexOf('/') + 1);
                if (filename.startsWith(prefile) &&
                        filename.endsWith(".txt")) {
                    if (filename.compareTo(logname) < 0) {
                        file.delete();
                    }
                }
            }
        }
    }

    private static File createFile(String path, String fileName) {
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            File rootFile = new File(Environment.getExternalStorageDirectory() + path);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            File retFile = new File(rootFile, fileName);
            if (!retFile.exists()) {
                try {
                    retFile.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return retFile;
        }
        return null;
    }


    /**
     * 将Bitmap 图片保存到本地路径，并返回路径
     *
     * @param fileName 文件名称
     * @param bitmap   图片
     * @param资源类型，参照 MultimediaContentType 枚举，根据此类型，保存时可自动归类
     */
    public static String saveFile(Context c, String fileName, Bitmap bitmap) {
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    /**
     * Bitmap 转 字节数组
     *
     * @param bm
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/cxs/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

}
