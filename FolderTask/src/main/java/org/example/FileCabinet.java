package org.example;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders == null ? List.of() : folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return getFlattenFolders()
			.filter(f -> f.getName().equalsIgnoreCase(name))
			.findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return getFlattenFolders()
			.filter(f -> f.getSize().equalsIgnoreCase(size))
			.collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int)getFlattenFolders().count();
    }

	private Stream<Folder> getFlattenFolders() {
		return folders.stream()
			.flatMap(FileCabinet::flatten);
	}

	private static Stream<Folder> flatten(Folder f) {
		if (f instanceof MultiFolder mf) {
			return Stream.concat(
				Stream.of(f),
				mf.getFolders().stream().flatMap(FileCabinet::flatten)
			);
		}
		
		return Stream.of(f);
	}
}
