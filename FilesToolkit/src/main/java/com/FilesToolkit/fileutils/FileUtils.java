package com.FilesToolkit.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FileUtils {
private static Logger log = Logger.getLogger("FileUtils");


	//调用示例：readFromFile("C:\\min\\test1.txt");
	public String readFromFile(String filePath){
		String encoding="UTF-8";
		String res = null;
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
			String line = null;
			res = "";
			while((line = input.readLine())!=null){
				res += line;
			}
			System.out.println("写入文件的内容是："+res);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("不支持编码："+encoding);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(input!=null)
					input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	
	//调用示例：write2file("C:\\min\\test1.txt", "helloworld\n");
	public void write2file(String file, String content){
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//创建一个文件
	//调用示例：createFile("C:\\min\\test1.txt");
	public boolean createFile(String destFileName) {  
        File file = new File(destFileName);  
        if(file.exists()) {  
            log.info("创建单个文件" + destFileName + "失败，目标文件已存在！");  
            return false;  
        }  
        //创建目标文件  
        try {  
            if (file.createNewFile()) {  
            	//System.out.print(file.getAbsolutePath());
            	log.info("创建单个文件" + destFileName + "成功！");  
                return true;  
            } else {  
            	log.info("创建单个文件" + destFileName + "失败！");  
                return false;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            log.info("创建单个文件" + destFileName + "失败！" + e.getMessage());  
            return false;  
        } 
	}
	
	//调用示例：deleteFile("C:\\min\\test1.txt");
	public boolean deleteFile(String fileName){

		File file = new File(fileName);
		boolean deleteSuccess = false;
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				deleteSuccess = true;
				log.info("删除单个文件" + fileName + "成功！");
			} else {
				log.info("删除单个文件" + fileName + "失败！");
			}
		} else {
			log.info("删除单个文件失败：" + fileName + "不存在！");
		}
		return deleteSuccess;
	}
	
	//生成随机数与日期组成的文件名
	public String getRandomFileName(){
		String uuid=UUID.randomUUID().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//设置日期格式
        String currentTime = df.format(new Date());
        //System.out.println(uuid+"_"+currentTime);
        return uuid+"_"+currentTime;
	}
	
	public boolean uploadFile(String hostname,// FTP服务器hostname
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String path, // FTP服务器保存目录
			String srcFileName, // 上传到FTP服务器上的本地文件名
			String destFileName//, // 上传到FTP服务器上的目标文件名
			//String orginfilename // 输入流文件名
		){
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(srcFileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//InputStream is = new ByteArrayInputStream(contents.getBytes());
		
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("gbk");//"GBK"
		ftpClient.setBufferSize(1024);//设置1M缓冲
		boolean uploadSuccess = false;
		
		try {
			int reply;
			ftpClient.connect(hostname, port);
			ftpClient.login(username, password);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return uploadSuccess;
			}
			//设置文件类型
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//设置上传目录
			//ftpClient.makeDirectory(path);
			ftpClient.changeWorkingDirectory(path);//进入或切换到该dir目录
			
			//上传
			ftpClient.storeFile(destFileName, fis);
			fis.close();
			ftpClient.logout();
			uploadSuccess = true;
			log.info("文件"+destFileName+"上传成功！");
			
			
			
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.severe("关闭FTP连接发生异常！");
					log.severe("Error:" + e.getMessage());
				}

			}
		}
		
		return uploadSuccess;
	}
	
}
