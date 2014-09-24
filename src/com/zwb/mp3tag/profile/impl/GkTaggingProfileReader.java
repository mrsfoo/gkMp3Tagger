package com.zwb.mp3tag.profile.impl;

import java.util.ArrayList;
import java.util.List;

import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeException;
import com.zwb.mp3tag.profile.api.IGkTaggingProfileReader;
import com.zwb.mp3tag.profile.api.IMetadataProvider;
import com.zwb.mp3tag.profile.api.ITaggingProfile;
import com.zwb.mp3tag.profile.api.MetadataProviderFactory;
import com.zwb.mp3tag.profile.api.ReleaseType;
import com.zwb.mp3tag.util.Config;

public class GkTaggingProfileReader implements IGkTaggingProfileReader
{
    private List<String> filterPatterns = new ArrayList<>();
    
    public ITaggingProfile read(String folder)
    {
	IMetadataProvider prov = MetadataProviderFactory.createMetadataProvider(folder, Config.METADATA_FILENAME);
	ReleaseType rt = prov.getReleaseType();
	if (ReleaseType.ALBUM.equals(rt))
	{
	    TaggingProfileAlbum pr = new TaggingProfileAlbum(filter(prov.getArtistName()), filter(prov.getReleaseName()));
	    List<String> tracks = prov.getTrackNames();
	    int i = 1;
	    for (String track : tracks)
	    {
		pr.addTrack(filter(track), i);
		i++;
	    }
	    return pr;
	}
	else if (ReleaseType.SAMPLER.equals(rt))
	{
	    TaggingProfileSampler pr = new TaggingProfileSampler(filter(prov.getReleaseName()));
	    List<String> tracks = prov.getTrackNames();
	    List<String> trackArtists = prov.getTrackArtists();
	    if (tracks.size() != trackArtists.size())
	    {
		throw new GkMp3TaggerRuntimeException("file crippled!");
	    }
	    for (int i = 0; i < tracks.size(); i++)
	    {
		pr.addTrack(filter(trackArtists.get(i)), filter(tracks.get(i)), i + 1);
	    }
	    return pr;
	}
	throw new RuntimeException("WTF!? THIS SHOULD NEVER HAPPEN!");
    }
    
    private String filter(String in)
    {
	String s = new String(in);
	for (String regex : this.filterPatterns)
	{
	    s = s.replaceAll(regex, "");
	}
	return s.trim();
    }
    
    @Override
    public void addFilterRegex(String regex)
    {
	this.filterPatterns.add(regex);
    }
    
    public String getMetaInfoFilename()
    {
	return Config.METADATA_FILENAME;
    }
    
}