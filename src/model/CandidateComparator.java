package model;

import java.util.Comparator;

public class CandidateComparator implements Comparator<Candidate> {

	@Override
	public int compare(Candidate candidate1, Candidate candidate2) {
		return candidate2.getVotes() - candidate1.getVotes();
	}

}
