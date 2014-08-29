package com.zwb.mp3tag.profile.api;

import java.util.List;

import com.zwb.mp3tag.profile.impl.AttributeKeys;

public interface IMetadataProvider 
{
	public String getArtistName();
	public void setArtistName(String name);
	public String getReleaseName();
	public void setReleaseName(String name);
	public String getTrackName(int trackNo);
	public void setTrackName(int trackNo, String trackName);
	public List<String> getTrackNames();
	public void setTrackNames(List<String> tracks);
	public void clear();

}
