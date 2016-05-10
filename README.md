# Software Studio Assignment 6
Eva Arevalo 
103062181

### Operation
The Character class is initialized with the parent MainApplet, a name string, a color string. 
The constructor initializes the default coordinates of the object (initx, inity) and other properties as well as the Charachter ArrayList 
for storing the links The color in the .json file written in hexadecimal form is decoded with the statement Long.decode(color)+4278190080L)
we add the constant to skip the alpha option. 
Setters and getters are provided for the main properties of the character object such as position, name and Color and the Charachter ArrayList
representing the charachter's links to preserve encapsulation.
A function to put the charachter in network and pop it out of it are written (they only modify a private bollean variable that keeps the state of
the charachter). forceInNet() and forceOutNet() . Another function returns ths status inside/outside network by evaluating its position in the 
screen. inNetwork()
A function for external classes to check the limits of the object is provided: inCharachterLimits.  
The function display() draws the circle representing the character and checks if the mouse is inside the Character limits to draw the circle slightly
bigger and draw the name labels of the charachters.

The Network Class contains a size() method that returns how many Characters are in network. A function allows to modify the weight of the line used to 
draw the network's circle (which is modified by using a function evaluating if the mouse hovers over the area of the network and if a charachter has 
been dragged to it). There is a removeFromNet and addToNet methods that take a Character object as arggument and add it to the internal ArrayList<Charachter>
object by performing a check with all of its elements using the character.equals method. The methods also call the Charachters functions c.forceOUtNet() to
indirectly set the inNet() internal variable to the appropriate function.
Finally the display function modifies the position of each of the charachter in the Network so that they're equidistant in the outline of the circle using 
the below formulas, provided they're not clicked at the moment: 
CharsInNet.get(i).setX( (int) (centerX + radius * Math.cos(2*Math.PI*i/CharsInNet.size())) );
CharsInNet.get(i).setY( (int) (centerY + radius * Math.sin(2*Math.PI*i/CharsInNet.size()))

The MainApplet class contains a load() function that parses input from .json files and initializes the charachters accordingly. to parse objects more 
efficiently, we add a Pair class we created to replicate the Tuple class in C++. In this way, we have an ArrayList<Pair<Charachter,int>> list for the
 links inside the Charachter object that provide us with the link and the value of it at the same time.
In the setup() method we initialize the PGraphics objects used to display the names of the charachters when the mouse hovers above it, and we smooth it,
we initialize an ArrayList<Character> for the Charachters and a Network object and load data.
keyPressed() function evaluates changes episode with the key pressed.
mousePressed(), mouseReleased() and mouse(Dragged) helped us to code the appropriate behaviour of the object. We check the state in Network and the clicked
state to override the x, y coordinates of the charachters to thos of the mouse and return them either to their default position or to their position in 
network according to their state in the network. A function in Network() helps us to check wether the charachter has been dragged to the network to add it to the network.
The addAll() function adds each charachter to the network by calling the network's addToNet method for each charachter and the charachter's method forceInNet().
The ClearAll() method removes each charachter out of the network and provides animation required to get characters back into their original position.
a checkAddAllLimits() and checkClearAllLimits() functions are added to check the position of the mouse in the screen and clear or add the charachters if the
mouse is clicked inside the (mouseReleased()) method.
Finally draw() draws everything  in the main window, and links are only drawn if charachters are inside the network.

### Visualization
The program takes the value of the links, divides it by two and assigns it as the width of the curve between source and target.
The control points of the curves are the center of the network circle. The color is also assigned according to the value of the link.
A special function decodeColor() takes the parsed value from the .JSON objects and searchs for the case statement containing that number
in a switch/case statement which uses Color's constructor to create a new color and returns a Color to the caller.
 There's 26 colors in total, there's a density scale on the right that shows the exact correspondence between the specific colors and the 
 valules of the link. 
 
 
 ###Issues
 
 The labels of the names of the characters where initially covered by the colored circles. Creating a PGraphics object and adding the names
 to the label layer instead of to the MainApplet solved the issue.
 There was an issue with clicking inside the network before touching any of the charachters since the code uses a variable that points to
 the last pressed charachter; which caused the program to throw a nullPointer exception. We fixed it by checking at the beggining if this 
 variable was null before using it.
 
 I created the repositroy earl on by forking the project and began to work on it. My partner unfortunately had some issues with his Github
 account and was sadly unable to fix it on time. In the end, he sent his part, animation and keypress switch of episode to me for me to upload 
 it to the repository.
