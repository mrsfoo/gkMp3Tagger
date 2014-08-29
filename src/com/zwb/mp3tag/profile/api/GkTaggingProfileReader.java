package com.zwb.mp3tag.profile.api;

import java.util.List;

import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeException;
import com.zwb.mp3tag.profile.impl.TaggingProfileAlbum;
import com.zwb.mp3tag.profile.impl.TaggingProfileSampler;
import com.zwb.mp3tag.util.Config;

public class GkTaggingProfileReader
{
	public static ITaggingProfile read(String folder)
	{
		IMetadataProvider prov = MetadataProviderFactory.createMetadataProvider(folder, Config.METADATA_FILENAME);
		ReleaseType rt = prov.getReleaseType();
		if(ReleaseType.ALBUM.equals(rt))
		{
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
		else if(ReleaseType.SAMPLER.equals(rt))
		{
			TaggingProfileSampler pr = new TaggingProfileSampler(prov.getReleaseName());
			List<String> tracks = prov.getTrackNames();
			List<String> trackArtists = prov.getTrackArtists();
			if(tracks.size()!=trackArtists.size())
			{
				throw new GkMp3TaggerRuntimeException("file crippled!");
			}
			for(int i=0; i<tracks.size(); i++)
			{
				pr.addTrack(trackArtists.get(i), tracks.get(i), i+1);
			}
			return pr;			
		}
		throw new RuntimeException("WTF!? THIS SHOULD NEVER HAPPEN!");
	}

}