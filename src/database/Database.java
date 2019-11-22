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
	public static String DATABASE_NAME = "database.db";

	private Connection connection;

	private PreparedStatement insertNewCandidate;
	private PreparedStatement selectCandidateByNumber;
	private PreparedStatement selectAllCandidates;
	private PreparedStatement updateVoteFor;

	public Database() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);

			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS Candidatos ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "numero INTEGER NOT NULL, " + "nome TEXT NOT NULL, " + "apelido TEXT, " + "idade INTEGER, "
					+ "setor TEXT, " + "tempoServico TEXT, " + "votos INTEGER " + ");");

			insertNewCandidate = connection.prepareStatement(
					"INSERT INTO Candidatos (numero, nome, apelido, idade, setor, tempoServico, votos) VALUES (?, ?, ?, ?, ?, ?, ?)");

			selectAllCandidates = connection.prepareStatement("SELECT * FROM Candidatos");
			selectCandidateByNumber = connection.prepareStatement("SELECT * FROM Candidatos WHERE numero = ?");

			updateVoteFor = connection.prepareStatement("UPDATE Candidatos SET votos = ? WHERE numero = ?");

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void addCandidate(int number, String name, String nickname, int age, String sector, String serviceTime) {
		try {
			insertNewCandidate.setInt(1, number);
			insertNewCandidate.setString(2, name);
			insertNewCandidate.setString(3, nickname);
			insertNewCandidate.setInt(4, age);
			insertNewCandidate.setString(5, sector);
			insertNewCandidate.setString(6, serviceTime);
			insertNewCandidate.setInt(7, 0);
			insertNewCandidate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Candidate> getAllCandidates() {
		List<Candidate> candidates = new LinkedList<Candidate>();
		ResultSet resultSet = null;

		try {
			resultSet = selectAllCandidates.executeQuery();

			while (resultSet.next()) {
				candidates.add(new Candidate(resultSet.getInt("id"), resultSet.getInt("numero"),
						resultSet.getString("nome"), resultSet.getString("apelido"), resultSet.getInt("idade"),
						resultSet.getString("setor"), resultSet.getString("tempoServico"), resultSet.getInt("votos")));
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

	public Candidate getCandidateByNumber(int numero) {
		List<Candidate> candidatos = new LinkedList<Candidate>();
		ResultSet resultSet = null;

		try {
			selectCandidateByNumber.setInt(1, numero);
			resultSet = selectCandidateByNumber.executeQuery();

			while (resultSet.next()) {
				candidatos.add(new Candidate(resultSet.getInt("id"), resultSet.getInt("numero"),
						resultSet.getString("nome"), resultSet.getString("apelido"), resultSet.getInt("idade"),
						resultSet.getString("setor"), resultSet.getString("tempoServico"), resultSet.getInt("votos")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return candidatos.get(0);
	}

	public void voteFor(int numero) {
		Candidate candidate = getCandidateByNumber(numero);

		try {
			updateVoteFor.setInt(1, candidate.getVotes() + 1);
			updateVoteFor.setInt(2, numero);
			updateVoteFor.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
