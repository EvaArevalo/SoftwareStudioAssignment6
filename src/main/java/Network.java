package main.java;

import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used for the visualization of the network.
* Depending on your implementation, you might not need to use this class or create a class on your own.
* I used the class to draw the circle and re-arrange nodes and links.
* You will need to declare other variables.
*/
public class Network {
	
	private PApplet parent;
	private ArrayList<Character> CharsInNet;
	private int weight;
	public int centerX,centerY,radius;

	public Network(PApplet parent){
		this.CharsInNet = new ArrayList<Character>();
		this.parent = parent;
		this.weight = 6;
		this.centerX = 650;
		this.centerY = 350;
		this.radius = 250;
	}

	public void display(){
		this.parent.noFill();
		this.parent.stroke(102,204,0);
		this.parent.strokeWeight(weight);
		this.parent.ellipse(centerX, centerY, radius*2, radius*2);
		
	/*	for(int i=0; i < CharsInNet.size(); i++){
			CharsInNet.get(i).setX( (int) (centerX + radius * Math.cos(2*Math.PI*i/CharsInNet.size())) );
			CharsInNet.get(i).setY( (int) (centerY + radius * Math.sin(2*Math.PI*i/CharsInNet.size())) );
		}*/
	}
	
	public void addToNet(Character c){
		CharsInNet.add(c);
	}
	
	public void popNet (Character c){
		//for(Character character:CharsInNet){
			//if(character.equals(c)){
			//	CharsInNet.remove
			//}
		//}
		CharsInNet.remove(c);
		display();
	}
	
	public void setWeight(int x){
		this.weight = x;
	}
	
	public int getWeight(){
		return this.weight;
	}
	
}
