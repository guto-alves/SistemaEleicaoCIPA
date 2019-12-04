package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Report {
	private JFrame frame;

	private List<Candidate> candidates;

	private int totalVotes;

	public Report(JFrame frame, List<Candidate> candidates, int totalVotes) {
		this.frame = frame;
		this.candidates = candidates;
		this.totalVotes = totalVotes;
	}

	public String generateReport() {
		String report = String.format("%s %10s %19s %12s %7s\n", "Posição", "Número", "Nome", "Votos", "%");

		int count = 0;

		for (Candidate candidate : candidates) {
			report += String.format("%02d° %13d %20s %10d %10.1f\n", (count + 1), candidate.getNumber(),
					candidate.getName(), candidate.getVotes(),
					totalVotes == 0 ? 0 : 100 * ((double) candidate.getVotes() / totalVotes));

			count++;
		}

		return report;
	}

	public void saveReport(String report) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
