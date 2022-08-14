package com.example.pricechecker.model;

import com.example.pricechecker.logic.classCreators.ClassWithoutArgumentsCreator;
import com.example.pricechecker.logic.collectionConverters.CollectionConverter;
import com.example.pricechecker.logic.collectionConverters.OneElementCollectionConverter;
import com.example.pricechecker.logic.parsing.pageParsers.OneAttributeFromElementsPageParser;
import com.example.pricechecker.logic.parsing.pageParsers.OneAttributePageParser;
import com.example.pricechecker.logic.parsing.pageParsers.TextPageParser;
import com.example.pricechecker.model.parseInfo.classInfo.ClassParseInfo;
import com.example.pricechecker.model.parseInfo.fieldInfo.FieldParseInfo;
import com.example.pricechecker.model.parseInfo.page.PageParseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Configuration {
    private final FieldSetter fieldSetter;
    private final ClassField<Product,Long> price;
    private final ClassField<Product,String> name;

    public List<SiteInfo<Product>> getSiteInfoList() {
        return siteInfoList;
    }

    private final List<SiteInfo<Product>> siteInfoList = new ArrayList<>();

    public Configuration(){
        fieldSetter = new FieldSetter();
        price = priceField();
        name = nameField();
        siteInfoList.add(ebayInfo());
        siteInfoList.add(vekInfo());
    }

    public ClassField<Product,Long> priceField(){
        return new ClassField<>(fieldSetter,"price",Product.class,Long.class);
    }

    public ClassField<Product,String> nameField(){
        return new ClassField<>(fieldSetter,"name",Product.class,String.class);
    }

    public SiteInfo<Product> ebayInfo(){
        SiteInfo<Product> siteInfo = new SiteInfo<>();
        siteInfo.setSiteName("ebay");
        siteInfo.setFindPageFormat("https://www.ebay.com/sch/i.html?_from=R40&_nkw=%s&_sacat=0&LH_TitleDesc=0&_pgn=%d");
        siteInfo.setUrlParser(new OneAttributeFromElementsPageParser("div.s-item__info a.s-item__link","href"));
        CollectionConverter<Long,String> converter = (list)->{
            if(!list.isEmpty()){
                String str = list.get(0).split("\\.")[0];
                return Optional.of(Long.parseLong(str));
            } else{
                return Optional.empty();
            }
        };
        FieldParseInfo<Product,Long,String> priceParseInfo = new FieldParseInfo<Product,Long,String>(price,converter,List.of(new PageParseInfo<>(null,new OneAttributePageParser("span#prcIsum","content"))));
        FieldParseInfo<Product,String,String> nameParseInfo = new FieldParseInfo<>(name,new OneElementCollectionConverter<>(),List.of(new PageParseInfo<>(null,new TextPageParser("h1.x-item-title__mainTitle span.ux-textspans--BOLD"))));
        ClassParseInfo<Product> classParseInfo = new ClassParseInfo<Product>(new ClassWithoutArgumentsCreator<>(Product.class),List.of(nameParseInfo,priceParseInfo),new ArrayList<>());
        siteInfo.setClassParseInfo(classParseInfo);
        return siteInfo;
    }

    public SiteInfo<Product> vekInfo(){
        SiteInfo<Product> siteInfo = new SiteInfo<>();
        siteInfo.setSiteName("21vek.by");
        siteInfo.setFindPageFormat("https://www.21vek.by/search/page:%2$d/?sa=&term=%1$s&searchId=a451c474eefd4162a65ad9950fa48de9");
        siteInfo.setUrlParser(new OneAttributeFromElementsPageParser("dt.result__root a.result__link","href"));
        CollectionConverter<Long,String> converter = (list)->{
            if(!list.isEmpty()){
                return Optional.of(Long.parseLong(list.get(0).split("\\.")[0]));
            } else{
                return Optional.empty();
            }
        };
        FieldParseInfo<Product,Long,String> priceParseInfo = new FieldParseInfo<Product,Long,String>(price,converter,List.of(new PageParseInfo<>(null,new OneAttributePageParser("span.g-price span.g-item-data","data-price"))));
        FieldParseInfo<Product,String,String> nameParseInfo = new FieldParseInfo<>(name,new OneElementCollectionConverter<>(),List.of(new PageParseInfo<>(null,new TextPageParser("div.content__header h1[itemprop='name']"))));
        ClassParseInfo<Product> classParseInfo = new ClassParseInfo<Product>(new ClassWithoutArgumentsCreator<>(Product.class),List.of(nameParseInfo,priceParseInfo),new ArrayList<>());
        siteInfo.setClassParseInfo(classParseInfo);
        return siteInfo;
    }
}
