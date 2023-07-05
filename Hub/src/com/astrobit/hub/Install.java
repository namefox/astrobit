package com.astrobit.hub;

import java.io.Serializable;

public class Install implements Serializable {
    
    private String path;
    private String version;
    private String[] extensions;
    
    public Install(String path, String version, String... extensions) {
        this.path = path;
        this.version = version;
        this.extensions = extensions;
    }

    public String path() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String version() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] extensions() {
        return extensions;
    }

    public void setExtensions(String... extensions) {
        this.extensions = extensions;
    }
}