package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/** 大敌机*/
public class BigAirplane extends FlyingObject implements Score{
	private static BufferedImage[] images;
	static{
		images=new BufferedImage[5];
		images[0]=readImage("bigairplane0.png");
		for(int i=1;i<images.length;i++){
			images[i]=readImage("bom"+i+".png");
		}
	}
	// 大敌机属性
	int step;
	public BigAirplane() {
		super(66, 89);// 看图片属性
		step = 2;
	}

	// 大敌机方法
	public void show() {
		super.show();
		System.out.println("速度:" + step);
	}
	public void step(){
		y+=step;
	}
	int index=1;
	public BufferedImage getImage(){//方法都是单次,所以方法中不能做循环,方法多次执行也能作为循环
		BufferedImage img=null;
		if(isLife()){
			img=images[0];
		}else if(isDead()){
			img=images[index];
			index++;
			if(index==5){
				state=REMOVE;
			}
		}
		return img;
	}

	@Override
	public int getScore() {
		return 3;
	}

}
