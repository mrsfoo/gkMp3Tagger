package com.zwb.mp3tag.profile.api;

import java.util.List;
import java.util.Map;

public interface ITaggingProfileAlbum extends ITaggingProfile
{
	public String getArtistName();
	public void addTrack(String trackTitle, int trackNo);
}
