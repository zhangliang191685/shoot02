package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//�ӵ���
public class Bullet extends FlyingObject {
	private static BufferedImage image;
	static{
		//���ӵ�ͼƬ��ȡ��image��
		image=readImage("bullet.png");
	}
	//�ӵ�����
	int step;
	public Bullet(int x, int y) {
		super(8, 20, x, y);//�ӵ���λ�ò�ȷ��,˭��ʵ�����ӵ�,˭�����ӵ�λ��
		step =2;
	}

	//�ӵ�����
	public void show(){
		super.show();
		System.out.println("�ٶ�:"+step);
	}
	public void step(){
		y-=step;
	}
	public BufferedImage getImage(){
		BufferedImage img=null;
		if(isLife()){
			img=image;
		}else{
			state=REMOVE;//�ӵ�����ֱ���Ƴ�
		}
		return img;
	}
	//�ж��ӵ�����(��ʡ�ڴ�)
	public boolean isOutOfBounds(){
		return y<=-20;
	}
}
