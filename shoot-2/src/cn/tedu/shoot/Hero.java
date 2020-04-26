package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//Ӣ�ۻ���
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	static{
		images=new BufferedImage[2];
		images[0]=readImage("hero0.png");
		images[1]=readImage("hero1.png");
	}
	// û���ٶ�,��������ƶ��任λ��
	int life;// Ӣ�ۻ�������ֵ
	int fire;// Ӣ�ۻ��Ļ���ֵ

	public Hero() {
		super(97, 139, 153, 410);//x=(400-97)/2=307/2.ֻ����ʼֵ,���������λ���޹�
		life = 3;
		fire = 1;//���Բ�д
	}

	// Ӣ�ۻ�����
	public void show() {
		super.show();
		System.out.println("����ֵ:" + life);
		System.out.println("����ֵ:" + fire);
	}
	public void step(){
		//����Ҫ�ƶ��ٶ�,��ʵ�ּ���
	}
	int index=0;
	public BufferedImage getImage(){
//		BufferedImage img=null;
//		img=images[index%2];
//		index++;
		return images[index++%images.length];//������������е�����ͼƬ,
	}
	
	//���ڷ���(��ȡ�ӵ�,fire�й�)
	public Bullet[] shoot(){
		Bullet[] bls=null;
		//Bullet bl=new Bullet(x, y);
		int w=this.width/4;
		if(fire>0){
			//��˫����
			//�ӵ���λ��ȡ��Ӣ��
			bls=new Bullet[2];
			bls[0]=new Bullet(this.x+w-4, this.y-20);
			bls[1]=new Bullet(this.x+3*w-4,this.y-20);
			//������ֵ
			fire--;
		}else{
			//��������
			bls=new Bullet[1];
			//ʵ����һ���ӵ�������
			bls[0]=new Bullet(this.x+2*w-4,this.y-20);
		}
		return  bls;
	}
	public void moveTo(int x,int y){
		//���ﶨ���x,y������λ��
		this.x=x-width/2;
		this.y=y-height/2;
	}
	//��������ֵ�ķ���,
	public void addLife(){
		life++;
	}
	//���ӻ���ֵ�ķ���
	public void addFire(){
		fire+=15;//��ǰ���fire--���Ӿ��Ǳ�ʾ�ṩ�����Ļ���ֵ
	}
	//��д������ֵ�ķ���
	public void subLife(){
		life--;
	}
	public void clearFire(){
		fire=0;
	}
	//��������ֵ
	public int getLife(){//6���������Լ�˽��,Ȼ����get������ȡ
		return life;
	}
	//���ػ���ֵ
	public int getFire(){
		return fire;
	} 
}
