package com.zwb.mp3tag.tagger.api;

import com.zwb.mp3tag.exception.GkMp3TaggerExceptionTagger;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionIllegalInput;
import com.zwb.mp3tag.profile.api.ITaggingProfileAlbum;

public interface IMp3Tagger 
{
	public void tag(String path, ITaggingProfileAlbum profile) throws GkMp3TaggerExceptionTagger;
}
