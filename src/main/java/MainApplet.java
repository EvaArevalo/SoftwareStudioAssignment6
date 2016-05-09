package main.java;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	
	//Path in Eva's computer
	private String path = "C:/Users/user/Documents/SoftwareStudioAssignment6/src/main/resources/";
	//Path 
	//private String path = "main/resources/";
	private String file = "starwars-episode-1-interactions.json";
	private String title = "Star Wars  1";
	JSONObject data;
	JSONArray nodes, links;
	private ArrayList<Character> characters;
	public PGraphics labelLayer;
	private Character lastPressChar;
	public Network starNet;
	
	private final static int width = 1200, height = 650;
	
	public void setup() {
		size(width, height);
		labelLayer = createGraphics(width,height);
		smooth();
		labelLayer.smooth();
		
		characters = new ArrayList<Character>();
		starNet = new Network(this);
		loadData();
	}

	public void draw() {
		background(255);
		this.textSize(36);
		this.fill(0,0,0);
		text(this.title,(1200-title.length()*15)/2,50);
		
		labelLayer.clear();
		
		starNet.display();
		
		for(Character character: this.characters){
			if(character.inNetwork()){
				for(Character link: character.getLinks()){
					if (link.inNetwork()){
						stroke(186,255,115);
						strokeWeight(2);
						noFill();
						//line(character.getX(), character.getY(), link.getX(), link.getY());
						//control point: 650, 300 > ellipse origin
						curve(starNet.centerX,starNet.centerY,
								character.getX(),character.getY(),link.getX(), link.getY(), 
								starNet.centerX,starNet.centerY);
					}
				}
			}
		}
		
		for(Character character: this.characters)
			character.display();
		
		labelLayer.beginDraw();
		labelLayer.noFill();
		labelLayer.noStroke();
		labelLayer.endDraw();	
		image(labelLayer,0,0);
			
	}
	

	private void loadData(){
		
		data = loadJSONObject(path+file);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");

		for(int i=0; i<nodes.size(); i++){
			JSONObject node = nodes.getJSONObject(i);
			characters.add(new Character(this, node.getString("name"), node.getString("colour"), i));
		}
		
		for(int i=0; i<links.size(); i++){
			JSONObject link = links.getJSONObject(i);
			characters.get(link.getInt("source")).addLink(characters.get(link.getInt("target")));
		}
	}
	
	public void keyPressed(){
		//to change episodes
		//if(keyCode==32)
			//setup();
	}
	
	public void mousePressed(){
		for (Character character: this.characters){
			if(character.inCharacterLimits()){
				lastPressChar = character;
			}
		}
	}
	
	public void mouseDragged(){
		lastPressChar.setX(mouseX);
		lastPressChar.setY(mouseY);
		if(inNetwork(mouseX,mouseY))
			starNet.setWeight(12);
		else
			starNet.setWeight(6);
	}
	
	public void mouseReleased(){
		for (Character character: this.characters){
			if (!character.inNetwork()){
				character.setX(character.initX);
				character.setY(character.initY);
				starNet.popNet(character);
			}
			else if (character.inNetwork()){
				starNet.addToNet(character);
			}
		}
		starNet.setWeight(6);
	}
	
	public boolean inNetwork(int x, int y){
		
		int coordx,coordy,distance;
		
		coordx = Math.abs(x - 650);
		coordy = Math.abs(y - 350);
		distance = (int) (Math.pow(coordx, 2) + Math.pow(coordy, 2));
		
		if( Math.sqrt(distance) <= 250 )
			return true;
		
		else return false;
			
	}
}
