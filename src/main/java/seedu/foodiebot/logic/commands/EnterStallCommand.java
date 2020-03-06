package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.logic.parser.ParserContext;
import seedu.foodiebot.model.Model;
import seedu.foodiebot.model.canteen.Stall;

/**
 * Represents a command telling the user to enter a particular stall
 */
public class EnterStallCommand extends Command {
    public static final String COMMAND_WORD = "enter";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD
            + "Parameters: "
            + "STALL_NAME \n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + "Taiwanese ";

    public static final String MESSAGE_SUCCESS = "";
    private static final Logger logger = LogsCenter.getLogger(EnterCanteenCommand.class);

    private final Optional<String> stallName;
    private final Optional<Index> index;

    /**
     * @param index of the canteen in the filtered stall list to edit
     */
    public EnterStallCommand(Index index) {
        requireNonNull(index);
        this.index = Optional.of(index);
        this.stallName = Optional.empty();
    }

    /**
     * @param stallName from the given canteen name
     */
    public EnterStallCommand(String stallName) {
        requireNonNull(stallName);
        this.index = Optional.empty();
        this.stallName = Optional.of(stallName);
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (index.isPresent()) {
            Stall stall = model.getFilteredStallList().get(index.get().getZeroBased());
            logger.info("Enter " + stall.getName());
            model.updateFilteredFoodList(stall.getFoodMenu());

        } else if (stallName.isPresent()) {
            List<Stall> stalls = model.getFilteredStallList();
            for (Stall s : stalls) {
                if (s.getName().toString().equalsIgnoreCase(stallName.get())) {
                    ParserContext.setStallContext(s);
                    model.updateFilteredFoodList(s.getFoodMenu());
                    break;
                }
            }
        }
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
