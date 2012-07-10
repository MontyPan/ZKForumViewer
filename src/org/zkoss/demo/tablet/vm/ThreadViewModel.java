package org.zkoss.demo.tablet.vm;

import java.util.List;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.demo.tablet.DataServer;
import org.zkoss.demo.tablet.vo.ContentVO;
import org.zkoss.demo.tablet.vo.ThreadVO;

public class ThreadViewModel {
	private DataServer server;
	private ThreadVO selectedThread;
	
	@NotifyChange("*")
	public void setSelectedThread(ThreadVO thread){
		this.selectedThread = thread;
	}
	
	public ThreadViewModel() {
		server  = new DataServer();
		selectedThread = server.getThreadList().get(0);
	}
		
	public List<ThreadVO> getThreadList(){	
		return server.getThreadList();
	}
	
	public List<ContentVO> getContentList(){
		return server.getContentList(this.selectedThread);
	}
}