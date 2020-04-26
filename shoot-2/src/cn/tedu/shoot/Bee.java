package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;


//С�۷���
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images;
	static{
		images=new BufferedImage[5];
		images[0]=readImage("bee0.png");
		for(int i=1;i<images.length;i++){
			images[i]=readImage("bom"+i+".png");
		}
	}
	// ����������
	int xStep;// x��(����)�ƶ��ٶ�
	int yStep;// y��(����)�ƶ��ٶ�
	int awardType;// 0�Ǽ���,1�Ǽӻ���

	// ���������췽��
	public Bee() {
		super(60, 51);
		xStep = 3;
		yStep = 3;
		Random ran = new Random();
		awardType = ran.nextInt(2);
	}

	// ����������
	public void show() {
		super.show();
		System.out.println("x�ٶ�:" + xStep + ",y�ٶ�:" + yStep);
		System.out.println("��������:" + awardType);
	}
	public void step(){
		y+=yStep;
		x+=xStep;
		//�Ӹ���,��������ѭ��,����д���ε�
		if(x<=0||x>=World.WIDTH-width){
			xStep*=-1;
		}
		
	}
	int index=1;
	public BufferedImage getImage(){//���ʹ�÷���
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
		return awardType;//ǰ����Ѿ�����������,
	}

}
