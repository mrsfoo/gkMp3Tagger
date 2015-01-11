package com.zwb.mp3tag.profile.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zwb.lazyload.ILoader;
import com.zwb.lazyload.LazyLoader;
import com.zwb.lazyload.Ptr;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionFileIO;
import com.zwb.mp3tag.exception.GkMp3TaggerRuntimeExceptionIllegalInput;
import com.zwb.mp3tag.profile.api.IMetadataPersister;
import com.zwb.mp3tag.profile.api.ReleaseType;
import com.zwb.tab.Tab;

public class MetadataSimplePersister implements IMetadataPersister
{
    private static final int POS_SIG_NAME = 0;
    private static final int POS_RELEASE_TYPE = 1;
    private static final int POS_ARTIST_NAME = 2;
    private static final int POS_RELEASE_NAME = 3;
    private static final int POS_TRACKS = 4;
    private static final String TRACK_SEPARATOR = "////";
    
    private static final String SIGNATURE = "# geekOlogy tagger profile (simple format); last accessed ";
    
    private File file;
    private Ptr<String> artistName = new Ptr<>();
    private Ptr<String> releaseName = new Ptr<>();
    private Ptr<String> releaseType = new Ptr<>();
    private Ptr<List<String>> trackNames = new Ptr<>();
    private Ptr<List<String>> trackArtists = new Ptr<>();
    private int trackTotal = 0;
    
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
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
	    String firstLine = br.readLine();
	    br.close();
	    return firstLine.startsWith(SIGNATURE);
	}
	catch (IOException e)
	{
	    return false;
	}
    }
    
    public MetadataSimplePersister(File file)
    {
	try
	{
	    if (file.exists() && !checkFormat(file))
	    {
		throw new GkMp3TaggerRuntimeExceptionIllegalInput("persisted metadata not of correct format!");
	    }
	    this.file = file;
	    init();
	}
	catch (IOException e)
	{
	    throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + this.file.getAbsolutePath() + ">", e);
	}
    }
    
    private void init() throws IOException
    {
	if (!this.file.exists())
	{
	    this.file.createNewFile();
	}
	this.writeNewLine(POS_SIG_NAME, SIGNATURE + new Timestamp(System.currentTimeMillis()));
    }
    
    @Override
    public String getValue(String key)
    {
	if (key.equals(AttributeKeys.getKeyArtistName()))
	{
	    return LazyLoader.loadLazy(this.artistName, new ArtistNameLoader());
	}
	else if (key.equals(AttributeKeys.getKeyReleaseType()))
	{
	    return LazyLoader.loadLazy(this.releaseType, new ReleaseTypeLoader());
	}
	else if (key.equals(AttributeKeys.getKeyReleaseName()))
	{
	    return LazyLoader.loadLazy(this.releaseName, new ReleaseNameLoader());
	}
	else if (AttributeKeys.matchesKeyTrackName(key))
	{
	    int i = AttributeKeys.extractTrackNo(key) - 1;
	    if (getTracks().size() < i + 1)
	    {
		return "";
	    }
	    return getTracks().get(i).toString();
	}
	else if (AttributeKeys.matchesKeyTrackArtist(key))
	{
	    int i = AttributeKeys.extractTrackNo(key) - 1;
	    if (getTrackArtists().size() < i + 1)
	    {
		return "";
	    }
	    return getTrackArtists().get(i).toString();
	}
	return null;
    }
    
    public List<String> getTracks()
    {
	return LazyLoader.loadLazy(this.trackNames, new TrackNamesLoader());
    }
    
    public List<String> getTrackArtists()
    {
	return LazyLoader.loadLazy(this.trackArtists, new TrackArtistLoader());
    }
    
    @Override
    public void setValue(String key, String value)
    {
	if (key.equals(AttributeKeys.getKeyArtistName()))
	{
	    this.writeNewLine(POS_ARTIST_NAME, value);
	    this.artistName.setValue(null);
	}
	else if (key.equals(AttributeKeys.getKeyReleaseName()))
	{
	    this.writeNewLine(POS_RELEASE_NAME, value);
	    this.releaseName.setValue(null);
	}
	else if (key.equals(AttributeKeys.getKeyReleaseType()))
	{
	    this.writeNewLine(POS_RELEASE_TYPE, value);
	    this.releaseType.setValue(null);
	}
	else if (AttributeKeys.matchesKeyTrackName(key))
	{
	    int i = AttributeKeys.extractTrackNo(key);
	    if (isReleaseType(ReleaseType.ALBUM))
	    {
		this.writeNewLine(POS_TRACKS + i - 1, value);
		this.trackNames.setValue(null);
	    }
	    else if (isReleaseType(ReleaseType.SAMPLER))
	    {
		String trackArtist = this.getValue(AttributeKeys.getKeyTrackArtist(i));
		if (trackArtist == null)
		{
		    trackArtist = "";
		}
		this.writeNewLine(POS_TRACKS + i - 1, trackArtist + TRACK_SEPARATOR + value);
		this.trackArtists.setValue(null);
	    }
	}
	else if (AttributeKeys.matchesKeyTrackArtist(key))
	{
	    int i = AttributeKeys.extractTrackNo(key);
	    if (isReleaseType(ReleaseType.ALBUM))
	    {
		throw new GkMp3TaggerRuntimeExceptionIllegalInput("normal albums don't have different artists for tracks!");
	    }
	    else if (isReleaseType(ReleaseType.SAMPLER))
	    {
		String trackName = this.getValue(AttributeKeys.getKeyTrackName(i));
		if (trackName == null)
		{
		    trackName = "";
		}
		this.writeNewLine(POS_TRACKS + i - 1, value + TRACK_SEPARATOR + trackName);
		this.trackArtists.setValue(null);
	    }
	}
    }
    
    private boolean isReleaseType(ReleaseType t)
    {
	return t.toString().equals(this.getValue(AttributeKeys.getKeyReleaseType()));
    }
    
    @Override
    public void setValues(Map<String, String> values)
    {
	for (Entry<String, String> e : values.entrySet())
	{
	    this.setValue(e.getKey(), e.getValue());
	}
    }
    
    @Override
    public String printFormatted()
    {
	String artist = getValue(AttributeKeys.getKeyArtistName());
	String release = getValue(AttributeKeys.getKeyReleaseName());
	List<String> tracks = getTracks();
	Tab tab = new Tab("", "key", "value");
	tab.addRow("artist", artist);
	tab.addRow("release", release);
	int i = 1;
	for (String s : tracks)
	{
	    tab.addRow("track " + i, s);
	    i++;
	}
	return tab.printFormatted();
    }
    
    @Override
    public void sort()
    {
	// must do nothing, implicitly formatted
    }
    
    private BufferedReader brFromLine(int line)
    {
	try
	{
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF8"));
	    for (int i = 0; i < line; i++)
	    {
		br.readLine();
	    }
	    return br;
	}
	catch (IOException e)
	{
	    throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + this.file.getAbsolutePath() + ">", e);
	}
    }
    
    private List<String> readFileLineByLine()
    {
	try
	{
	    FileReader a = new FileReader(this.file);
	    BufferedReader br = new BufferedReader(a);
	    List<String> lines = new ArrayList<String>();
	    Iterator<String> it = br.lines().iterator();
	    while (it.hasNext())
	    {
		lines.add(it.next());
	    }
	    return lines;
	}
	catch (IOException e)
	{
	    throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + this.file.getAbsolutePath() + ">", e);
	}
    }
    
    private void writeNewLine(int lineNo, String line)
    {
	List<String> lines = readFileLineByLine();
	int newToAdd = Math.max(0, lineNo - lines.size() + 1);
	for (int i = 0; i < newToAdd; i++)
	{
	    lines.add("");
	}
	lines.set(lineNo, line);
	try
	{
	    FileWriter fw = new FileWriter(this.file);
	    for (String l : lines)
	    {
		fw.write(l + "\n");
	    }
	    fw.close();
	}
	catch (IOException e)
	{
	    throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + this.file.getAbsolutePath() + ">", e);
	}
    }
    
    class ArtistNameLoader implements ILoader<String>
    {
	@Override
	public String load()
	{
	    try
	    {
		BufferedReader br = brFromLine(POS_ARTIST_NAME);
		return br.readLine().trim();
	    }
	    catch (IOException e)
	    {
		throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + MetadataSimplePersister.this.file.getAbsolutePath() + ">", e);
	    }
	}
    }
    
    class ReleaseNameLoader implements ILoader<String>
    {
	@Override
	public String load()
	{
	    try
	    {
		BufferedReader br = brFromLine(POS_RELEASE_NAME);
		return br.readLine().trim();
	    }
	    catch (IOException e)
	    {
		throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + MetadataSimplePersister.this.file.getAbsolutePath() + ">", e);
	    }
	}
    }
    
    class ReleaseTypeLoader implements ILoader<String>
    {
	@Override
	public String load()
	{
	    try
	    {
		BufferedReader br = brFromLine(POS_RELEASE_TYPE);
		String line = br.readLine();
		if (line != null)
		{
		    line = line.trim();
		}
		return line;
	    }
	    catch (IOException e)
	    {
		throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + MetadataSimplePersister.this.file.getAbsolutePath() + ">", e);
	    }
	}
    }
    
    class TrackNamesLoader implements ILoader
    {
	@Override
	public List<String> load()
	{
	    try
	    {
		List<String> l = new ArrayList<>();
		BufferedReader br = brFromLine(POS_TRACKS);
		String line;
		while ((line = br.readLine()) != null)
		{
		    l.add(MetadataSimplePersister.this.extractTrack(line.trim()));
		}
		MetadataSimplePersister.this.trackTotal = l.size();
		return l;
	    }
	    catch (IOException e)
	    {
		throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + MetadataSimplePersister.this.file.getAbsolutePath() + ">", e);
	    }
	}
    }
    
    class TrackArtistLoader implements ILoader
    {
	@Override
	public List<String> load()
	{
	    try
	    {
		List<String> l = new ArrayList<>();
		BufferedReader br = brFromLine(POS_TRACKS);
		String line;
		while ((line = br.readLine()) != null)
		{
		    l.add(MetadataSimplePersister.this.extractArtist(line.trim()));
		}
		MetadataSimplePersister.this.trackTotal = l.size();
		return l;
	    }
	    catch (IOException e)
	    {
		throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + MetadataSimplePersister.this.file.getAbsolutePath() + ">", e);
	    }
	}
    }
    
    private String extractTrack(String line)
    {
	if (isReleaseType(ReleaseType.ALBUM))
	{
	    return line;
	}
	else if (isReleaseType(ReleaseType.SAMPLER))
	{
	    return line.split(TRACK_SEPARATOR)[1];
	}
	else
	{
	    return null;
	}
    }
    
    private String extractArtist(String line)
    {
	if (isReleaseType(ReleaseType.ALBUM))
	{
	    return this.getValue(AttributeKeys.getKeyArtistName());
	}
	if (isReleaseType(ReleaseType.SAMPLER))
	{
	    return line.split(TRACK_SEPARATOR)[0];
	}
	else
	{
	    return null;
	}
    }
    
    @Override
    public void clear()
    {
	try
	{
	    this.file.delete();
	    init();
	    this.artistName.setValue(null);
	    this.releaseName.setValue(null);
	    this.releaseType.setValue(null);
	    this.trackNames.setValue(null);
	}
	catch (IOException e)
	{
	    throw new GkMp3TaggerRuntimeExceptionFileIO("error writing/reading file <" + this.file.getAbsolutePath() + ">", e);
	}
    }
}
