package com.zwb.mp3tag.profile.junit;

import java.sql.Timestamp;

import junit.framework.TestCase;

import com.zwb.mp3tag.profile.api.IMetadataPersister;
import com.zwb.mp3tag.profile.api.MetadataProviderFactory;
import com.zwb.mp3tag.profile.impl.AttributeKeys;
import com.zwb.mp3tag.profile.impl.MetadataSimplePersister;

public class SimpleProviderTest extends TestCase
{
	public void testReadWrite()
	{
		IMetadataPersister w = MetadataProviderFactory.createMetadataPersister("TestData", "sample2.properties", true);
		System.out.println(w.printFormatted());

		int trackCnt = (int) (Math.random()*10);
		int trackPrefix = (int) (Math.random()*1000);
		String aName = "artist ["+trackPrefix+"/"+trackCnt+"]/"+new Timestamp(System.currentTimeMillis())+"/"+(int)(Math.random()*100000);
		String rName = "release "+new Timestamp(System.currentTimeMillis())+"/"+(int)(Math.random()*100000);
		
		w.setValue(AttributeKeys.getKeyReleaseName(), rName);
		w.setValue(AttributeKeys.getKeyArtistName(), "FOOOOO");
		w.setValue(AttributeKeys.getKeyArtistName(), aName);
		System.err.println("setting "+trackCnt+" tracks with prefix "+trackPrefix);
		for(int i=1; i<trackCnt+1; i++)
		{
			String trackName = "("+i+")"+trackPrefix+"/"+new Timestamp(System.currentTimeMillis())+"/"+(int)(Math.random()*100000);
			String trackKey = AttributeKeys.getKeyTrackName(i);
			w.setValue(trackKey, trackName);
		}
		
		System.out.println("-----------------------------");
		System.out.println(w.printFormatted());

		w = MetadataProviderFactory.createMetadataPersister("TestData", "sample2.properties", true);
		System.out.println("------------------------------------");
		System.out.println(w.printFormatted());
}

}
