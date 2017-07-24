package com.hamsterfurtif.cop.display.GUIMenu;

public class Button {

	public String name;
	public Object params;
	public Button(String name){
		this.name=name;
	}
	
	public Button(String name, Object args){
		this.name=name;
		this.params = args;
	}
	
	public Object trigger(Object args){
		return null;
	}
	
}
