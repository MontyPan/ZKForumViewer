package org.zkoss.demo.tablet.vm;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.demo.tablet.DataServer;
import org.zkoss.demo.tablet.vo.ContentVO;
import org.zkoss.demo.tablet.vo.ThreadVO;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

public class ThreadViewModel {
	private DataServer server;
	private ThreadVO selectedThread;
	private List<ContentVO> selectedThreadContent;
	
	@Wire("#contentPanel") private Window contentPanel;
		
	public ThreadViewModel() {
		server  = new DataServer();
		selectedThread = server.getThreadList().get(0);
		selectedThreadContent = server.getContentList(this.selectedThread); 
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@NotifyChange("contentList")
	public void setSelectedThread(ThreadVO thread){
		this.selectedThread = thread;
		selectedThreadContent = server.getContentList(this.selectedThread); 
		contentPanel.invalidate();
	}
	
	@Command
	public void contentOpen(){
		//FIXME check it work fine or not
		Clients.resize(contentPanel);
	}
	
	public List<ThreadVO> getThreadList(){	
		return server.getThreadList();
	}
	
	public List<ContentVO> getContentList(){
		return selectedThreadContent;
	}
}