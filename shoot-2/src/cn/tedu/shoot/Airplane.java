package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//小敌机类,是飞行物,有得分能力
public class Airplane extends FlyingObject implements Score {
	//声明一个数组保存小敌机需要用到的图片
	private static BufferedImage[] images;
	//静态块负责将需要用到的图片加载到数组中
	//input=null,异常中表示图片zhaobudao
	static{
		images=new BufferedImage[5];//
		images[0]=readImage("airplane0.png");//输入文件名,返回是BufferedImage类型
		//注意i的值从1开始
		for(int i=1;i<images.length;i++){
			images[i]=readImage("bom"+i+".png");
		}
	}
	
	// 小敌机属性
	int step;// 速度
	// 小敌机的构造方法

	public Airplane() {//这个不报错的原因是,默认掉用父类无参构造,但是这里调用了有参构造,没有问题
		super(48, 50);// 构造方法中直接赋值
		this.step = 4;
		// 小敌机的宽高不需要在mian方法中赋值,
		// 可以直接写无参,不需要传值
	}

	// 小敌机的输出方法
	public void show() {
		super.show();
		System.out.println("速度:" + step);
	}
	
	//重写父类中抽象方法
	public void step(){
		y+=step;
	}
	int index=1;//死亡次数索引
	//获得当前状态图片
	public BufferedImage getImage(){
		BufferedImage img=null;
		if(isLife()){
			img=images[0];
		}else if(isDead()){
			img=images[index];
			index++;//准备下张图
			if(index>4){
				state=REMOVE;//图片为空,即为移除
			}
		}
		return img;
	}

	@Override
	public int getScore() {
		return 1;
	}
	
}
