package com.zwb.mp3tag.profile.impl;

import java.util.ArrayList;
import java.util.List;

import com.zwb.mp3tag.profile.api.IMetadataPersister;
import com.zwb.mp3tag.profile.api.IMetadataProvider;

public class MetadataProvider implements IMetadataProvider
{
	IMetadataPersister provider;
	
	public MetadataProvider(IMetadataPersister provider)
	{
		this.provider = provider;
	}

	public String getArtistName()
	{
		return provider.getValue(AttributeKeys.getKeyArtistName());
	}
	
	public void setArtistName(String name)
	{
		provider.setValue(AttributeKeys.getKeyArtistName(), name);
	}

	public String getReleaseName()
	{
		return provider.getValue(AttributeKeys.getKeyReleaseName());
	}
	
	public void setReleaseName(String name)
	{
		provider.setValue(AttributeKeys.getKeyReleaseName(), name);
	}
	
	public String getTrackName(int trackNo)
	{
		return provider.getValue(AttributeKeys.getKeyTrackName(trackNo));
	}
	
	public void setTrackName(int trackNo, String trackName)
	{
		provider.setValue(AttributeKeys.getKeyTrackName(trackNo), trackName);
	}

	@Override
	public List<String> getTrackNames() 
	{
		List<String> tracks = new ArrayList<String>();
		for(int i=1;;i++)
		{
			String track = getTrackName(i);
			if((track==null)||track.isEmpty())
			{
				break;
			}
			else
			{
				tracks.add(track);
			}
		}
		return tracks;
	}

	@Override
	public void setTrackNames(List<String> tracks)
	{
		for(int i=0; i<tracks.size(); i++)
		{
			this.setTrackName(i+1, tracks.get(i));
		}
	}

	@Override
	public void clear() 
	{
		this.provider.clear();
	}

}
