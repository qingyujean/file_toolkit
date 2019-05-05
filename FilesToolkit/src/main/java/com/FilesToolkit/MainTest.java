package com.FilesToolkit;


import java.text.ParseException;

import com.FilesToolkit.fileutils.FileListener;
import com.FilesToolkit.fileutils.FileUtils;
import com.FilesToolkit.fileutils.ZipCompressor;
import com.FilesToolkit.fileutils.ZipFilesUtil;

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
        
        //创建文件测试
        String fileName = "create_test.txt";
        String filePath = "."+fileDir+fileName;
        
        Thread.sleep(3000);
        fUtils.createFile(filePath);
        
        
        String fileName2 = fUtils.getRandomFileName();
        System.out.println("fileName2="+fileName2);
        
        //写文件测试
        Thread.sleep(3000);
        fUtils.write2file(filePath, "Hello World!");
        //读文件测试
        Thread.sleep(3000);
        fUtils.readFromFile(filePath);
        
        
        Thread.sleep(3000);
        //FTP上传文件测试
        String hostname = "192.168.0.4";// FTP服务器hostname
		int port = 21;// FTP服务器端口
		String username = "test"; // FTP登录账号
		String password = "111111"; // FTP登录密码
		String path = fileDir; // 本地需要上传文件所在目录
		String srcFileName = System.getProperty("user.dir")+fileDir+fileName; // 上传到FTP服务器上的本地文件名
		String destFileName = fileName; // 上传到FTP服务器上的目标文件名
		fUtils.uploadFile(hostname, port,//上传
				username, password,
				path,
				srcFileName, destFileName);
		
		//文件打包测试，删除包时检查文件大小是否超过阈值，超过了就时间顺序删除老文件，直至文件大小低于阈值
		Thread.sleep(3000);
		ZipFilesUtil.zipFiles(System.getProperty("user.dir")+fileDir);
		
		Thread.sleep(3000);
		try {
			ZipFilesUtil.deleteZipFiles(System.getProperty("user.dir")+fileDir);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//删除文件测试
		String fileName1 = "create_test1.txt";
        String filePath1 = "."+fileDir+fileName1;
        Thread.sleep(3000);
        fUtils.createFile(filePath1);
        
		Thread.sleep(3000);
        fUtils.deleteFile(filePath1);
        
        
        //压缩文件测试，跟前面的ZIP打包类似，就是实现不一样
        Thread.sleep(3000);
        ZipCompressor zc = new ZipCompressor(System.getProperty("user.dir")+fileDir+"files_compress_test.zip");     
        zc.compress(System.getProperty("user.dir")+fileDir+"compress_test/");
    }
}
