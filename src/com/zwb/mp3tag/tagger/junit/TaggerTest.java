package com.zwb.mp3tag.tagger.junit;

import junit.framework.TestCase;

import com.zwb.mp3tag.exception.GkMp3TaggerExceptionTagger;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionIllegalInput;
import com.zwb.mp3tag.tagger.api.IMp3Tagger;
import com.zwb.mp3tag.tagger.impl.Mp3Tagger;

public class TaggerTest extends TestCase
{
	String path = "c:\\zwb\\geekOlogy\\bsp\\Jens Friebe, In Hypnose";
	public void testTagger() throws GkMp3TaggerExceptionTagger
	{
		IMp3Tagger tagger = new Mp3Tagger();
		tagger.tag(path, null);
	}
	
}
