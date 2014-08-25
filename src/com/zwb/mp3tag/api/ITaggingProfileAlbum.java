package com.zwb.mp3tag.api;

import java.util.List;

public interface ITaggingProfileAlbum 
{
	public String getArtistName();
	public String getReleaseName();
	public List<ITaggingTrackInfo> getTrackInfos();
	public void persist(String path);
}
