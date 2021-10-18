package com.example.springbootsecurity.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: ZipCompress
 * @author: HuangLang
 * @description: zip解压缩
 * @date: 2021-06-23 上午 10:41
 */
public class ZipCompress {
    /**
     * 压缩后的zip文件名
     */
    private String zipFileName;

    /**
     * 源文件(待压缩的文件或文件夹)
     */
    private String sourceFileName;

    public ZipCompress(String zipFileName, String sourceFileName) {
        this.zipFileName = zipFileName;
        this.sourceFileName = sourceFileName;
    }

    public void zip() throws Exception {
        System.out.println("开始压缩文件...");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File sourceFile = new File(sourceFileName);
        compress(out, bos, sourceFile, sourceFile.getName());
        bos.close();
        out.close();
        System.out.println("压缩完成");
    }

    public void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws IOException {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] files = sourceFile.listFiles();
            //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if (files.length == 0) {
                System.out.println(base + File.separator);
                out.putNextEntry(new ZipEntry(base + File.separator));
            }
            // //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            else {
                for (int i = 0; i < files.length; i++) {
                    compress(out, bos, files[i], base + File.separator + files[i].getName());
                }
            }
        }
        //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            System.out.println(base);
            while ((tag = bis.read()) != -1) {
                bos.write(tag);
            }
            bis.close();
            fos.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ZipCompress zipCompress = new ZipCompress("D:\\learn1.zip", "D:\\learn1");
        zipCompress.zip();
    }

}
