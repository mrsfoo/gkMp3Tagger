package com.zwb.mp3tag.profile.api;

public interface IGkTaggingProfileReader
{
    public ITaggingProfile read(String folder);
    
    public void addFilterRegex(String regex);
    
    public String getMetaInfoFilename();
    
}
