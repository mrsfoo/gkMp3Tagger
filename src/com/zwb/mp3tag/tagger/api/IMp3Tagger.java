package com.zwb.mp3tag.tagger.api;

import com.zwb.mp3tag.exception.GkMp3TaggerExceptionTagger;
import com.zwb.mp3tag.profile.api.ITaggingProfile;

public interface IMp3Tagger 
{
	public void tag(String path, ITaggingProfile profile) throws GkMp3TaggerExceptionTagger;
}
