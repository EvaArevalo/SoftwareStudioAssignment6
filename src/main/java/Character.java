package main.java;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	private int x, y;
	private String name;
	private int orderNum;
	private Color color;
	private ArrayList<Character> Links;

	public Character(MainApplet parent, String name, int orderNum /*, color var*/){
		this.parent = parent;
		this.name = name;
		this.orderNum = orderNum;
		Links = new ArrayList<Character>();
		//set color
		//set default x and y according to order num 
		//i.e. z number of circles per row, this.x = z*(orderNum+spacing)
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.getName();
	}
	
	public void addLink(Character Link){
		this.Links.add(Link);
	}
	
	public ArrayList<Character> getLinks(){
		return this.Links;
	}

	public void display(){

	}
	
}
