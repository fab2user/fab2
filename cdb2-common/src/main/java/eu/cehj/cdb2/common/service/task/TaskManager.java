package eu.cehj.cdb2.common.service.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * In charge of storing status related to time consuming tasks, such as xls import.
 */
@Service
public class TaskManager {

    public static enum Type{
        IMPORT_XLS,
        EXPORT_XLS
    }

    private final Map<String, TaskStatus> tasks = new HashMap<>();

    public TaskStatus getTask(final String code) {
        return this.tasks.get(code);
    }

    public void addTask(final TaskStatus task) {
        this.tasks.put(task.getCode(), task);
    }

    public TaskStatus createTask(final Type taskType) {
        final TaskStatus task = new TaskStatus(this.generateCode(taskType));
        this.tasks.put(task.getCode(), task);
        return task;
    }

    private String generateCode(final Type type) {
        return type + Long.toString(new Date().getTime());
    }
}
