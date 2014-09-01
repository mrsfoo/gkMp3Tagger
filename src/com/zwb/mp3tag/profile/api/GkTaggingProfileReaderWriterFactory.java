package com.zwb.mp3tag.profile.api;

import com.zwb.mp3tag.profile.impl.GkTaggingProfileReader;
import com.zwb.mp3tag.profile.impl.GkTaggingProfileWriter;

public class GkTaggingProfileReaderWriterFactory
{
	public static IGkTaggingProfileReader createReader()
	{
		return new GkTaggingProfileReader();
	}
	
	public static IGkTaggingProfileWriter createWriter()
	{
		return new GkTaggingProfileWriter();
	}
		
}
