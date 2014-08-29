package com.zwb.mp3tag.profile.api;

import java.util.List;
import java.util.Map;

public interface ITaggingProfileAlbum 
{
	public String getArtistName();
	public String getReleaseName();
	public List<ITaggingTrackInfo> getTrackInfos();
	public void persist(String path, boolean simpleFormat);	
	public String printFormatted();
}
