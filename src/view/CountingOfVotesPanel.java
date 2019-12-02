package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.Database;
import model.Candidate;
import model.CandidateComparator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

public class CountingOfVotesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel tableModel;

	public CountingOfVotesPanel(JFrame frame) {
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Posição");
		tableModel.addColumn("Número");
		tableModel.addColumn("Nome");
		tableModel.addColumn("Votos");
		tableModel.addColumn("%");

		JTable jTable = new JTable(tableModel);
		jTable.setEnabled(false);
		jTable.setSelectionBackground(Color.GREEN);
		jTable.setDefaultRenderer(Object.class, new CellRenderer());
		add(new JScrollPane(jTable));

		Database database = new Database();
		List<Candidate> candidates = database.getAllCandidates(true);
		Collections.sort(candidates, new CandidateComparator());

		int totalVotos = 0;
		for (Candidate candidato : candidates)
			totalVotos += candidato.getVotes();

		for (int i = 0; i < candidates.size(); i++) {
			Candidate candidato = candidates.get(i);
			tableModel.addRow(new Object[] { i + 1 + "°", candidato.getNumber(), candidato.getName(),
					candidato.getVotes(),
					totalVotos == 0 ? 0 : String.format("%.1f", 100 * ((double) candidato.getVotes() / totalVotos)) });
		}

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.setBackground(Color.WHITE);
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton voltarButton = new JButton();
		voltarButton.setBackground(Color.WHITE);
		voltarButton.setIcon(new ImageIcon(CountingOfVotesPanel.class.getResource("/images/go_back.png")));
		buttonsPanel.add(voltarButton);
		voltarButton.addActionListener(event -> {
			MainFrame.changeTo(frame, this, new MenuPanel(frame));
		});
	}

}
