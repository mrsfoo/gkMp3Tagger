package com.zwb.mp3tag.exception;

public class GkMp3TaggerRuntimeException extends RuntimeException
{
	public GkMp3TaggerRuntimeException(String message)
	{
		super(message);
	}
	
	public GkMp3TaggerRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
