package org.zkoss.demo.tablet.vm;

import java.util.Date;
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
	private static final String[] CATEGORY_LIST = {
		DataServer.HELP, DataServer.STUDIO, DataServer.GENERAL, DataServer.ANNOUNCE, DataServer.INSTALL
		, "サポート", "ご要望", "お知らせ"	 //Delete just for demo
	};

	private AbstractConverter converter = new AbstractConverter();
	private DataServer server;
	private ThreadVO selectedThread;
	private String selectedCategory = DataServer.ANNOUNCE;
	private List<ThreadVO> selectedThreadList;
	private List<ContentVO> selectedThreadContent;
	private boolean westFlag = true;
	private boolean centerFlag = true;
	
	@Wire("#contentPanel") private Window contentPanel;
		
	public ThreadViewModel() {
		server  = new DataServer();
		fetchData();
	}
	
	private void fetchData(){
		selectedThreadList = server.getThreadList(selectedCategory);
		selectedThread = selectedThreadList.get(0);
		selectedThreadContent = server.getContentList(this.selectedThread);
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@NotifyChange({"contentList", "selectedThread"})
	public void setSelectedThread(ThreadVO thread){
		this.selectedThread = thread;
		selectedThreadContent = server.getContentList(this.selectedThread); 
		contentPanel.invalidate();
	}
	
	@NotifyChange("*")
	public void setSelectedCategory(String category){
		westFlag = !westFlag;
		selectedCategory = category;
		fetchData();
		contentPanel.invalidate();
	}
	
	@Command
	public void openContent(){		
		//FIXME check it work fine or not
		Clients.resize(contentPanel);
	}
	
	@Command
	@NotifyChange({"westMode", "threadList", "categoryList"})  
	public void showCategory(){
		westFlag = !westFlag;
	}

	public boolean isWestMode() {
		return westFlag;
	}

	@Command
	@NotifyChange("centerUrl")
	public void showSetting(){
		centerFlag = !centerFlag;
	}
	
	public String getCenterUrl() {
		return centerFlag ? "content.zul" : "setting.zul";
	}
	
	@Command
	public List<ThreadVO> getThreadList(){	
		return selectedThreadList;
	}
	
	public List<ContentVO> getContentList(){
		return selectedThreadContent;
	}
	
	public ThreadVO getSelectedThread(){
		return selectedThread;
	}
		
	public String[] getCategoryList(){
		return CATEGORY_LIST;
	}

	public String getSelectedCategory(){
		return selectedCategory;
	}
	
	public int getCategoryIndex(){
		for(int i=0; i<CATEGORY_LIST.length; i++){
			if(CATEGORY_LIST[i].equals(selectedCategory)){
				return i;
			}
		}
		return -1;
	}
	
	public AbstractConverter getConverter(){
		return converter;
	}
	
	public Date getNow(){
		return new Date();
	}
}