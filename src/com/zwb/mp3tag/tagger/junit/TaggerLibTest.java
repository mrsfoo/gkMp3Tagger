package com.zwb.mp3tag.tagger.junit;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

import junit.framework.TestCase;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;

public class TaggerLibTest extends TestCase
{
	public void test() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException
	{
		File testFile = new File("c:\\zwb\\geekOlogy\\gkMp3Tagger\\testdata\\mp3.mp3");
		AudioFile af = AudioFileIO.read(testFile);
		Tag tag = af.getTag();

		String artist = "artist::" + new Timestamp(System.currentTimeMillis()) +"/"+ createInt();
		String album = "album::" + new Timestamp(System.currentTimeMillis()) +"/"+ createInt();
		String title = "title::" + new Timestamp(System.currentTimeMillis()) +"/"+ createInt();
		String track = Integer.toString(createInt());

		System.out.println("-> "+FieldKey.ARTIST+": "+tag.getAll(FieldKey.ARTIST));
		System.out.println("-> "+FieldKey.ALBUM+": "+tag.getAll(FieldKey.ALBUM));
		System.out.println("-> "+FieldKey.TITLE+": "+tag.getAll(FieldKey.TITLE));
		System.out.println("-> "+FieldKey.TRACK+": "+tag.getAll(FieldKey.TRACK));
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		tag.setField(FieldKey.ARTIST, artist);
		tag.setField(FieldKey.ALBUM, album);
		tag.setField(FieldKey.TITLE, title);
		tag.setField(FieldKey.TRACK, track);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("-> "+FieldKey.ARTIST+": "+tag.getAll(FieldKey.ARTIST));
		System.out.println("-> "+FieldKey.ALBUM+": "+tag.getAll(FieldKey.ALBUM));
		System.out.println("-> "+FieldKey.TITLE+": "+tag.getAll(FieldKey.TITLE));
		System.out.println("-> "+FieldKey.TRACK+": "+tag.getAll(FieldKey.TRACK));

		af.commit();
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("-> "+FieldKey.ARTIST+": "+tag.getAll(FieldKey.ARTIST));
		System.out.println("-> "+FieldKey.ALBUM+": "+tag.getAll(FieldKey.ALBUM));
		System.out.println("-> "+FieldKey.TITLE+": "+tag.getAll(FieldKey.TITLE));
		System.out.println("-> "+FieldKey.TRACK+": "+tag.getAll(FieldKey.TRACK));
		
		assertEquals(tag.getAll(FieldKey.ARTIST).get(0), artist);
		assertEquals(tag.getAll(FieldKey.ALBUM).get(0), album);
		assertEquals(tag.getAll(FieldKey.TITLE).get(0), title);
		assertEquals(tag.getAll(FieldKey.TRACK).get(0), track);
		

	}
	
	private int createInt()
	{
		return (int)(Math.random()*10);
	}
}