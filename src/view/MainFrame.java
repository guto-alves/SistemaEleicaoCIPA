package view;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
	private JPanel contentPane;

	public static JPanel cardsPanel;
	public static CardLayout cardLayout;

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
		setTitle("Sistema de eleição da CIPA.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(430, 350));
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		cardsPanel = new JPanel(new CardLayout());
		cardsPanel.add(new MenuPanel(), "menu");
		cardsPanel.add(new CadidateRegistrationPanel(), "register");
		cardsPanel.add(new CountingOfVotesPanel(), "counting of votes");
		cardsPanel.add(new VotingPanel(), "voting");

		contentPane.add(cardsPanel, BorderLayout.CENTER);

		cardLayout = (CardLayout) (cardsPanel.getLayout());
		cardLayout.show(cardsPanel, "menu");
	}
}
