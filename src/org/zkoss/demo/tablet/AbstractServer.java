package org.zkoss.demo.tablet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.demo.tablet.vo.ContentVO;
import org.zkoss.demo.tablet.vo.ThreadVO;

public abstract class AbstractServer {
	public static final String HELP = "Help";
	public static final String STUDIO = "ZK Studio";
	public static final String GENERAL = "General";
	public static final String ANNOUNCE = "Announcements";
	public static final String INSTALL = "Installation";
	
	public static final String INBOX = "Inbox";
	public static final String IMPORTANT = "Important";
	public static final String STARRED = "Starred";
	public static final String TRASH = "Trash";
	
	public static final String[] FOLDER_LIST = {
		INBOX, IMPORTANT, STARRED//, TRASH
	};
	
	public static final String[] CATEGORY_LIST = {
		HELP, STUDIO, GENERAL, ANNOUNCE, INSTALL
	};
	
	public static final HashMap<String, Integer> CATEGORY_URL = new HashMap<String, Integer>();
	static{
		CATEGORY_URL.put(HELP, 14);
		CATEGORY_URL.put(STUDIO, 18);
		CATEGORY_URL.put(GENERAL, 13);
		CATEGORY_URL.put(ANNOUNCE, 15);
		CATEGORY_URL.put(INSTALL, 17);
	}
	
	//Refactory extract parser of DataServer and MockServer
	public abstract List<ThreadVO> getThreadList(String type);

	protected static final String CONTENT_AUTHOR_HEADER = "<span class=\"author-name\">";
	protected static final String CONTENT_AUTHOR_TAIL = "</span>";
	protected static final String CONTENT_DATE_HEADER = "<span class=\"comment-date\">";
	protected static final String CONTENT_DATE_TAIL = "</span>";
	protected static final String CONTENT_PRE_HEADER = "<span class=\"comment-content\">";
	protected static final String CONTENT_HEADER = "</span>";
	protected static final String CONTENT_TAIL = "</div></div><div class=\"author\">";
	public abstract List<ContentVO> getContentList(ThreadVO thread);

	public abstract void moveToTrash(ArrayList<ThreadVO> selectedThread);

}