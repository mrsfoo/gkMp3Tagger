package com.zwb.mp3tag.tagger.api;

import com.zwb.mp3tag.profile.api.ITaggingProfileAlbum;
import com.zwb.mp3tag.profile.api.ITaggingProfileSampler;
import com.zwb.mp3tag.profile.impl.TaggingProfileAlbum;
import com.zwb.mp3tag.profile.impl.TaggingProfileSampler;
import com.zwb.mp3tag.tagger.impl.Mp3Tagger;

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
		
	public static ITaggingProfileSampler createTaggingProfileSampler(String releaseName)
	{
		return new TaggingProfileSampler(releaseName);
	}
		
}
