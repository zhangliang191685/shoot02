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

import javax.swing.JFrame;//frame窗口//相框
import javax.swing.JPanel;//java panel面板//相片

public class World extends JPanel {
	// 游戏状态的图片
	public static BufferedImage startIng;
	public static BufferedImage pauseIng;
	public static BufferedImage overIng;
	static {
		startIng = FlyingObject.readImage("start.png");
		pauseIng = FlyingObject.readImage("pause.png");
		overIng = FlyingObject.readImage("gameover.png");
	}

	// 定义游戏游戏界面宽高的常量
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	// 游戏常量
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	// 游戏中分数
	private int score = 0;
	// 敌机数组,包含小敌机,大敌机,奖励机
	FlyingObject[] enemy = {};
	Sky sky = new Sky();
	Hero hero = new Hero();
	Bullet[] bls = {};

	// 该处是写属性,需要出现类型,如果写enemy[0],这个是对象,不是变量
	// 当前游戏状态
	private int state = START;

	public void start() {
		// 编写鼠标操作,使用匿名内部类继承鼠标适配器类
		MouseAdapter l = new MouseAdapter() {
			// 鼠标移动的操作
			@Override
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {// 运行状态,鼠标才用
					int x = e.getX();
					int y = e.getY();
					// 控制英雄机移动到鼠标位置
					hero.moveTo(x, y);
				}
			}

			// 鼠标点击的操作
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 如果是开始状态,切换到运行
				// 如果是结束状态,切换到开始
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					state = START;
					// 清场操作
					score = 0;
					sky = new Sky();
					hero = new Hero();
					enemy = new FlyingObject[0];
					bls = new Bullet[0];
					break;
				}
			}

			// 鼠标移除操作
			@Override
			public void mouseExited(MouseEvent arg0) {
				// 如果运行状态切换到运行,
				if (state == RUNNING) {
					state = PAUSE;// 当前什么状态,做了什么操作,切换成什么状态

				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// 如果是暂停状态,切换到运行
				if (state == PAUSE) {
					state = RUNNING;
				}
			}
		};
		// 将鼠标事件注册到监听器
		this.addMouseListener(l);// 移动监听
		this.addMouseMotionListener(l);// 滑动监听

		// 计时器配套使用
		Timer timer = new Timer();
		int interval = 20;// 运行间隔,(建议30-50)
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) {
					System.out.print("敌机数量:" + enemy.length);
					System.out.println("子弹:" + bls.length);
					moveAction();
					enemyEnterAction();// 重写计时器或加if判断都可以控制频率
					bulletEnterAction();
					outOfBoundsAction();
					hitAction();
					heroHitAction();
					gameOverAction();
				}
				// 别忘了重绘整个串口
				repaint();// 永远在最后,不管游戏什么状态,都得重画
			}
		};
		timer.schedule(task, interval, interval);
	}

	// 判断结束的方法
	public void gameOverAction() {
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
		}
	}

	// 判断英雄机碰撞敌机的方法
	public void heroHitAction() {
		for (int i = 0; i < enemy.length; i++) {
			if (enemy[i].isHit(hero) && enemy[i].isLife()) {// 这个很重要,敌机是或者状态
				enemy[i].goDead();
				hero.subLife();
				hero.clearFire();
			}
		}
	}

	// 子弹碰撞敌机,
	public void hitAction() {// 先把子弹遍历一遍,然后每颗子弹和敌机遍历一遍
		for (int i = 0; i < bls.length; i++) {
			for (int j = 0; j < enemy.length; j++) {
				if (bls[i].isHit(enemy[j]) && enemy[j].isLife()) {// 有一种是子弹撞爆炸图,这种情况剔除
					bls[i].goDead();
					enemy[j].goDead();
					if (enemy[j] instanceof Score) {
						Score s = (Score) enemy[j];
						// 强转获得分数方法,并加载总分属性中
						score += s.getScore();
					}
					// 如果击中奖励机
					if (enemy[j] instanceof Award) {
						// 获得奖励值
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

	// 移除出界或被移除的敌机和子弹
	public void outOfBoundsAction() {
		int index = 0;// 复制时做下标,遍历完数组后做新数组长度
		// 定义一个和敌机长度一样的数组,用于存放没有移除的敌机
		FlyingObject[] fs = new FlyingObject[enemy.length];
		for (int i = 0; i < enemy.length; i++) {
			FlyingObject f = enemy[i];
			if (!f.isOutOfBounds() && !f.isRemove()) {// 没出界没移除
				fs[index] = f;
				index++;// 为下个敌机做准备
			}
		}
		enemy = Arrays.copyOf(fs, index);// 新数组缩容为index的长度并赋给enemy
		index = 0;// 归零
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

	// 子弹进场,数组的赋值,如果是一个的可以单个赋值,但是多个就要使用数组赋值方法
	int bulletIndex = 1;

	public void bulletEnterAction() {
		if (bulletIndex % 1 == 0) {
			// 调用英雄机方法,接受
			Bullet[] bs = hero.shoot();
			// 数组扩容,长度为bs的长度

			bls = Arrays.copyOf(bls, bls.length + bs.length);
			System.arraycopy(bs, 0, bls, bls.length - bs.length, bs.length);
		}
		bulletIndex++;
	}

	// 敌机进场
	int enemyIndex = 1;

	public void enemyEnterAction() {
		if (enemyIndex %20 == 0) {// 套用计时器,求余取零来限制频率
			FlyingObject fly = nextEnemy();// 获得随机敌机
			// 加一架敌机
			enemy = Arrays.copyOf(enemy, enemy.length + 1);// 数组长度不能改变,这个是把引用改变,之前的数组被回收
			// 放入敌机
			enemy[enemy.length - 1] = fly;
		}
		enemyIndex++;
	}

	// 随机产生敌机(利用方法,想得到什么)
	public FlyingObject nextEnemy() {// 子类对象不确定,利用父类造,
		FlyingObject fly = null;
		Random ran = new Random();
		// 为了控制敌机产生的几率,
		int num = ran.nextInt(100);// 0-99随机数
		if (num < 40) {
			fly = new Airplane();
		} else if (num < 80) {
			fly = new BigAirplane();
		} else {
			fly = new Bee();
		}
		return fly;
	}

	// 除英雄机外所有对象的移动方法
	public void moveAction() {
		// 只要action结尾的都放在run,是个人习惯
		sky.step();// 天空动
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].step();// 敌机动
		}
		for (int i = 0; i < bls.length; i++) {
			bls[i].step();// 子弹动
		}

	}

	// 这个paint方法是重写JPanel类中的,方法签名相同
	public void paint(Graphics g) {
		// 先画底图
		sky.paintObject(g);
		hero.paintObject(g);
		// 画敌机
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].paintObject(g);
		}
		// 画子弹
		for (int i = 0; i < bls.length; i++) {
			bls[i].paintObject(g);
		}
		// 在窗体左上方写字
		g.drawString("SCORW:" + score, 10, 15);// x,y
		g.drawString("LIFE:" + hero.getLife(), 10, 35);
		g.drawString("FIRE:" + hero.getFire(), 10, 55);
		// 根据当前状态绘制不同图片
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
		// 创建一个窗口对象
		JFrame f = new JFrame("飞机大战");// 相框
		// 将World对象的内容显示到窗口中
		f.add(w);// 相框加相片
		// 设置窗口大小
		f.setSize(400, 700);
		// 设置居中显示
		f.setLocationRelativeTo(null);// 快捷键.setLR输出
		// 设置关闭窗体同时结束程序;有些窗体关闭程序还会运行
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置显示窗体
		f.setVisible(true);// 快捷键.setV,自动调用paint方法

		w.start();

	}

}
