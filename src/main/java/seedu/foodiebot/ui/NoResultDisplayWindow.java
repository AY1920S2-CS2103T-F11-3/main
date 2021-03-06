package seedu.foodiebot.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.foodiebot.commons.core.GuiSettings;
import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.logic.Logic;
import seedu.foodiebot.logic.commands.CommandResult;
import seedu.foodiebot.logic.commands.exceptions.CommandException;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where
 * other JavaFX elements can be placed.
 */
public class NoResultDisplayWindow extends UiPart<Stage> {

    private static final String FXML = "NoResultDisplayScene.fxml";
    @FXML
    protected MenuItem helpMenuItem;
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;



    private HelpWindow helpWindow;

    private boolean isStallInitialised;
    private boolean isFoodInitialised;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public NoResultDisplayWindow(Stage primaryStage, Logic logic, String layoutName) {
        super(layoutName, primaryStage);
        init(primaryStage, logic);
    }

    public NoResultDisplayWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        init(primaryStage, logic);
    }

    /** Runs the initialisation method. */
    private void init(Stage primaryStage, Logic logic) {
        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        isStallInitialised = false;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot()
            .addEventFilter(
                KeyEvent.KEY_PRESSED,
                event -> {
                    if (event.getTarget() instanceof TextInputControl
                        && keyCombination.match(event)) {
                        menuItem.getOnAction().handle(new ActionEvent());
                        event.consume();
                    }
                });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        CommandBox commandBox = new CommandBox(this::executeCommand);
        getCommandBoxPlaceholder().getChildren().add(commandBox.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFoodieBotFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

    }

    public StackPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    public StackPane getListPanelPlaceholder() {
        return listPanelPlaceholder;
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void addToListPanel(UiPart<Region> regionUiPart) {
        listPanelPlaceholder = (StackPane) primaryStage.getScene().lookup("#listPanelPlaceholder");
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(regionUiPart.getRoot());
    }
    /**
     * Fills the stallListPanel region.
     */
    @FXML
    public void handleListStalls() {
        addToListPanel(new StallsListPanel(logic.getFilteredStallList(isStallInitialised)));
        isStallInitialised = true;
    }

    /**
     * Fills the foodListPanel region.
     */
    @FXML
    public void handleListFood() {
        addToListPanel(new FoodListPanel(logic.getFilteredFoodList(isFoodInitialised)));
        isFoodInitialised = true;
    }

    /**
     * Fills the foodListPanel region.
     */
    @FXML
    public void handleListFavorites() {
        addToListPanel(new FoodListPanel(logic.getFilteredFavoriteFoodList(false)));
    }



    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        GuiSettings guiSettings =
            new GuiSettings(
                primaryStage.getWidth(),
                primaryStage.getHeight(),
                (int) primaryStage.getX(),
                (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.foodiebot.logic.Logic#execute(String)
     */
    protected CommandResult executeCommand(String commandText)
        throws CommandException, ParseException, IOException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            Parent pane = loadFxmlFile("MainScene.fxml");

            primaryStage.setScene(new Scene(pane));

            return commandResult;
        } catch (CommandException | ParseException | IOException e) {
            logger.info("Invalid command: " + commandText);


            Parent pane = loadFxmlFile("MainScene.fxml");

            primaryStage.setScene(new Scene(pane));
            throw e;
        }
    }
}
