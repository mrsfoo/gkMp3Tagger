package com.zwb.mp3tag.tagger.impl;

import com.zwb.mp3tag.profile.api.ITaggingTrackInfo;

public class TaggingTrackInfo implements ITaggingTrackInfo, Comparable<ITaggingTrackInfo>
{

	private String artistName;
	private String releaseName;
	private String trackName;
	private int trackNo;

	public TaggingTrackInfo(String artistName, String releaseName, String trackName, int trackNo)
	{
		this.artistName = artistName;
		this.releaseName = releaseName;
		this.trackName = trackName;
		this.trackNo = trackNo;
	}
	
	@Override
	public String getArtistName() 
	{
		return this.artistName;
	}

	@Override
	public String getReleaseName() 
	{
		return this.releaseName;
	}
	
	@Override
	public int getTrackNo() 
	{
		return this.trackNo;
	}

	@Override
	public String getTrackName() 
	{
		return this.trackName;
	}

	@Override
	public int compareTo(ITaggingTrackInfo o) 
	{
		return this.getTrackNo()-o.getTrackNo();
	}

}
