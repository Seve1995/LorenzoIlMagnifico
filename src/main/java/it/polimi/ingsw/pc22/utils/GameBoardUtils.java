package it.polimi.ingsw.pc22.utils;

import java.util.HashMap;
import java.util.Map;

public class GameBoardUtils {
	
	private static final Map<Integer, Integer> fromFaithToVictory = new HashMap<Integer, Integer>();
	
	static
	{
		
		fromFaithToVictory.put(0, 0);
		fromFaithToVictory.put(1, 1);
		fromFaithToVictory.put(2, 2);
		fromFaithToVictory.put(3, 3);
		fromFaithToVictory.put(4, 4);
		fromFaithToVictory.put(5, 5);
		fromFaithToVictory.put(6, 7);
		fromFaithToVictory.put(7, 9);
		fromFaithToVictory.put(8, 11);
		fromFaithToVictory.put(9, 13);
		fromFaithToVictory.put(10, 15);
		fromFaithToVictory.put(11, 17);
		fromFaithToVictory.put(12, 19);
		fromFaithToVictory.put(13, 22);
		fromFaithToVictory.put(14, 25);

	}

	public static int CalculateVictoryPointsForFaithPoints(int faithPoint)
	{
		
		if (faithPoint > 14)
			return 25;
		
		return fromFaithToVictory.get(faithPoint);

	}

}
