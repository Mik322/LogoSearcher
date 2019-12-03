package server.dealwith.comparator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class ResultsComparator implements Comparator<ArrayList<Point[]>> {

	@Override
	public int compare(ArrayList<Point[]> o1, ArrayList<Point[]> o2) {
		return o2.size()-o1.size();
	}

}
