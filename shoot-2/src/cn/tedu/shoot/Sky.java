package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

//天空类
public class Sky extends FlyingObject {
	private static BufferedImage image;
	static{
		image=readImage("background0.png");
	}
	// 属性
	int y1;// 第二张图片的y轴位置
	int step;

	public Sky() {
		super(400,700,0,0);
		y1 = -700;
		step =1;
	}

	// 天空类方法
	public void show() {
		super.show();
		System.out.println("y1:" + y1);
		System.out.println("速度:" + step);
	}
	public void step(){
		y+=step;
		y1+=step;
		if(y>=World.HEIGHT){
			y=-World.HEIGHT;
		}
		if(y1>=World.HEIGHT){
			y1=-World.HEIGHT;
		}
	}
	
	public BufferedImage getImage(){
		return image;
	}
	@Override//注解,可以删,快捷键alt+/+p
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}
