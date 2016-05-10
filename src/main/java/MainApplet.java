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
	private int last_key;
	public Network starNet;
	
	private final static int width = 1200, height = 650;
	
	public void setup() {
		size(width, height);
		//label layers has names of characters, ensures they're on foreground
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
		this.fill(186,255,115);
		text(this.title,(1200-title.length()*15)/2 + 50,50);
		
		//Add All Button
		noStroke();
		fill(102,204,0);
		if(checkAddAllLimits())
			rect(995,95,160,60,18);
		else
			rect(1000, 100, 150, 50, 18);
		textSize(24);
		fill(255);
		text("Add All", 1000 + 30, 100 + 33 );
		
		//ClearAllButton
		noStroke();
		fill(102,204,0);
		if(checkClearAllLimits())
			rect(995,195,160,60,18);
		else
			rect(1000, 200, 150, 50, 18);
		textSize(24);
		fill(255);
		text("Clear All", 1000 + 28, 200 + 33 );
		
		//ColorScale
		this.fill(186,255,115);
		textSize(22);
		text("Density Scale", 1005,475);
		textSize(10);
		text("0", 1155,440);
		text("10", 1155,390);
		text("20", 1155,340);
		text("25", 1155,320);
		for(int i=26; i>0; i--){
			Color scaleColor = decodeColor(i);
			fill(scaleColor.getRed(),scaleColor.getGreen(),scaleColor.getBlue());
			rect(1000,440-i*5,150,5);
		}
		
		//StarNet number of characters in network
		textSize(36);
		fill(102,204,0);
		text(String.valueOf(starNet.size()), 640, 345 );
		
		labelLayer.clear();
		starNet.display();
		
		//Draw links and characters
		for(Character character: this.characters){
			if(character.inNet){
				for(int i=0; i<character.getLinks().size(); i++){
					Character link = character.getLinks().get(i).getLeft();
					if (link.inNet){
						Color strokeColor = decodeColor(character.getLinks().get(i).getRight());
						stroke(strokeColor.getRed(),strokeColor.getGreen(),strokeColor.getBlue());
						strokeWeight(character.getLinks().get(i).getRight()/2);
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
		
		//Label layer
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
			characters.get(link.getInt("source")).addLink(characters.get(link.getInt("target")),link.getInt("value"));
		}
	}
	
	public void keyPressed() {
		if (keyCode != last_key) {
			if (keyCode == '1') {
				title = "Star Wars  1";
				file = "starwars-episode-1-interactions.json";
			} else if (keyCode == '2') {
				title = "Star Wars  2";
				file = "starwars-episode-2-interactions.json";
			} else if (keyCode == '3') {
				title = "Star Wars  3";
				file = "starwars-episode-3-interactions.json";
			} else if (keyCode == '4') {
				title = "Star Wars  4";
				file = "starwars-episode-4-interactions.json";
			} else if (keyCode == '5') {
				title = "Star Wars  5";
				file = "starwars-episode-5-interactions.json";
			} else if (keyCode == '6') {
				title = "Star Wars  6";
				file = "starwars-episode-6-interactions.json";
			} else if (keyCode == '7') {
				title = "Star Wars  7";
				file = "starwars-episode-7-interactions.json";
			}
			if (keyCode >= '1' && keyCode <= '7' && last_key != keyCode){
				last_key = keyCode;
				setup();
			}
		}
	}
	
	public void mousePressed(){
		for (Character character: this.characters){
			if(character.inCharacterLimits()){
				lastPressChar = character;
				lastPressChar.clicked = true;
			}
		}
		if(checkAddAllLimits()){
			addAll();
		}
		else if(checkClearAllLimits()){
			clearAll();
		}
	}
	
	public void mouseDragged(){
		if (lastPressChar!=null){
			if(lastPressChar.clicked){
				lastPressChar.setX(mouseX);
				lastPressChar.setY(mouseY);
			}
		}
		if(inNetwork(mouseX,mouseY))
			starNet.setWeight(12);
		else
			starNet.setWeight(6);
	}
	
	public void mouseReleased(){
		if (!inNetwork(mouseX,mouseY) && !checkAddAllLimits()  && !checkClearAllLimits()){
			if (lastPressChar!=null){
				if(lastPressChar.clicked){
					lastPressChar.setX(lastPressChar.initX);
					lastPressChar.setY(lastPressChar.initY);
					starNet.removeFromNet(lastPressChar);
					if (lastPressChar.inNet)
						lastPressChar.forceOutNet();
				}
			}
		}
		else if (inNetwork(mouseX,mouseY) && !checkAddAllLimits() && !checkClearAllLimits() ){
			if (lastPressChar!=null){
				if(lastPressChar.clicked){
					starNet.addToNet(lastPressChar);
					lastPressChar.forceInNet();
				}
			}
		}
		starNet.setWeight(6);
		
		if (lastPressChar!=null)
			lastPressChar.clicked = false;
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
	
	public void addAll(){
		for(Character character:characters){
			character.forceInNet();
			starNet.addToNet(character);
		}
	}
	
	public void clearAll() {
		for (Character character : characters) {
			double[] vec = new double[2];
			double[] src = new double[2];
			vec[0] = (double)(character.initX - character.getX()) / 60.0;
			vec[1] = (double)(character.initY - character.getY()) / 60.0;
			src[0] = (double)character.getX();
			src[1] = (double)character.getY();
			Thread childThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(Math.abs(character.getX() - character.initX) > 3 || Math.abs(character.getY() - character.initY) > 3){
						character.setX((int)Math.floor(src[0] += vec[0]));
						character.setY((int)Math.floor(src[1] += vec[1]));
						try {
							Thread.sleep(16);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			childThread.start();
			character.forceOutNet();
			starNet.removeFromNet(character);
		}
	}
	
	public boolean checkAddAllLimits(){
		if(mouseX>1000 && mouseX<1000+150 && mouseY>100 && mouseY<100+50)
			return true;
		else return false;
	}
	
	public boolean checkClearAllLimits(){
		if(mouseX>1000 && mouseX<1000+150 && mouseY>200 && mouseY<200+50)
			return true;
		else return false;
	}
	
	public Color decodeColor(int i){
		Color strokeColor;
		switch(i){
		case 1: 
			strokeColor = new Color(0,0,128); ///navy
			break;
		case 2:
			strokeColor = new Color(0,0,205); //dark blue
			break;
		case 3:
			strokeColor = new Color(0,0,255); // blue
			break;
		case 4:
			strokeColor = new Color(75,0,130); //indigo
			break;
		case 5:
			strokeColor = new Color(139,0,139); //dark magenta
			break;
		case 6:
			strokeColor = new Color(148,0,211); //dark violet
			break;
		case 7:
			strokeColor = new Color(186,85,211); //medium orchid
			break;
		case 8:
			strokeColor = new Color(255,105,180); //hot pink
			break;
		case 9:
			strokeColor = new Color(250,128,114); // salmon
			break;
		case 10:
			strokeColor = new Color(255,0,0); //red
			break;
		case 11:
			strokeColor = new Color(220,20,60); //crimson
			break;
		case 12:
			strokeColor = new Color(178,34,34); //firebrick
			break;
		case 13:
			strokeColor = new Color(218,165,32); //golden rod
			break;
		case 14:
			strokeColor = new Color(255,215,0); //gold
			break;
		case 15:
			strokeColor = new Color(255,255,0); //yellow
			break;
		case 16:
			strokeColor = new Color(173,255,47); //green yellow
			break;
		case 17:
			strokeColor = new Color(154,205,50); //yellow green
			break;
		case 18:
			strokeColor = new Color(124,252,0); //lawn
			break;
		case 19:
			strokeColor = new Color(0,255,0); //lime
			break;
		case 20:
			strokeColor = new Color(0,128,0); //green
			break;
		case 21:
			strokeColor = new Color(0,100,0); //dark green
			break;
		case 22:
			strokeColor = new Color(60,179,113); //medium sea green
			break;
		case 23:
			strokeColor = new Color(32,178,170); //light sea green
			break;
		case 24:
			strokeColor = new Color(72,209,204); //medium turquoise
			break;
		case 25:
			strokeColor = new Color(127,255,212); //aquamarine
			break;
		case 26:
			strokeColor = new Color(176,224,230); //powder blue
			break;
		default:
			strokeColor = new Color(176,224,230); //powder blue
		}
		return strokeColor;
	}
}
