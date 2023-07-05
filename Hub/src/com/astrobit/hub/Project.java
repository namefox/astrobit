package com.astrobit.hub;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Project implements Serializable {

    private String path;
    private String editorVersion;
    private Date modified;

    public Project(String path, String editorVersion) {
        this.path = path;
        this.editorVersion = editorVersion;
        this.modified = new Date();
    }

    public String path() {
        return path;
    }

    public String editorVersion() {
        return editorVersion;
    }

    public Date modified() {
        return modified;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setEditorVersion(String editorVersion) {
        this.editorVersion = editorVersion;
    }

    public void modify() {
        this.modified = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Project) obj;
        return Objects.equals(this.path, that.path) &&
                Objects.equals(this.editorVersion, that.editorVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, editorVersion);
    }

    @Override
    public String toString() {
        return "Project[" +
                "path=" + path + ", " +
                "editorVersion=" + editorVersion + ']';
    }

}