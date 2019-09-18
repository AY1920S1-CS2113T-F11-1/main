package duke.tasks;

public class Recursive extends Task {
    protected String forTask;

    public Recursive(String description, String forTask ) {
        super(description);
        this.forTask = forTask;
    }

    @Override
    public String toString() {
        return ("[R]" + super.toString() + " (after " + this.forTask + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("R" + super.fileOutFormat() + "|" + this.forTask);
    }

}

