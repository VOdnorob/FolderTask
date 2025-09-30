package org.example;

import java.util.List;

public class MultiFolderImpl implements MultiFolder {
    private final String name;
    private final String size;
    private final List<Folder> children;

    public MultiFolderImpl(String name, String size, List<Folder> children) {
        this.name = name;
        this.size = size;
        this.children = children == null ? List.of() : children;
    }

    @Override
    public List<Folder> getFolders() {
        return children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size;
    }
}
