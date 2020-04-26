package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;//frame����//���
import javax.swing.JPanel;//java panel���//��Ƭ

public class World extends JPanel {
	// ��Ϸ״̬��ͼƬ
	public static BufferedImage startIng;
	public static BufferedImage pauseIng;
	public static BufferedImage overIng;
	static {
		startIng = FlyingObject.readImage("start.png");
		pauseIng = FlyingObject.readImage("pause.png");
		overIng = FlyingObject.readImage("gameover.png");
	}

	// ������Ϸ��Ϸ�����ߵĳ���
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	// ��Ϸ����
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	// ��Ϸ�з���
	private int score = 0;
	// �л�����,����С�л�,��л�,������
	FlyingObject[] enemy = {};
	Sky sky = new Sky();
	Hero hero = new Hero();
	Bullet[] bls = {};

	// �ô���д����,��Ҫ��������,���дenemy[0],����Ƕ���,���Ǳ���
	// ��ǰ��Ϸ״̬
	private int state = START;

	public void start() {
		// ��д������,ʹ�������ڲ���̳������������
		MouseAdapter l = new MouseAdapter() {
			// ����ƶ��Ĳ���
			@Override
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {// ����״̬,������
					int x = e.getX();
					int y = e.getY();
					// ����Ӣ�ۻ��ƶ������λ��
					hero.moveTo(x, y);
				}
			}

			// ������Ĳ���
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// ����ǿ�ʼ״̬,�л�������
				// ����ǽ���״̬,�л�����ʼ
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					state = START;
					// �峡����
					score = 0;
					sky = new Sky();
					hero = new Hero();
					enemy = new FlyingObject[0];
					bls = new Bullet[0];
					break;
				}
			}

			// ����Ƴ�����
			@Override
			public void mouseExited(MouseEvent arg0) {
				// �������״̬�л�������,
				if (state == RUNNING) {
					state = PAUSE;// ��ǰʲô״̬,����ʲô����,�л���ʲô״̬

				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// �������ͣ״̬,�л�������
				if (state == PAUSE) {
					state = RUNNING;
				}
			}
		};
		// ������¼�ע�ᵽ������
		this.addMouseListener(l);// �ƶ�����
		this.addMouseMotionListener(l);// ��������

		// ��ʱ������ʹ��
		Timer timer = new Timer();
		int interval = 20;// ���м��,(����30-50)
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) {
					System.out.print("�л�����:" + enemy.length);
					System.out.println("�ӵ�:" + bls.length);
					moveAction();
					enemyEnterAction();// ��д��ʱ�����if�ж϶����Կ���Ƶ��
					bulletEnterAction();
					outOfBoundsAction();
					hitAction();
					heroHitAction();
					gameOverAction();
				}
				// �������ػ���������
				repaint();// ��Զ�����,������Ϸʲô״̬,�����ػ�
			}
		};
		timer.schedule(task, interval, interval);
	}

	// �жϽ����ķ���
	public void gameOverAction() {
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
		}
	}

	// �ж�Ӣ�ۻ���ײ�л��ķ���
	public void heroHitAction() {
		for (int i = 0; i < enemy.length; i++) {
			if (enemy[i].isHit(hero) && enemy[i].isLife()) {// �������Ҫ,�л��ǻ���״̬
				enemy[i].goDead();
				hero.subLife();
				hero.clearFire();
			}
		}
	}

	// �ӵ���ײ�л�,
	public void hitAction() {// �Ȱ��ӵ�����һ��,Ȼ��ÿ���ӵ��͵л�����һ��
		for (int i = 0; i < bls.length; i++) {
			for (int j = 0; j < enemy.length; j++) {
				if (bls[i].isHit(enemy[j]) && enemy[j].isLife()) {// ��һ�����ӵ�ײ��ըͼ,��������޳�
					bls[i].goDead();
					enemy[j].goDead();
					if (enemy[j] instanceof Score) {
						Score s = (Score) enemy[j];
						// ǿת��÷�������,�������ܷ�������
						score += s.getScore();
					}
					// ������н�����
					if (enemy[j] instanceof Award) {
						// ��ý���ֵ
						Award aw = (Award) enemy[j];
						int num = aw.getAward();
						switch (num) {
						case Award.LIFE:
							hero.addLife();
						case Award.FIRE:
							hero.addFire();
						}
					}
					break;
				}
			}
		}
	}

	// �Ƴ�������Ƴ��ĵл����ӵ�
	public void outOfBoundsAction() {
		int index = 0;// ����ʱ���±�,������������������鳤��
		// ����һ���͵л�����һ��������,���ڴ��û���Ƴ��ĵл�
		FlyingObject[] fs = new FlyingObject[enemy.length];
		for (int i = 0; i < enemy.length; i++) {
			FlyingObject f = enemy[i];
			if (!f.isOutOfBounds() && !f.isRemove()) {// û����û�Ƴ�
				fs[index] = f;
				index++;// Ϊ�¸��л���׼��
			}
		}
		enemy = Arrays.copyOf(fs, index);// ����������Ϊindex�ĳ��Ȳ�����enemy
		index = 0;// ����
		Bullet[] bs = new Bullet[bls.length];
		for (int i = 0; i < bls.length; i++) {
			Bullet b = bls[i];
			if (!b.isOutOfBounds() && !b.isRemove()) {
				bs[index] = b;
				index++;
			}
		}
		bls = Arrays.copyOf(bs, index);
	}

	// �ӵ�����,����ĸ�ֵ,�����һ���Ŀ��Ե�����ֵ,���Ƕ����Ҫʹ�����鸳ֵ����
	int bulletIndex = 1;

	public void bulletEnterAction() {
		if (bulletIndex % 1 == 0) {
			// ����Ӣ�ۻ�����,����
			Bullet[] bs = hero.shoot();
			// ��������,����Ϊbs�ĳ���

			bls = Arrays.copyOf(bls, bls.length + bs.length);
			System.arraycopy(bs, 0, bls, bls.length - bs.length, bs.length);
		}
		bulletIndex++;
	}

	// �л�����
	int enemyIndex = 1;

	public void enemyEnterAction() {
		if (enemyIndex %20 == 0) {// ���ü�ʱ��,����ȡ��������Ƶ��
			FlyingObject fly = nextEnemy();// �������л�
			// ��һ�ܵл�
			enemy = Arrays.copyOf(enemy, enemy.length + 1);// ���鳤�Ȳ��ܸı�,����ǰ����øı�,֮ǰ�����鱻����
			// ����л�
			enemy[enemy.length - 1] = fly;
		}
		enemyIndex++;
	}

	// ��������л�(���÷���,��õ�ʲô)
	public FlyingObject nextEnemy() {// �������ȷ��,���ø�����,
		FlyingObject fly = null;
		Random ran = new Random();
		// Ϊ�˿��Ƶл������ļ���,
		int num = ran.nextInt(100);// 0-99�����
		if (num < 40) {
			fly = new Airplane();
		} else if (num < 80) {
			fly = new BigAirplane();
		} else {
			fly = new Bee();
		}
		return fly;
	}

	// ��Ӣ�ۻ������ж�����ƶ�����
	public void moveAction() {
		// ֻҪaction��β�Ķ�����run,�Ǹ���ϰ��
		sky.step();// ��ն�
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].step();// �л���
		}
		for (int i = 0; i < bls.length; i++) {
			bls[i].step();// �ӵ���
		}

	}

	// ���paint��������дJPanel���е�,����ǩ����ͬ
	public void paint(Graphics g) {
		// �Ȼ���ͼ
		sky.paintObject(g);
		hero.paintObject(g);
		// ���л�
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].paintObject(g);
		}
		// ���ӵ�
		for (int i = 0; i < bls.length; i++) {
			bls[i].paintObject(g);
		}
		// �ڴ������Ϸ�д��
		g.drawString("SCORW:" + score, 10, 15);// x,y
		g.drawString("LIFE:" + hero.getLife(), 10, 35);
		g.drawString("FIRE:" + hero.getFire(), 10, 55);
		// ���ݵ�ǰ״̬���Ʋ�ͬͼƬ
		switch (state) {
		case START:
			g.drawImage(startIng, 0, 0, null);
			break;
		// case RUNNING:
		// break;
		case PAUSE:
			g.drawImage(pauseIng, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(overIng, 0, 0, null);
			break;
		}
	}

	public static void main(String[] args) {
		World w = new World();
		// ����һ�����ڶ���
		JFrame f = new JFrame("�ɻ���ս");// ���
		// ��World�����������ʾ��������
		f.add(w);// ������Ƭ
		// ���ô��ڴ�С
		f.setSize(400, 700);
		// ���þ�����ʾ
		f.setLocationRelativeTo(null);// ��ݼ�.setLR���
		// ���ùرմ���ͬʱ��������;��Щ����رճ��򻹻�����
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ������ʾ����
		f.setVisible(true);// ��ݼ�.setV,�Զ�����paint����

		w.start();

	}

}
