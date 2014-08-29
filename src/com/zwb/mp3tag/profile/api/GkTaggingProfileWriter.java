package com.zwb.mp3tag.profile.api;

import java.util.List;

import com.zwb.mp3tag.util.Config;


public class GkTaggingProfileWriter
{

	public static void write(ITaggingProfileAlbum profile, String path, boolean simpleFormat) 
	{
		IMetadataProvider prov = MetadataProviderFactory.createMetadataProvider(path, Config.METADATA_FILENAME, simpleFormat);
		prov.clear();
		prov.setArtistName(profile.getArtistName());
		prov.setReleaseName(profile.getReleaseName());
		List<ITaggingTrackInfo> tracks = profile.getTrackInfos();
		tracks.forEach((ITaggingTrackInfo t) -> (prov.setTrackName(t.getTrackNo(), t.getTrackName())));
	}

}
