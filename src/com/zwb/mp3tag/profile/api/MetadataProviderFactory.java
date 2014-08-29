package com.zwb.mp3tag.profile.api;

import java.io.File;

import com.zwb.mp3tag.profile.impl.MetadataPropertyPersister;
import com.zwb.mp3tag.profile.impl.MetadataProvider;
import com.zwb.mp3tag.profile.impl.MetadataSimplePersister;

public class MetadataProviderFactory
{
	public static IMetadataPersister createMetadataPersister(String path, boolean simpleFormat)
	{
		File f = new File(path);
		if(simpleFormat)
		{
			return new MetadataSimplePersister(f);			
		}
		else
		{
			return new MetadataPropertyPersister(f);						
		}
	}
	
	public static IMetadataPersister createMetadataPersister(String foldername, String filename, boolean simpleFormat)
	{
		File f = new File(foldername, filename);
		return createMetadataPersister(f.getAbsolutePath(), simpleFormat);
	}

	public static IMetadataProvider createMetadataProvider(String path, boolean simpleFormat)
	{
		return new MetadataProvider(createMetadataPersister(path, simpleFormat));
	}

	public static IMetadataProvider createMetadataProvider(String foldername, String filename, boolean simpleFormat)
	{
		return new MetadataProvider(createMetadataPersister(foldername, filename, simpleFormat));
	}
	
	public static IMetadataPersister createMetadataPersister(String path)
	{
		File f = new File(path);
		boolean isSimpleFormat = true;
		if(f.exists())
		{
			isSimpleFormat = MetadataSimplePersister.isSimpleFormat(path);
		}
		return createMetadataPersister(path, isSimpleFormat);
	}

	public static IMetadataPersister createMetadataPersister(String foldername, String filename)
	{
		File f = new File(foldername, filename);
		return createMetadataPersister(f.getAbsolutePath());		
	}

	public static IMetadataProvider createMetadataProvider(String path)
	{
		return new MetadataProvider(createMetadataPersister(path));
	}

	public static IMetadataProvider createMetadataProvider(String foldername, String filename)
	{
		return new MetadataProvider(createMetadataPersister(foldername, filename));		
	}

}
