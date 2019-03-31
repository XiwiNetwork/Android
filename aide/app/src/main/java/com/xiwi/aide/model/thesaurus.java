package com.xiwi.aide.model;

public class thesaurus
{
    private String keyword;
    private String reply;

    public thesaurus(String keyword, String reply){
        this.keyword = keyword;
        this.reply = reply;
    }
    
    public void setReply ( String reply )
    {
        this.reply = reply;
    }

    public String getReply ( )
    {
        return reply;
    }
    
    public void setKeyword ( String keyword )
    {
        this.keyword = keyword;
    }

    public String getKeyword ( )
    {
        return keyword;
    }}
