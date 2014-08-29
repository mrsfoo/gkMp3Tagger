package com.zwb.mp3tag.profile.junit;

import java.sql.Timestamp;

import junit.framework.TestCase;

import com.zwb.mp3tag.profile.api.IMetadataPersister;
import com.zwb.mp3tag.profile.api.MetadataProviderFactory;

public class PropertyProviderTest extends TestCase
{
	public void testPropertyAccess()
	{
		IMetadataPersister w = MetadataProviderFactory.createMetadataPersister("TestData", "sample.properties", false);
		System.out.println(w.printFormatted());
		w.setValue("time", new Timestamp(System.currentTimeMillis()).toGMTString());
		w.setValue("key."+Integer.toString((int)(Math.random()*100)), Integer.toString((int)(Math.random()*100)));
		w.sort();
		System.out.println("------------------------------------");
		System.out.println(w.printFormatted());
	}
}
