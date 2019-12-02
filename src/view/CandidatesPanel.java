package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.MaskFormatter;

import database.Database;
import model.Candidate;

import java.awt.FlowLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class CandidatesPanel extends JPanel {
	private static final long serialVersionUID = 4987742184236660664L;

	private JFormattedTextField numberTextField;
	private JTextField nameTextField;
	private JTextField nicknameTextField;
	private JFormattedTextField ageTextField;
	private JTextField sectorTextField;
	private JTextField fotoTextField;
	private JTextField serviceTimeTextField;

	private JTable table;
	private List<Candidate> candidates = new LinkedList<>();
	private CandidateTableModel candidateTableModel;

	private Database database = new Database();

	public CandidatesPanel(JFrame frame) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(600, 400));
		setLayout(new BorderLayout(0, 0));

		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(50, 205, 50));
		titlePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblCandidato = new JLabel("CANDIDATO");
		lblCandidato.setForeground(Color.WHITE);
		lblCandidato.setHorizontalAlignment(SwingConstants.CENTER);
		lblCandidato.setFont(new Font("Arial", Font.BOLD, 18));
		titlePanel.add(lblCandidato);
		add(titlePanel, BorderLayout.NORTH);

		JPanel candidateRegistrationPanel = new JPanel();
		candidateRegistrationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		candidateRegistrationPanel.setForeground(Color.WHITE);
		candidateRegistrationPanel.setBackground(Color.WHITE);
		add(candidateRegistrationPanel);
		candidateRegistrationPanel.setLayout(new GridLayout(10, 2, 4, 4));

		JLabel lblNumber = new JLabel("N\u00FAmero:");
		candidateRegistrationPanel.add(lblNumber);

		numberTextField = new JFormattedTextField();
		candidateRegistrationPanel.add(numberTextField);
		numberTextField.setColumns(10);
		try {
			new MaskFormatter("#####").install(numberTextField);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel lblName = new JLabel("Nome:");
		candidateRegistrationPanel.add(lblName);

		nameTextField = new JTextField();
		candidateRegistrationPanel.add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblNickname = new JLabel("Apelido:");
		candidateRegistrationPanel.add(lblNickname);

		nicknameTextField = new JTextField();
		candidateRegistrationPanel.add(nicknameTextField);
		nicknameTextField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Idade:");
		candidateRegistrationPanel.add(lblNewLabel_2);

		ageTextField = new JFormattedTextField();
		candidateRegistrationPanel.add(ageTextField);
		ageTextField.setColumns(2);
		try {
			new MaskFormatter("##").install(ageTextField);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("Setor:");
		candidateRegistrationPanel.add(lblNewLabel);

		sectorTextField = new JTextField();
		candidateRegistrationPanel.add(sectorTextField);
		sectorTextField.setColumns(4);

		JLabel lblFoto = new JLabel("Foto:");
		candidateRegistrationPanel.add(lblFoto);

		fotoTextField = new JTextField();
		candidateRegistrationPanel.add(fotoTextField);
		fotoTextField.setColumns(5);

		JLabel lblNewLabel_1 = new JLabel("Tempo de servi\u00E7o:");
		candidateRegistrationPanel.add(lblNewLabel_1);

		serviceTimeTextField = new JTextField();
		candidateRegistrationPanel.add(serviceTimeTextField);
		serviceTimeTextField.setColumns(10);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton voltarButton = new JButton();
		voltarButton.setBackground(Color.WHITE);
		voltarButton.setIcon(new ImageIcon(CandidatesPanel.class.getResource("/images/go_back.png")));
		voltarButton.addActionListener(event -> {
			MainFrame.changeTo(frame, this, new MenuPanel(frame));
		});
		buttonsPanel.add(Box.createHorizontalStrut(50));
		buttonsPanel.add(voltarButton);
		buttonsPanel.add(Box.createHorizontalStrut(20));

		JButton registerButton = new JButton("");
		registerButton.setIcon(new ImageIcon(CandidatesPanel.class.getResource("/images/register_icon1.png")));
		registerButton.setBackground(Color.WHITE);
		registerButton.setOpaque(true);
		registerButton.addActionListener(registerButtonListener);
		buttonsPanel.add(registerButton);
		buttonsPanel.add(Box.createHorizontalStrut(50));

		candidates = database.getAllCandidates(false);

		candidateTableModel = new CandidateTableModel(candidates);

		table = new JTable(candidateTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CellRenderer());
		table.setSelectionBackground(new Color(50, 205, 50));
		addShortcutListenerToDeleteCandidate(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(Color.WHITE);
		add(scrollPane, BorderLayout.EAST);
	}

	private ActionListener registerButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isValidFields()) {
				if (numberTextField.getText().trim().length() != 5) {
					JOptionPane.showMessageDialog(null, "Número do candidato deve conter 5 dígitos", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (Integer.parseInt(numberTextField.getText().trim()) == 0) {
					JOptionPane.showMessageDialog(null, "Número 00000 é para votos em branco.\nTente outro número.",
							"Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Candidate candidate = new Candidate(Integer.parseInt(numberTextField.getText()),
						nameTextField.getText(), nicknameTextField.getText(),
						Integer.parseInt(ageTextField.getText().trim()), sectorTextField.getText(),
						serviceTimeTextField.getText());

				if (database.addCandidate(candidate)) {
					candidates.add(candidate);
					candidateTableModel.fireTableDataChanged();
					clearFields();
					JOptionPane.showMessageDialog(null, "Canditado cadastrado com sucesso!", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(null, "Número do candidato já existe!", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
			}
		}
	};

	private boolean isValidFields() {
		if (numberTextField.getText().isBlank() || nameTextField.getText().isEmpty()
				|| nicknameTextField.getText().isEmpty() || sectorTextField.getText().isEmpty()
				|| serviceTimeTextField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", "Aviso",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void clearFields() {
		numberTextField.setText("");
		nameTextField.setText("");
		nicknameTextField.setText("");
		ageTextField.setText("");
		sectorTextField.setText("");
		serviceTimeTextField.setText("");
		fotoTextField.setText("");
	}

	private void addShortcutListenerToDeleteCandidate(JTable table) {
		ActionMap actionMap = new ActionMap();
		actionMap.put("deleteCandidate", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(CandidatesPanel.this,
						"Tem certeza que deseja excluir o candidato selecionado?", "Excluir Candidato",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) {
					int candidateIndex = table.getSelectedRow();

					Candidate candidate = candidates.get(candidateIndex);

					database.deleteCandidate(candidate);
					candidates.remove(candidateIndex);

					candidateTableModel.fireTableDataChanged();
				}
			}
		});
		table.setActionMap(actionMap);

		InputMap inputMap = table.getInputMap(JTable.WHEN_FOCUSED);
		inputMap.put(KeyStroke.getKeyStroke("DELETE"), "deleteCandidate");
	}
}
