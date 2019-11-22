package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

public class MenuPanel extends JPanel {

	public MenuPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(MenuPanel.class.getResource("/images/cipa_topo2.png")));
		add(label);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(245, 245, 245));
		buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton iniciarVotacaoButton = new JButton("Iniciar Vota\u00E7\u00E3o");
		buttonsPanel.add(iniciarVotacaoButton);

		JButton cadastrarCandidatosButton = new JButton("Cadastrar Candidatos");
		buttonsPanel.add(cadastrarCandidatosButton);

		JButton apurarVotosButton = new JButton("Apurar Votos");
		buttonsPanel.add(apurarVotosButton);

		apurarVotosButton.addActionListener(event -> {
			MainFrame.cardLayout.show(MainFrame.cardsPanel, "counting of votes");
		});
		cadastrarCandidatosButton.addActionListener(event -> {
			MainFrame.cardLayout.show(MainFrame.cardsPanel, "register");
		});
		iniciarVotacaoButton.addActionListener(event -> {
			MainFrame.cardLayout.show(MainFrame.cardsPanel, "voting");
		});
	}
}
