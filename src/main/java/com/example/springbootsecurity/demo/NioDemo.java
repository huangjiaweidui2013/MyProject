package com.example.springbootsecurity.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: NioDemo
 * @author: HuangLang
 * @description: java new io 的一些代码示例
 * @date: 2021-06-18 下午 5:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NioDemo {

    @Test
    public void testFiles() throws IOException {
        Path path = Paths.get("D:\\home\\ruoyi\\logs");
        // 1.Files.exists()方法检查文件系统中是否存在给定的Path
        boolean exists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
        System.out.println("目录是否存在: " + exists);
        if (!exists) {
            // 2.Files.createDirectory()方法从Path实例创建一个新目录
            Path newPath = Files.createDirectories(path);
            System.out.println("newPath: " + newPath);
        }
        Path sourcePath = Paths.get("D:\\learn\\sourceFile.txt");
        Path destinationPath1 = Paths.get("D:\\learn\\destination\\destinationFile.txt");
        Path destinationPath2 = Paths.get("D:\\learn\\destination\\destination.txt");
        // 3.Files.copy()方法将文件从一个路径复制到另一路径
        Path copyPath1 = Files.copy(sourcePath, destinationPath1);
        System.out.println("copyPath1: " + copyPath1);
        // Files.copy()方法覆盖文件现有内容
        Path copyPath2 = Files.copy(sourcePath, destinationPath2, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("copyPath2: " + copyPath2);
    }


    public static void main(String[] args) throws IOException {
//        fileMove();
//        fileDelete();
//        fileWalkFileTree();
        searchFileByWalkFileTree();
    }

    /**
     * 5.Files.move()
     * Java NIO Files类还包含一个用于将文件从一个路径移动到另一路径的函数。移动文件与重命名相同，
     * 不同之处在于移动文件既可以将文件移动到另一个目录，又可以在同一操作中更改其名称。java.io
     * .File类也可以使用自己的namedTo()方法来做到这一点
     *
     * @throws IOException
     */
    public static void fileMove() throws IOException {
        Path sourcePath = Paths.get("D:\\learn\\sourceFile.txt");
        Path destinationPath = Paths.get("D:\\learn\\empty\\sourceFile.txt");
        Path movePath = Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("movePath: " + movePath);
    }

    /**
     * 6.Files.delete()
     * Files.delete()方法可以删除文件或目录
     *
     * @throws IOException
     */
    public static void fileDelete() throws IOException {
        Path sourcePath = Paths.get("D:\\learn\\test");
        Files.delete(sourcePath);
    }

    /**
     * 7.Files.walkFileTree()
     * Files.walkFileTree()方法包含用于递归遍历目录树的功能。 walkFileTree()方法将Path实例
     * 和FileVisitor作为参数，Path实例指向要遍历的目录。在遍历期间将调用FileVisitor
     *
     * @throws IOException
     */
    public static void fileWalkFileTree() throws IOException {
        Path rootPath = Paths.get("D:\\learn");
        Files.walkFileTree(rootPath, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("pre visit dir:" + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("visit file:" + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visit file failed:" + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("post visit directory: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * 8.搜索文件
     * 这是walkFileTree()，使用SimpleFileVisitor来查找名为README.txt的文件
     *
     * @throws IOException
     */
    public static void searchFileByWalkFileTree() throws IOException {
        Path rootPath = Paths.get("D:\\learn");
        String fileToFind = File.separator + "destination.txt";
        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileString = file.toAbsolutePath().toString();
                if (fileString.endsWith(fileToFind)) {
                    System.out.println("file found at path: " + file.toAbsolutePath());
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Files.walkFileTree()也可以用于删除其中包含的所有文件和子目录。如果目录为空，则Files.delete()方法将仅删除该目录。
     * 通过遍历所有目录并删除每个目录中的所有文件(在visitFile()内部)，然后删除目录本身(在postVisitDirectory()内部)，
     * 可以删除包含所有子目录和文件
     *
     * @throws IOException
     */
    public static void deleteFileByWalkFileTree() throws IOException {
        Path rootPath = Paths.get("D:\\learn2");
        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("delete file: " + file.toString());
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                System.out.println("delete dir: " + dir.toString());
                return FileVisitResult.CONTINUE;
            }
        });

    }
}
