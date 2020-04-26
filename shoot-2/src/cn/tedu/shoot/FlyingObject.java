package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
/** 父类飞行物*/
public abstract class FlyingObject {
	//定义三种状态的常量
	public static final int LIFE=0;
	public static final int DEAD=1;
	public static final int REMOVE=2;//移除--2
	//表示当前对象的状态的属性
	protected int state=LIFE;////如果这个地方加static就会全部消失,所有的对象都变成一个状态
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;

	// 小敌机\大敌机\奖励机的构造
	public FlyingObject(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		// 敌机的x轴是随机的 和y轴是固定的
		y = -height;//因为小敌机/大敌机/奖励机的初始位置一样,那么直接在父类中写就行
		Random ran = new Random();
		x = ran.nextInt(400 - width);
	}

	// 天空,子弹,英雄机的构造方法
	public FlyingObject(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;//天空,子弹,英雄机的位置不确定,所以还是需要到子类中自己确定
		this.y = y;
	}

	public void show() {
		System.out.println("宽:" + width + ",高:" + height);
		System.out.println("x:" + x + ",y:" + y);
	}
//	使用类接受图片,根据文件名,将这个图片文件转换成java中的图片类型对象(将文件转换为对象)
	public static BufferedImage readImage(String fileName){
			//ImageIO,类名调取read方法,
			BufferedImage img;
			try {
				img = ImageIO.read(FlyingObject.class.getResource(fileName));
				return img;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}//返回读取到的图片		
	}
	
	public abstract void step();//速度抽象方法
	//编写一个抽象方法
	//获得当前子类对象要显示图片
	public abstract BufferedImage getImage();
	//辅助方法
	//判断是否活着(状态)
	public boolean isLife(){
		return state==LIFE;
	}
	//判断是否死
	public boolean isDead(){
		return state==DEAD;
	}
	//判断是否移除
	public boolean isRemove(){
		return state==REMOVE;
	}
	//将图片画到窗口
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);//对象,坐标
	}
	//修改状态为死的方法
	public void goDead(){
		state=DEAD;
	}
	//判断敌机出界(子类会继承)
	public boolean isOutOfBounds(){
		return y>=World.HEIGHT;
	}

	//碰撞
	public boolean isHit(FlyingObject other){//在父类中同时使用两个子类,其中一个要变成参数
		//碰撞是两个飞行物的判断,this子弹英雄机,other敌机区别两个对象
		//计算碰撞域x1,x2,y1,y2,
		int x1=other.x-this.width;
		int y1=other.y-this.height;
		int x2=other.x+other.width;
		int y2=other.y+other.height;
		//返回this的x和y坐标是否在碰撞区
		return x>x1&&x<x2&&y>y1&&y<y2;//飞机是移动的
	}
	
	
}
