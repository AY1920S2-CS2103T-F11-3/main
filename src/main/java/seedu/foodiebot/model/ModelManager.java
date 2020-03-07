package seedu.foodiebot.model;

import static java.util.Objects.requireNonNull;
import static seedu.foodiebot.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.foodiebot.commons.core.GuiSettings;
import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.commons.exceptions.DataConversionException;
import seedu.foodiebot.model.budget.Budget;
import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.Stall;
import seedu.foodiebot.model.food.Food;
import seedu.foodiebot.storage.FoodieBotStorage;
import seedu.foodiebot.storage.JsonFoodieBotStorage;
import seedu.foodiebot.storage.Storage;
import seedu.foodiebot.storage.StorageManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FoodieBot foodieBot;
    private final UserPrefs userPrefs;
    private final FilteredList<Canteen> filteredCanteens;
    private final FilteredList<Stall> filteredStalls;
    private final FilteredList<Food> filteredFoods;

    private final Budget budget;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyFoodieBot foodieBot, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(foodieBot, userPrefs);

        logger.fine("Initializing with food data: " + foodieBot + " and user prefs " + userPrefs);

        this.foodieBot = new FoodieBot(foodieBot);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredCanteens = new FilteredList<>(this.foodieBot.getCanteenList());
        filteredStalls = new FilteredList<>(this.foodieBot.getStallList());
        filteredFoods = new FilteredList<>(this.foodieBot.getFoodList());
        budget = this.foodieBot.getBudget();
    }

    public ModelManager() {
        this(new FoodieBot(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getFoodieBotFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setFoodieBotFilePath(addressBookFilePath);
    }

    // =========== AddressBook
    // ================================================================================

    @Override
    public ReadOnlyFoodieBot getFoodieBot() {
        return foodieBot;
    }

    @Override
    public void setFoodieBot(ReadOnlyFoodieBot foodieBot) {
        this.foodieBot.resetData(foodieBot);
    }

    @Override
    public boolean hasCanteen(Canteen canteen) {
        requireNonNull(canteen);
        return foodieBot.hasCanteen(canteen);
    }

    @Override
    public void deleteCanteen(Canteen target) {
        foodieBot.removeCanteen(target);
    }

    @Override
    public void addCanteen(Canteen canteen) {
        foodieBot.addCanteen(canteen);
        updateFilteredCanteenList(PREDICATE_SHOW_ALL_CANTEEN);
    }

    @Override
    public void setCanteen(Canteen target, Canteen editedCanteen) {
        requireAllNonNull(target, editedCanteen);

        foodieBot.setCanteen(target, editedCanteen);
    }

    @Override
    public void setBudget(Budget budget) {
        requireAllNonNull(budget);
        foodieBot.setBudget(budget);
    }

    /**
     * Reads the stored budget in the Json file.
     * @return The budget object stored in the Json file. If the file is not present,
     * returns an empty Optional value.
     */
    @Override
    public Optional<Budget> getBudget() {
        try {
            FoodieBotStorage foodieBotStorage =
                    new JsonFoodieBotStorage(userPrefs.getBudgetFilePath());
            Storage storage = new StorageManager(foodieBotStorage);
            Optional<ReadOnlyFoodieBot> newBot = storage.readFoodieBot(Budget.class.getSimpleName());
            if (newBot.equals(Optional.empty())) {
                return Optional.empty();
            }
            return Optional.of(newBot.get().getBudget());
        } catch (DataConversionException e) {
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
        // return foodieBot.getBudget();

    }



    // =========== Filtered Person List Accessors
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Canteen> getFilteredCanteenList() {
        return filteredCanteens;
    }

    @Override
    public void updateFilteredCanteenList(Predicate<Canteen> predicate) {
        requireNonNull(predicate);
        filteredCanteens.setPredicate(predicate);
    }

    public ObservableList<Stall> getFilteredStallList(boolean isInitialised) {
        if (!isInitialised) {
            updateModelManagerStalls();
        }

        return filteredStalls;
    }

    @Override
    public ObservableList<Stall> getFilteredStallList(Predicate<Stall> predicate) {
        requireNonNull(predicate);
        filteredStalls.setPredicate(predicate);

        return filteredStalls;
    }

    public ObservableList<Stall> getFilteredStallList() {
        return filteredStalls;
    }

    @Override
    public ObservableList<Food> getFilteredFoodList() {
        return filteredFoods;
    }

    @Override
    public void updateFilteredFoodList(List<Food> foods) {
        //List<Food> filterCopy = new ArrayList<>(filteredFoods);
        //filteredFoods.removeAll(filterCopy);
        //filteredFoods.addAll(foods);
    }


    /**
     * Updates the filter of the filtered stall list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredStallList(Predicate<Stall> predicate) {
        requireNonNull(predicate);
        filteredStalls.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStallList(List<Stall> list) {
        //List<Stall> filterCopy = new ArrayList<>(filteredStalls);
        //filteredStalls.removeAll(filterCopy);
        //filteredStalls.addAll(list);
    }

    /**
     * .
     */
    public void updateModelManagerStalls() {
        try {
            FoodieBotStorage foodieBotStorage =
                new JsonFoodieBotStorage(userPrefs.getStallsFilePath());
            Storage storage = new StorageManager(foodieBotStorage);
            Optional<ReadOnlyFoodieBot> newBot = storage.readFoodieBot(Stall.class.getSimpleName());
            foodieBot.setStalls(newBot.get().getStallList());
        } catch (DataConversionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return foodieBot.equals(other.foodieBot)
            && userPrefs.equals(other.userPrefs)
            && filteredCanteens.equals(other.filteredCanteens);
    }
}
