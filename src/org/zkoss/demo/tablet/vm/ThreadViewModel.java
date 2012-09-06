package org.zkoss.demo.tablet.vm;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.demo.tablet.AbstractServer;
import org.zkoss.demo.tablet.DataServer;
import org.zkoss.demo.tablet.DeviceMode;
import org.zkoss.demo.tablet.mock.MockServer;
import org.zkoss.demo.tablet.vo.ContentVO;
import org.zkoss.demo.tablet.vo.ThreadVO;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Center;
import org.zkoss.zul.Popup;

public class ThreadViewModel {
	private static final String[] CATEGORY_LIST = {
		AbstractServer.HELP, AbstractServer.STUDIO, AbstractServer.GENERAL, AbstractServer.ANNOUNCE, AbstractServer.INSTALL
	};

	private AbstractServer server;
	private ThreadVO nowThread;
	private int nowThreadIndex;
	private int nowCategoryIndex = 1;
	private List<ThreadVO> nowThreadList;
	private List<ContentVO> nowContentList;
	private ContentVO lastContent;
	private boolean westFlag = true;
	private boolean centerFlag = true;
	private DeviceMode deviceMode = new DeviceMode();
	
	@Wire("#contentPanel") private Center contentPanel;
	@Wire("#popup") private Popup popup;
	
	private void fetchThread(){
		nowThreadList = server.getThreadList(CATEGORY_LIST[nowCategoryIndex]);
		nowThreadIndex = 0;
		fetchContent();
	}
	
	private void fetchContent(){
		nowThread = nowThreadList.get(nowThreadIndex);
		nowContentList = server.getContentList(this.nowThread);
		lastContent = nowContentList.remove(nowContentList.size() - 1);
		lastContent.setOpen(true);
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, @BindingParam("mode") String mode){
		server  = ("mock".equals(mode) ?  new MockServer() : new DataServer()); 	
		fetchThread();		
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		 Selectors.wireComponents(view, this, false);
	 }

	@Command
	@NotifyChange("deviceMode")
	public void clientInfoChanged(@BindingParam("event") ClientInfoEvent event){
		deviceMode.setClientInfo(event);
	}
	
	public DeviceMode getDeviceMode() {
		return deviceMode;
	}
	
	@NotifyChange({"contentList", "lastContent", "selectedThread", "threadStart", "threadEnd", "threadMode"})
	public void setSelectedThreadIndex(int index){ 
		nowThreadIndex = index;
		threadFlag = true;
		fetchContent();
	}

	@Command
	@NotifyChange("threadMode")
	public void backToCategory(){
		threadFlag = false;
	}
	
	private boolean threadFlag = false;
	//For Phone device
	public boolean isThreadMode(){
		return threadFlag; 
	}

	@NotifyChange("*")
	public void setSelectedCategoryIndex(int index){
		nowCategoryIndex = index;
		westFlag = !westFlag;
		fetchThread();	
	}
			
	@Command
	@NotifyChange(
		{"contentList", "lastContent", "selectedThread", "selectedThreadIndex", 
		 "threadEnd", "threadStart"}
	)
	public void prevThread(){
		if(nowThreadIndex>0){
			nowThreadIndex--;
			fetchContent();
		}
	}
	
	@Command
	@NotifyChange(
		{"contentList", "lastContent", "selectedThread", "selectedThreadIndex", 
		 "threadEnd", "threadStart"}
	)
	public void nextThread(){
		if(nowThreadIndex<nowThreadList.size()-1){
			nowThreadIndex++;
			fetchContent();
		}
	}
	
	//TODO rename and improve the function
	@Command
	@NotifyChange("contentList")
	public void collapseAll(){
		for(ContentVO cvo : nowContentList)
			cvo.setOpen(!cvo.isOpen());
	}
	
	//FIXME button must click twice
	private boolean popupFlag = false;
	@Command
	public void showPopup(@BindingParam("component") Component c){
		if(popupFlag){
			popup.close();
		}else{
			popup.open(c, "before_center");
		}
		popupFlag = !popupFlag;
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
	@NotifyChange({"centerUrl", "centerMode"})
	public void showSetting(){
		centerFlag = !centerFlag;
		contentPanel.invalidate(); //Refactory should trigger after setCenterUrl()
	}
	
	public String getCenterUrl() {
		return centerFlag ? "content.zul" : "setting.zul";
	}
	
	public boolean isCenterMode() {
		return centerFlag;
	}
		
	public List<ThreadVO> getThreadList(){	
		return nowThreadList;
	}
	
	public List<ContentVO> getContentList(){
		return nowContentList;
	}
	
	public ContentVO getLastContent() {
		return lastContent;
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
		return CATEGORY_LIST[nowCategoryIndex];
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