package com.zwb.mp3tag.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zwb.mp3tag.api.ITaggingProfileAlbum;
import com.zwb.mp3tag.api.ITaggingTrackInfo;

public class TaggingProfileAlbum implements ITaggingProfileAlbum
{
	private List<ITaggingTrackInfo> tracks = new ArrayList<>();
	private String releaseName;
	private String artistName;
	
	public TaggingProfileAlbum(String artistName, String releaseName)
	{
		this.artistName = artistName;
		this.releaseName = releaseName;
	}
	
	public void addTrack(String trackTitle, int trackNo)
	{
		this.tracks.add(new TaggingTrackInfo(this.getArtistName(), this.getReleaseName(), trackTitle, trackNo));
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
	public List<ITaggingTrackInfo> getTrackInfos()
	{
		Collections.sort(this.tracks);
		return this.tracks;
	}

	@Override
	public void persist(String path)
	{
		// TODO Auto-generated method stub
	}

}
