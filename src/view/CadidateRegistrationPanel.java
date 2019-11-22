package view;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.text.ParseException;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.registerCandidateListener;

import java.awt.FlowLayout;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class CadidateRegistrationPanel extends JPanel {
	private static final long serialVersionUID = 4987742184236660664L;

	public CadidateRegistrationPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

		JLabel lblCandidato = new JLabel("CANDIDATO");
		lblCandidato.setHorizontalAlignment(SwingConstants.CENTER);
		lblCandidato.setFont(new Font("Arial", Font.BOLD, 18));

		titlePanel.add(Box.createHorizontalStrut(60));
		titlePanel.add(lblCandidato);
		add(titlePanel);

		titlePanel.add(Box.createHorizontalStrut(60));

		JPanel candidatePanel = new JPanel();
		candidatePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		candidatePanel.setForeground(Color.WHITE);
		candidatePanel.setBackground(Color.WHITE);
		add(candidatePanel);
		candidatePanel.setLayout(new GridLayout(7, 2, 4, 4));

		JLabel lblNumber = new JLabel("N\u00FAmero:");
		candidatePanel.add(lblNumber);

		JFormattedTextField numberTextField = new JFormattedTextField();
		candidatePanel.add(numberTextField);
		numberTextField.setColumns(10);
		try {
			new MaskFormatter("#####").install(numberTextField);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JLabel lblName = new JLabel("Nome:");
		candidatePanel.add(lblName);

		JTextField nameTextField = new JTextField();
		candidatePanel.add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblNickname = new JLabel("Apelido:");
		candidatePanel.add(lblNickname);

		JTextField nicknameTextField = new JTextField();
		candidatePanel.add(nicknameTextField);
		nicknameTextField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Setor:");
		candidatePanel.add(lblNewLabel_2);

		JTextField sectorTextField = new JTextField();
		candidatePanel.add(sectorTextField);
		sectorTextField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Idade:");
		candidatePanel.add(lblNewLabel);

		JFormattedTextField ageTextField = new JFormattedTextField();
		candidatePanel.add(ageTextField);
		ageTextField.setColumns(4);
		try {
			new MaskFormatter("##").install(ageTextField);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JLabel lblFoto = new JLabel("Foto:");
		candidatePanel.add(lblFoto);

		JTextField fotoTextField = new JTextField();
		candidatePanel.add(fotoTextField);
		fotoTextField.setColumns(5);

		JLabel lblNewLabel_1 = new JLabel("Tempo de servi\u00E7o:");
		candidatePanel.add(lblNewLabel_1);

		JTextField tempoServicoTextField = new JTextField();
		candidatePanel.add(tempoServicoTextField);
		tempoServicoTextField.setColumns(10);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(buttonsPanel);

		JButton voltarButton = new JButton("");
		voltarButton.setIcon(new ImageIcon(CadidateRegistrationPanel.class.getResource("/images/go_back3.png")));
		voltarButton.addActionListener(event -> {
			MainFrame.cardLayout.previous(MainFrame.cardsPanel);
		});

		buttonsPanel.add(Box.createHorizontalStrut(50));
		buttonsPanel.add(voltarButton);
		buttonsPanel.add(Box.createHorizontalStrut(20));

		JButton registerButton = new JButton("Cadastrar");
		registerButton.setBackground(Color.BLUE);
		registerButton.setForeground(Color.WHITE);
		registerButton.addActionListener(new registerCandidateListener(numberTextField, nameTextField,
				nicknameTextField, sectorTextField, ageTextField, fotoTextField, tempoServicoTextField));
		buttonsPanel.add(registerButton);
		buttonsPanel.add(Box.createHorizontalStrut(50));
	}

}
