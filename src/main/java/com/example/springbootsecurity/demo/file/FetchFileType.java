package com.example.springbootsecurity.demo.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: huang lang
 * @description: 根据文件头信息获取文件的真正类型
 * @date: 2022/1/13 14:18
 */
public class FetchFileType {

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();
    private static FileInputStream is;

    static {
        getAllFileType(); // 初始化文件类型信息
    }

    /**
     * 常用文件格式
     */
    private static void getAllFileType() {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg");
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
        FILE_TYPE_MAP.put("47494638396126026f01", "gif");
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp");
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp");
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp");
        FILE_TYPE_MAP.put("41433130313500000000", "dwg");
        FILE_TYPE_MAP.put("3c21444f435459504520", "html");
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm");
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css");
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js");
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");
        FILE_TYPE_MAP.put("38425053000100000000", "psd");
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");
        // MS Excel 注意：word、msi 、Visio 绘图和 excel的文件头一样
        // WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc/vsd/");
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf");
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb");
        FILE_TYPE_MAP.put("464c5601050000000900", "flv");
        FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg");
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv");
        FILE_TYPE_MAP.put("52494646e27807005741", "wav");
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid");
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");
        FILE_TYPE_MAP.put("7061636b616765207765", "java");
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");
        FILE_TYPE_MAP.put("49545346030000006000", "chm");
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("6D6F6F76", "mov");
        FILE_TYPE_MAP.put("FF575043", "wpd");
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx");
        FILE_TYPE_MAP.put("2142444E", "pst");
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf");
        FILE_TYPE_MAP.put("E3828596", "pwl");
        FILE_TYPE_MAP.put("2E7261FD", "ram");
        FILE_TYPE_MAP.put("null", null);
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据指定文件的文件头判断其文件类型
     *
     * @param filePah 文件路径
     * @return 文件类型，如 pdf
     */
    public static String getFileType(String filePah) {
        String result = null;
        try {
            is = new FileInputStream(filePah);
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);
            if (fileCode == null) {
                return null;
            }
            for (String key : FILE_TYPE_MAP.keySet()) {
                // 验证前6个字符比较
                if (key.toLowerCase().startsWith(fileCode.toLowerCase().substring(0, 6))
                        || fileCode.toLowerCase().substring(0, 6).startsWith(key.toLowerCase())) {
                    result = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据指定文件的文件头判断其文件类型
     *
     * @param content 文件内容字节数组
     * @return 文件类型，如 pdf
     */
    public static String getFileType(byte[] content) {
        String result = null;
        byte[] b = Arrays.copyOf(content, 10);
        String fileCode = bytesToHexString(b);
        if (fileCode == null) {
            return null;
        }
        for (String key : FILE_TYPE_MAP.keySet()) {
            // 验证前6个字符比较
            if (key.toLowerCase().startsWith(fileCode.toLowerCase().substring(0, 6))
                    || fileCode.toLowerCase().substring(0, 6).startsWith(key.toLowerCase())) {
                result = FILE_TYPE_MAP.get(key);
                break;
            }
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
//        String filePath = "D:\\图纸\\图纸\\Circle\\Dwg2013\\Circle1.dwg";
        String filePath = "D:\\阿里巴巴Java开发手册终极版v1.3.0.pdf";
        String type = getFileType(filePath);
        System.out.println("Except : " + type);

    }
}
