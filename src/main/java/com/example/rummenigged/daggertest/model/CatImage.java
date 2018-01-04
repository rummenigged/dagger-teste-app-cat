package com.example.rummenigged.daggertest.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by rummenigged on 04/01/18.
 */

@Root(name = "image")
public class CatImage {
    @Element(name = "id")
    public String id;

    @Element(name = "url")
    public String url;

    @Element(name = "source_url")
    public String sourceUrl;
}
