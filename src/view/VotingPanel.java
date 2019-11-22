package view;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import database.Database;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.text.ParseException;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

public class VotingPanel extends JPanel {
	private JFormattedTextField numberTextField;
	private JLabel nameLabel;
	private JLabel apelidoLabel;
	private JLabel sectorLabel;
	private JLabel ageLabel;
	private JLabel profileLabel;
	private JLabel serviceTimeLabel;

	private Database database = new Database();

	public VotingPanel() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout(0, 0));

		JPanel candidatePanel = new JPanel();
		candidatePanel.setBackground(Color.WHITE);
		candidatePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(candidatePanel, BorderLayout.CENTER);
		candidatePanel.setLayout(new GridLayout(0, 2, 20, 20));

		JLabel lblNewLabel = new JLabel("N\u00FAmero:");
		candidatePanel.add(lblNewLabel);

		numberTextField = new JFormattedTextField();
		numberTextField.setFont(new Font("Arial Black", Font.BOLD, 16));
		numberTextField.setHorizontalAlignment(JTextField.CENTER);
		candidatePanel.add(numberTextField);
		numberTextField.setColumns(5);
		try {
			MaskFormatter formatter = new MaskFormatter("#####");
			formatter.install(numberTextField);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel lblNome = new JLabel("Nome:");
		candidatePanel.add(lblNome);

		nameLabel = new JLabel("");
		nameLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
		candidatePanel.add(nameLabel);

		JLabel lblNewLabel_1 = new JLabel("Apelido:");
		candidatePanel.add(lblNewLabel_1);

		apelidoLabel = new JLabel("");
		candidatePanel.add(apelidoLabel);

		JLabel lblApelido = new JLabel("Setor:");
		candidatePanel.add(lblApelido);

		sectorLabel = new JLabel("");
		candidatePanel.add(sectorLabel);

		JLabel lblIdade = new JLabel("Idade:");
		candidatePanel.add(lblIdade);

		ageLabel = new JLabel("");
		candidatePanel.add(ageLabel);

		JLabel lblNewLabel_4 = new JLabel("Tempo servi\u00E7o:");
		candidatePanel.add(lblNewLabel_4);

		serviceTimeLabel = new JLabel("");
		candidatePanel.add(serviceTimeLabel);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton whiteButton = new JButton("BRANCO");
		buttonsPanel.add(whiteButton);
		whiteButton.setBackground(Color.WHITE);
		whiteButton.addActionListener(event -> {
			JOptionPane.showMessageDialog(this, "Voto registrado com sucesso!");
		});

		JButton confirmButton = new JButton("CONFIRMAR");
		buttonsPanel.add(confirmButton);
		confirmButton.setBackground(Color.GREEN);
		confirmButton.addActionListener(event -> {
			if (!numberTextField.getText().isBlank()) {
				database.voteFor(Integer.parseInt(numberTextField.getText()));
				limparCampos();
				JOptionPane.showMessageDialog(this, "Voto registrado com sucesso!");
			} else
				JOptionPane.showMessageDialog(this, "Por favor, insira o número do canditado que deseja votar.",
						"Aviso", JOptionPane.ERROR_MESSAGE);
		});
	}

	private void limparCampos() {
		numberTextField.setText("");
		nameLabel.setText("");
		apelidoLabel.setText("");
		sectorLabel.setText("");
		ageLabel.setText("");
		serviceTimeLabel.setText("");
	}
}
