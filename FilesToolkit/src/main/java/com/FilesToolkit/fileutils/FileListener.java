package com.FilesToolkit.fileutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Logger;




public class FileListener extends Thread {
	private String listenDir;
	private static Logger log = Logger.getLogger("FileListener");
	
	public FileListener(String listenDir){
		this.listenDir = listenDir;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("文件监听器已经启动...");
		super.run();
		try {
			//获取文件系统的WatchService 对象
			WatchService watchService = FileSystems.getDefault().newWatchService();
			//为指定目录路径注册所要监听的事件
			Paths.get(this.listenDir).register(watchService, 
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_CREATE);
			
			//获取下一个文件变化事件
			while(true){
				try {
					WatchKey key = watchService.take();
					for (WatchEvent<?> event : key.pollEvents()) {
						String fileName = event.context().toString();
                        System.out.println(fileName+"文件发生了"+event.kind()+"事件");
                        //是否是一个.ok文件，是则做相关处理
					}
					// 重设WatchKey
		            boolean valid = key.reset();
		            // 如果重设失败，退出监听
		            if (!valid) 
		            {
		                break;
		            }
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
