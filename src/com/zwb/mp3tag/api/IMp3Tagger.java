package com.zwb.mp3tag.api;

import com.zwb.mp3tag.exception.GkMp3TaggerExceptionIllegalInput;

public interface IMp3Tagger 
{
	public void tag(String path, ITaggingProfileAlbum profile) throws GkMp3TaggerExceptionIllegalInput;
}
