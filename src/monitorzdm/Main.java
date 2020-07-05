package monitorzdm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	static MouseListener mousel[] = new MouseListener[24];
	static int lastindex=0;
	public static void openURL(String url) {
		try {
			String command = "cmd /c start  ";
			Runtime.getRuntime().exec(command + url);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static ImageIcon change(ImageIcon image) {

		int width = 130;// (int) (image.getIconWidth()*i);
		int height = 130;// (int) (image.getIconHeight()*i);
		Image img = image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon image2 = new ImageIcon(img);

		return image2;
	}

	public static void setpic(JLabel l[], int index) {
		ResultSet rs = null;
		
		try {
			Connection c = DriverManager.getConnection(JDBCConf.JDBCURL, JDBCConf.USERNAME,JDBCConf.PASSWORD);
			String sql = "select * from top3 ORDER BY id DESC LIMIT 24";
			PreparedStatement ps = c.prepareStatement(sql);

			ps.execute();
			rs = ps.executeQuery();
			int id[]=new int[24];
			String pic[]=new String[24];
			String url[]=new String[24];
			while (rs.next()) {
				int row = rs.getRow()-1;
				id[row]=rs.getInt("id");
				pic[row]=rs.getString("pic_url");
				url[row]=rs.getString("url");
				lastindex=rs.getInt("id");
				Thread t = new Thread() {
					public void run() {
						try {
						System.out.println(id[row] + "\t" + pic[row] + "\t" + url[row]);
						URL picurl = new URL(pic[row]);
						BufferedImage image = ImageIO.read(picurl);
						ImageIcon im = new ImageIcon(image);
						im = change(im);

						// 设置ImageIcon
						l[row].setIcon(im);
						l[row].removeMouseListener(mousel[row]);
						MouseListener ml=new MouseListener() {

							@Override
							public void mouseReleased(MouseEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void mousePressed(MouseEvent e) {
								// TODO Auto-generated method stub
								openURL(url[row]);
							}

							@Override
							public void mouseExited(MouseEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void mouseEntered(MouseEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub

							}
							
						};
						mousel[row]=ml;
						
						l[row].addMouseListener(ml);
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
					}
				};
				t.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		// 连接数据库获得结果集
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JFrame f = new JFrame("什么值得买TOP3小时");
		JLabel l[] = new JLabel[24];
		// 设置默认成员id
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(8, 3));
        // 设置面板大小
        p1.setBounds(50, 25, 1080, 1200);
        // 设置面板背景颜色
//      p1.setBackground(Color.GRAY);
        
        
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 主窗体设置大小
//		f.setSize(1080, 700);


		// 主窗体中的组件设置为绝对定位
		f.setLayout(null);
		f.setBounds(600,80, 1200, 1300);
		// 按钮组件
		JButton b = new JButton("刷新");

		// 同时设置组件的位置和大小
		b.setBounds(400, 600, 280, 30);
		f.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				f.requestFocus();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		b.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getKeyCode());
				if (e.getKeyCode() == 39) {
					// 图片向右移动 （y坐标不变，x坐标增加）
					b.setLocation(b.getX() + 10, b.getY());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getKeyCode());
				switch (e.getKeyCode()) {
				case 39:
					b.setLocation(b.getX() + 1, b.getY());
					break;
				case 40:
					b.setLocation(b.getX(), b.getY() + 1);
					break;
				case 38:
					b.setLocation(b.getX(), b.getY() - 1);
					break;
				case 37:
					b.setLocation(b.getX() - 1, b.getY());
					break;
				default:
					break;
				}
			}
		});
		b.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				setpic(l, lastindex);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		// 把按钮加入到主窗体中
		
		f.add(p1);

		// 关闭窗体的时候，退出程序
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 让窗体变得可见
		f.setVisible(true);
		f.requestFocus();

		for (int i = 0; i < 24; i++) {
			l[i] = new JLabel();
			
//			l[i].setBounds(200 * i + 50, 50, 150, 150);
			p1.add(l[i]);
		}
/*		for (int i = 0; i < 5; i++) {
			l[i + 5] = new JLabel();
//			l[i + 5].setBounds(200 * i + 50, 350, 150, 150);
			p1.add(l[i + 5]);
		}
		*/
		setpic(l, lastindex);
//		p1.add(b);

	}
}
