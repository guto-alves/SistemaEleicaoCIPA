package view;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import database.Database;
import model.Candidate;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;

public class VotingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JFormattedTextField[] numerosTextFields;

	private JLabel numeroLabel;
	private JLabel nomeJLabel;
	private JLabel apelidoLabel;
	private JLabel setorLabel;
	private JLabel idadeLabel;
	private JLabel tempoServicoLabel;

	private JPanel buttonsInfoPanel;

	private Database database = new Database();

	private JFrame frame;

	private Atalho encerrar = new Atalho();

	public VotingPanel(JFrame frame) {
		setPreferredSize(new Dimension(850, 500));
		setLayout(new BorderLayout(0, 0));

		this.frame = frame;

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);

		numeroLabel = new JLabel("N\u00FAmero:");
		numeroLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numeroLabel.setBounds(10, 66, 60, 14);
		centerPanel.add(numeroLabel);

		JPanel numeroCandidatoPanel = new JPanel();
		numeroCandidatoPanel.setBackground(Color.WHITE);
		numeroCandidatoPanel.setBounds(92, 47, 173, 47);
		centerPanel.add(numeroCandidatoPanel);

		numerosTextFields = new JFormattedTextField[5];

		for (int i = 0; i < numerosTextFields.length; i++) {
			numerosTextFields[i] = new JFormattedTextField();
			numerosTextFields[i].setColumns(1);
			numerosTextFields[i].setHorizontalAlignment(SwingConstants.CENTER);
			numerosTextFields[i].setFont(new Font("Arial Black", Font.BOLD, 24));
			numerosTextFields[i].setDisabledTextColor(Color.BLACK);

			try {
				MaskFormatter formatter = new MaskFormatter("#");
				formatter.install(numerosTextFields[i]);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			numeroCandidatoPanel.add(numerosTextFields[i]);
		}

		numerosTextFields[0].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numerosTextFields[0]))
					enableUsedField(1);
			}
		});

		numerosTextFields[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numerosTextFields[1]))
					enableUsedField(2);

			}
		});

		numerosTextFields[2].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numerosTextFields[2]))
					enableUsedField(3);

			}
		});

		numerosTextFields[3].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numerosTextFields[3]))
					enableUsedField(4);
			}
		});

		numerosTextFields[4].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isValidField(numerosTextFields[4]) && numerosTextFields[4].isEditable()) {
					enableUsedField(-1);
					int numberSearched = getCandidateNumber();

					Candidate candidate = searchCandidate(numberSearched);

					if (candidate != null)
						displayInfoCandidate(candidate);
					else
						JOptionPane
								.showMessageDialog(VotingPanel.this,
										"Não existe candidato com o número " + numberSearched
												+ ".\nPor favor, tente novamente.",
										"Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("SEU VOTO PARA");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 146, 14);
		centerPanel.add(lblNewLabel);

		nomeJLabel = new JLabel();
		nomeJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nomeJLabel.setText("Nome: ");
		nomeJLabel.setBounds(10, 124, 382, 22);
		centerPanel.add(nomeJLabel);

		apelidoLabel = new JLabel();
		apelidoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		apelidoLabel.setText("Apelido:");
		apelidoLabel.setBounds(10, 157, 280, 14);
		centerPanel.add(apelidoLabel);

		setorLabel = new JLabel();
		setorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		setorLabel.setText("Setor:");
		setorLabel.setBounds(10, 189, 337, 14);
		centerPanel.add(setorLabel);

		idadeLabel = new JLabel();
		idadeLabel.setText("Idade: ");
		idadeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		idadeLabel.setBounds(10, 218, 214, 14);
		centerPanel.add(idadeLabel);

		tempoServicoLabel = new JLabel();
		tempoServicoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tempoServicoLabel.setText("Tempo:");
		tempoServicoLabel.setBounds(10, 249, 239, 14);
		centerPanel.add(tempoServicoLabel);

		buttonsInfoPanel = new JPanel();
		buttonsInfoPanel.setBackground(Color.WHITE);
		buttonsInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) buttonsInfoPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		buttonsInfoPanel.setBounds(10, 287, 710, 61);
		centerPanel.add(buttonsInfoPanel);

		JLabel buttonsInforLabel = new JLabel(
				"<html>Aperte a tecla:<br>&nbsp;&nbsp;&nbsp;CONFIRMA para CONFIRMAR este voto<br>&nbsp;&nbsp;&nbsp;&nbsp;CORRIGE para REINICIAR este voto</html");
		buttonsInfoPanel.add(buttonsInforLabel);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(51, 51, 51));
		buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton brancoButton = new JButton("BRANCO");
		brancoButton.setBackground(Color.WHITE);
		brancoButton.addActionListener(event -> {
			registerVote(-1);
		});
		buttonsPanel.add(brancoButton);

		JButton corrigeButton = new JButton("CORRIGE");
		corrigeButton.addActionListener(corrigeButtonListener);
		corrigeButton.setBackground(Color.RED);
		buttonsPanel.add(corrigeButton);

		JButton confirmaButton = new JButton("CONFIRMA");
		confirmaButton.setBackground(new Color(50, 205, 50));
		confirmaButton.addActionListener(confirmaButtonListener);
		buttonsPanel.add(confirmaButton);

		infoCandidato(false);

		enableUsedField(0);
		addFinishVotingListener(this);
	}

	private final ActionListener confirmaButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isValidFields())
				registerVote(getCandidateNumber());
			else
				JOptionPane.showMessageDialog(VotingPanel.this, "Por favor, insira o número do canditado para votar.",
						"Aviso", JOptionPane.ERROR_MESSAGE);
		}
	};

	private final ActionListener corrigeButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < numerosTextFields.length; i++)
				numerosTextFields[i].setText("");

			enableUsedField(0);
			infoCandidato(false);
		}
	};

	private void registerVote(int number) {
		database.voteFor(number);

		MainFrame.changeTo(frame, VotingPanel.this, new RecordingVotePanel(frame));
	}

	protected Candidate searchCandidate(int number) {
		Candidate candidate = database.getCandidateByNumber(number);
		return candidate;
	}

	private void displayInfoCandidate(Candidate candidate) {
		nomeJLabel.setText(
				String.format("<html>Nome: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>%s</strong></html>",
						candidate.getName()));
		apelidoLabel.setText(
				String.format("<html>Apelido: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>", candidate.getNickname()));
		setorLabel.setText(
				String.format("<html>Setor: &nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>", candidate.getSector()));
		idadeLabel.setText(
				String.format("<html>Idade: &nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>", candidate.getAge()));
		tempoServicoLabel.setText(
				String.format("<html>Tempo: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s</html>", candidate.getServiceTime()));

		infoCandidato(true);
	}

	private int getCandidateNumber() {
		String numeroCandidato = "";

		for (JFormattedTextField field : numerosTextFields)
			numeroCandidato += field.getText().toString();

		return Integer.parseInt(numeroCandidato);
	}

	private void enableUsedField(int usedField) {
		for (int i = 0; i < numerosTextFields.length; i++) {
			if (i != usedField)
				numerosTextFields[i].setEditable(false);
			else {
				numerosTextFields[i].setEditable(true);
				numerosTextFields[i].grabFocus();
			}
		}
	}

	private boolean isValidFields() {
		for (JFormattedTextField field : numerosTextFields) {
			if (!isValidField(field))
				return false;
		}

		return true;
	}

	private boolean isValidField(JFormattedTextField field) {
		return !field.getText().isBlank();
	}

	public void infoCandidato(boolean visible) {
		numeroLabel.setVisible(visible);
		nomeJLabel.setVisible(visible);
		apelidoLabel.setVisible(visible);
		setorLabel.setVisible(visible);
		idadeLabel.setVisible(visible);
		tempoServicoLabel.setVisible(visible);
		buttonsInfoPanel.setVisible(visible);
	}

	private void addFinishVotingListener(JPanel painel) {
		ActionMap actionMap = painel.getActionMap();
		actionMap.put("encerrarAction", encerrar);
		painel.setActionMap(actionMap);

		InputMap imap = painel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);

		imap.put(KeyStroke.getKeyStroke("F4"), "encerrarAction");
	}

	private class Atalho extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(VotingPanel.this, "Tem certeza que deseja encerrar a votação?",
					"Encerrar Votação", JOptionPane.OK_CANCEL_OPTION);

			if (result == 0)
				MainFrame.changeTo(frame, VotingPanel.this, new MenuPanel(frame));
		}
	}
}
