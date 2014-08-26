package com.zwb.mp3tag.api;

public interface ITaggingTrackInfo extends Comparable<ITaggingTrackInfo>
{
	public int getTrackNo();
	public String getTrackName();
	public String getArtistName();
	public String getReleaseName();
}
