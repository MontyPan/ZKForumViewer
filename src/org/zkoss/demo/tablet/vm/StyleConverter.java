package org.zkoss.demo.tablet.vm;

import java.util.HashMap;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.demo.tablet.AbstractServer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

public class StyleConverter implements Converter<String, String, Component>{
	private static final HashMap<String, String> colorTable = new HashMap<String, String>();
	static{
		for(int i=0; i<AbstractServer.CATEGORY_LIST.length; i++){
			String color = "#";
			switch(i % 3){
			case 0: color += "0099CC"; break;
			case 1: color += "CC6600"; break;
			case 2: color += "333366"; break;
			case 3: color += "CC3300"; break;
			case 4: color += "339900"; break;
			}
			colorTable.put(AbstractServer.CATEGORY_LIST[i], color);
		}
	}
	
	public HashMap<String, String> getColorTable(){
		return colorTable;
	}
	
	public void setCategoryColor(String category, String color){
		colorTable.put(category, color);
	}
	
	@Override
	public String coerceToUi(String beanProp, Component component, BindContext ctx) {
		Label lbl = (Label) component;
		if(beanProp == null){
			System.out.println("FUCK! " + lbl.getValue());//Delete
			return "border:10px solid red;";
		}
		String bgColor = colorTable.get(beanProp);
		int x = Integer.parseInt(bgColor.substring(1), 16);
		double a = 1 - ( 
				0.299 * ((x>>16) & 0xFF) + 
				0.587 * ((x>>8) & 0xFF) + 
				0.114 * (x & 0xFF)
			)/255;
		String color = "black";
		if(a>0.5){
			color = "white";
		}
		return "color:"+color+";padding:1px 3px;border:1px solid black;background-color:"+bgColor;
	}

	@Override
	public String coerceToBean(String compAttr, Component component, BindContext ctx) {
		return null;
	}

}
