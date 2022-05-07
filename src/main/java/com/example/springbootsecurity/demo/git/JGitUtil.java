package com.example.springbootsecurity.demo.git;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Git操作工具类
 */
@Slf4j
public class JGitUtil {
    private static String LOCAL_REPO_PATH = "D:/workspace/project";// D:/workspace/project
    private static String LOCAL_REPOSITORY_GIT_CONFIG = "D:\\java_git_operate\\jgit/.git"; // D:/workspace/project/.git
    private static String REMOTE_REPO_URI = "https://github.com/huangjiaweidui2013/PowerMockExamples.git"; // git@github.com:xxx/project.git
    private static String INIT_LOCAL_CODE_DIR = "D:\\java_git_operate\\jgit"; // D:/workspace
    private static String LOCAL_CODE_CT_SQL_DIR = "sqlpath/"; // sqlpath/
    private static String BRANCH_NAME = "main"; // v1.0
    private static String GIT_USERNAME = "huangjiaweidui2013"; // admin
    private static String GIT_PASSWORD = "hubeiyingshan8706"; // admin

    @Slf4j
    public enum SqlTypeEnum {
        SQL_CALC, EMAIL, MYSQL_TO_HIVE, HIVE_TO_MYSQL
    }

    public static void main(String[] args) {
//        setupRepository(BRANCH_NAME);
        pull("main");

    }

    /**
     * sql脚本文件同步到git仓库
     *
     * @param qte       SQl类型
     * @param loginName 系统登录名
     * @param fileName  文件名
     * @param sqlConent 文件内容
     * @param comment   提交说明
     * @return
     */
    public static boolean writeFileToGit(SqlTypeEnum qte, String loginName, String sqlConent, String fileName, String comment) {

        JGitUtil.pull();
        String dest = LOCAL_CODE_CT_SQL_DIR + qte.name().toLowerCase();
        String path = LOCAL_REPO_PATH + "/" + dest;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        dest = dest + "/" + fileName;
        path = path + "/" + fileName;
        comment = loginName + " option of " + comment;
        return true == JGitUtil.createFile(sqlConent, path) == JGitUtil.commitAndPush(dest, comment);
    }

    /**
     * 添加文件
     *
     * @param fileName
     * @return
     */
    public static boolean addFile(String fileName) {

        boolean addFileFlag = true;
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG))) {
            //add file to git
            String filePath = LOCAL_CODE_CT_SQL_DIR + fileName;
            git.add().addFilepattern(INIT_LOCAL_CODE_DIR).call();
            System.out.println("Added file " + filePath + " to repository at " + git.getRepository().getDirectory());
        } catch (Exception e) {
            e.printStackTrace();
            addFileFlag = false;
        }
        return addFileFlag;
    }

    /**
     * 提交代码到本地仓库
     *
     * @param comment 提交git内容描述
     * @return
     */
    public static boolean commitFile(String comment) {

        boolean commitFileFlag = true;
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG));) {
            //提交代码到本地仓库
            git.commit().setMessage(comment).call();
            log.info("Committed to repository at " + git.getRepository().getDirectory());
        } catch (Exception e) {
            e.printStackTrace();
            commitFileFlag = false;
            log.error("commitFile error! \n" + e.getMessage());
        }
        return commitFileFlag;
    }

    public static boolean push() {

        boolean pushFlag = true;
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG));) {
            //提交代码到本地仓库
            git.push().call();
            log.info("push " + git.getRepository() + File.separator + git.getRepository().getBranch());
        } catch (Exception e) {
            e.printStackTrace();
            pushFlag = false;
            log.error("push error! \n" + e.getMessage());
        }
        return pushFlag;
    }

    /**
     * 提交并推送代码至远程服务器
     *
     * @param filePath 提交文件路径(相对路径)
     * @param desc     提交描述
     * @return
     */
    public static boolean commitAndPush(String filePath, String desc) {

        boolean commitAndPushFlag = true;
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG));) {
//        	 //创建用户文件的过程
//             File myfile = new File(filePath);
//             myfile.createNewFile();
            UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(GIT_USERNAME, GIT_PASSWORD);
            git.add().addFilepattern(filePath).call();
            //提交
            git.commit().setMessage(desc).call();
            //推送到远程
            if (StringUtils.isBlank(GIT_USERNAME) || StringUtils.isBlank(GIT_PASSWORD)) {
                git.push().setCredentialsProvider(provider).call();
            } else {
                git.push().call();
            }
            log.info("Commit And Push file " + filePath + " to repository at " + git.getRepository().getDirectory());
        } catch (Exception e) {
            e.printStackTrace();
            commitAndPushFlag = false;
            log.error("Commit And Push error! \n" + e.getMessage());
        }
        return commitAndPushFlag;

    }

    public static boolean pull() {
        return pull(BRANCH_NAME);
    }

    /**
     * 拉取远程代码
     *
     * @param remoteBranchName
     * @return 远程分支名
     */
    public static boolean pull(String remoteBranchName) {

        boolean pullFlag = true;
        String fileDir = INIT_LOCAL_CODE_DIR + "/" + remoteBranchName;
        //检查目标文件夹是否存在
        File file = new File(fileDir);
        if (file.exists()) {
            deleteFolder(file);
        }
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG));) {
            git.cloneRepository().setTimeout(300).setURI(REMOTE_REPO_URI).setDirectory(file).call();
        } catch (Exception e) {
            e.printStackTrace();
            pullFlag = false;
        }
        return pullFlag;
    }

    public static boolean checkout(String branchName) {

        boolean checkoutFlag = true;
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG));) {
            git.checkout().setName("refs/heads/" + branchName).setForce(true).call();
        } catch (Exception e) {
            e.printStackTrace();
            checkoutFlag = false;
        }
        return checkoutFlag;
    }

    public static boolean checkout() throws GitAPIException {
        return checkout(BRANCH_NAME);

    }

    /**
     * 从远程获取最新版本到本地   不会自动合并 merge
     *
     * @return
     */
    public static boolean fetch() {

        boolean fetchFlag = true;
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG));) {
            git.fetch().setCheckFetchedObjects(true).call();
        } catch (Exception e) {
            e.printStackTrace();
            fetchFlag = false;
        }
        return fetchFlag;
    }

    /**
     * 拉取新创建的分支到本地
     *
     * @param cloneURL
     * @return
     */
    @SuppressWarnings("static-access")
    public static boolean pullNewBranchToLocal(String cloneURL) {
        boolean resultFlag = false;
        String[] splitURL = cloneURL.split(" ");
        String branchName = splitURL[1];
        String fileDir = INIT_LOCAL_CODE_DIR + "/" + branchName;
        //检查目标文件夹是否存在
        File file = new File(fileDir);
        if (file.exists()) {
            deleteFolder(file);
        }
        try (Git git = Git.open(new File(LOCAL_REPOSITORY_GIT_CONFIG))) {
            git.cloneRepository().setURI(cloneURL).setDirectory(file).call();
            resultFlag = true;
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
        return resultFlag;
    }


    private static void deleteFolder(File file) {
        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFolder(files[i]);
                files[i].delete();
            }
        }
    }

    /**
     * 生成文件写内容
     *
     * @param content  文件内容
     * @param filePath 文件名称
     */
    @SuppressWarnings("unused")
    private static boolean createFile(String content, String filePath) {
        boolean createFileFlag = true;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                createFileFlag = false;
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8));) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            createFileFlag = false;
        }
        return createFileFlag;
    }

    /**
     * 创建本地新仓库
     *
     * @param repoPath 仓库地址 D:/workspace/TestGitRepository
     * @return
     * @throws IOException
     */
    public static Repository createNewRepository(String repoPath) throws IOException {
        File localPath = new File(repoPath);
        // create the directory
        try (Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"))) {
            repository.create();
            return repository;
        }
    }

    /**
     * 创建仓库，仅需要执行一次
     */
    public static boolean setupRepository(String defaultBranchName) {
        boolean setupRepositoryFlag = true;
        try {
            //设置远程服务器上的用户名和密码
            UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(GIT_USERNAME, GIT_PASSWORD);
            if (StringUtils.isBlank(GIT_USERNAME) || StringUtils.isBlank(GIT_PASSWORD)) {
                Git.cloneRepository().setURI(REMOTE_REPO_URI) //设置远程URI
                        .setBranch(defaultBranchName)   //设置clone下来的分支,默认master
                        .setDirectory(new File(LOCAL_REPO_PATH))  //设置下载存放路径
                        .setTimeout(300)//设置超时时间，单位：秒
                        .call();
            } else {
                Git.cloneRepository().setURI(REMOTE_REPO_URI) //设置远程URI
                        .setBranch(defaultBranchName)   //设置clone下来的分支,默认master
                        .setDirectory(new File(LOCAL_REPO_PATH))  //设置下载存放路径
                        .setCredentialsProvider(provider) //设置权限验证
                        .setTimeout(300)//设置超时时间，单位：秒
                        .call();
            }
        } catch (Exception e) {
            e.printStackTrace();
            setupRepositoryFlag = false;
        }
        return setupRepositoryFlag;
    }
}