package com.zwb.mp3tag.junit;

import junit.framework.TestCase;

import com.zwb.mp3tag.api.IMp3Tagger;
import com.zwb.mp3tag.exception.Mp3TaggerExceptionIllegalInput;
import com.zwb.mp3tag.impl.Mp3Tagger;

public class TaggerTest extends TestCase
{
	String path = "c:\\zwb\\geekOlogy\\bsp\\Jens Friebe, In Hypnose";
	public void testTagger() throws Mp3TaggerExceptionIllegalInput
	{
		IMp3Tagger tagger = new Mp3Tagger();
		tagger.tag(path, null);
		
	}
	
}
