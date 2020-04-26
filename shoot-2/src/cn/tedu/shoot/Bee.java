package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;


//小蜜蜂类
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images;
	static{
		images=new BufferedImage[5];
		images[0]=readImage("bee0.png");
		for(int i=1;i<images.length;i++){
			images[i]=readImage("bom"+i+".png");
		}
	}
	// 奖励机属性
	int xStep;// x轴(横向)移动速度
	int yStep;// y轴(纵向)移动速度
	int awardType;// 0是加命,1是加火力

	// 奖励机构造方法
	public Bee() {
		super(60, 51);
		xStep = 3;
		yStep = 3;
		Random ran = new Random();
		awardType = ran.nextInt(2);
	}

	// 奖励机方法
	public void show() {
		super.show();
		System.out.println("x速度:" + xStep + ",y速度:" + yStep);
		System.out.println("奖励类型:" + awardType);
	}
	public void step(){
		y+=yStep;
		x+=xStep;
		//加负号,方法不是循环,所以写单次的
		if(x<=0||x>=World.WIDTH-width){
			xStep*=-1;
		}
		
	}
	int index=1;
	public BufferedImage getImage(){//多次使用方法
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
	public int getAward() {
		return awardType;//前面的已经声明过奖励,
	}

}
