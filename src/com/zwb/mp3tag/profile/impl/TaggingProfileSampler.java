package com.zwb.mp3tag.profile.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zwb.mp3tag.profile.api.GkTaggingProfileReaderWriterFactory;
import com.zwb.mp3tag.profile.api.ITaggingProfileAlbum;
import com.zwb.mp3tag.profile.api.ITaggingProfileSampler;
import com.zwb.mp3tag.profile.api.ITaggingTrackInfo;
import com.zwb.mp3tag.profile.api.ReleaseType;
import com.zwb.mp3tag.tagger.impl.TaggingTrackInfo;
import com.zwb.tab.Tab;

public class TaggingProfileSampler implements ITaggingProfileSampler
{
	private List<ITaggingTrackInfo> tracks = new ArrayList<>();
	private String releaseName;
	
	public TaggingProfileSampler(String releaseName)
	{
		this.releaseName = releaseName;
	}
	
	public void addTrack(String trackArtist, String trackTitle, int trackNo)
	{
		this.tracks.add(new TaggingTrackInfo(trackArtist, this.getReleaseName(), trackTitle, trackNo));
	}
	
	@Override
	public String getReleaseName()
	{
		return this.releaseName;
	}

	@Override
	public List<ITaggingTrackInfo> getTrackInfos()
	{
		Collections.sort(this.tracks);
		return this.tracks;
	}

	@Override
	public void persist(String path, boolean simpleFormat)
	{
		GkTaggingProfileReaderWriterFactory.createWriter().write(this, path, simpleFormat);
	}

	@Override
	public String printFormatted() 
	{
		Tab tab = new Tab("tagging profile", "track no", "artist name", "release name", "track name");
		tab.setTableComment("release name  : "+getReleaseName());
		List<ITaggingTrackInfo> tracks = this.getTrackInfos();
		Collections.sort(tracks);
		tracks.forEach((ITaggingTrackInfo t) -> (tab.addRow(Integer.toString(t.getTrackNo()), t.getArtistName(), t.getReleaseName(), t.getTrackName())));
		return tab.printFormatted();
	}

	@Override
	public ReleaseType getReleaseType() 
	{
		return ReleaseType.SAMPLER;
	}

}
