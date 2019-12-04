package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import database.Database;
import model.Candidate;
import model.CandidateComparator;
import model.Report;

public class CountingOfVotesController {
	private List<Candidate> candidates;

	private DefaultTableModel tableModel;

	private int totalVotes;

	private JFrame frame;

	public CountingOfVotesController(JFrame frame, DefaultTableModel tableModel) {
		this.frame = frame;
		this.tableModel = tableModel;

		Database database = new Database();

		candidates = database.getAllCandidates(true);

		Collections.sort(candidates, new CandidateComparator());

		for (Candidate candidate : candidates)
			totalVotes += candidate.getVotes();
	}

	public void listCandidates() {
		int totalCandidates = candidates.size();

		for (int i = 0; i < totalCandidates; i++) {
			Candidate candidate = candidates.get(i);

			tableModel.addRow(new Object[] { (i + 1) + "°", candidate.getNumber(), candidate.getName(),
					candidate.getVotes(), String.format("%.1f", calculatePercent(candidate.getVotes())) });
		}
	}

	private double calculatePercent(int votes) {
		if (totalVotes == 0)
			return 0;

		return ((double) votes / totalVotes) * 100;
	}

	private final ActionListener reportButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Report report = new Report(frame, candidates, totalVotes);
			report.saveReport(report.generateReport());

			JOptionPane.showMessageDialog(frame, "Relatório gerado com sucesso", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	public ActionListener getReportButtonListener() {
		return reportButtonListener;
	}

}
