package duke.tasklist;

import duke.data.exception.DukeException;
import duke.data.task.Task;
import duke.data.task.ToDo;
import duke.data.task.Deadline;
import duke.data.task.Event;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The TaskList class represents a collection of tasks managed by the ChatterChicken task manager.
 * It allows for adding, marking, unmarking, and deleting tasks, as well as printing the list of tasks
 * along with their respective indexes. Tasks can be of different types: ToDo, Deadline, and Event.
 */
public class TaskList implements Iterable<Task> {

    private final ArrayList<Task> taskList;

    /**
     * Constructs a new TaskList with the provided list of tasks and a UI component for user interactions.
     *
     * @param taskList The initial list of tasks.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Adds a new task to the task list based on the provided Task object.
     * Displays a confirmation message with the added task's details.
     *
     * @param task The task to be added to the task list.
     * @throws DukeException If there is an error in adding the task.
     */
    public void addTask(Task task) {
        assert task != null : "Task should not be null"; // Check that the task is not null
        taskList.add(task);
    }

    /**
     * Deletes a task from the task list based on the provided input.
     * Displays a confirmation message after deleting the task.
     *
     * @param input The input containing task information to be deleted.
     * @throws DukeException If there is an error in deleting the task or if the input is invalid.
     */
    public Task deleteTask(String input) throws DukeException {
        assert input != null : "Input should not be null"; // Check that the input is not null
        try {
            int index = getIndex(input);
            Task deletedTask = taskList.get(index);
            assert deletedTask != null : "Task should not be null"; // Check that the task is not null
            taskList.remove(index);
            return deletedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Invalid input for list of length " + taskList.size());
        }
    }

    /**
     * Marks a task as done based on the provided input.
     * Displays a confirmation message with the marked task's details.
     *
     * @param input The input containing task information to mark as done.
     * @throws DukeException If there is an error in marking the task or if the input is invalid.
     */
    public Task markTask(String input) throws DukeException {
        assert input != null : "Input should not be null"; // Check that the input is not null
        assert !input.isEmpty() : "Input should not be empty"; // Check that the input is not empty

        try {
            Task task = taskList.get(getIndex(input));
            assert task != null : "Task should not be null"; // Check that the task is not null
            task.setDone(true);
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Invalid input for marking list of length " + taskList.size());
        }
    }

    /**
     * Unmarks a previously marked task as not done based on the provided input.
     * Displays a confirmation message with the unmarked task's details.
     *
     * @param input The input containing task information to unmark.
     * @throws DukeException If there is an error in unmarking the task or if the input is invalid.
     */
    public Task unmarkTask(String input) throws DukeException {
        assert input != null : "Input should not be null"; // Check that the input is not null
        assert !input.isEmpty() : "Input should not be empty"; // Check that the input is not empty

        try {
            Task task = taskList.get(getIndex(input));
            assert task != null : "Task should not be null"; // Check that the task is not null
            task.setDone(false);
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Invalid input for list of length " + taskList.size());
        }
    }

    /**
     * Searches for tasks in the task list that contain the specified keyword in their descriptions.
     *
     * @param input The keyword or search query used to find matching tasks.
     * @return A string containing a list of tasks that match the search query.
     */
    public TaskList find(String input) {
        assert input != null : "Input should not be null"; // Check that the input is not null
        TaskList matchingTasks = new TaskList(new ArrayList<>());
        for (Task task : taskList) {
            if (task.getTaskDescription().contains(input)) {
                matchingTasks.addTask(task);
            }
        }
        return matchingTasks;
    }


    public TaskList getCompletedTasks() {
        TaskList completedTasks = new TaskList(new ArrayList<>());
        for (Task task : taskList) {
            if (task.isDone()) {
                completedTasks.addTask(task);
            }
        }
        return completedTasks;
    }

    public TaskList getUncompletedTasks() {
        TaskList uncompletedTasks = new TaskList(new ArrayList<>());
        for (Task task : taskList) {
            if (!task.isDone()) {
                uncompletedTasks.addTask(task);
            }
        }
        return uncompletedTasks;
    }

    public TaskList getTodos() {
        TaskList todos = new TaskList(new ArrayList<>());
        for (Task task : taskList) {
            if (task instanceof ToDo) {
                todos.addTask(task);
            }
        }
        return todos;
    }

    public TaskList getDeadlines() {
        TaskList deadlines = new TaskList(new ArrayList<>());
        for (Task task : taskList) {
            if (task instanceof Deadline) {
                deadlines.addTask(task);
            }
        }
        return deadlines;
    }

    public TaskList getEvents() {
        TaskList events = new TaskList(new ArrayList<>());
        for (Task task : taskList) {
            if (task instanceof Event) {
                events.addTask(task);
            }
        }
        return events;
    }

    public int percentDone() {
        double completedCount = getCompletedTasks().getSize();
        double result = completedCount / getSize() * 100;
        return (int) result;
    }

    /**
     * Prints the list of tasks with their respective indexes.
     */
    public String getFormattedList() {
        String indent = "      ";
        String result = "";
        for (int i = 0; i < taskList.size(); i++) {
            result += "\n" + indent + (i + 1) + "." + taskList.get(i).getFormattedTask();
        }
        return result;
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

    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }

    public int getSize() {
        return taskList.size();
    }
}