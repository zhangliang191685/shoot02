package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/** ��л�*/
public class BigAirplane extends FlyingObject implements Score{
	private static BufferedImage[] images;
	static{
		images=new BufferedImage[5];
		images[0]=readImage("bigairplane0.png");
		for(int i=1;i<images.length;i++){
			images[i]=readImage("bom"+i+".png");
		}
	}
	// ��л�����
	int step;
	public BigAirplane() {
		super(66, 89);// ��ͼƬ����
		step = 2;
	}

	// ��л�����
	public void show() {
		super.show();
		System.out.println("�ٶ�:" + step);
	}
	public void step(){
		y+=step;
	}
	int index=1;
	public BufferedImage getImage(){//�������ǵ���,���Է����в�����ѭ��,�������ִ��Ҳ����Ϊѭ��
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
