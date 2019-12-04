package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Color;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import controller.Shortcut;
import controller.VotingController;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;

public class VotingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JFrame frame;

	private VotingController votingController;

	public VotingPanel(JFrame frame) {
		setPreferredSize(new Dimension(850, 500));
		setLayout(new BorderLayout(0, 0));

		this.frame = frame;

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);

		JLabel numberJLabel = new JLabel("N\u00FAmero:");
		numberJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numberJLabel.setBounds(10, 66, 60, 14);
		centerPanel.add(numberJLabel);

		JPanel numeroCandidatoPanel = new JPanel();
		numeroCandidatoPanel.setBackground(Color.WHITE);
		numeroCandidatoPanel.setBounds(92, 47, 173, 47);
		centerPanel.add(numeroCandidatoPanel);

		JFormattedTextField[] numbersTextFields = new JFormattedTextField[5];

		for (int i = 0; i < numbersTextFields.length; i++) {
			numbersTextFields[i] = new JFormattedTextField();
			numbersTextFields[i].setColumns(1);
			numbersTextFields[i].setHorizontalAlignment(SwingConstants.CENTER);
			numbersTextFields[i].setFont(new Font("Arial Black", Font.BOLD, 24));
			numbersTextFields[i].setDisabledTextColor(Color.BLACK);

			try {
				MaskFormatter formatter = new MaskFormatter("#");
				formatter.install(numbersTextFields[i]);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			numeroCandidatoPanel.add(numbersTextFields[i]);
		}

		JLabel lblNewLabel = new JLabel("SEU VOTO PARA");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 146, 14);
		centerPanel.add(lblNewLabel);

		JLabel nameJLabel = new JLabel();
		nameJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameJLabel.setText("Nome: ");
		nameJLabel.setBounds(10, 110, 564, 22);
		centerPanel.add(nameJLabel);

		JLabel nicknameJLabel = new JLabel();
		nicknameJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nicknameJLabel.setText("Apelido:");
		nicknameJLabel.setBounds(10, 150, 280, 14);
		centerPanel.add(nicknameJLabel);

		JLabel sectorJLabel = new JLabel();
		sectorJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sectorJLabel.setText("Setor:");
		sectorJLabel.setBounds(10, 185, 443, 14);
		centerPanel.add(sectorJLabel);

		JLabel ageJLabel = new JLabel();
		ageJLabel.setText("Idade: ");
		ageJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ageJLabel.setBounds(10, 218, 214, 14);
		centerPanel.add(ageJLabel);

		JLabel serviceTimeJLabel = new JLabel();
		serviceTimeJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		serviceTimeJLabel.setText("Tempo:");
		serviceTimeJLabel.setBounds(10, 249, 239, 14);
		centerPanel.add(serviceTimeJLabel);

		JPanel buttonsInfoPanel = new JPanel();
		buttonsInfoPanel.setBackground(Color.WHITE);
		buttonsInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) buttonsInfoPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		buttonsInfoPanel.setBounds(10, 287, 710, 61);
		centerPanel.add(buttonsInfoPanel);

		JLabel buttonsInforLabel = new JLabel(
				"<html>Aperte a tecla:<br>&nbsp;&nbsp;&nbsp;CONFIRMA para CONFIRMAR este voto<br>&nbsp;&nbsp;&nbsp;&nbsp;CORRIGE para REINICIAR este voto</html");
		buttonsInfoPanel.add(buttonsInforLabel);

		JLabel voteBlankOrNullJLabel = new JLabel("VOTO EM BRANCO");
		voteBlankOrNullJLabel.setVisible(false);
		voteBlankOrNullJLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		voteBlankOrNullJLabel.setBounds(299, 157, 222, 46);
		centerPanel.add(voteBlankOrNullJLabel);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(51, 51, 51));
		buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton blankVoteButton = new JButton("BRANCO");
		blankVoteButton.setBackground(Color.WHITE);
		buttonsPanel.add(blankVoteButton);

		JButton correctButton = new JButton("CORRIGE");
		correctButton.setBackground(Color.RED);
		buttonsPanel.add(correctButton);

		JButton confirmButton = new JButton("CONFIRMA");
		confirmButton.setBackground(new Color(50, 205, 50));
		buttonsPanel.add(confirmButton);

		Shortcut.addListener(this, action, JComponent.WHEN_IN_FOCUSED_WINDOW, "F4");

		votingController = new VotingController(frame, this, numbersTextFields, numberJLabel, nameJLabel,
				nicknameJLabel, sectorJLabel, ageJLabel, serviceTimeJLabel, buttonsInfoPanel, voteBlankOrNullJLabel);

		blankVoteButton.addActionListener(votingController.getBlankVoteButtonListener());
		correctButton.addActionListener(votingController.getCorrectButtonListener());
		confirmButton.addActionListener(votingController.getConfirmButtonListener());
	}

	private final AbstractAction action = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(VotingPanel.this, "Tem certeza que deseja encerrar a votação?",
					"Encerrar Votação", JOptionPane.OK_CANCEL_OPTION);

			if (result == 0)
				MainFrame.changeTo(frame, VotingPanel.this, new MenuPanel(frame));
		}
	};

}
