package com.example.rummenigged.daggertest.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;


/**
 * Created by rummenigged on 04/01/18.
 */

@Root(name = "response")
public class CatImages {
    @ElementList(inline = true)
    @Path("data/images")
    public List<CatImage> catImages;
}
