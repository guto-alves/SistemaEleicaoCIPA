package model;

public class Candidate {
	private int number;
	private String name;
	private String nickname;
	private int age;
	private String sector;
	private String serviceTime;
	private int votes;

	public Candidate() {
	}

	public Candidate(int number, String name, String nickname, int age, String sector, String serviceTime) {
		this(number, name, nickname, age, sector, serviceTime, 0);
	}

	public Candidate(int number, String name, String nickname, int age, String sector, String serviceTime, int votes) {
		this.number = number;
		this.name = name;
		this.nickname = nickname;
		this.age = age;
		this.sector = sector;
		this.serviceTime = serviceTime;
		this.votes = votes;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
}
