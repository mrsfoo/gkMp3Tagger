package com.zwb.mp3tag.tagger.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.zwb.mp3tag.exception.GkMp3TaggerExceptionTagger;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionIllegalInput;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionNotReadable;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionNotWritable;
import com.zwb.mp3tag.profile.api.ITaggingProfile;
import com.zwb.mp3tag.profile.api.ITaggingProfileAlbum;
import com.zwb.mp3tag.tagger.api.IMp3Tagger;
import com.zwb.mp3tag.util.MyLogger;
import com.zwb.tab.Tab;

public class Mp3Tagger implements IMp3Tagger
{
	private File folder;
	private MyLogger log = new MyLogger(this.getClass());
	
	@Override
	public void tag(String path, ITaggingProfile profile) throws GkMp3TaggerExceptionTagger 
	{
		this.folder = new File(path);
		List<File> filesToTag = this.checkFolder(folder, profile);
		Tab tab = new Tab("files to tag", "#" , "filename");
		tab.setTableComment("files to tag in folder <"+folder+"> (count=<"+filesToTag.size()+">)");
		int i = 1;
		for(File f: filesToTag)
		{
			tab.addRow(Integer.toString(i), f.getName());
			i++;
		}
		
		log.debug("FILES TO TAG:\n"+tab.printFormatted());
		log.debug("TAGGING PROFILE:\n"+profile.printFormatted());
		
		tagFiles(filesToTag, profile);
	}
	
	private void tagFiles(List<File> files, ITaggingProfile profile) throws GkMp3TaggerExceptionTagger
	{
		log.debug("beginning with tagging of files: "+files);
		int total = files.size();
		for(int i=0; i<total; i++)
		{
			File file = files.get(i);
			log.debug("now tagging file: "+file.getAbsolutePath());
			try 
			{
				AudioFile mp3 = AudioFileIO.read(file);
				Tag tag = mp3.getTag();
				tag.setField(FieldKey.ARTIST, profile.getTrackInfos().get(i).getArtistName());
				tag.setField(FieldKey.ALBUM, profile.getTrackInfos().get(i).getReleaseName());
				tag.setField(FieldKey.TITLE, profile.getTrackInfos().get(i).getTrackName());
				tag.setField(FieldKey.TRACK, Integer.toString(profile.getTrackInfos().get(i).getTrackNo()));
				tag.setField(FieldKey.TRACK_TOTAL, Integer.toString(total));
				mp3.commit();
			} 
			catch (CannotReadException e) 
			{
				throw new GkMp3TaggerRuntimeExceptionNotReadable("file <"+file.getName()+"> not readable!", e);
			} 
			catch (IOException e) 
			{
				throw new GkMp3TaggerRuntimeExceptionIllegalInput("general IO exception in file <"+file.getName()+">!", e);
			} 
			catch (ReadOnlyFileException | CannotWriteException e) 
			{
				throw new GkMp3TaggerRuntimeExceptionNotWritable("file <"+file.getName()+"> not writable!", e);
			} 
			catch (InvalidAudioFrameException | TagException e) 
			{
				throw new GkMp3TaggerExceptionTagger("tagger exception in file <"+file.getName()+">!", e);
			} 
		}
	}
	
	private List<File> checkFolder(File folder, ITaggingProfile profile) throws GkMp3TaggerRuntimeExceptionIllegalInput
	{
		if(profile==null)
		{
			throw new GkMp3TaggerRuntimeExceptionIllegalInput("the passed tagging profile is NULL!");
		}
		if(folder==null)
		{
			throw new GkMp3TaggerRuntimeExceptionIllegalInput("the folder <"+folder+"> is NULL!");
		}
		if(!folder.exists())
		{
			throw new GkMp3TaggerRuntimeExceptionIllegalInput("the folder <"+folder+"> doesn't exist!");
		}
		if(!folder.isDirectory())
		{
			throw new GkMp3TaggerRuntimeExceptionIllegalInput("the folder <"+folder+"> is not a folder!");
		}
		List<File> content = getMp3Files(folder);
		if(content.size()!=profile.getTrackInfos().size())
		{
			throw new GkMp3TaggerRuntimeExceptionIllegalInput("the folder <"+folder+"> doesn't contain the correct number of mp3 files!");
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
