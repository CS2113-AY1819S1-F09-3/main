package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UID;

import java.util.Random;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uid;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add_guest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a guest to the address book. \n"
            + "Note: UID is either auto-assigned [if you enter u/00000] "
            + "or user defined [anything other than u/00000].\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_PAYMENT + "PAYMENT "
            + PREFIX_ATTENDANCE + "ATTENDANCE "
            + PREFIX_UID + "UID "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@gmail.com "
            + PREFIX_PAYMENT + "PAID "
            + PREFIX_ATTENDANCE + "PRESENT "
            + PREFIX_UID + "00000 "
            + PREFIX_TAG + "NORMAL "
            + PREFIX_TAG + "NoShrimp "
            + PREFIX_TAG + "NORMAL";


    public static final String MESSAGE_SUCCESS = "New guest added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This guest already exists in the guest list.";
    public static final String MESSAGE_DUPLICATE_UID =
            "This UID is already used in the guest list. Please use another UID.";
    public static final Uid DEFAULT_TO_GENERATE_UID = new Uid("00000");

    private Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    /**
     * Generate a random UID
     */
    public Uid generateUid() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return new Uid(String.format("%06d", number));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        //@@author kronicler
        if (model.hasUid(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_UID);
        }

        if (toAdd.getUid().equals(DEFAULT_TO_GENERATE_UID)) {
            boolean unique = false;
            while (!unique) {
                Person temp = new Person(toAdd.getName(), toAdd.getPhone(), toAdd.getEmail(), toAdd.getPayment(),
                        toAdd.getAttendance(), generateUid(), toAdd.getTags());
                if (model.hasUid(temp) == false) {
                    unique = true;
                    toAdd = temp;
                }
            }
        }
        //@@author

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
