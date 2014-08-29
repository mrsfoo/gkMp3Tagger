package com.zwb.mp3tag.exception;

public class GkMp3TaggerRuntimeExceptionNotWritable extends GkMp3TaggerRuntimeExceptionFileIO
{
	public GkMp3TaggerRuntimeExceptionNotWritable(String message)
	{
		super(message);
	}
	
	public GkMp3TaggerRuntimeExceptionNotWritable(String message, Throwable cause)
	{
		super(message, cause);
	}
}
