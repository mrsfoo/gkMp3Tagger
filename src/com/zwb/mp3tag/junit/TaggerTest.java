package com.zwb.mp3tag.junit;

import junit.framework.TestCase;

import com.zwb.mp3tag.api.IMp3Tagger;
import com.zwb.mp3tag.exception.GkMp3TaggerExceptionIllegalInput;
import com.zwb.mp3tag.impl.Mp3Tagger;

public class TaggerTest extends TestCase
{
	String path = "c:\\zwb\\geekOlogy\\bsp\\Jens Friebe, In Hypnose";
	public void testTagger() throws GkMp3TaggerExceptionIllegalInput
	{
		IMp3Tagger tagger = new Mp3Tagger();
		tagger.tag(path, null);
		
	}
	
}
