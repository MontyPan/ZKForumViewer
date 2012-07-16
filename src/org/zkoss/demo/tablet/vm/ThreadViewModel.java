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
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

public class ThreadViewModel {
	private static final String[] CATEGORY_LIST = {
		DataServer.HELP, DataServer.STUDIO, DataServer.GENERAL, DataServer.ANNOUNCE, DataServer.INSTALL
		, "サポート", "ご要望", "お知らせ"	 //Delete just for demo
	};

	private DataServer server;
	private ThreadVO nowThread;
	private int nowThreadIndex;
	private int nowCategoryIndex = 3;
	private String nowCategory = CATEGORY_LIST[nowCategoryIndex];
	private List<ThreadVO> nowThreadList;
	private List<ContentVO> nowContentList;
	private boolean westFlag = true;
	private boolean centerFlag = true;
	
	@Wire("#contentPanel") private Window contentPanel;
		
	public ThreadViewModel() {
		Image xd = new Image();
		server  = new DataServer();
		fetchThread();
	}
	
	private void fetchThread(){
		nowThreadList = server.getThreadList(CATEGORY_LIST[nowCategoryIndex]);
		nowThreadIndex = 0;
		fetchContent();
	}
	
	private void fetchContent(){
		nowThread = nowThreadList.get(nowThreadIndex);
		nowContentList = server.getContentList(this.nowThread);
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@NotifyChange({"contentList", "selectedThread", "threadStart", "threadEnd"})
	public void setSelectedThreadIndex(int index){
		nowThreadIndex = index;
		fetchContent();
		contentPanel.invalidate();
	}
	
	@NotifyChange("*")
	public void setSelectedCategoryIndex(int index){
		nowCategoryIndex = index;
		westFlag = !westFlag;
		fetchThread();
		contentPanel.invalidate();		
	}
		
	@Command
	@NotifyChange({"contentList", "selectedThread", "selectedThreadIndex", "threadEnd", "threadStart"})
	public void prevThread(){
		if(nowThreadIndex>0){
			nowThreadIndex--;
			fetchContent();
		}
	}
	
	@Command
	@NotifyChange({"contentList", "selectedThread", "selectedThreadIndex", "threadEnd", "threadStart"})
	public void nextThread(){
		if(nowThreadIndex<nowThreadList.size()-1){
			nowThreadIndex++;
			fetchContent();
		}
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
	
	public List<ThreadVO> getThreadList(){	
		return nowThreadList;
	}
	
	public List<ContentVO> getContentList(){
		return nowContentList;
	}
	
	public ThreadVO getSelectedThread(){
		return nowThread;
	}
		
	public String[] getCategoryList(){
		return CATEGORY_LIST;
	}
	
	public int getSelectedCategoryIndex(){
		return nowCategoryIndex;
	}
	
	public String getSelectedCategory(){
		return nowCategory;
	}
	
	public Date getNow(){
		return new Date();
	}

	public int getSelectedThreadIndex() {
		return nowThreadIndex;
	}
	
	public boolean isThreadEnd(){
		return nowThreadIndex==nowThreadList.size()-1;
	}
	
	public boolean isThreadStart(){
		return nowThreadIndex==0;
	}
}