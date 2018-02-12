package david.model;

import javafx.beans.property.SimpleStringProperty;

public class SmartWord {

    private int id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty definition;

    public SmartWord(String name, String definition) {
        this.name = new SimpleStringProperty(name);
        this.definition = new SimpleStringProperty(definition);
    }

    public SmartWord(int id, String name, String definition) {
        this.id = id;
        this.name =  new SimpleStringProperty(name);
        this.definition = new SimpleStringProperty(definition);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDefinition() {
        return definition.get();
    }

    public void setDefinition(String definition) {
        this.definition.set(definition);
    }
}
