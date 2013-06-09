package org.javafling.pokerenlighter.simulation;

import org.javafling.pokerenlighter.combination.Card;

/**
 * Provides export functionality for simulation results.
 * 
 * @author Radu Murzea
 * 
 * @version 1.0
 */
public class SimulationExport
{
	private static final String LINE_END = System.lineSeparator ();
	
	/**
	 * Constructs a XML representation of the simulation result and returns it in the form of
	 * a String.
	 * 
	 * @param result the result of the simulation that must be converted to XML.
	 * 
	 * @return a String containing an XML representation of the provided result. If the result
	 * is null, then an empty String is returned.
	 */
	public static String getResultXMLString (SimulationFinalResult result)
	{
		if (result == null)
		{
			return "";
		}
		
		StringBuilder xml = new StringBuilder ();
		
		xml.append ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>");
		xml.append (LINE_END);
		xml.append ("<simulation>");
		xml.append (LINE_END);
		xml.append ("<meta>");
		xml.append (LINE_END);
		xml.append ("<playercount>");
		xml.append (result.getNrOfPlayers ());
		xml.append ("</playercount>");
		xml.append (LINE_END);
		xml.append ("<rounds>");
		xml.append (result.getRounds ());
		xml.append ("</rounds>");
		xml.append (LINE_END);
		xml.append ("<type>");
		xml.append (result.getPokerType ());
		xml.append ("</type>");
		xml.append (LINE_END);
		xml.append ("</meta>");
		xml.append (LINE_END);
		xml.append ("<players>");
		xml.append (LINE_END);
		for (int i = 1; i <= result.getNrOfPlayers (); i++)
		{
			xml.append ("<player id=\"");
			xml.append (i);
			xml.append ("\">");
			xml.append (LINE_END);
			xml.append ("<handtype>");
			PlayerProfile profile = result.getPlayer (i - 1);
			if (profile.getHandType () == HandType.RANDOM)
			{
				xml.append ("random");
				xml.append ("</handtype>");
				xml.append (LINE_END);
			}
			else if (profile.getHandType () == HandType.EXACTCARDS)
			{
				xml.append ("cards");
				xml.append ("</handtype>");
				xml.append (LINE_END);
				Card[] cards = profile.getCards ();
				for (Card card : cards)
				{
					xml.append ("<card>");
					xml.append (card.toString ());
					xml.append ("</card>");
					xml.append (LINE_END);
				}
			}
			else if (profile.getHandType () == HandType.RANGE)
			{
				xml.append ("range");
				xml.append ("</handtype>");
				xml.append (LINE_END);
				Range range = profile.getRange ();
				for (int row = 0; row < 13; row++)
				{
					for (int col = 0; col < 13; col++)
					{
						if (range.getValue (row, col))
						{
							xml.append ("<cardtype>");
							xml.append (Range.rangeNames[row][col]);
							xml.append ("</cardtype>");
							xml.append (LINE_END);
						}
					}
				}
			}
			
			xml.append ("</player>");
			xml.append (LINE_END);
		}
		
		xml.append ("</players>");
		xml.append (LINE_END);
		
		xml.append ("<result>");
		xml.append (LINE_END);
		xml.append ("<threads>");
		xml.append (result.getNrOfThreads ());
		xml.append ("</threads>");
		xml.append (LINE_END);
		xml.append ("<duration>");
		xml.append (result.getDuration ());
		xml.append ("</duration>");
		xml.append (LINE_END);
		
		for (int i = 1; i <= result.getNrOfPlayers (); i++)
		{
			xml.append ("<player id=\"");
			xml.append (i);
			xml.append ("\">");
			xml.append (LINE_END);
			xml.append ("<wins>");
			xml.append (result.getFormattedWinPercentage (i - 1));
			xml.append ("</wins>");
			xml.append (LINE_END);
			xml.append ("<loses>");
			xml.append (result.getFormattedLosePercentage (i - 1));
			xml.append ("</loses>");
			xml.append (LINE_END);
			xml.append ("<ties>");
			xml.append (result.getFormattedTiePercentage (i - 1));
			xml.append ("</ties>");
			xml.append (LINE_END);
			xml.append ("</player>");
		}
		
		xml.append (LINE_END);
		xml.append ("</result>");
		xml.append (LINE_END);
		xml.append ("</simulation>");
		
		return xml.toString ();
	}
}