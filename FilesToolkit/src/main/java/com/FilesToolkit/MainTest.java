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
        String filePath = "."+fileDir+"create_test.txt";
        //System.out.println(System.getProperty("user.dir"));
        FileListener fListener = new FileListener(System.getProperty("user.dir")+fileDir);
        fListener.start();
        
        FileUtils fUtils = new FileUtils();
        Thread.sleep(3000);
        fUtils.createFile(filePath);
        
        Thread.sleep(3000);
        fUtils.write2file(filePath, "Hello World!");
        
        Thread.sleep(3000);
        fUtils.readFromFile(filePath);
        
        Thread.sleep(3000);
        fUtils.deleteFile(filePath);
        
    }
}
