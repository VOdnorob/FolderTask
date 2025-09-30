import org.example.FileCabinet;
import org.example.FolderImpl;
import org.example.MultiFolderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FileCabinetTest {

	@Test
	void whenFolderExists_findFolderByName_returnsOptionalWithFolder() {
		var folder = new FolderImpl("Folder 1", "SMALL");
		var fileCabinet = new FileCabinet(List.of(folder));

		var result = fileCabinet.findFolderByName("Folder 1");

		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals("Folder 1", result.get().getName());
	}

	@Test
	void whenFolderDoesNotExist_findFolderByName_returnsEmptyOptional() {
		var folder = new FolderImpl("Folder 1", "SMALL");
		var fileCabinet = new FileCabinet(List.of(folder));

		var result = fileCabinet.findFolderByName("Unknown folder");

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void whenFoldersHaveDifferentSizes_findFoldersBySize_returnsOnlyMatching() {
		var folder1 = new FolderImpl("Folder 1", "SMALL");
		var folder2 = new FolderImpl("Folder 2", "MEDIUM");
		var fileCabinet = new FileCabinet(Arrays.asList(folder1, folder2));

		var result = fileCabinet.findFoldersBySize("SMALL");

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Folder 1", result.getFirst().getName());
	}

	@Test
	void whenFoldersNested_findFoldersBySize_returnsAllMatching() {
		var folder1 = new FolderImpl("Folder 1", "SMALL");
		var folder2 = new FolderImpl("Folder 2", "SMALL");
		var folder3 = new FolderImpl("Folder 3", "LARGE");
		var multiFolder = new MultiFolderImpl("Multi Folder 1", "LARGE", List.of(folder1, folder2, folder3));
		var fileCabinet = new FileCabinet(List.of(multiFolder));

		var result = fileCabinet.findFoldersBySize("SMALL");

		Assertions.assertEquals(2, result.size());
		Assertions.assertTrue(result.stream().anyMatch(f -> f.getName().equals("Folder 1")));
		Assertions.assertTrue(result.stream().anyMatch(f -> f.getName().equals("Folder 2")));
	}

	@Test
	void whenFoldersNested_count_returnsTotalNumber() {
		var folder1 = new FolderImpl("Folder 1", "SMALL");
		var folder2 = new FolderImpl("Folder 2", "SMALL");
		var multiFolder1 = new MultiFolderImpl("Multi Folder 1", "MEDIUM", List.of(folder1, folder2));
		var folder3 = new FolderImpl("Folder 3", "SMALL");
		var multiFolder2 = new MultiFolderImpl("Multi Folder 2", "LARGE", List.of(multiFolder1, folder3));
		var folder4 = new FolderImpl("Folder 4", "MEDIUM");
		var fileCabinet = new FileCabinet(List.of(multiFolder2, folder4));

		var result = fileCabinet.count();

		Assertions.assertEquals(6, result);
	}
}
