package seedu.address.commons.util;

import seedu.address.logic.commands.exceptions.CommandException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;

/**
 * Helps with reading from and writing to XML files.
 */
public class CsvUtil {

    public static List<String> getDataLinesFromFile(Path path) throws IOException {
        if (!FileUtil.isFileExists(path)) {
            throw new FileNotFoundException(String.format(MESSAGE_FILE_NOT_FOUND,path.toAbsolutePath()));
        }
        String fileContent = FileUtil.readFromFile(path);
        return Arrays.asList(fileContent.split("\\r?\\n"));
    }
}
