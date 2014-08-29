package com.zwb.mp3tag.exception;

public class GkMp3TaggerRuntimeExceptionNotReadable extends GkMp3TaggerRuntimeExceptionFileIO
{
	public GkMp3TaggerRuntimeExceptionNotReadable(String message)
	{
		super(message);
	}
	
	public GkMp3TaggerRuntimeExceptionNotReadable(String message, Throwable cause)
	{
		super(message, cause);
	}
}
