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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CountingOfVotesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel tableModel;

	private List<Candidate> candidates;

	private int totalVotes;

	private JFrame frame;

	public CountingOfVotesPanel(JFrame frame) {
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		this.frame = frame;

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
		candidates = database.getAllCandidates(true);
		Collections.sort(candidates, new CandidateComparator());

		for (Candidate candidato : candidates)
			totalVotes += candidato.getVotes();

		for (int i = 0; i < candidates.size(); i++) {
			Candidate candidato = candidates.get(i);
			tableModel.addRow(new Object[] { i + 1 + "°", candidato.getNumber(), candidato.getName(),
					candidato.getVotes(),
					totalVotes == 0 ? 0 : String.format("%.1f", 100 * ((double) candidato.getVotes() / totalVotes)) });
		}

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.setBackground(Color.WHITE);
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton reportButton = new JButton();
		reportButton.setBackground(Color.WHITE);
		reportButton.setIcon(new ImageIcon(CountingOfVotesPanel.class.getResource("/images/icone-relatorio.png")));
		reportButton.addActionListener(reportButtonListener);
		buttonsPanel.add(reportButton);

		JButton voltarButton = new JButton();
		voltarButton.setBackground(Color.WHITE);
		voltarButton.setIcon(new ImageIcon(CountingOfVotesPanel.class.getResource("/images/go_back.png")));
		buttonsPanel.add(voltarButton);
		voltarButton.addActionListener(event -> {
			MainFrame.changeTo(frame, this, new MenuPanel(frame));
		});
	}

	private final ActionListener reportButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String report = generateReport();
			saveReport(report);
		}
	};

	private String generateReport() {
		String report = String.format("%s %10s %19s %12s %8s\n", "Posição", "Número", "Nome", "Votos", "%");

		int count = 0;

		for (Candidate candidate : candidates) {
			report += String.format("%02d° %13d %20s %10d %10.1f%%\n", (count + 1), candidate.getNumber(),
					candidate.getName(), candidate.getVotes(),
					totalVotes == 0 ? 0 : 100 * ((double) candidate.getVotes() / totalVotes));

			count++;
		}

		return report;
	}

	private void saveReport(String report) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Salvar como");
		chooser.setFileFilter(new FileNameExtensionFilter("Documentos de texto (*.txt)", "txt"));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		String currentDate = dateFormat.format(System.currentTimeMillis());

		chooser.setSelectedFile(new File(currentDate + " - Relatório Eleição CIPA.txt"));
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);

		int result = chooser.showSaveDialog(frame);

		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				String fileName = chooser.getSelectedFile().getPath();
				if (!fileName.contains(".txt"))
					fileName += ".txt";

				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				writer.write(report);
				writer.close();

				JOptionPane.showMessageDialog(frame, "Relatório gerado com sucesso", "Aviso",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
