package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//子弹类
public class Bullet extends FlyingObject {
	private static BufferedImage image;
	static{
		//将子弹图片读取到image中
		image=readImage("bullet.png");
	}
	//子弹属性
	int step;
	public Bullet(int x, int y) {
		super(8, 20, x, y);//子弹的位置不确定,谁来实例化子弹,谁来给子弹位置
		step =2;
	}

	//子弹方法
	public void show(){
		super.show();
		System.out.println("速度:"+step);
	}
	public void step(){
		y-=step;
	}
	public BufferedImage getImage(){
		BufferedImage img=null;
		if(isLife()){
			img=image;
		}else{
			state=REMOVE;//子弹死了直接移除
		}
		return img;
	}
	//判断子弹出界(节省内存)
	public boolean isOutOfBounds(){
		return y<=-20;
	}
}
