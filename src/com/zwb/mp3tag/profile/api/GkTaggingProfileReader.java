package com.zwb.mp3tag.profile.api;

import java.util.List;

import com.zwb.mp3tag.tagger.impl.TaggingProfileAlbum;
import com.zwb.mp3tag.util.Config;

public class GkTaggingProfileReader
{
	public static ITaggingProfileAlbum read(String folder)
	{
		IMetadataProvider prov = MetadataProviderFactory.createMetadataProvider(folder, Config.METADATA_FILENAME);
		TaggingProfileAlbum pr = new TaggingProfileAlbum(prov.getArtistName(), prov.getReleaseName());
		List<String> tracks = prov.getTrackNames();
		int i = 1;
		for(String track: tracks)
		{
			pr.addTrack(track, i);
			i++;
		}
		return pr;
	}

}