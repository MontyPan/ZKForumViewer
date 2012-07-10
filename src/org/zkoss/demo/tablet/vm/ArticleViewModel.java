package org.zkoss.demo.tablet.vm;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.demo.tablet.vo.ThreadVO;

public class ArticleViewModel {
	private ThreadVO thread;
	
	
	@GlobalCommand
	@NotifyChange("*")
	public void assignThread(@BindingParam("thread") ThreadVO thread) {
		this.thread = thread;
	}
}