package com.zwb.mp3tag.profile.api;

import java.util.List;
import java.util.Map;

public interface ITaggingProfileSampler extends ITaggingProfile
{
	public void addTrack(String trackArtist, String trackTitle, int trackNo);
}
