package com.zwb.mp3tag.profile.impl;

import java.util.List;

import com.zwb.mp3tag.profile.api.IGkTaggingProfileWriter;
import com.zwb.mp3tag.profile.api.IMetadataProvider;
import com.zwb.mp3tag.profile.api.ITaggingProfile;
import com.zwb.mp3tag.profile.api.ITaggingProfileAlbum;
import com.zwb.mp3tag.profile.api.ITaggingProfileSampler;
import com.zwb.mp3tag.profile.api.ITaggingTrackInfo;
import com.zwb.mp3tag.profile.api.MetadataProviderFactory;
import com.zwb.mp3tag.profile.api.ReleaseType;
import com.zwb.mp3tag.util.Config;

public class GkTaggingProfileWriter implements IGkTaggingProfileWriter
{
    public void write(ITaggingProfile profile, String path, boolean simpleFormat)
    {
	IMetadataProvider prov = MetadataProviderFactory.createMetadataProvider(path, Config.METADATA_FILENAME, simpleFormat);
	prov.clear();
	prov.setReleaseName(profile.getReleaseName());
	List<ITaggingTrackInfo> tracks = profile.getTrackInfos();
	tracks.forEach((ITaggingTrackInfo t) -> (prov.setTrackName(t.getTrackNo(), t.getTrackName())));
	prov.setReleaseType(profile.getReleaseType());
	
	if (profile.getReleaseType().equals(ReleaseType.ALBUM))
	{
	    writeAlbum((ITaggingProfileAlbum) profile, prov);
	}
	else if (profile.getReleaseType().equals(ReleaseType.SAMPLER))
	{
	    writeSampler((ITaggingProfileSampler) profile, prov);
	}
	else
	{
	    throw new RuntimeException("WTF!? THIS SHOULD NEVER HAPPEN!");
	}
    }
    
    private static void writeAlbum(ITaggingProfileAlbum profile, IMetadataProvider provider)
    {
	provider.setArtistName(profile.getArtistName());
    }
    
    private static void writeSampler(ITaggingProfileSampler profile, IMetadataProvider provider)
    {
	List<ITaggingTrackInfo> tracks = profile.getTrackInfos();
	tracks.forEach((ITaggingTrackInfo t) -> (provider.setTrackArtist(t.getTrackNo(), t.getArtistName())));
    }
    
    public String getMetaInfoFilename()
    {
	return Config.METADATA_FILENAME;
    }
    
}
