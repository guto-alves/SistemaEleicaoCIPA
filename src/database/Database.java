package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Candidate;

public class Database {
	public static String DATABASE_NAME = "./src/database.db";

	private Connection connection;

	private PreparedStatement insertNewCandidate;
	private PreparedStatement updateCandidate;
	private PreparedStatement deleteCandidate;
	private PreparedStatement selectCandidateByNumber;
	private PreparedStatement selectAllCandidates;
	private PreparedStatement updateVoteFor;
	private PreparedStatement clearVotes;

	public Database() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);

			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS Candidatos ( "
					+ "numero INTEGER PRIMARY KEY NOT NULL UNIQUE, " + "nome TEXT NOT NULL, " + "apelido TEXT, "
					+ "idade INTEGER, " + "setor TEXT, " + "tempoServico TEXT, " + "votos INTEGER" + " );");

			insertNewCandidate = connection.prepareStatement(
					"INSERT INTO Candidatos (numero, nome, apelido, idade, setor, tempoServico, votos) VALUES (?, ?, ?, ?, ?, ?, ?)");

			selectAllCandidates = connection.prepareStatement("SELECT * FROM Candidatos");
			selectCandidateByNumber = connection.prepareStatement("SELECT * FROM Candidatos WHERE numero = ?");

			updateCandidate = connection.prepareStatement(
					"UPDATE Candidatos SET numero = ?, nome = ?, apelido = ?, idade = ?, setor = ?, tempoServico = ? WHERE numero = ?");

			deleteCandidate = connection.prepareStatement("DELETE FROM Candidatos WHERE numero = ?");

			updateVoteFor = connection.prepareStatement("UPDATE Candidatos SET votos = ? WHERE numero = ?");

			clearVotes = connection.prepareStatement("UPDATE Candidatos SET votos = 0");

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public boolean addCandidate(Candidate candidate) {
		try {
			insertNewCandidate.setInt(1, candidate.getNumber());
			insertNewCandidate.setString(2, candidate.getName());
			insertNewCandidate.setString(3, candidate.getNickname());
			insertNewCandidate.setInt(4, candidate.getAge());
			insertNewCandidate.setString(5, candidate.getSector());
			insertNewCandidate.setString(6, candidate.getServiceTime());
			insertNewCandidate.setInt(7, 0);
			insertNewCandidate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean updateCandidate(int currentNumber, Candidate candidate) {
		try {
			updateCandidate.setInt(1, candidate.getNumber());
			updateCandidate.setString(2, candidate.getName());
			updateCandidate.setString(3, candidate.getNickname());
			updateCandidate.setInt(4, candidate.getAge());
			updateCandidate.setString(5, candidate.getSector());
			updateCandidate.setString(6, candidate.getServiceTime());
			updateCandidate.setInt(7, currentNumber);
			updateCandidate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void deleteCandidate(Candidate candidate) {
		try {
			deleteCandidate.setInt(1, candidate.getNumber());
			deleteCandidate.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean voteFor(int numero) {
		Candidate candidate = getCandidateByNumber(numero);

		try {
			updateVoteFor.setInt(1, candidate.getVotes() + 1);
			updateVoteFor.setInt(2, numero);
			updateVoteFor.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void clearVotes() {
		try {
			clearVotes.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Candidate> getAllCandidates(boolean blankAndNull) {
		List<Candidate> candidates = new LinkedList<Candidate>();
		ResultSet resultSet = null;

		try {
			resultSet = selectAllCandidates.executeQuery();

			while (resultSet.next()) {
				int number = resultSet.getInt("numero");

				if (blankAndNull)
					candidates.add(new Candidate(number, resultSet.getString("nome"), resultSet.getString("apelido"),
							resultSet.getInt("idade"), resultSet.getString("setor"),
							resultSet.getString("tempoServico"), resultSet.getInt("votos")));
				else if (!isVoteBlankOrNull(number)) {
					candidates.add(new Candidate(number, resultSet.getString("nome"), resultSet.getString("apelido"),
							resultSet.getInt("idade"), resultSet.getString("setor"),
							resultSet.getString("tempoServico"), resultSet.getInt("votos")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return candidates;
	}

	private boolean isVoteBlankOrNull(int number) {
		return number == -1 || number == 0 ? true : false;
	}

	public Candidate getCandidateByNumber(int numero) {
		List<Candidate> candidatos = new LinkedList<Candidate>();
		ResultSet resultSet = null;

		try {
			selectCandidateByNumber.setInt(1, numero);
			resultSet = selectCandidateByNumber.executeQuery();

			while (resultSet.next()) {
				candidatos.add(new Candidate(resultSet.getInt("numero"), resultSet.getString("nome"),
						resultSet.getString("apelido"), resultSet.getInt("idade"), resultSet.getString("setor"),
						resultSet.getString("tempoServico"), resultSet.getInt("votos")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return candidatos.size() == 0 ? null : candidatos.get(0);
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
