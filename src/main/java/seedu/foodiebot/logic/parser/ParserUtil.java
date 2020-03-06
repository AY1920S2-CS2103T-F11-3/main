package seedu.foodiebot.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.commons.util.StringUtil;
import seedu.foodiebot.logic.parser.exceptions.ParseException;
import seedu.foodiebot.model.canteen.Address;
import seedu.foodiebot.model.canteen.Block;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.Name;
import seedu.foodiebot.model.canteen.Stall;
import seedu.foodiebot.model.tag.Tag;

/** Contains utility methods used for parsing strings in the various *Parser classes. */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String name} into a numberOfStalls. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static int parseNoOfStalls(String numberOfStalls) throws ParseException {
        requireNonNull(numberOfStalls);

        return Integer.parseInt(numberOfStalls);
    }
    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String blockName} into an {@code blockName}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code blockName} is invalid.
     */
    public static String parseBlockName(String blockName) throws ParseException {
        requireNonNull(blockName);
        String trimmedBlockName = blockName.trim();
        if (!Block.isValidBlock(trimmedBlockName)) {
            throw new ParseException(Block.MESSAGE_CONSTRAINTS);
        }
        return trimmedBlockName;
    }

    /**
     * Parses a {@code String canteenName} into an {@code canteenName}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code canteenName} is invalid.
     */
    public static String parseCanteenName(String canteenName) throws ParseException {
        requireNonNull(canteenName);
        String trimmedCanteenName = canteenName.trim();
        if (!Canteen.isValidCanteen(trimmedCanteenName)) {
            throw new ParseException(Canteen.MESSAGE_CONSTRAINTS);
        }
        return trimmedCanteenName;
    }

    /**
     * Parses a {@code String stallName} into a {@code stallName}. Leading and trailing whitespaces will
     * be trimmed
     *
     * @throws ParseException if the given {@code stallName} is invalid
     */
    public static String parseStallName(String stallName) throws ParseException {
        requireNonNull(stallName);
        String trimmedStallName = stallName.trim();
        if (!Stall.isValidStall(trimmedStallName)) {
            throw new ParseException(Stall.MESSAGE_CONSTRAINTS);
        }
        return trimmedStallName;

    }


    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /** Parses {@code Collection<String> tags} into a {@code Set<Tag>}. */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
