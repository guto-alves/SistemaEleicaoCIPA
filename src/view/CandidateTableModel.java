package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import database.Database;
import model.Candidate;

public class CandidateTableModel extends AbstractTableModel {
	private List<Candidate> candidates;

	private String[] columnNames = { "Número", "Nome", "Apelido", "Idade", "Setor", "Tempo" };

	public static final int INDEX_NUM = 0;
	public static final int INDEX_NOME = 1;
	public static final int INDEX_APELIDO = 2;
	public static final int INDEX_IDADE = 3;
	public static final int INDEX_SETOR = 4;
	public static final int INDEX_TEMPO = 5;

	private Database database = new Database();

	public CandidateTableModel(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Candidate candidate = candidates.get(rowIndex);
		int currentNumber = candidate.getNumber();

		switch (columnIndex) {
		case INDEX_NUM:
			if (aValue.toString().length() == 5)
				candidate.setNumber(Integer.parseInt(aValue.toString()));
			break;
		case INDEX_NOME:
			candidate.setName((String) aValue);
			break;
		case INDEX_APELIDO:
			candidate.setNickname((String) aValue);
			break;
		case INDEX_IDADE:
			candidate.setAge(Integer.parseInt(aValue.toString()));
			break;
		case INDEX_SETOR:
			candidate.setSector((String) aValue);
			break;
		case INDEX_TEMPO:
			candidate.setServiceTime((String) aValue);
			break;
		}

		if (database.updateCandidate(currentNumber, candidate)) {
			candidates.remove(rowIndex);
			candidates.add(rowIndex, candidate);
			fireTableDataChanged();
		}
	}

	@Override
	public int getRowCount() {
		return candidates.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Candidate candidate = candidates.get(rowIndex);

		switch (columnIndex) {
		case INDEX_NUM:
			return candidate.getNumber();
		case INDEX_NOME:
			return candidate.getName();
		case INDEX_APELIDO:
			return candidate.getNickname();
		case INDEX_IDADE:
			return candidate.getAge();
		case INDEX_SETOR:
			return candidate.getSector();
		case INDEX_TEMPO:
			return candidate.getServiceTime();
		default:
			return new Object();
		}
	}

}
