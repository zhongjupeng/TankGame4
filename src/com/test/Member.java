package com.test;

import java.awt.Graphics;
import java.util.Vector;
import java.io.*;

class Tank1{
	//̹������
	int x=0;
	int y=0;
	//0��  1��  2��   3�� 
	int direct=0;
	//̹�˵��ٶ�
	int speed=2;
	//̹�˵���ɫ
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
		//�����ӵ��߳�
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

//����̹���࣬������̹�������߳�
class EnemyTank extends Tank1 implements Runnable{
	
	int timers=0;
	//����һ���������õ�Mypanel�����еĵ���̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//����һ�����˵��ӵ����飬
	Vector<Shot> ss=new Vector<Shot>();
	//������̹�˴���ʱ�͵���̹���ӵ�����ʱ����ӵ����ӵ�
	public EnemyTank(int x,int y){
		super(x,y);
	}
	
	//�õ����еĵ���̹������
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//�ж��Ƿ������˱�ĵ���̹��
	public boolean isTouchOtherEnemyTank()
	{
		boolean b=false;
		switch(this.direct)
		{
		case 0:
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//������Ǹ�̹���Լ�
				if(et!=this)
				{
					//������˵�̹�������ϻ�������
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
					
					//������˵�̹���������������
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
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//������Ǹ�̹���Լ�
				if(et!=this)
				{
					//������˵�̹�������ϻ�������
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
					
					//������˵�̹���������������
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
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//������Ǹ�̹���Լ�
				if(et!=this)
				{
					//������˵�̹�������ϻ�������
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
					
					//������˵�̹���������������
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
			//̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//������Ǹ�̹���Լ�
				if(et!=this)
				{
					//������˵�̹�������ϻ�������
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
					
					//������˵�̹���������������
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
		//�ж��Ƿ�Ҫ��������ӵ�
		timers++;
		if(timers%2==0){
		  if(isLive){
			if(ss.size()<5){
				Shot s=null;
				//û���ӵ�������ӵ�
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
				//�����ӵ��߳�
				Thread t=new Thread(s);
				t.start();
			}
		}
		}
		//��̹�˲����������
		this.direct=(int)(Math.random()*4);
		//�жϵ���̹���Ƿ�����
		if(this.isLive==false){
			//����ط�̹�����������������߳�
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
	//�ӵ��Ƿ���ʧ
	boolean isLive=true; 
	public Shot(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run(){
		
		while(true){
			//50����
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
	
		//�ӵ���ʱ����
		
		//�ж��ӵ��Ƿ�������Ե
		
		if(x<0||x>400||y<0||y>300){
			isLive=false;
			break;
		}
		}
	}
	
}

//ը����
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

//��¼����
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
//��¼�࣬ͬʱҲ���Ա�����ҵ�����
class Recorder
{
 
	//��¼û���ж��ٵ���
	private static int enNum=20; 
	//��¼�ҵ�����
		private static int myLife=3;
	//��¼�ܹ����������
		private static int allEnemy=0;
		private static FileWriter fw=null;
		private static BufferedWriter bw=null;
		private static FileReader fr=null;
		private static BufferedReader br=null;
		private Vector<EnemyTank> ets=new Vector<EnemyTank>();
		//���ļ��лָ���¼��
		private Vector<Node> Nodes=new Vector<Node>();
		
     //��ȡ�����˳�������
		public Vector<Node> getNodeAndEnnums()
		{
			try {
				fr=new FileReader("E:\\JAVAprogram\\MyTankGame4\\123.txt");
				br=new BufferedReader(fr);
                String n=" ";
                //�ȶ�ȡ��һ��
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
		//�����˳�
		public void keepRecondAndEnemyTank()
		{
			try {
				fw=new FileWriter("E:\\JAVAprogram\\MyTankGame4\\123.txt");
				bw=new BufferedWriter(fw);
				bw.write(allEnemy+"\r\n");
				//���浱ǰ���ŵ�̹��λ�úͷ���
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
		//��ȡ�ʼ���Ϣ
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
    //���ܹ�����ĵ���̹������¼���ʼ���
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
	//�������
	public static void addEnNum()
	{
		allEnemy++;
	}
	
	
}