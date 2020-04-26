package cn.tedu.shoot;

import java.awt.image.BufferedImage;

//С�л���,�Ƿ�����,�е÷�����
public class Airplane extends FlyingObject implements Score {
	//����һ�����鱣��С�л���Ҫ�õ���ͼƬ
	private static BufferedImage[] images;
	//��̬�鸺����Ҫ�õ���ͼƬ���ص�������
	//input=null,�쳣�б�ʾͼƬzhaobudao
	static{
		images=new BufferedImage[5];//
		images[0]=readImage("airplane0.png");//�����ļ���,������BufferedImage����
		//ע��i��ֵ��1��ʼ
		for(int i=1;i<images.length;i++){
			images[i]=readImage("bom"+i+".png");
		}
	}
	
	// С�л�����
	int step;// �ٶ�
	// С�л��Ĺ��췽��

	public Airplane() {//����������ԭ����,Ĭ�ϵ��ø����޲ι���,��������������вι���,û������
		super(48, 50);// ���췽����ֱ�Ӹ�ֵ
		this.step = 4;
		// С�л��Ŀ�߲���Ҫ��mian�����и�ֵ,
		// ����ֱ��д�޲�,����Ҫ��ֵ
	}

	// С�л����������
	public void show() {
		super.show();
		System.out.println("�ٶ�:" + step);
	}
	
	//��д�����г��󷽷�
	public void step(){
		y+=step;
	}
	int index=1;//������������
	//��õ�ǰ״̬ͼƬ
	public BufferedImage getImage(){
		BufferedImage img=null;
		if(isLife()){
			img=images[0];
		}else if(isDead()){
			img=images[index];
			index++;//׼������ͼ
			if(index>4){
				state=REMOVE;//ͼƬΪ��,��Ϊ�Ƴ�
			}
		}
		return img;
	}

	@Override
	public int getScore() {
		return 1;
	}
	
}
