package com.zwb.mp3tag.profile.api;

public enum ReleaseType 
{
	ALBUM, SAMPLER;
	
	public String toString()
	{
		return super.toString();
	}
	
	public static ReleaseType stringToReleaseType(String type)
	{
		if(ReleaseType.ALBUM.toString().equals(type))
		{
			return ReleaseType.ALBUM;
		}
		else if(ReleaseType.SAMPLER.toString().equals(type))
		{
			return ReleaseType.SAMPLER;
		}
		return null;
	}
}
