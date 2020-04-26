package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//英雄机类
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	static{
		images=new BufferedImage[2];
		images[0]=readImage("hero0.png");
		images[1]=readImage("hero1.png");
	}
	// 没有速度,跟随鼠标移动变换位置
	int life;// 英雄机的生命值
	int fire;// 英雄机的火力值

	public Hero() {
		super(97, 139, 153, 410);//x=(400-97)/2=307/2.只看初始值,跟后期鼠标位置无关
		life = 3;
		fire = 1;//可以不写
	}

	// 英雄机方法
	public void show() {
		super.show();
		System.out.println("生命值:" + life);
		System.out.println("火力值:" + fire);
	}
	public void step(){
		//不需要移动速度,空实现即可
	}
	int index=0;
	public BufferedImage getImage(){
//		BufferedImage img=null;
//		img=images[index%2];
//		index++;
		return images[index++%images.length];//轮流获得数组中的两张图片,
	}
	
	//开炮方法(或取子弹,fire有关)
	public Bullet[] shoot(){
		Bullet[] bls=null;
		//Bullet bl=new Bullet(x, y);
		int w=this.width/4;
		if(fire>0){
			//开双管炮
			//子弹的位置取决英雄
			bls=new Bullet[2];
			bls[0]=new Bullet(this.x+w-4, this.y-20);
			bls[1]=new Bullet(this.x+3*w-4,this.y-20);
			//减火力值
			fire--;
		}else{
			//开单管炮
			bls=new Bullet[1];
			//实例化一个子弹到数组
			bls[0]=new Bullet(this.x+2*w-4,this.y-20);
		}
		return  bls;
	}
	public void moveTo(int x,int y){
		//这里定义的x,y是鼠标的位置
		this.x=x-width/2;
		this.y=y-height/2;
	}
	//增加生命值的方法,
	public void addLife(){
		life++;
	}
	//增加火力值的方法
	public void addFire(){
		fire+=15;//与前面的fire--叠加就是表示提供限量的火力值
	}
	//编写减生命值的方法
	public void subLife(){
		life--;
	}
	public void clearFire(){
		fire=0;
	}
	//返回生命值
	public int getLife(){//6个子类属性加私有,然后用get方法获取
		return life;
	}
	//返回火力值
	public int getFire(){
		return fire;
	} 
}
