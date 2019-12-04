package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.Database;
import model.Candidate;
import view.MainFrame;
import view.RecordingVotePanel;

public class VotingController {
	private JFrame frame;
	private JPanel panel;
	private JFormattedTextField[] numbersTextFields;
	private JLabel numberJLabel;
	private JLabel nameJLabel;
	private JLabel nicknameJLabel;
	private JLabel sectorJLabel;
	private JLabel ageJLabel;
	private JLabel serviceTimeJLabel;

	private JPanel buttonsInfoPanel;

	private JLabel voteBlankOrNullJLabel;
	private boolean voteBlank;
	private boolean voteNull;

	private Database database = new Database();

	public VotingController(JFrame frame, JPanel panel, JFormattedTextField[] numbersTextFields, JLabel numberJLabel,
			JLabel nameJLabel, JLabel nicknameJLabel, JLabel sectorJLabel, JLabel ageJLabel, JLabel serviceTimeJLabel,
			JPanel buttonsInfoPanel, JLabel voteBlankOrNullJLabel) {
		this.frame = frame;
		this.panel = panel;
		this.numbersTextFields = numbersTextFields;
		this.numberJLabel = numberJLabel;
		this.nameJLabel = nameJLabel;
		this.nicknameJLabel = nicknameJLabel;
		this.sectorJLabel = sectorJLabel;
		this.ageJLabel = ageJLabel;
		this.serviceTimeJLabel = serviceTimeJLabel;
		this.buttonsInfoPanel = buttonsInfoPanel;
		this.voteBlankOrNullJLabel = voteBlankOrNullJLabel;

		numbersTextFields[0].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numbersTextFields[0]))
					enableUsedField(1);
			}
		});

		numbersTextFields[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numbersTextFields[1]))
					enableUsedField(2);

			}
		});

		numbersTextFields[2].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numbersTextFields[2]))
					enableUsedField(3);

			}
		});

		numbersTextFields[3].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numbersTextFields[3]))
					enableUsedField(4);
			}
		});

		numbersTextFields[4].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numbersTextFields[4]) && numbersTextFields[4].isEditable()) {
					enableUsedField(-1);
					int numberSearched = getCandidateNumber();

					Candidate candidate = searchCandidate(numberSearched);

					if (candidate != null) {
						if (candidate.getNumber() == 0) {
							voteBlankOrNullJLabel.setText("VOTO NULO");
							voteBlankOrNullJLabel.setVisible(true);
							voteBlank = false;
							voteNull = true;
						} else {
							voteBlankOrNullJLabel.setVisible(false);
							displayInfoCandidate(candidate);
							voteBlank = false;
							voteNull = false;
						}
					} else
						JOptionPane.showMessageDialog(panel, "Não existe candidato com o número " + numberSearched
								+ ".\nPor favor, tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		showCandidateInformation(false);
		enableUsedField(0);
	}

	private final ActionListener confirmButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (voteBlank)
				registerVote(-1);
			else if (voteNull)
				registerVote(0);
			else if (isValidFields())
				registerVote(getCandidateNumber());
			else
				JOptionPane.showMessageDialog(panel, "Por favor, insira o número do canditado para votar.", "Aviso",
						JOptionPane.ERROR_MESSAGE);
		}
	};

	private final ActionListener correctButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < numbersTextFields.length; i++)
				numbersTextFields[i].setText("");

			voteBlankOrNullJLabel.setVisible(false);
			showCandidateInformation(false);
			enableUsedField(0);
		}
	};

	private final ActionListener blankVoteButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			showCandidateInformation(false);

			enableUsedField(-1);
			voteBlankOrNullJLabel.setText("VOTO EM BRANCO");
			voteBlankOrNullJLabel.setVisible(true);
			voteBlank = true;
			voteNull = false;
		}
	};

	private void registerVote(int number) {
		database.voteFor(number);

		MainFrame.changeTo(frame, panel, new RecordingVotePanel(frame));
	}

	private Candidate searchCandidate(int number) {
		Candidate candidate = database.getCandidateByNumber(number);
		return candidate;
	}

	private void displayInfoCandidate(Candidate candidate) {
		nameJLabel.setText(
				String.format("<html>Nome:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>%s</strong></html>",
						candidate.getName()));
		nicknameJLabel.setText(
				String.format("<html>Apelido:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>", candidate.getNickname()));
		sectorJLabel.setText(String.format("<html>Setor:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>",
				candidate.getSector()));
		ageJLabel.setText(String.format("<html>Idade:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>",
				candidate.getAge()));
		serviceTimeJLabel.setText(
				String.format("<html>Tempo:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>", candidate.getServiceTime()));

		showCandidateInformation(true);
	}

	private int getCandidateNumber() {
		String numeroCandidato = "";

		for (JFormattedTextField field : numbersTextFields)
			numeroCandidato += field.getText().toString().trim();

		return Integer.parseInt(numeroCandidato);
	}

	private void enableUsedField(int usedField) {
		for (int i = 0; i < numbersTextFields.length; i++) {
			if (i == usedField) {
				numbersTextFields[i].setEditable(true);
				numbersTextFields[i].grabFocus();
			} else
				numbersTextFields[i].setEditable(false);
		}
	}

	private boolean isValidFields() {
		for (JFormattedTextField field : numbersTextFields) {
			if (!isValidField(field))
				return false;
		}

		return true;
	}

	private boolean isValidField(JFormattedTextField field) {
		return !field.getText().isBlank();
	}

	private void showCandidateInformation(boolean visible) {
		numberJLabel.setVisible(visible);
		nameJLabel.setVisible(visible);
		nicknameJLabel.setVisible(visible);
		sectorJLabel.setVisible(visible);
		ageJLabel.setVisible(visible);
		serviceTimeJLabel.setVisible(visible);
		buttonsInfoPanel.setVisible(visible);
	}

	public ActionListener getConfirmButtonListener() {
		return confirmButtonListener;
	}

	public ActionListener getCorrectButtonListener() {
		return correctButtonListener;
	}

	public ActionListener getBlankVoteButtonListener() {
		return blankVoteButtonListener;
	}

}
