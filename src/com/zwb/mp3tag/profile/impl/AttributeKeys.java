package com.zwb.mp3tag.profile.impl;

public class AttributeKeys 
{
	private static final String ARTIST_NAME = "release.artist";
	private static final String RELEASE_NAME = "release.name";
	private static final String TRACK_NAME_PREFIX = "track.#.name";
	
	public static String getKeyArtistName()
	{
		return ARTIST_NAME;
	}
	
	public static String getKeyReleaseName()
	{
		return RELEASE_NAME;
	}
	
	public static String getKeyTrackName(int trackNo)
	{
		String key = new String(TRACK_NAME_PREFIX);
		if(trackNo<10)
		{
			key = key.replaceAll("#", "0"+Integer.toString(trackNo));			
		}
		else
		{
			key = key.replaceAll("#", Integer.toString(trackNo));			
		}
		return key;
	}
	
	public static int extractTrackNo(String key)
	{
		String[] ex = key.split("\\.");
		return Integer.parseInt(ex[1]);
	}

	public static boolean matchesKeyTrackName(String key)
	{
		String regex = new String(TRACK_NAME_PREFIX);
		regex = regex.replaceAll("#", "[0-9][0-9]{0,1}");
		boolean match = key.matches(regex);
		return match;
	}
}
