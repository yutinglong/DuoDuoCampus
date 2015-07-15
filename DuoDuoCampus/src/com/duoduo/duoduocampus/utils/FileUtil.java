package com.duoduo.duoduocampus.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.util.ByteArrayBuffer;

import com.duoduo.duoduocampus.system.status.ExternalStorageReceiver;

/**
 * @description: 文件读写相关类
 * @company: 多多校园
 * @author yutinglong
 * @version 1.0
 * @created 2015-7-15 下午6:33:02
 * @changeRecord
 */
public class FileUtil {
    private static final int BUFFER_SIZE = 4096;
    
    /**
     * 把str写入文件
     * @param filePath 文件路径
     * @param str json字符串
     * @return 写入成功与否
     * @author yutinglong
     * @since 2013-01-24
     */
    public static boolean writeString(String filePath, String str) {
        FileOutputStream out = null;
        try {
            File file = makeDIRAndCreateFile(filePath);
            out = new FileOutputStream(file);
            out.write(str.getBytes());
            out.flush();
        } catch (IOException e) {
            LogUtil.e(LogUtil.getTraceInfo() + e.toString(), e);
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    LogUtil.e(LogUtil.getTraceInfo() + e.toString(), e);
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 创建目录和文件， 如果目录或文件不存在，则创建出来
     * 
     * @param filePath 文件路径
     * @return 创建后的文件
     * @throws IOException
     * @author yutinglong
     * @since 2013-01-24
     */
    public static synchronized File makeDIRAndCreateFile(String filePath) throws IOException {
        LogUtil.d(LogUtil.getTraceInfo() + "filePath--->" + filePath);
        
        if(filePath == null || "".equals(filePath)) {
            return null;
        }
        
        if(filePath.startsWith(MobileUtil.getSdCardPath()) && !ExternalStorageReceiver.isSDCardMounted) {
            return null;
        }
        
        File file = new File(filePath);
        String parent = file.getParent();
        File parentFile = new File(parent);
        if (!parentFile.exists()) {
            if (parentFile.mkdirs()) {
                file.createNewFile();
            } else {
                throw new IOException("创建目录失败！");
            }
        } else {
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        return file;
    }
    
    public static byte[] gzipDecoder(byte[] src) {
        ByteArrayInputStream is = new ByteArrayInputStream(src);
        GZIPInputStream gis = null;
        byte[] desbyte = null;

        try {
            gis = new GZIPInputStream(is);
            ByteArrayBuffer buffer = new ByteArrayBuffer(0);
            int byteRead = 0;
            byte[] tempBytes = new byte[BUFFER_SIZE];

            while ((byteRead = gis.read(tempBytes)) != -1) {
                buffer.append(tempBytes, 0, byteRead);
            }
            desbyte = buffer.buffer();
            buffer.clear();

        } catch (IOException e) {
            LogUtil.e("Gzip exception: " + e.toString(), e);
            return null;
        } finally {
            try {
                is.close();
                if (gis != null) {
                    gis.close();
                }
            } catch (IOException e) {
                LogUtil.e(LogUtil.getTraceInfo() + e.toString(), e);
                return null;
            }
        }

        return desbyte;
    }
    
    public static ByteArrayBuffer readBytesFromFile(ByteArrayBuffer bytBuffer, File file) {
        if(bytBuffer == null || file == null) {
            return null;
        }
        if (!file.exists()) {
            return null;
        }
        InputStream inputStream = null;
        byte[] byt = new byte[Constants.READ_BUFFER];
        try {
            inputStream = new FileInputStream(file);
            int num = 0;
            while ((num = inputStream.read(byt, 0, Constants.READ_BUFFER)) != -1) {
                bytBuffer.append(byt, 0, num);
            }
        } catch (FileNotFoundException e) {
            LogUtil.e(e.toString(), e);
        } catch (IOException e) {
            LogUtil.e(e.toString(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogUtil.e(e.toString(), e);
                    return null;
                }
            }
        }

        return bytBuffer;
    }
}
