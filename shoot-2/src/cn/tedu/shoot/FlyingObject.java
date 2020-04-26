package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
/** ���������*/
public abstract class FlyingObject {
	//��������״̬�ĳ���
	public static final int LIFE=0;
	public static final int DEAD=1;
	public static final int REMOVE=2;//�Ƴ�--2
	//��ʾ��ǰ�����״̬������
	protected int state=LIFE;////�������ط���static�ͻ�ȫ����ʧ,���еĶ��󶼱��һ��״̬
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;

	// С�л�\��л�\�������Ĺ���
	public FlyingObject(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		// �л���x��������� ��y���ǹ̶���
		y = -height;//��ΪС�л�/��л�/�������ĳ�ʼλ��һ��,��ôֱ���ڸ�����д����
		Random ran = new Random();
		x = ran.nextInt(400 - width);
	}

	// ���,�ӵ�,Ӣ�ۻ��Ĺ��췽��
	public FlyingObject(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;//���,�ӵ�,Ӣ�ۻ���λ�ò�ȷ��,���Ի�����Ҫ���������Լ�ȷ��
		this.y = y;
	}

	public void show() {
		System.out.println("��:" + width + ",��:" + height);
		System.out.println("x:" + x + ",y:" + y);
	}
//	ʹ�������ͼƬ,�����ļ���,�����ͼƬ�ļ�ת����java�е�ͼƬ���Ͷ���(���ļ�ת��Ϊ����)
	public static BufferedImage readImage(String fileName){
			//ImageIO,������ȡread����,
			BufferedImage img;
			try {
				img = ImageIO.read(FlyingObject.class.getResource(fileName));
				return img;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}//���ض�ȡ����ͼƬ		
	}
	
	public abstract void step();//�ٶȳ��󷽷�
	//��дһ�����󷽷�
	//��õ�ǰ�������Ҫ��ʾͼƬ
	public abstract BufferedImage getImage();
	//��������
	//�ж��Ƿ����(״̬)
	public boolean isLife(){
		return state==LIFE;
	}
	//�ж��Ƿ���
	public boolean isDead(){
		return state==DEAD;
	}
	//�ж��Ƿ��Ƴ�
	public boolean isRemove(){
		return state==REMOVE;
	}
	//��ͼƬ��������
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);//����,����
	}
	//�޸�״̬Ϊ���ķ���
	public void goDead(){
		state=DEAD;
	}
	//�жϵл�����(�����̳�)
	public boolean isOutOfBounds(){
		return y>=World.HEIGHT;
	}

	//��ײ
	public boolean isHit(FlyingObject other){//�ڸ�����ͬʱʹ����������,����һ��Ҫ��ɲ���
		//��ײ��������������ж�,this�ӵ�Ӣ�ۻ�,other�л�������������
		//������ײ��x1,x2,y1,y2,
		int x1=other.x-this.width;
		int y1=other.y-this.height;
		int x2=other.x+other.width;
		int y2=other.y+other.height;
		//����this��x��y�����Ƿ�����ײ��
		return x>x1&&x<x2&&y>y1&&y<y2;//�ɻ����ƶ���
	}
	
	
}
