package view;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/images/logo_siga.jpg")));
		setTitle("Sistema de eleição da CIPA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(750, 450));
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		add(new MenuPanel(this));
	}

	public static void changeTo(JFrame frame, JPanel current, JPanel next) {
		frame.remove(current);
		frame.add(next);
		frame.repaint();
		frame.revalidate();
	}
}
