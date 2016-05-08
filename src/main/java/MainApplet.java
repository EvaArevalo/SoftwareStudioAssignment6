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
	//private String path = "main/resources/";
	private String file = "starwars-episode-1-interactions.json";
	private String title = "Star Wars  1";
	JSONObject data;
	JSONArray nodes, links;
	private ArrayList<Character> characters;
	
	private final static int width = 1200, height = 650;
	
	public void setup() {
		size(width, height);
		smooth();
		characters = new ArrayList<Character>();
		loadData();
	}

	public void draw() {
		background(255);
		this.textSize(36);
		this.fill(0,0,0);
		text(this.title,(1200-title.length()*15)/2,50);
		
		for(Character character: this.characters)
			character.display();

	}
	
	public void keyPressed(){
		//if(keyCode==32)
			//setup();
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

}
