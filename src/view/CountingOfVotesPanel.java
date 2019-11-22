package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.Database;
import model.Candidate;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class CountingOfVotesPanel extends JPanel {
	private DefaultTableModel tableModel;

	public CountingOfVotesPanel() {
		setLayout(new BorderLayout(0, 0));

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Posição");
		tableModel.addColumn("Número");
		tableModel.addColumn("Nome");
		tableModel.addColumn("Votos");
		tableModel.addColumn("%");
		JTable jTable = new JTable(tableModel);
		jTable.setEnabled(false);
		add(new JScrollPane(jTable));

		Database database = new Database();
		List<Candidate> candidatos = database.getAllCandidates();

		int totalVotos = 0;
		for (Candidate candidato : candidatos)
			totalVotos += candidato.getVotes();

		for (int i = 0; i < candidatos.size(); i++) {
			Candidate candidato = candidatos.get(i);
			tableModel.addRow(new Object[] { i, candidato.getNumber(), candidato.getName(), candidato.getVotes(),
					totalVotos == 0 ? 0 : candidato.getVotes() / totalVotos * 100 });
		}

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton voltarButton = new JButton();
		voltarButton.setIcon(new ImageIcon(CountingOfVotesPanel.class.getResource("/images/go_back3.png")));
		buttonsPanel.add(voltarButton);
		voltarButton.addActionListener(event -> {
			MainFrame.cardLayout.show(MainFrame.cardsPanel, "menu");
		});
	}

}
