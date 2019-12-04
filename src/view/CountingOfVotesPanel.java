package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CountingOfVotesController;
import database.Database;
import model.Candidate;
import model.CandidateComparator;
import model.Report;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class CountingOfVotesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private CountingOfVotesController controller;

	public CountingOfVotesPanel(JFrame frame) {
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Posição");
		tableModel.addColumn("Número");
		tableModel.addColumn("Nome");
		tableModel.addColumn("Votos");
		tableModel.addColumn("%");

		JTable table = new JTable(tableModel);
		table.setEnabled(false);
		table.setDefaultRenderer(Object.class, new CellRenderer());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(50, 205, 50));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Resultado da Vota\u00E7\u00E3o");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(lblNewLabel);
		add(new JScrollPane(table));

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.setBackground(Color.WHITE);
		add(buttonsPanel, BorderLayout.SOUTH);

		JButton reportButton = new JButton();
		reportButton.setBackground(Color.WHITE);
		reportButton.setIcon(new ImageIcon(CountingOfVotesPanel.class.getResource("/images/icone-relatorio.png")));

		buttonsPanel.add(reportButton);

		JButton voltarButton = new JButton();
		voltarButton.setBackground(Color.WHITE);
		voltarButton.setIcon(new ImageIcon(CountingOfVotesPanel.class.getResource("/images/go_back.png")));
		buttonsPanel.add(voltarButton);
		voltarButton.addActionListener(event -> {
			MainFrame.changeTo(frame, this, new MenuPanel(frame));
		});

		controller = new CountingOfVotesController(frame, tableModel);
		controller.listCandidates();
		reportButton.addActionListener(controller.getReportButtonListener());
	}

}
