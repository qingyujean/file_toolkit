package com.FilesToolkit;


import com.FilesToolkit.fileutils.FileListener;
import com.FilesToolkit.fileutils.FileUtils;

/**
 * Hello world!
 *
 */
public class MainTest 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	
        String fileDir = "/data_files/";
        
        //System.out.println(System.getProperty("user.dir"));
        FileListener fListener = new FileListener(System.getProperty("user.dir")+fileDir);
        fListener.start();
        
        FileUtils fUtils = new FileUtils();
        String fileName2 = fUtils.getRandomFileName();
        System.out.println("fileName2="+fileName2);
        
        String fileName = "create_test.txt";
        String filePath = "."+fileDir+fileName;
        
        Thread.sleep(3000);
        fUtils.createFile(filePath);
        
        Thread.sleep(3000);
        fUtils.write2file(filePath, "Hello World!");
        
        Thread.sleep(3000);
        fUtils.readFromFile(filePath);
        
        
        Thread.sleep(3000);
        //FTP上传文件测试
        String hostname = "10.10.129.216";// FTP服务器hostname
		int port = 21;// FTP服务器端口
		String username = "test"; // FTP登录账号
		String password = "123456"; // FTP登录密码
		String path = fileDir; // 本地需要上传文件所在目录
		String srcFileName = System.getProperty("user.dir")+fileDir+fileName; // 上传到FTP服务器上的本地文件名
		String destFileName = fileName; // 上传到FTP服务器上的目标文件名
		fUtils.uploadFile(hostname, port,//上传
				username, password,
				path,
				srcFileName, destFileName);
		
		
		Thread.sleep(3000);
        fUtils.deleteFile(filePath);
    }
}
