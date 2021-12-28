package com.example.springbootsecurity.demo.exe;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;

/**
 * @author: huang lang
 * @description: 使用java代码调用exe可执行程序
 * @date: 2021/12/25 16:16
 */
public class JavaCallExe {
    public static void main(String[] args) throws IOException {
        callByCommand();
//        callByProcessBuilder1();
//        callByExec("www.baidu.com -t");
    }

    /**
     * 使用cmd命令的方式
     *
     * @return: void
     * @description:
     */
    public static void callByCommand() throws IOException {
        String[] params = new String[]{"C:\\Program Files (x86)\\Notepad++\\notepad++.exe", "D:\\test_node_pad_open.txt"};
        BufferedReader bufferedReader;
        Process process = Runtime.getRuntime().exec(params);
        boolean alive = process.isAlive();
//        bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//        String line = null;
//        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println(line);
//        }
        try {
            Thread.sleep(5000);
            if (alive) {
                process.destroy();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void callByRuntime(String[] args) {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("mspaint.exe");
            rt.exec("D:\\Program Files\\Tencent\\QQ\\Bin\\QQ.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ProcessBuilder 调用cmd
     */
    private static void callByProcessBuilder1() {
        BufferedReader bufferedReader = null;
        try {
            // 执行命令返回执行的子进程对象
            Process proc = new ProcessBuilder("C:/Program Files (x86)/Notepad++/notepad++.exe").start();
            // 获取子进程的错误流，并打印
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * ProcessBuilder打开nginx
     *
     * @throws IOException
     */
    private static void callByProcessBuilder2() {
        BufferedReader bufferedReader = null;
        ProcessBuilder pb = new ProcessBuilder("cmd ", "/c", "start nginx");
        Map<String, String> env = pb.environment();
        pb.directory(new File("D:/workspace-mars-2019-app/ywpt/nginx/windows"));

        try {
            Process proc = pb.start();

            // 获取子进程的错误流，并打印
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                }
            }
        }

    }

    /**
     * getDesktop方式
     *
     * @throws IOException
     */
    private static void callByDesktop() throws IOException {

        BufferedReader bufferedReader = null;
        try {
            Desktop.getDesktop().open(new File("C:/Program Files (x86)/Notepad++/notepad++.exe"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * 使用Runtime对象的exec方法，运行cmd命令。
     */
    public static void callByExec(String cmd) {
        Runtime rt = Runtime.getRuntime();
        try {
            //运行cmd命令
            Process pr = rt.exec("ping " + cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String s = br.readLine();
            String temp = "";
            while (null != s) {
                if (!"".equals(s.trim())) {
                    temp = s;
                }
                System.out.println(s);
                s = br.readLine();
            }
            br.close();
            //导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止。
            pr.waitFor();
            //此 Process 对象表示的子进程的出口值。根据惯例，值 0 表示正常终止。
            if (0 == pr.exitValue()) {
                JOptionPane.showMessageDialog(null, temp);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void SentZipPackage2() {
        //windows    //
        // String cmd = "F:\\apache-tomcat-6.0.20.exe";
//      String cmd = "D:\\Program Files\\Microsoft Office\\OFFICE11\\WINWORD.EXE F:\\test.doc";
//      String cmd = "cmd.exe /c start F:\\test.doc";
        String cmd = "C:\\Users\\Desktop\\curl.exe -i -v -X POST -H \"Content-Type: multipart/form-data\" -F \"upload=@C:\\Users\\\\Desktop\\log.txt.zip\" http://127.0.0.1:8080/do.action";
        //linux    //
//        String cmd="./fork_wait";
//        String cmd="ls -l";
//        String[]cmd=new String[3];
//        cmd[0]="/bin/sh";
//        cmd[1]="-c";    //
//        cmd[2]="ls -l ./";
        //返回与当前 Java 应用程序相关的运行时对象
        Runtime run = Runtime.getRuntime();
        try {
            // 启动另一个进程来执行命令
            Process p = run.exec(cmd);
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            System.out.println("开始");
            while ((lineStr = inBr.readLine()) != null)
            //获得命令执行后在控制台的输出信息
            {
                System.err.println("获得命令执行后在控制台的输出信息");
            }
            // 打印输出信息
            System.out.println(lineStr);
            // 检查命令是否执行失败。
            if (p.waitFor() != 0) {
                //p.exitValue()==0表示正常结束，1：非正常结束
                if (p.exitValue() == 1) {
                    System.err.println("命令执行失败!");
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
