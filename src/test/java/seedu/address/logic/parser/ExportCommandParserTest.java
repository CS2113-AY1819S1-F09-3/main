package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_PATH;
import static seedu.address.commons.core.Messages.MESSAGE_UNSUPPORTED_FILE_EXTENSION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.converters.CsvPersonConverter;
import seedu.address.logic.converters.fileformats.csv.CsvFile;

/**
 * Contains tests to test whether ExportCommandParser can handle all user input scenarios.
 */
public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_csvFileFormat_success() {
        String filename = "testing.csv";
        ExportCommand expectedExportCommand = new ExportCommand(new CsvFile(filename), new CsvPersonConverter());
        assertParseSuccess(parser, filename, expectedExportCommand);
    }

    @Test
    public void parse_invalidPath_throwsParseException() {
        assertParseFailure(parser, "path@.csv",
                String.format(MESSAGE_INVALID_FILE_PATH, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFileExtension_throwsParseException() {
        assertParseFailure(parser, "testing.txt",
                String.format(MESSAGE_UNSUPPORTED_FILE_EXTENSION, ExportCommand.MESSAGE_USAGE));
    }

}
