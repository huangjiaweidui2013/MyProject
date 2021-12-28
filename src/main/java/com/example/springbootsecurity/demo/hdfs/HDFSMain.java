package com.example.springbootsecurity.demo.hdfs;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * hdfs常见操作
 *
 * @author hadoop
 */
public class HDFSMain {

    private static final Logger log = LoggerFactory.getLogger(HDFSMain.class);

    /**
     * 创建文件夹
     *
     * @param fs
     */
    public static void mkdir(FileSystem fs) {
        Path path = new Path("/user/root/hdfs");
        try {
            boolean exists = fs.exists(path);
            if (!exists) {
                log.info("path /user/root/hdfs doesn't exists.");
                boolean status = fs.mkdirs(path);
                log.info("mkdir success : " + status);
            } else {
                log.info("path /user/root/hdfs exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件并写入内容
     *
     * @param fs
     */
    public static void createFile(FileSystem fs) {
        byte[] data = "hello,hdfs!\n".getBytes();
        try {
            Path path = new Path("/user/root/hdfs/20190830.txt");
            FSDataOutputStream output = fs.create(path);
            output.write(data);
            output.close();
            log.info("createFile done.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     *
     * @param fs
     */
    public static void readFile(FileSystem fs) {
        Path path = new Path("/user/root/hdfs/20190830.txt");
        try {
            FSDataInputStream input = fs.open(path);
            byte[] buffer = new byte[1024];
            int result = input.read(buffer);
            if (result != -1) {
                log.info("file content is : " + new String(buffer));
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重命名
     *
     * @param fs
     */
    public static void rename(FileSystem fs) {
        Path path = new Path("/user/root/hdfs");
        Path newPath = new Path("/user/root/2019");
        try {
            boolean status = fs.rename(path, newPath);
            log.info("path /user/root/hdfs rename success " + status + ".");
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件最后修改时间
     *
     * @param fs
     */
    public static void getLastModificationTime(FileSystem fs) {
        Path file = new Path("/user/root/2019/20190830.txt");
        try {
            FileStatus status = fs.getFileStatus(file);
            long time = status.getModificationTime();
            log.info("file " + file.getName() + " last modification time :" + new Date(time));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝本地文件到hdfs
     *
     * @param fs
     */
    public static void copyToHDFS(FileSystem fs) {
        //Path src = new Path("/root/test.txt");
        Path src = new Path("D:\\push.txt");
        Path dst = new Path("/user/root/2019/");
        try {
            fs.copyFromLocalFile(src, dst);
            log.info("copyFromLocal done.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加内容到文件
     *
     * @param fs
     */
    public static void appendToFile(FileSystem fs) {
        byte[] data = "append for hdfs!\n".getBytes();
        Path path = new Path("/user/root/2019/20190830.txt");
        try {
            FSDataOutputStream output = fs.append(path);
            output.write(data);
            output.close();
            log.info("append to file ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     *
     * @param fs
     */
    public static void delete(FileSystem fs) {
        Path path = new Path("/user/root/2019");
        try {
            boolean exists = fs.exists(path);
            if (exists) {
                boolean success = fs.delete(path, true);
                log.info("path " + path.getName() + " delete successfully : " + success + ".");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            System.setProperty("HADOOP_USER_NAME", "root");
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", "hdfs://192.168.56.202:9000");
            conf.setBoolean("dfs.support.append", true);
            conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
            FileSystem fs = FileSystem.get(conf);
            //1 mkdir
            //mkdir(fs);
            //2 createFile
            //createFile(fs);
            //3 readFile
            //readFile(fs);
            //4 rename
            //rename(fs);
            //5 getLastModificationTime
            //getLastModificationTime(fs);
            //6 copyToHDFS
            //copyToHDFS(fs);
            //7 appendToFile
            appendToFile(fs);
            //8 delete
            //delete(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
