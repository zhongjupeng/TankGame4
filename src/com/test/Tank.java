/**
 * 坦克大战2.0
 * 实现坦克的移动
 * 实现坦克发射子弹i,子弹可以连发（最多可以发5可）
 * 当子弹打到敌方坦克时出现爆炸效果
 * 防止敌人坦克重叠运动
 * 可以分关
 * 在游戏进行时可以暂停和继续
 * (当用户点击暂停时把子弹速度和坦克速度为0，并让坦克的方向不要变化)
 * 可以记录玩家的成绩
 * 1.1退出时保存当前成绩退出
 * 1.2保存当前游戏状态退出,并恢复
 * Java操作声音文件
 */
package com.test;
import java.awt.*;

import javax.imageio.*;
import javax.swing.*;

import java.util.*;
import java.io.*;
import java.awt.event.*;
public class Tank extends JFrame implements ActionListener{

	Mypanel mp=null;
	//定义一个开始的jpanel
	MyStartPanel msp=null;
	
	//作出我需要的菜单
	JMenuBar jmb=null;
	JMenu jm1=null;
	//开始游戏
	JMenuItem jmi1=null;
	//退出系统
	JMenuItem jmi2=null;
	//存盘退出
	JMenuItem jmi3=null;
	//继续上一局游戏，
	JMenuItem jmi4=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Tank t=new Tank();
	}

	public Tank(){
		
		
		//创建一个菜单及菜单选项
		jmb=new JMenuBar();
		jm1=new JMenu("游戏(G)");
		//设置快捷方式
		jm1.setMnemonic('G');
		jmi1=new JMenuItem("开始新游戏(N)");
		jmi2=new JMenuItem("退出游戏(E)");
		jmi3=new JMenuItem("保存退出游戏(C)");
		jmi4=new JMenuItem("继续上一局游戏(T)");
		//注册监听
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("conGame");
		
		
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		jmb.add(jm1);
		msp=new MyStartPanel();
		Thread t=new Thread(msp);
		t.start();
		
		this.setJMenuBar(jmb);
		this.add(msp);
		this.setSize(600,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//对用户不同点击作出不同处理
		if(e.getActionCommand().equals("newgame"))
		{
			mp=new Mypanel("newGame");
			//启动mp线程
			Thread t=new Thread(mp);
			t.start();
			//先删除旧的，再添加新的
			this.remove(msp);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示，刷新JFrame
			this.setVisible(true);
		}else if(e.getActionCommand().equals("exit"))  //退出游戏
		{
			Recorder.keepReconding();
			System.exit(0);
		}else if(e.getActionCommand().equals("saveExit"))//保存退出
		{
			Recorder rd=new Recorder();
			rd.setEts(mp.ets);
			rd.keepRecondAndEnemyTank();
			System.exit(0);
		}else if(e.getActionCommand().equals("conGame"))//继续上一局游戏
		{
			mp=new Mypanel("con");
			//启动mp线程
		
			Thread t=new Thread(mp);
			t.start();
			//先删除旧的，再添加新的
			this.remove(msp);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示，刷新JFrame
			this.setVisible(true);
		}
	}
}

//定义一个提示信息类
class MyStartPanel extends JPanel implements Runnable
{
	//开关控制什么时候该画字体
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if(times%2==0)
		{
		g.setColor(Color.YELLOW);
		//开关信息的提示
	  Font MyFont=new Font("华文魏体",Font.BOLD,30);
	  g.setFont(MyFont);
	  g.drawString("state： 1",150, 150);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//休眠
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			times++;
			
			this.repaint();
		}
	}
}

class Mypanel extends JPanel implements KeyListener, Runnable{
	 
//定义三张图片,三张图片才能组成一个炸弹
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//定义一个我的坦克
	Hero hero=null;
	
	//定义一个敌人坦克组 
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	Vector<Node> nodes=new Vector<Node>();
	//定义一个炸弹组
	Vector<Bomb> bombs=new Vector<Bomb>();
	int ensize=4;
	public Mypanel(String flag){
		//恢复保存的信息
		Recorder.getReconding();
		//初始化我方坦克
		hero=new Hero(150,150);
		//初始化敌人坦克
		if(flag.equals("newGame"))
		{
		for(int i=0;i<ensize;i++){
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(1);
			et.setDirect(2);
			//把Mypanel上的敌人坦克向量交给的敌人
			et.setEts(ets);
			//启动敌方坦克
			Thread t=new Thread(et);
			t.start();
			//添加敌人坦克
			Shot s=new Shot(et.x+10,et.y+33,2);
			et.ss.add(s);
			//启动敌人子弹线程
			Thread t1=new Thread(s);
			t1.start();
			ets.add(et);
		        }
		}else
		{
			nodes=new Recorder().getNodeAndEnnums();
			for(int i=0;i<nodes.size();i++){
				Node node=nodes.get(i);
				
				EnemyTank et=new EnemyTank(node.x,node.y);
				et.setColor(1);
				et.setDirect(node.direct);
				//把Mypanel上的敌人坦克向量交给的敌人
				et.setEts(ets);
				//启动敌方坦克
				Thread t=new Thread(et);
				t.start();
				//添加敌人坦克
				Shot s=new Shot(et.x+10,et.y+33,2);
				et.ss.add(s);
				//启动敌人子弹线程
				Thread t1=new Thread(s);
				t1.start();
				ets.add(et);
			}
			
		}
		//初始化三张图片
		try {
			image1=ImageIO.read(new File("6.gif"));
			image2=ImageIO.read(new File("8.gif"));
			image3=ImageIO.read(new File("10.gif"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/6.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/8.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/10.gif"));

	}
	
	public void tipinformation(Graphics g)
	{
		//画出提示信息坦克
		
				this.drawTank(80, 330, g, 0, 1);
				g.setColor(Color.BLACK);
				g.drawString(Recorder.getEnNum()+"", 110, 350);
				this.drawTank(135, 330, g, 0, 2);
				g.setColor(Color.BLACK);
				g.drawString(Recorder.getMyLife()+"", 170, 350);
				
			//画出玩家总成绩
				g.setColor(Color.black);
				Font f=new Font("宋体",Font.BOLD,20);
				g.setFont(f);
				g.drawString("您的总成绩", 420, 30);
				
				this.drawTank(420, 60, g, 0, 1);
				
				g.setColor(Color.black);
				g.drawString(Recorder.getAllEnemy()+"", 460, 80);
				
	}
	//重写paint
	public void paint(Graphics g){
		//调用父类完成初始化，该句不能少
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//画出提示信息
		this.tipinformation(g);
		//画出我方坦克
		if(hero.isLive){
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 2);
		}
		//画出我方坦克的子弹
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myShot=hero.ss.get(i);
		if(myShot!=null && myShot.isLive==true){
			g.draw3DRect(myShot.x, myShot.y, 1, 1,false);
		}
		if(myShot.isLive==false){
			hero.ss.remove(myShot);
		}
		}
		
		//画出炸弹
		for(int i=0;i<bombs.size();i++){
			System.out.println("fhkb");
			Bomb b=bombs.get(i);
			if(b.life>6){
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			}else if(b.life>3)
			{
				g.drawImage(image2, b.x, b.y, 30, 30, this);

			}else
			{
				g.drawImage(image3, b.x, b.y, 30, 30, this);

			}
			//让炸弹的生命值减小
			b.lifeDown();
			//如果生命为0，则将其从bombs向量中删除
			if(b.life==0){
			   bombs.remove(b);
			}
		}
		
		
		//画出敌人坦克，和敌人的子弹
		for(int i=0;i<ets.size();i++){
			//取出敌人的坦克
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
			this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 1);
		}
			//画出敌人的子弹
			//取出子弹
			for(int j=0;j<et.ss.size();j++){
				Shot enemyshot=et.ss.get(j);
				if(enemyshot.isLive){
					g.draw3DRect(enemyshot.x, enemyshot.y, 1, 1,false);	
				}else
				{
					//则把敌人坦克子弹从Vector中删除
					et.ss.remove(enemyshot);
					
				}
			}
		}
	}
	
	//判断我的坦克是否击中敌人坦克
	public void hitEnemyTank(){
		//判断是否被击中
		
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myShot=hero.ss.get(i); 
			//判断子弹是否活着
			if(myShot.isLive){
				//取出敌人坦克，与其判断
				for(int j=0;j<ets.size();j++)
				{
					//取出坦克
					EnemyTank et=ets.get(j);
					if(et.isLive){
					if(this.hitTank(myShot, et))
					{
						//减少敌人坦克数量
						Recorder.reduce();
						//增加我的成绩
						Recorder.addEnNum();
					}
					}
				}
			}
			
		}
	}
	
	//判断我的坦克是否被击中、
	public void hitMe(){
		//取出每一个坦克
		for(int i=0;i<ets.size();i++){
			EnemyTank et=ets.get(i);
			//取出每一颗子弹
			for(int j=0;j<et.ss.size();j++){
				Shot nemyshot=et.ss.get(j);
				if(hero.isLive){
				if(this.hitTank(nemyshot, hero))
				{
					//////////
					
				}
				}
			}
		}
	}
	//定义一个函数，判断是否敌人的坦克被击中(子弹是否击中坦克)
	public boolean hitTank(Shot s,Tank1 et){
		boolean a=false;
		//判断该坦克的方向
		switch(et.direct){
		case 0:
		case 2:
			if(s.x>et.x && s.x<et.x+20 && s.y>et.y && s.y<et.y+30){
				//敌机被击中
				//子弹死亡
				s.isLive=false;
				//敌人坦克死亡
				et.isLive=false;
				a=true;
				//创建一个炸弹放入Vector中
				Bomb b=new Bomb(et.x,et.y);
				//放入Vector
				bombs.add(b);
				//break;
			}
			break;
			
		case 1:
		case 3:
			if(s.x>et.x && s.x<et.x+30 && s.y>et.y && s.y<et.y+20){
				//敌机被击中
				s.isLive=false;
				//敌人坦克死亡
				et.isLive=false;
				a=true;
				//创建一个炸弹放入Vector中
				Bomb b=new Bomb(et.x,et.y);
				//放入Vector
				bombs.add(b);
				//break;
			}
		
		}
		return a;
	}
	//画出我的坦克的函数
	public void drawTank(int x,int y,Graphics g,int direct,int type){
		switch(type){
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.YELLOW);
		    break;
		case 2:
			g.setColor(Color.RED);
		}
		
		switch(direct){
		case 0:
			//画出左边的矩形
		     g.fill3DRect(x, y, 5, 30, false);
			//画出右边的矩形
		     g.fill3DRect(x+15, y, 5, 30,false);
			//画出中间的矩形
		     g.fill3DRect(x+5, y+5, 10, 20, false);
			//画出中间的圆
		     g.fillOval(x+4, y+10,10,10);
			//画出中间的直线
		     g.drawLine(x+9, y+15, x+9, y-4);
		     break;
		case 1:
		    //方向向右
		     g.fill3DRect(x, y, 30, 5, false);
		     g.fill3DRect(x, y+15, 30, 5, false);
		     g.fill3DRect(x+5, y+5, 20, 10, false);
		     g.fillOval(x+10, y+4, 10, 10);
		     g.drawLine(x+15, y+9, x+30, y+9);
		     break;
		case 2:
			//方向向下
			//画出左边的矩形
		     g.fill3DRect(x, y, 5, 30, false);
			//画出右边的矩形
		     g.fill3DRect(x+15, y, 5, 30,false);
			//画出中间的矩形
		     g.fill3DRect(x+5, y+5, 10, 20, false);
			//画出中间的圆
		     g.fillOval(x+4, y+10,10,10);
			//画出中间的直线
		     g.drawLine(x+9, y+15, x+9, y+31);
		     break;
		case 3:
			//方向向左
		     g.fill3DRect(x, y, 30, 5, false);
		     g.fill3DRect(x, y+15, 30, 5, false);
		     g.fill3DRect(x+5, y+5, 20, 10, false);
		     g.fillOval(x+10, y+4, 10, 10);
		     g.drawLine(x+15, y+9, x, y+9);
		     break;
		}
		
		
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//w上  d右   s下   a左
		if(e.getKeyCode()==KeyEvent.VK_W){
			
			this.hero.setDirect(0);
			this.hero.moveup();
						
		}else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			this.hero.setDirect(1);
			this.hero.moveright();
		}else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			this.hero.setDirect(2);
			this.hero.movedown();
		}else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			this.hero.setDirect(3);
			this.hero.moveleft();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_J){
			if(hero.ss.size()<=4){
				this.hero.shotEnemyTank();
			}
		}
		
		this.repaint();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} 
			//是否击中敌人坦克
			this.hitEnemyTank();
			//是否击中我的坦克
			this.hitMe();
			
			
		this.repaint();
		}
		
	}
	
	
}


