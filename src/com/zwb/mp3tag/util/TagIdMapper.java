package com.zwb.mp3tag.util;

import java.util.HashMap;
import java.util.Map;

import org.jaudiotagger.tag.FieldKey;

import com.zwb.mp3tag.tagger.api.TagId;

public class TagIdMapper
{
	private static final Map<TagId, FieldKey> theMap = new HashMap<>();
	{
		theMap.put(TagId.ARTIST_NAME, FieldKey.ARTIST);
		theMap.put(TagId.RELEASE_NAME, FieldKey.ALBUM);
		theMap.put(TagId.TRACK_NAME, FieldKey.TITLE);
		theMap.put(TagId.TRACK_NO, FieldKey.TRACK);
		theMap.put(TagId.TRACKS_TOTAL, FieldKey.TRACK_TOTAL);
	}

	public static FieldKey map(TagId id)
	{
		return theMap.get(id);
	}
}
