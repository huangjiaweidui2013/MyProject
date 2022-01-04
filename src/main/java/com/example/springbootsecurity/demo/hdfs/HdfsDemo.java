package com.example.springbootsecurity.demo.hdfs;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author: huang lang
 * @description:
 * @date: 2021/12/28 11:41
 */
@Slf4j
public class HdfsDemo {
    // HDFS服务器地址
    private static final String BASE_URL = "hdfs://192.168.56.130:9000";
    private static final String BASE_DIR = "/test/root/huang";

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//        readFile();
//        mkdir("/test/root/huang/child1");
//        createFile("test_file2.txt", "用户文件表数据库表结构设计（userId、文件上传存储名称、文件大小、文件类型、文件路径、上传时间、版本号等）");
//        createFile("/test/root/huang/child1", "test11.txt", LocalDateTime.now().toString());
//        createFile("/test/root/huang/child1", "test12.txt", LocalDateTime.now().toString());
//        createFile("/test/root/huang/child2", "test21.txt", LocalDateTime.now().toString());
//        readRemoteFile(BASE_DIR + "/text_create.txt");
//        appendFile("test_file2.txt", "\n这里的内容是为了测试是否能追加HDFS上的文件内容，追加时间为：" + LocalDateTime.now() + "\n");
//        rename("/test/root/huang3", "/test/root/huang123456789");
        rename("/test/root/huang/child1/test11.txt", "/test/root/huang/child1/test1111111111.txt");
//        getFileStatus(BASE_DIR + "/text_create.txt");
//        copyToHDFS("D:\\java_tools\\nacos-server-2.0.3.zip", "/test/root/huang123456789");
//        copyToLocal("/test/root/huang123456789/nacos-server-2.0.3.zip", "D:\\nacos-server-2.0.3.zip");
//        copyToHDFS("D:\\java_tools\\nacos-server-2.0.3.zip", "/test/root/huang2");
//        appendToFile(BASE_DIR + "/" + "test_file2.txt", "/n 再次追加文件内容，123456789，China！");
//        deleteFile("/test/root/huang123456789/apache-maven-3.6.1-bin.zip");
//        listFile1(BASE_DIR);
    }

    public static FileSystem getFileSystem() {
        try {
            Configuration configuration = new Configuration();
            // 下面的两行代码死为了设置能迅速修改文件中的内容
            /*
             * 运行之后，还会出现上面出现过的报错，解决方案就是：不要再用之前使用追加文件内容命令的源文件和目的文件了！
             * 如果仅仅测试，可以另建测试文件，重新上传，然后尝试追加；如果之前的文件还要用，那就复制到新文件里去，再追加。
             * 总之，修改程序和配置文件后，appendToFile和fs.append()的对象不能是你之前让程序报错的那俩文件。
             * 如果还不行就重启下Hadoop或系统即可。
             * 原文链接：https://blog.csdn.net/twentyonepilots/article/details/105523240
             */
            configuration.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
            configuration.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
            return FileSystem.get(new URI(BASE_URL), configuration, "root");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取hdfs文件内容
     *
     * @throws IOException
     */
    public static void readFile() throws IOException {
        Configuration configuration = new Configuration();
        Path src = new Path("/test_file.txt");
        FSDataInputStream dis = getFileSystem().open(src);
        IOUtils.copyBytes(dis, System.out, configuration);
        dis.close();
    }

    /**
     * 创建文件夹
     *
     * @param dirPath 路径
     */
    public static void mkdir(String dirPath) {
        Path path = new Path(dirPath);
        FileSystem fs = getFileSystem();
        try {
            boolean exists = fs.exists(path);
            if (!exists) {
                log.info("path {} doesn't exist.", path);
                boolean b = fs.mkdirs(path);
                log.info("mkdir success : " + b);
            } else {
                log.info("path {} exist.", path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件并写入内容
     *
     * @param fileName 文件名
     * @param content  文件内容
     */
    public static void createFile(String fileName, String content) {
        createFile(BASE_DIR + "/" + fileName, fileName, content);
    }

    /**
     * 创建文件并写入内容
     *
     * @param fileName 文件名
     * @param content  文件内容
     */
    public static void createFile(String filePath, String fileName, String content) {
        byte[] data = content.getBytes(StandardCharsets.UTF_8);
        try {
            FileSystem fs = getFileSystem();
            Path path = new Path(filePath + File.separator + fileName);
            FSDataOutputStream dataOutputStream = fs.create(path);
            dataOutputStream.write(data);
            dataOutputStream.close();
            log.info("create file done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器上文件追加内容
     *
     * @param fileName 文件名
     * @param content  写入的内容
     */
    public static void appendFile(String fileName, String content) {
        byte[] data = content.getBytes(StandardCharsets.UTF_8);
        try {
            FileSystem fs = getFileSystem();
            Path path = new Path(BASE_DIR + "/" + fileName);
            FSDataOutputStream dataOutputStream = fs.append(path);
            dataOutputStream.write(data);
            dataOutputStream.flush();
            dataOutputStream.close();
            log.info("append file content done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取服务器上的文件内容
     *
     * @param filePath
     */
    public static void readRemoteFile(String filePath) {
        Path path = new Path(filePath);
        FileSystem fs = getFileSystem();
        try {
            FSDataInputStream inputStream = fs.open(path);
            byte[] buffer = new byte[1024];
            int result = inputStream.read(buffer);
            if (result != -1) {
                log.info("file content is : " + new String(buffer));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件或者文件夹重命名
     *
     * @param originName 原名称
     * @param newName    新名称
     */
    public static void rename(String originName, String newName) {
        Path originPath = new Path(originName);
        Path newPath = new Path(newName);
        FileSystem fs = getFileSystem();
        try {
            boolean b = fs.rename(originPath, newPath);
            log.info("path {} rename to {} success: {}", originName, newName, b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件最后修改时间等信息
     *
     * @param filePath 文件路径
     */
    public static void getFileStatus(String filePath) {
        FileSystem fs = getFileSystem();
        Path file = new Path(filePath);
        try {
            FileStatus status = fs.getFileStatus(file);
            long time = status.getModificationTime();
            log.info("file " + file.getName() + " last modification time :" + new Date(time));
            log.info("status info => owner: {} , path: {}, len: {}", status.getOwner(),
                    status.getPath(), status.getLen());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传：拷贝本地文件到HDFS
     *
     * @param localFilePath 本地文件路径
     * @param destPath      HDFS目标路径
     */
    public static void copyToHDFS(String localFilePath, String destPath) {
        Instant start = Instant.now();
        FileSystem fs = getFileSystem();
        Path src = new Path(localFilePath);
        Path dst = new Path(destPath);
        try {
            fs.copyFromLocalFile(src, dst);
            fs.close();
            log.info("copy from local done.");
            Instant end = Instant.now();
            log.info("上传文件至HDFS系统，耗时(ms): " + Duration.between(start, end).toMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载：拷贝HDFS上的文件至本地
     *
     * @param remoteFilePath
     * @param localFilePath
     */
    public static void copyToLocal(String remoteFilePath, String localFilePath) {
        Instant start = Instant.now();
        FileSystem fs = getFileSystem();
        Path remotePath = new Path(remoteFilePath);
        Path localPath = new Path(localFilePath);
        try {
            fs.copyToLocalFile(false, remotePath, localPath, true);
            fs.close();
            log.info("copy to local done.");
            Instant end = Instant.now();
            log.info("拷贝HDFS系统上的文件至本地，耗时(ms): " + Duration.between(start, end).toMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加内容到指定文件
     *
     * @param filePath 文件路径
     * @param content  追加的内容
     */
    public static void appendToFile(String filePath, String content) {
        byte[] data = content.getBytes();
        FileSystem fs = getFileSystem();
        Path path = new Path(filePath);
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
     * 删除指定文件捉着文件夹
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        Path path = new Path(filePath);
        FileSystem fs = getFileSystem();
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

    /**
     * 使用 FileSystem.listFiles 方法遍历文件
     *
     * @param filePath
     * @throws IOException
     */
    public static void listFile1(String filePath) throws IOException {
        Path path = new Path(filePath);
        FileSystem fs = getFileSystem();
        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(path, true);
        while (iterator.hasNext()) {
            LocatedFileStatus fileStatus = iterator.next();
            Path statusPath = fileStatus.getPath();
            if (fileStatus.isFile()) {
                System.out.println("文件： " + statusPath);
                System.out.println("文件名： " + statusPath.getName());
            } else {
                System.out.println("目录： " + statusPath);
            }
        }
    }

    /**
     * 使用 FileSystem.listFiles 方法遍历文件
     *
     * @param filePath
     * @throws IOException
     */
    public static void listFile2(String filePath) throws IOException {
        Path path = new Path(filePath);
        FileSystem fs = getFileSystem();
        FileStatus[] fileStatuses = fs.listStatus(path);
        for (FileStatus status : fileStatuses) {
            if (status.isFile()) {
                System.out.println("文件： " + status);
                System.out.println("文件名： " + status.getPath().getName());
            } else {
                System.out.println("目录： " + status.getPath());
            }
        }
    }


    @SneakyThrows
    public static void getFileSystemInfo() {
        FileSystem fs = getFileSystem();
        URI uri = fs.getUri();
        long used = fs.getUsed();
        Path workingDirectory = fs.getWorkingDirectory();
        System.out.println("uri: " + uri);
        System.out.println("used: " + used);
        System.out.println("workingDirectory: " + workingDirectory);
    }

}
