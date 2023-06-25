package com.astrobit.hub;

import java.util.Date;

public record Project(String path, String editorVersion, Date modified) implements java.io.Serializable {
}