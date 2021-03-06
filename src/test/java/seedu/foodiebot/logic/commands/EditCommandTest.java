package seedu.foodiebot.logic.commands;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit
 * tests for EditCommand.
 */

public class EditCommandTest {
/*
    private Model model = new ModelManager(getTypicalFoodieBot(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Canteen editedCanteen = new CanteenBuilder().build();
        EditCommand.EditCanteenDescriptor descriptor =
                new EditCanteenDescriptorBuilder(editedCanteen).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCanteen);

        Model expectedModel =
                new ModelManager(new FoodieBot(model.getFoodieBot()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedCanteen);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        CanteenBuilder personInList = new CanteenBuilder(lastPerson);
        Person editedPerson =
                personInList
                        .withName(VALID_NAME_BOB)
                        .withPhone(VALID_PHONE_BOB)
                        .withTags(VALID_TAG_HUSBAND)
                        .build();

        EditCanteenDescriptor descriptor =
                new EditCanteenDescriptorBuilder()
                        .withName(VALID_NAME_BOB)
                        .withPhone(VALID_PHONE_BOB)
                        .withTags(VALID_TAG_HUSBAND)
                        .build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new FoodieBot(model.getFoodieBot()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand =
                new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditCanteenDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new FoodieBot(model.getFoodieBot()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList =
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson =
                new CanteenBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand =
                new EditCommand(
                        INDEX_FIRST_PERSON,
                        new EditCanteenDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new FoodieBot(model.getFoodieBot()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditCanteenDescriptor descriptor =
                new EditCanteenDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList =
                model.getFoodieBot().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand =
                new EditCommand(
                        INDEX_FIRST_PERSON, new EditCanteenDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCanteenDescriptor descriptor =
                new EditCanteenDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    */

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of
     * address book
     */

    /*
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFoodieBot().getPersonList().size());

        EditCommand editCommand =
                new EditCommand(
                        outOfBoundIndex,
                        new EditCanteenDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditCanteenDescriptor copyDescriptor = new EditCanteenDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }
    */
}

