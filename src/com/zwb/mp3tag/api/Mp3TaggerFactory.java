package com.zwb.mp3tag.api;

import com.zwb.mp3tag.impl.Mp3Tagger;
import com.zwb.mp3tag.impl.TaggingProfileAlbum;

public class Mp3TaggerFactory 
{
	public static IMp3Tagger createTagger()
	{
		return new Mp3Tagger();
	}
	
	public static ITaggingProfileAlbum createTaggingProfileAlbum(String artistName, String releaseName)
	{
		return new TaggingProfileAlbum(artistName, releaseName);
	}
		
}
