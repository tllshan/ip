package duke.tasklist;

import duke.data.exception.CCException;
import duke.data.task.Task;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The TaskList class represents a collection of tasks managed by the ChatterChicken task manager.
 * It allows for adding, marking, unmarking, and deleting tasks, as well as printing the list of tasks
 * along with their respective indexes. Tasks can be of different types: ToDo, Deadline, and Event.
 */
public class TaskList implements Iterable<Task> {

    private final ArrayList<Task> taskList;

    private final Ui ui;

    /**
     * Constructs a new TaskList with the provided list of tasks and a UI component for user interactions.
     *
     * @param taskList The initial list of tasks.
     * @param ui The user interface component for displaying messages and user interactions.
     */
    public TaskList(ArrayList<Task> taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Adds a new task to the task list based on the provided Task object.
     * Displays a confirmation message with the added task's details.
     *
     * @param task The task to be added to the task list.
     * @throws CCException If there is an error in adding the task.
     */
    public String addTask(Task task) {
        taskList.add(task);
        return ui.displayAddTask(task, taskList.size());
    }

    /**
     * Marks a task as done based on the provided input.
     * Displays a confirmation message with the marked task's details.
     *
     * @param input The input containing task information to mark as done.
     * @throws CCException If there is an error in marking the task or if the input is invalid.
     */
    public String markTask(String input) throws CCException {
        try {
            Task task = taskList.get(getIndex(input));
            task.setDone(true);
            return ui.displayMarkTask(task);
        } catch (IndexOutOfBoundsException e) {
            throw new CCException("Invalid input for marking list of length " + taskList.size());
        }
    }

    /**
     * Unmarks a previously marked task as not done based on the provided input.
     * Displays a confirmation message with the unmarked task's details.
     *
     * @param input The input containing task information to unmark.
     * @throws CCException If there is an error in unmarking the task or if the input is invalid.
     */
    public String unmarkTask(String input) throws CCException {
        try {
            Task task = taskList.get(getIndex(input));
            task.setDone(false);
            return ui.displayUnmarkTask(task);
        } catch (IndexOutOfBoundsException e) {
            throw new CCException("Invalid input for list of length " + taskList.size());
        }
    }

    /**
     * Deletes a task from the task list based on the provided input.
     * Displays a confirmation message after deleting the task.
     *
     * @param input The input containing task information to be deleted.
     * @throws CCException If there is an error in deleting the task or if the input is invalid.
     */
    public String deleteTask(String input) throws CCException {
        try {
            int index = getIndex(input);
            Task task = taskList.get(index);
            taskList.remove(index);
            return ui.displayDeleteTask(task, taskList.size());
        } catch (IndexOutOfBoundsException e) {
            throw new CCException("Invalid input for list of length " + taskList.size());
        }
    }

    public String find(String input) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : taskList) {
            if(task.getTaskDescription().contains(input)) {
                matchingTasks.add(task);
            }
        }
        return ui.displayMatchingTasks(matchingTasks);
    }

    /**
     * Retrieves the index of a task based on the provided input.
     *
     * @param input The input containing task information and index as the last character.
     * @return The index of the task parsed from the input.
     */
    private int getIndex(String input) {
        return input.charAt(input.length() - 1) - '0' - 1;
    }

    /**
     * Prints the list of tasks with their respective indexes.
     */
    public String printList() {
        return ui.displayList(taskList);
    }

    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }
}