package com.zwb.mp3tag.profile.api;

public interface ITaggingTrackInfo extends Comparable<ITaggingTrackInfo>
{
	public int getTrackNo();
	public String getTrackName();
	public String getArtistName();
	public String getReleaseName();
}
