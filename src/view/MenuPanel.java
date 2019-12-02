package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

import database.Database;
import model.Candidate;

public class MenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public MenuPanel(JFrame frame) {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(MenuPanel.class.getResource("/images/cipa_topo.png")));
		add(label);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton cadastrarCandidatosButton = new JButton("Candidatos");
		buttonsPanel.add(cadastrarCandidatosButton);

		JButton startVotingButton = new JButton("Iniciar Vota\u00E7\u00E3o");
		buttonsPanel.add(startVotingButton);

		JButton apurarVotosButton = new JButton("Apurar Votos");
		buttonsPanel.add(apurarVotosButton);

		cadastrarCandidatosButton.addActionListener(event -> {
			MainFrame.changeTo(frame, this, new CandidatesPanel(frame));
		});

		startVotingButton.addActionListener(event -> {
			Database database = new Database();

			try {
				database.addCandidate(new Candidate(0, "Nulo", "-", 0, "-", "-"));
			} catch (Exception e) {
			}

			try {
				database.addCandidate(new Candidate(-1, "Branco", "-", 0, "-", "-"));
			} catch (Exception e) {
			}

			database.clearVotes();
			MainFrame.changeTo(frame, this, new VotingPanel(frame));
		});

		apurarVotosButton.addActionListener(event -> {
			MainFrame.changeTo(frame, this, new CountingOfVotesPanel(frame));
		});
	}

}
