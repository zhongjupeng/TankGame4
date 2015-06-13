package com.test;

import java.awt.Graphics;
import java.util.Vector;
import java.io.*;

class Tank1{
	//坦克坐标
	int x=0;
	int y=0;
	//0上  1右  2下   3左 
	int direct=0;
	//坦克的速度
	int speed=2;
	//坦克的颜色
	int color;
	boolean isLive=true;
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
    public Tank1(int x,int y){
    	this.x=x;
    	this.y=y;
    }	
	
}

class Hero extends Tank1{
	Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	public Hero(int x,int y){
		super(x,y);
	}
	public void shotEnemyTank(){
		
		switch(this.direct)
		{
		case 0:
			s=new Shot(x+10,y-3,0);
			ss.add(s);
		    break;
		case 1:
			s=new Shot(x+33,y+10,1);
			ss.add(s);
            break;
		case 2:
			s=new Shot(x+10,y+33,2);
		    ss.add(s);
			break;

		case 3:
			s=new Shot(x-3,y+10,3);
			ss.add(s);
		    break;
		}
		//启动子弹线程
		Thread t=new Thread(s);
		t.start();
	}
	public void moveup(){
		y-=speed*2;
	}
	public void moveright(){
		x+=speed*2;
	}
	public void movedown(){
		y+=speed*2;
	}
	public void moveleft(){
		x-=speed*2;
	}
}

//敌人坦克类，将敌人坦克做成线程
class EnemyTank extends Tank1 implements Runnable{
	
	int timers=0;
	//定义一个向量，得到Mypanel上所有的敌人坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//定义一个敌人的子弹组组，
	Vector<Shot> ss=new Vector<Shot>();
	//当敌人坦克创建时和敌人坦克子弹死亡时，添加敌人子弹
	public EnemyTank(int x,int y){
		super(x,y);
	}
	
	//得到所有的敌人坦克向量
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//判断是否碰到了别的敌人坦克
	public boolean isTouchOtherEnemyTank()
	{
		boolean b=false;
		switch(this.direct)
		{
		case 0:
			//坦克向上
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是该坦克自己
				if(et!=this)
				{
					//如果敌人的坦克是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+20>=et.y&&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					
					//如果敌人的坦克是向左或者向下
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
					}
					
				}
			}
			break;
		case 1:
			//坦克向右
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是该坦克自己
				if(et!=this)
				{
					//如果敌人的坦克是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y+20>=et.y&&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					
					//如果敌人的坦克是向左或者向下
					if(et.direct==1||et.direct==3)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+30&&this.y+20>=et.y&&this.y+20<=et.y+20)
						{
							return true;
						}
					}
					
				}
			}
			break;
		case 2:
			//坦克向下
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是该坦克自己
				if(et!=this)
				{
					//如果敌人的坦克是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+30>=et.y&&this.y+30<=et.y+30)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y+30>=et.y&&this.y+30<=et.y+30)
						{
							return true;
						}
					}
					
					//如果敌人的坦克是向左或者向右
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y+30>=et.y&&this.y+30<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y+30>=et.y&&this.y+30<=et.y+20)
						{
							return true;
						}
					}
					
				}
			}
			break;
		case 3:
			//坦克向左
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出第一个坦克
				EnemyTank et=ets.get(i);
				//如果不是该坦克自己
				if(et!=this)
				{
					//如果敌人的坦克是向上或者向下
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+20>=et.y&&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					
					//如果敌人的坦克是向左或者向右
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+30&&this.y+20>=et.y&&this.y+20<=et.y+20)
						{
							return true;
						}
					}
					
				}
			}
			break;
		
		}
		return b;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
		
		switch(this.direct){
		case 0:
			for(int i=0;i<30;i++){
				if(y>0&&!this.isTouchOtherEnemyTank()){
			y-=speed;
				}
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
			break;
		case 1:
			for(int i=0;i<30;i++){
				if(x<370&&!this.isTouchOtherEnemyTank()){
			x+=speed;
				}
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
			break;
		case 2:
			for(int i=0;i<30;i++){
				if(y<270&&!this.isTouchOtherEnemyTank()){
			y+=speed;
				}
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
			break;
		case 3:
			for(int i=0;i<30;i++){
				if(x>0&&!this.isTouchOtherEnemyTank()){
			x-=speed;
				}
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
			break;
			
		    }
		//判断是否要加入敌人子弹
		timers++;
		if(timers%2==0){
		  if(isLive){
			if(ss.size()<5){
				Shot s=null;
				//没有子弹，添加子弹
				switch(this.direct)
				{
				case 0:
					s=new Shot(x+10,y-3,0);
					ss.add(s);
				    break;
				case 1:
					s=new Shot(x+33,y+10,1);
					ss.add(s);
		            break;
				case 2:
					s=new Shot(x+10,y+33,2);
				    ss.add(s);
					break;

				case 3:
					s=new Shot(x-3,y+10,3);
					ss.add(s);
				    break;
				}
				//启动子弹线程
				Thread t=new Thread(s);
				t.start();
			}
		}
		}
		//让坦克产生随机方向
		this.direct=(int)(Math.random()*4);
		//判断敌人坦克是否死亡
		if(this.isLive==false){
			//如果地方坦克死亡，则跳出该线程
			break;
		}
		}
		
	}
	
} 

class Shot implements Runnable{
	int x=0;
	int y=0;
	int direct;
	int speed=3;	
	//子弹是否消失
	boolean isLive=true; 
	public Shot(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run(){
		
		while(true){
			//50毫秒
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		switch(this.direct)
		{
		
		case 0:
			y-=speed;
			break;

		case 1:
			x+=speed;
			break;

		case 2:
			y+=speed;
			break;

		case 3:
			x-=speed;
			break;
		}
	
		//子弹何时死亡
		
		//判断子弹是否碰到边缘
		
		if(x<0||x>400||y<0||y>300){
			isLive=false;
			break;
		}
		}
	}
	
}

//炸弹类
class Bomb
{
	int x=0,y=0;
	boolean isLive=true;
	int life=9;
	public Bomb(int x,int y){
		this.x=x;
		this.y=y;
	}
public void lifeDown(){
	if(life>0){
		life--;
	}else
		this.isLive=false;
}
}

//记录点类
class Node
{
	int x;
	int y;
	int direct;
	Node(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}

}
//记录类，同时也可以保存玩家的设置
class Recorder
{
 
	//记录没关有多少敌人
	private static int enNum=20; 
	//记录我的生命
		private static int myLife=3;
	//记录总共消灭多少人
		private static int allEnemy=0;
		private static FileWriter fw=null;
		private static BufferedWriter bw=null;
		private static FileReader fr=null;
		private static BufferedReader br=null;
		private Vector<EnemyTank> ets=new Vector<EnemyTank>();
		//从文件中恢复记录点
		private Vector<Node> Nodes=new Vector<Node>();
		
     //读取保存退出的数据
		public Vector<Node> getNodeAndEnnums()
		{
			try {
				fr=new FileReader("E:\\JAVAprogram\\MyTankGame4\\123.txt");
				br=new BufferedReader(fr);
                String n=" ";
                //先读取第一行
                n=br.readLine();
                allEnemy=Integer.parseInt(n);
                while((n=br.readLine())!=null)
                {
                	String []xyz=n.split(" ");
                	Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]));
                	Nodes.add(node);
                	
                }

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally
			{
				try {
					br.close();
					fr.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return Nodes;
		}
		//保存退出
		public void keepRecondAndEnemyTank()
		{
			try {
				fw=new FileWriter("E:\\JAVAprogram\\MyTankGame4\\123.txt");
				bw=new BufferedWriter(fw);
				bw.write(allEnemy+"\r\n");
				//保存当前活着的坦克位置和方向
				for(int i=0;i<ets.size();i++)
				{
					EnemyTank et=ets.get(i);
					if(et.isLive)
					{
						String x=et.x+" "+et.y+" "+et.direct;
						bw.write(x+"\r\n");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				
				try {
					bw.close();
					fw.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			
		}
		//读取问件信息
		public static void getReconding()
		{
			try {
				fr=new FileReader("E:\\JAVAprogram\\MyTankGame4\\123.txt");
				br=new BufferedReader(fr);
				String s=br.readLine();
				allEnemy=Integer.parseInt(s);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally
			{
				try {
					br.close();
					fr.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
    //吧总共消灭的敌人坦克数记录到问件中
		public static void keepReconding()
		{
			
			try {
				fw=new FileWriter("E:\\JAVAprogram\\MyTankGame4\\123.txt");
				bw=new BufferedWriter(fw);
				bw.write(allEnemy+"\r\n");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				
				try {
					bw.close();
					fw.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	public static int getAllEnemy() {
			return allEnemy;
		}
		public static void setAllEnemy(int allEnemy) {
			Recorder.allEnemy = allEnemy;
		}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	
	public Vector<EnemyTank> getEts() {
		return ets;
	}
	public void setEts(Vector<EnemyTank> ets) {
		this.ets = ets;
	}
	
	public static void reduce()
	{
		enNum--;
	}
	//消灭敌人
	public static void addEnNum()
	{
		allEnemy++;
	}
	
	
}