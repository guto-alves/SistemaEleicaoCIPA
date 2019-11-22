package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.Database;

public class registerCandidateListener implements ActionListener {
	private Database database = new Database();

	private JFormattedTextField numeroTextField;
	private JTextField nomeTextField;
	private JTextField apelidoTextField;
	private JTextField setorTextField;
	private JFormattedTextField idadeTextField;
	private JTextField fotoTextField;
	private JTextField tempoServicoTextField;

	public registerCandidateListener(JFormattedTextField numeroTextField, JTextField nomeTextField,
			JTextField apelidoTextField, JTextField setorTextField, JFormattedTextField idadeTextField,
			JTextField fotoTextField, JTextField tempoServicoTextField) {
		this.numeroTextField = numeroTextField;
		this.nomeTextField = nomeTextField;
		this.apelidoTextField = apelidoTextField;
		this.setorTextField = setorTextField;
		this.idadeTextField = idadeTextField;
		this.fotoTextField = fotoTextField;
		this.tempoServicoTextField = tempoServicoTextField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isValidFields()) {
			database.addCandidate(Integer.parseInt(numeroTextField.getText()), nomeTextField.getText(),
					apelidoTextField.getText(), Integer.parseInt(idadeTextField.getText()), setorTextField.getText(),
					tempoServicoTextField.getText());

			clearFields();
			JOptionPane.showMessageDialog(null, "Canditado cadastrado com sucesso!", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private boolean isValidFields() {
		if (numeroTextField.getText().isEmpty() || nomeTextField.getText().isEmpty()
				|| idadeTextField.getText().isEmpty() || apelidoTextField.getText().isEmpty()
				|| tempoServicoTextField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void clearFields() {
		numeroTextField.setText("");
		nomeTextField.setText("");
		apelidoTextField.setText("");
		setorTextField.setText("");
		idadeTextField.setText("");
		tempoServicoTextField.setText("");
		fotoTextField.setText("");
	}
}
