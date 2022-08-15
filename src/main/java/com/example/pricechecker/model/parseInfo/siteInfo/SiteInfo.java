package com.example.pricechecker.model.parseInfo.siteInfo;

import com.example.pricechecker.logic.parsing.pageParsers.interfaces.PageParser;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;

import java.util.List;

public class SiteInfo<T>{
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getFindPageFormat() {
        return findPageFormat;
    }

    public void setFindPageFormat(String findPageFormat) {
        this.findPageFormat = findPageFormat;
    }

    public PageParser<List<String>> getUrlParser() {
        return urlParser;
    }

    public void setUrlParser(PageParser<List<String>> urlParser) {
        this.urlParser = urlParser;
    }

    public ClassParseInfo<T> getClassParseInfo() {
        return classParseInfo;
    }

    public void setClassParseInfo(ClassParseInfo<T> classParseInfo) {
        this.classParseInfo = classParseInfo;
    }

    private String siteName;
    private String findPageFormat;
    private PageParser<List<String>> urlParser;
    private ClassParseInfo<T> classParseInfo;

}
