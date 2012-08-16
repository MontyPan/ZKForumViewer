package org.zkoss.demo.tablet;

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

	public static final HashMap<String, Integer> CAT_URL = new HashMap<String, Integer>();
	static{
		CAT_URL.put(HELP, 14);
		CAT_URL.put(STUDIO, 18);
		CAT_URL.put(GENERAL, 13);
		CAT_URL.put(ANNOUNCE, 15);
		CAT_URL.put(INSTALL, 17);
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

}