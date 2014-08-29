package com.zwb.mp3tag.profile.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionFileIO;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionIllegalInput;
import com.zwb.mp3tag.profile.api.IMetadataPersister;
import com.zwb.tab.Tab;

public class MetadataPropertyPersister implements IMetadataPersister
{
	Properties props;
	File file;
	private static final String SIGNATURE_KEY = "signature";
	private static final String SIGNATURE_VALUE = "geekOlogy tagger profile (property format); last accessed";

	public static boolean isSimpleFormat(String folder, String filename)
	{
		return checkFormat(new File(folder, filename));
	}
	
	public static boolean isSimpleFormat(String path)
	{
		return checkFormat(new File(path));
	}
	
	private static boolean checkFormat(File f)
	{
		try
		{
			FileReader reader = new FileReader(f);
			Properties props = new Properties();
			props.load(reader);
			if(props.containsKey(SIGNATURE_KEY))
			{
				return props.get(SIGNATURE_KEY).toString().startsWith(SIGNATURE_VALUE);
			}
			else
			{
				return false;
			}
		}
		catch (IOException e)
		{
			return false;
		}
	}
	
	public MetadataPropertyPersister(File file)
	{
		try
		{
			if(file.exists() && !checkFormat(file))
			{
				throw new GkMp3TaggerRuntimeExceptionIllegalInput("persisted metadata not of correct format!");
			}
			this.file = file;
			init();		
		}
		catch (IOException e) 
		{
			throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <"+this.file.getAbsolutePath()+">", e);
		}
	}
	
	private void init() throws IOException
	{
		if(!file.exists())
		{
			file.createNewFile();
		}
		FileReader reader = new FileReader(this.file);
		this.props = new Properties();
		this.props.load(reader);
		this.setValue(SIGNATURE_KEY, SIGNATURE_VALUE+new Timestamp(System.currentTimeMillis()).toString());
	}
	
	public String getValue(String key)
	{
		String val = this.props.getProperty(key);
		if(val==null)
		{
			return "";
		}
		return val.trim();
	}
	
	public void setValue(String key, String value)
	{
		try 
		{
			FileOutputStream out = new FileOutputStream(this.file);
			this.props.setProperty(key, value);
			this.props.store(out, new Timestamp(System.currentTimeMillis()).toString());
			out.close();
		} 
		catch (IOException e) 
		{
			throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <"+this.file.getAbsolutePath()+">", e);
		}
		this.sort();
	}
	
	public void setValues(Map<String,String> properties)
	{
		try 
		{
			FileOutputStream out = new FileOutputStream(this.file);
			for(Entry<String, String> e: properties.entrySet())
			{
				this.props.setProperty(e.getKey(), e.getValue());				
			}
			this.props.store(out, new Timestamp(System.currentTimeMillis()).toString());
			out.close();
		} 
		catch (IOException e) 
		{
			throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <"+this.file.getAbsolutePath()+">", e);
		}
	}
	
	public String toString()
	{
		String s = "{";
		String comma = "";
		for(Entry<Object, Object> e: this.props.entrySet())
		{
			s += comma+e.getKey()+"="+e.getValue();
			comma = ",";
		}
		s += "}";
		return s;
	}
	
	public String printFormatted()
	{
		Tab tab = new Tab("","key","value");
		if(this.props.entrySet()!=null)
		{
			for(Entry<Object, Object> e: this.props.entrySet())
			{
				tab.addRow(e.getKey().toString(), e.getValue().toString());
			}
		}
		return tab.printFormatted();
	}
	
	public void sort()
	{
		Map<String,String> map = new TreeMap<>();
		Tab tab = new Tab("","","","");
		if(this.props.entrySet()!=null)
		{
			for(Entry<Object, Object> e: this.props.entrySet())
			{
				map.put(e.getKey().toString(), e.getValue().toString());
			}
		}
		if(this.props.entrySet()!=null)
		{
			for(Entry<String, String> e: map.entrySet())
			{
				tab.addRow(e.getKey(), "=", e.getValue());
			}
		}
		try 
		{
			FileWriter fw = new FileWriter(this.file);
			fw.write(tab.printHeadless());
			fw.close();
		}
		catch (IOException e) 
		{
			throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <"+this.file.getAbsolutePath()+">", e);
		}
	}

	@Override
	public void clear() 
	{
		try
		{
			this.file.delete();
			init();
		}
		catch (IOException e) 
		{
			throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <"+this.file.getAbsolutePath()+">", e);
		}
	}
	
	
	
}
