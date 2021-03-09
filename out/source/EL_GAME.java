import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import gifAnimation.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class EL_GAME extends PApplet {

/*
---------------------------------------------------------
Enemy Logic
---------------------------------------------------------
Description:
Basic Enemy Logic
-------------------
Changelog:
04.03.2021 - v1 - Initial Version
  -Generation of Enemies of 3 different types(Shooter,
   Swordsman, Boss)
  -Display Enemy Type A; Walking animation
---------------------------------------------------------
JES - 2021
---------------------------------------------------------
*/

Gif enemywalk;
PImage back;
PImage idle;
public void setup(){

enemywalk = new Gif(this,"Enemy Walk.gif");
laneheight=height/9;
back = loadImage("Area01.png");
idle = loadImage("Enemy Base.png");
}
float diff=2;                       //Difficulty
float difffactor=diff/2;            //Factor of multiplier set by difficulty
int level = 3;                      //current Level
Enemy[] enemies = new Enemy[1];    //Enemies //<>// //<>//
boolean levelchanged=false;
float sumHP=0;
float laneheight;
float currentenemyrealy=0;
float currentenemyln=0;
public void draw(){
  back.resize(width,height);
  background(back);
    sumHP=0;
    for(int e=enemies.length;e>0;e--){
      try{
        sumHP=sumHP+enemies[e-1].getHP();
        enemies[e-1].Update();
        if(enemies[e-1].getSpawned()==true){
          currentenemyln=enemies[e-1].getPos()[1];
          if(currentenemyln==1){
            currentenemyrealy=laneheight*0.1f;}
            else if(currentenemyln==2){
            currentenemyrealy=laneheight*3.1f;}
            else if(currentenemyln==3){
            currentenemyrealy=laneheight*6.1f;}
            if(enemies[e-1].getIdle()==false){
          image(enemywalk,enemies[e-1].getPos()[0],currentenemyrealy); //<>//
          }else if(enemies[e-1].getIdle()==true){
          image(idle,enemies[e-1].getPos()[0],currentenemyrealy); //<>//
        }
        }}
      catch(Exception ex){
        println("no enemy " + ex);
      }
    }
    if(sumHP==0){
      levelchanged=true;
      enemywalk.loop();
    } 
    if (PApplet.parseInt(random(1,50))==25){
      if(level==1){
      enemies[PApplet.parseInt(random(1,18.49f))].Spawn();
      }
      else if(level==2){
      enemies[PApplet.parseInt(random(1,38.49f))].Spawn();
      }
      else if(level==3){
      enemies[PApplet.parseInt(random(1,59.49f))].Spawn();
      }
    }
    if(levelchanged==true){
        GenerateEnemies();
        levelchanged=false;
    }
}

class Enemy{
  ///<summary>
  ///A singular Enemy.Could be either Shooter, Swordsman or a boss.
  ///</summary>
    int type;                       //Describes Enemy Type. 1=Shooter; 2=Swordsman; 3=Special(Bosses)
    boolean spawned = false;        //Enemy has spawned. True if spawned
    float[] pos = {0,10000};  	    //Enemy Position [x,lane:'1' = Top ;'2' = Mid; '3' = Bottom]
    float speed=0;                  //Enemy Speed (Modulated by Type and difficulty)
    float HP=0;                     //Enemy HP (Modulated by Type and difficulty)
    boolean isidle=false;
    Enemy(int type){                   //Generate New Enemy By Type
    ///<summary>
    ///Call upon you a single enemy. the type you enter will be set as the enemy's type
    ///</summary>
        this.type=type;              //set internal t to classwide type
        if(type==1){                 //Shooter speed and HP settings
            speed=10*difffactor;
            HP=30*difffactor;
        }
        else if(type==2){            //Swordsman Basic Settings
            speed=20*difffactor;
            HP=10*difffactor;
        }
        else if(type==3){            //Boss Basic Settings
            speed=5*difffactor;
            HP=150*difffactor;
        }
    }
    public void Spawn(){
      ///<summary> //<>//
      ///Gives the Enemy a lane and position, and makes them drawable.
      ///</summary>
      if(spawned==false){
        spawned=true; //<>//
        pos[0]=width; //<>//
        pos[1]=PApplet.parseInt(random(1, 3.49f)); //<>//
      }
    }
    public float getHP(){
      ///<summary>
      ///Returns the Enemies' HP. Used to decide wether a level is complete or not
        return HP;
    }
    public void Update(){
      float posXmax=width/3;
      if(pos[0]>posXmax){
        pos[0]=pos[0]-speed;}
        else if(pos[0]<=posXmax){
        pos[0]=posXmax;
        if(spawned==true){
        isidle=true;
        }}
    }
    public float[] getPos(){
    return pos;
    }
    public boolean getSpawned(){
    return spawned;
    }
    public boolean getIdle(){
    return isidle;
    }
    public void Attack(){

    }
    public void Destroy(){

    }
}
public void GenerateEnemies(){
  for(int e=enemies.length-1;e>=0;e--){
  enemies = (Enemy[])shorten(enemies);} //<>//
  if(level==1){
    enemies = (Enemy[])expand(enemies, 20);
    for(int i=0;i<20;i++){
        enemies[i]=new Enemy(PApplet.parseInt(random(1, 2.5f)));
    }
  }
    else if(level==2){
      enemies = (Enemy[])expand(enemies, 40);
        for(int i=0;i<40;i++){
        enemies[i]=new Enemy(PApplet.parseInt(random(1, 2.5f)));
    }
    }else if(level==3){
      enemies = (Enemy[])expand(enemies, 61); //<>//
        for(int i=0;i<60;i++){ //<>//
        enemies[i]=new Enemy(PApplet.parseInt(random(1, 2.5f))); //<>//
    }enemies[enemies.length-1]=new Enemy(3);
    }
}
class Bullet{
    int pos;
    int dir;
    Bullet(float[] enemypos){

    }
}
  public void settings() { 
size(1500,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "EL_GAME" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
