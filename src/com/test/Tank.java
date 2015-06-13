/**
 * ̹�˴�ս2.0
 * ʵ��̹�˵��ƶ�
 * ʵ��̹�˷����ӵ�i,�ӵ����������������Է�5�ɣ�
 * ���ӵ��򵽵з�̹��ʱ���ֱ�ըЧ��
 * ��ֹ����̹���ص��˶�
 * ���Էֹ�
 * ����Ϸ����ʱ������ͣ�ͼ���
 * (���û������ͣʱ���ӵ��ٶȺ�̹���ٶ�Ϊ0������̹�˵ķ���Ҫ�仯)
 * ���Լ�¼��ҵĳɼ�
 * 1.1�˳�ʱ���浱ǰ�ɼ��˳�
 * 1.2���浱ǰ��Ϸ״̬�˳�,���ָ�
 * Java���������ļ�
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
	//����һ����ʼ��jpanel
	MyStartPanel msp=null;
	
	//��������Ҫ�Ĳ˵�
	JMenuBar jmb=null;
	JMenu jm1=null;
	//��ʼ��Ϸ
	JMenuItem jmi1=null;
	//�˳�ϵͳ
	JMenuItem jmi2=null;
	//�����˳�
	JMenuItem jmi3=null;
	//������һ����Ϸ��
	JMenuItem jmi4=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Tank t=new Tank();
	}

	public Tank(){
		
		
		//����һ���˵����˵�ѡ��
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ(G)");
		//���ÿ�ݷ�ʽ
		jm1.setMnemonic('G');
		jmi1=new JMenuItem("��ʼ����Ϸ(N)");
		jmi2=new JMenuItem("�˳���Ϸ(E)");
		jmi3=new JMenuItem("�����˳���Ϸ(C)");
		jmi4=new JMenuItem("������һ����Ϸ(T)");
		//ע�����
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
		//���û���ͬ���������ͬ����
		if(e.getActionCommand().equals("newgame"))
		{
			mp=new Mypanel("newGame");
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//��ɾ���ɵģ�������µ�
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ��ˢ��JFrame
			this.setVisible(true);
		}else if(e.getActionCommand().equals("exit"))  //�˳���Ϸ
		{
			Recorder.keepReconding();
			System.exit(0);
		}else if(e.getActionCommand().equals("saveExit"))//�����˳�
		{
			Recorder rd=new Recorder();
			rd.setEts(mp.ets);
			rd.keepRecondAndEnemyTank();
			System.exit(0);
		}else if(e.getActionCommand().equals("conGame"))//������һ����Ϸ
		{
			mp=new Mypanel("con");
			//����mp�߳�
		
			Thread t=new Thread(mp);
			t.start();
			//��ɾ���ɵģ�������µ�
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ��ˢ��JFrame
			this.setVisible(true);
		}
	}
}

//����һ����ʾ��Ϣ��
class MyStartPanel extends JPanel implements Runnable
{
	//���ؿ���ʲôʱ��û�����
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if(times%2==0)
		{
		g.setColor(Color.YELLOW);
		//������Ϣ����ʾ
	  Font MyFont=new Font("����κ��",Font.BOLD,30);
	  g.setFont(MyFont);
	  g.drawString("state�� 1",150, 150);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//����
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
	 
//��������ͼƬ,����ͼƬ�������һ��ը��
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//����һ���ҵ�̹��
	Hero hero=null;
	
	//����һ������̹���� 
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	Vector<Node> nodes=new Vector<Node>();
	//����һ��ը����
	Vector<Bomb> bombs=new Vector<Bomb>();
	int ensize=4;
	public Mypanel(String flag){
		//�ָ��������Ϣ
		Recorder.getReconding();
		//��ʼ���ҷ�̹��
		hero=new Hero(150,150);
		//��ʼ������̹��
		if(flag.equals("newGame"))
		{
		for(int i=0;i<ensize;i++){
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(1);
			et.setDirect(2);
			//��Mypanel�ϵĵ���̹�����������ĵ���
			et.setEts(ets);
			//�����з�̹��
			Thread t=new Thread(et);
			t.start();
			//��ӵ���̹��
			Shot s=new Shot(et.x+10,et.y+33,2);
			et.ss.add(s);
			//���������ӵ��߳�
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
				//��Mypanel�ϵĵ���̹�����������ĵ���
				et.setEts(ets);
				//�����з�̹��
				Thread t=new Thread(et);
				t.start();
				//��ӵ���̹��
				Shot s=new Shot(et.x+10,et.y+33,2);
				et.ss.add(s);
				//���������ӵ��߳�
				Thread t1=new Thread(s);
				t1.start();
				ets.add(et);
			}
			
		}
		//��ʼ������ͼƬ
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
		//������ʾ��Ϣ̹��
		
				this.drawTank(80, 330, g, 0, 1);
				g.setColor(Color.BLACK);
				g.drawString(Recorder.getEnNum()+"", 110, 350);
				this.drawTank(135, 330, g, 0, 2);
				g.setColor(Color.BLACK);
				g.drawString(Recorder.getMyLife()+"", 170, 350);
				
			//��������ܳɼ�
				g.setColor(Color.black);
				Font f=new Font("����",Font.BOLD,20);
				g.setFont(f);
				g.drawString("�����ܳɼ�", 420, 30);
				
				this.drawTank(420, 60, g, 0, 1);
				
				g.setColor(Color.black);
				g.drawString(Recorder.getAllEnemy()+"", 460, 80);
				
	}
	//��дpaint
	public void paint(Graphics g){
		//���ø�����ɳ�ʼ�����þ䲻����
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//������ʾ��Ϣ
		this.tipinformation(g);
		//�����ҷ�̹��
		if(hero.isLive){
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 2);
		}
		//�����ҷ�̹�˵��ӵ�
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
		
		//����ը��
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
			//��ը��������ֵ��С
			b.lifeDown();
			//�������Ϊ0�������bombs������ɾ��
			if(b.life==0){
			   bombs.remove(b);
			}
		}
		
		
		//��������̹�ˣ��͵��˵��ӵ�
		for(int i=0;i<ets.size();i++){
			//ȡ�����˵�̹��
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
			this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 1);
		}
			//�������˵��ӵ�
			//ȡ���ӵ�
			for(int j=0;j<et.ss.size();j++){
				Shot enemyshot=et.ss.get(j);
				if(enemyshot.isLive){
					g.draw3DRect(enemyshot.x, enemyshot.y, 1, 1,false);	
				}else
				{
					//��ѵ���̹���ӵ���Vector��ɾ��
					et.ss.remove(enemyshot);
					
				}
			}
		}
	}
	
	//�ж��ҵ�̹���Ƿ���е���̹��
	public void hitEnemyTank(){
		//�ж��Ƿ񱻻���
		
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myShot=hero.ss.get(i); 
			//�ж��ӵ��Ƿ����
			if(myShot.isLive){
				//ȡ������̹�ˣ������ж�
				for(int j=0;j<ets.size();j++)
				{
					//ȡ��̹��
					EnemyTank et=ets.get(j);
					if(et.isLive){
					if(this.hitTank(myShot, et))
					{
						//���ٵ���̹������
						Recorder.reduce();
						//�����ҵĳɼ�
						Recorder.addEnNum();
					}
					}
				}
			}
			
		}
	}
	
	//�ж��ҵ�̹���Ƿ񱻻��С�
	public void hitMe(){
		//ȡ��ÿһ��̹��
		for(int i=0;i<ets.size();i++){
			EnemyTank et=ets.get(i);
			//ȡ��ÿһ���ӵ�
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
	//����һ���������ж��Ƿ���˵�̹�˱�����(�ӵ��Ƿ����̹��)
	public boolean hitTank(Shot s,Tank1 et){
		boolean a=false;
		//�жϸ�̹�˵ķ���
		switch(et.direct){
		case 0:
		case 2:
			if(s.x>et.x && s.x<et.x+20 && s.y>et.y && s.y<et.y+30){
				//�л�������
				//�ӵ�����
				s.isLive=false;
				//����̹������
				et.isLive=false;
				a=true;
				//����һ��ը������Vector��
				Bomb b=new Bomb(et.x,et.y);
				//����Vector
				bombs.add(b);
				//break;
			}
			break;
			
		case 1:
		case 3:
			if(s.x>et.x && s.x<et.x+30 && s.y>et.y && s.y<et.y+20){
				//�л�������
				s.isLive=false;
				//����̹������
				et.isLive=false;
				a=true;
				//����һ��ը������Vector��
				Bomb b=new Bomb(et.x,et.y);
				//����Vector
				bombs.add(b);
				//break;
			}
		
		}
		return a;
	}
	//�����ҵ�̹�˵ĺ���
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
			//������ߵľ���
		     g.fill3DRect(x, y, 5, 30, false);
			//�����ұߵľ���
		     g.fill3DRect(x+15, y, 5, 30,false);
			//�����м�ľ���
		     g.fill3DRect(x+5, y+5, 10, 20, false);
			//�����м��Բ
		     g.fillOval(x+4, y+10,10,10);
			//�����м��ֱ��
		     g.drawLine(x+9, y+15, x+9, y-4);
		     break;
		case 1:
		    //��������
		     g.fill3DRect(x, y, 30, 5, false);
		     g.fill3DRect(x, y+15, 30, 5, false);
		     g.fill3DRect(x+5, y+5, 20, 10, false);
		     g.fillOval(x+10, y+4, 10, 10);
		     g.drawLine(x+15, y+9, x+30, y+9);
		     break;
		case 2:
			//��������
			//������ߵľ���
		     g.fill3DRect(x, y, 5, 30, false);
			//�����ұߵľ���
		     g.fill3DRect(x+15, y, 5, 30,false);
			//�����м�ľ���
		     g.fill3DRect(x+5, y+5, 10, 20, false);
			//�����м��Բ
		     g.fillOval(x+4, y+10,10,10);
			//�����м��ֱ��
		     g.drawLine(x+9, y+15, x+9, y+31);
		     break;
		case 3:
			//��������
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
		
		//w��  d��   s��   a��
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
			//�Ƿ���е���̹��
			this.hitEnemyTank();
			//�Ƿ�����ҵ�̹��
			this.hitMe();
			
			
		this.repaint();
		}
		
	}
	
	
}


