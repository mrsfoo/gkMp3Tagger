package com.zwb.mp3tag.api;

import com.zwb.mp3tag.exception.Mp3TaggerExceptionIllegalInput;

public interface IMp3Tagger 
{
	public void tag(String path, ITaggingProfileAlbum profile) throws Mp3TaggerExceptionIllegalInput;
	
}
