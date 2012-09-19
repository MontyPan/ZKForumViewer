package org.zkoss.demo.tablet;

import org.zkoss.zk.ui.event.ClientInfoEvent;

//TODO change to bit operate
public class DeviceMode {
	private boolean vertical;
	private int value;
	private int width;
	private int height;
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setVertical(boolean flag) {
		vertical = flag;
	}
	
	public void setPhone(boolean flag) {
		value = flag ? 4 : value;
	}
	
	public void setPad(boolean flag) {
		value = flag ? 2 : value;
	}
	
	public void setDesktop(boolean flag) {
		value = flag ? 0 : value;
	}
	
	public boolean isVertical() {
		return vertical;
	}
	
	public boolean isPhone() {
		return (value & 4) != 0;
	}
	
	public boolean isPad() {
		return (value & 2) != 0;
	}
	
	public boolean isDesktop() {
		return value != 0;
	}

	@Override
	public String toString() {
		return "DeviceMode [vertical=" + vertical + ", value=" + value + "]";
	}

	public void setClientInfo(ClientInfoEvent event) {
		width = event.getDesktopWidth();
		height = event.getDesktopHeight();
		this.setVertical(event.isVertical());
		if (vertical) {
			if (height <= 640){
				this.setPhone(true);
			} else if (height <= 960){
				this.setPad(true);
			}			
		} else{
			if (width <= 640){
				this.setPhone(true);
			} else if (width <= 1024){
				this.setPad(true);
			}
		}
	}
}
