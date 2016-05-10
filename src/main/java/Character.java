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
	public int initX, initY;
	private String name;
	private int orderNum;
	private Color color;
	public boolean inNet;
	//private ArrayList<Character> Links;
	private ArrayList<Pair<Character,Integer>> LinkArr;
	private final int width = 40;
	private final int spacing = 15;
	private final int margex = 30;
	private final int margey = 100;
	public boolean clicked;
	
	public Character(MainApplet parent, String name, String color, int orderNum){
		this.LinkArr = new ArrayList<Pair<Character,Integer>>();
		this.parent = parent;
		this.name = name;
		this.orderNum = orderNum;
		//parse hexadecimal to int to create color, remove alpha
		this.color = new Color((int) (Long.decode(color)+4278190080L));
		this.initX = (this.width + spacing) * (orderNum / 10) + margex;
		this.initY = (this.width + spacing) * (orderNum % 10) + margey;
		this.x = initX;
		this.y = initY;
		this.clicked = false;
	}
	
	public void setX(int x){ this.x = x; }
	
	public int getX(){ return this.x; }
	
	public void setY(int y){ this.y = y; }
	
	public int getY(){ return this.y; }
	
	public int getWidth() { return this.width; }
	
	public Color getColor() { return this.color; }
	
	public void setName(String name){ this.name = name; }
	
	public String getName(){ return this.getName(); }
	
	public void addLink(Character link, int val){ this.LinkArr.add(new Pair(link,val)); }
	
	public ArrayList<Pair<Character,Integer>> getLinks(){ return this.LinkArr;}
	
	public boolean inCharacterLimits(){
		if( parent.mouseX > this.x - this.width/2 && this.parent.mouseX < this.x + this.width/2
				&& parent.mouseY > this.y - this.width/2 && this.parent.mouseY < this.y + this.width/2 )
			return true;
		else return false;
	}
	
	public void display(){

		if(inCharacterLimits()){
			this.parent.noStroke();
			//if(inNetwork())
				//this.parent.noTint();
			this.parent.fill(this.color.getRed(),this.color.getGreen(), this.color.getBlue());
			this.parent.ellipse(this.x,this.y,this.width+5,this.width+5);
			this.parent.labelLayer.noStroke();
			this.parent.labelLayer.fill(230);
			//this.parent.labelLayer.noTint();
			this.parent.labelLayer.rect(parent.mouseX, parent.mouseY, this.name.length()*10 + 30, 20, 18);
			
			this.parent.labelLayer.textSize(16);
			this.parent.labelLayer.fill(this.color.getRed(),this.color.getGreen(), this.color.getBlue());
			this.parent.labelLayer.text(this.name, parent.mouseX +15, parent.mouseY+15);
		}
		else{
			this.parent.noStroke();
			//if(inNetwork())
			//	this.parent.tint(255, 126);
			this.parent.fill(this.color.getRed(),this.color.getGreen(), this.color.getBlue());
			this.parent.ellipse(this.x,this.y,this.width,this.width);
			//this.parent.labelLayer.noTint();
		}
	
	}
	
	public boolean inNetwork(){
		
		int coordx,coordy,distance;
		
		coordx = Math.abs(this.x - 650);
		coordy = Math.abs(this.y - 350);
		distance = (int) (Math.pow(coordx, 2) + Math.pow(coordy, 2));
		
		if( Math.sqrt(distance) <= 250 )
			inNet = true;
		
		return inNet;
			
	}
	
	public void forceInNet(){
		inNet = true;
	}
	
	public void forceOutNet(){
		this.inNet = false;
	}
	
}
