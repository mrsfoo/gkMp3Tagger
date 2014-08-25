package com.zwb.mp3tag.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.zwb.mp3tag.api.IMp3Tagger;
import com.zwb.mp3tag.api.ITaggingProfileAlbum;
import com.zwb.mp3tag.exception.Mp3TaggerExceptionIllegalInput;

public class Mp3Tagger implements IMp3Tagger
{
	private File folder;
	
	@Override
	public void tag(String path, ITaggingProfileAlbum profile) throws Mp3TaggerExceptionIllegalInput 
	{
		this.folder = new File(path);
		List<File> filesToTag = this.checkFolder(folder, profile);
		System.out.println("---> "+filesToTag);
		System.out.println("---> ["+filesToTag.size()+"]");
		for(File f: filesToTag)
		{
			System.out.println("* "+f.getName());
		}
	}
	
	private List<File> checkFolder(File folder, ITaggingProfileAlbum profile) throws Mp3TaggerExceptionIllegalInput
	{
		if(profile==null)
		{
			throw new Mp3TaggerExceptionIllegalInput("the passed tagging profile is NULL!");
		}
		if(folder==null)
		{
			throw new Mp3TaggerExceptionIllegalInput("the folder <"+folder+"> is NULL!");
		}
		if(!folder.exists())
		{
			throw new Mp3TaggerExceptionIllegalInput("the folder <"+folder+"> doesn't exist!");
		}
		if(!folder.isDirectory())
		{
			throw new Mp3TaggerExceptionIllegalInput("the folder <"+folder+"> is not a folder!");
		}
		List<File> content = getMp3Files(folder);
		if(content.size()!=profile.getTrackInfos().size())
		{
			throw new Mp3TaggerExceptionIllegalInput("the folder <"+folder+"> doesn't contain the correct number of mp3 files!");
		}
		return content;
	}
	
	private List<File> getMp3Files(File folder)
	{
		List<File> l = Arrays.asList(folder.listFiles());
		List<File> result = new ArrayList<>();
	    for(File f: l)
		{
			if(f.isFile() && f.getName().endsWith(".mp3"))
			{
				result.add(f);
			}
		}
		Collections.sort(result);
		return result;
	}
	
}
