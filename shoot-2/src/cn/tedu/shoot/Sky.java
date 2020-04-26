package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

//�����
public class Sky extends FlyingObject {
	private static BufferedImage image;
	static{
		image=readImage("background0.png");
	}
	// ����
	int y1;// �ڶ���ͼƬ��y��λ��
	int step;

	public Sky() {
		super(400,700,0,0);
		y1 = -700;
		step =1;
	}

	// ����෽��
	public void show() {
		super.show();
		System.out.println("y1:" + y1);
		System.out.println("�ٶ�:" + step);
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
	@Override//ע��,����ɾ,��ݼ�alt+/+p
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}
