package org.zkoss.demo.tablet.vm;

import java.util.ArrayList;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

public class FVViewModel {
	// ==== for setting category color ==== //
	private StyleConverter styleConverter = new StyleConverter();
	private String setCategory = AbstractServer.HELP;
	public StyleConverter getStyleConverter() {
		return styleConverter;
	}
	
	@NotifyChange("categoryColor")
	public void setSetColorCategory(String s){
		setCategory = s;
	}
	
	public String getSetColorCategory(){
		return setCategory;
	}
	
	public String getCategoryColor(){
		return styleConverter.getColorTable().get(setCategory);
	}
	
	@Command
	@NotifyChange({"threadList", "categoryList"})
	public void categoryColor(@BindingParam("color") String value, @BindingParam("category") String category){
		styleConverter.setCategoryColor(category, value);
	}
	// ======== //
	
	private AbstractServer server;
	private ThreadVO nowThread;
	private int nowThreadIndex;
	private int nowCategoryIndex = -1;
	private int nowFolderIndex=1;
	private List<ThreadVO> nowThreadList;
	private List<ContentVO> nowContentList;
	private ContentVO lastContent;
	private boolean westFlag = true;
	private boolean centerFlag = true;
	private DeviceMode deviceMode = new DeviceMode();
	
	@Wire("#contentPanel") private Center contentPanel;
	@Wire("#mainPanel") private Borderlayout mainPanel;
	@Wire("#threadList") private Listbox threadList;
	
	@Command
	@NotifyChange({"threadList", "selectedThread", "contentList"})
	public void deleteThread(){
		ArrayList<ThreadVO> selectedThread = new ArrayList<ThreadVO>(); 
		for(Listitem s : threadList.getItems()){
			Checkbox ckb = (Checkbox)s.getChildren().get(0).getChildren().get(0).getChildren().get(0);  //TODO WTF code
			if(ckb.isChecked()){
				selectedThread.add((ThreadVO)s.getValue());
			}
		}
		if(selectedThread.size()==0){
			Clients.showNotification("Please select as least one item.");
			return;
		}
		
		server.moveToTrash(selectedThread);
		fetchThread();
	}
	
	private void fetchThread(){
		if(nowCategoryIndex!=-1){
			nowThreadList = server.getThreadList(AbstractServer.CATEGORY_LIST[nowCategoryIndex]);
		}else{
			nowThreadList = server.getThreadList(AbstractServer.FOLDER_LIST[nowFolderIndex]);
		}
		nowThreadIndex = 0;
		
		if(nowThreadList.size()!=0){
			fetchContent();
		}
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
	@NotifyChange("*")
	public void clientInfoChanged(@BindingParam("event") ClientInfoEvent event){
		deviceMode.setClientInfo(event);
		Clients.resize(mainPanel);//For fxck Android native browser can't resize component
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
		nowFolderIndex = -1;
		westFlag = !westFlag;
		fetchThread();	
	}
	
	@NotifyChange("*")
	public void setSelectedFolderIndex(int index){
		nowFolderIndex = index;
		nowCategoryIndex = -1;
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

	@Command
	@NotifyChange({"westUrl", "threadList", "categoryList"})  
	public void showCategory(){
		westFlag = !westFlag;
	}

	public String getWestUrl() {
		return westFlag ? "thread.zul" : "category.zul";
	}
	
	public boolean isWestMode(){
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
	
	//TODO MVVM's issue? (why not : vm.threadList.empty())
	public boolean isThreadListEmpty(){
		return nowThreadList.isEmpty();
	}
	
	//TODO MVVM's issue?
	public int getEmptyIndex(){
		return -1;
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
	
	public String[] getFolderList(){
		return AbstractServer.FOLDER_LIST;
	}
	
	public int getSelectedFolderIndex(){
		return nowFolderIndex;
	}
	
	public String[] getCategoryList(){
		return AbstractServer.CATEGORY_LIST;
	}
	
	public int getSelectedCategoryIndex(){
		return nowCategoryIndex;
	}
	
	public String getSelectedCategory(){
		if(nowCategoryIndex!=-1){
			return AbstractServer.CATEGORY_LIST[nowCategoryIndex];	
		}else{
			return AbstractServer.FOLDER_LIST[nowFolderIndex];
		}
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