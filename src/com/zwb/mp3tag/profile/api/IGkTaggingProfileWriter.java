package com.zwb.mp3tag.profile.api;

public interface IGkTaggingProfileWriter
{
    public void write(ITaggingProfile profile, String path, boolean simpleFormat);
    
    public String getMetaInfoFilename();
    
}
