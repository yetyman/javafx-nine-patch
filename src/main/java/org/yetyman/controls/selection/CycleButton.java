package org.yetyman.controls.selection;

import javafx.beans.property.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.yetyman.controls.PseudoClassHelper;

import java.util.function.Function;

public class CycleButton<T> extends Button {

    public final ObservableList<T> items = FXCollections.observableArrayList();
    public final ObjectProperty<T> selectedItem = PseudoClassHelper.createPseudoClassObjectProperty(this, "%s", null);
    public final StringProperty nullString = new SimpleStringProperty("null");
    public final ObjectProperty<Function<T, String>> itemToStringFunc = new SimpleObjectProperty<>(o->o == null ? nullString.get() : o.toString());
    public final BooleanProperty allowNull = new SimpleBooleanProperty(false);

    public CycleButton() {
        super();
        this.getStyleClass().add("cycle-button");
        items.addListener(new ListChangeListener<T>() {
            @Override
            public void onChanged(Change<? extends T> c) {
                if(!allowNull.get() && selectedItem.get() == null && !items.isEmpty())
                    selectedItem.set(items.get(0));
            }
        });
        this.setOnAction(evt->{
            if(selectedItem.get() == null && !items.isEmpty())
                selectedItem.set(items.getFirst());
            else
                selectedItem.set(items.get((items.indexOf(selectedItem.get())+1)%items.size()));
        });

        selectedItem.addListener((observable, oldValue, newValue) -> {
            if (newValue != null ||  allowNull.get())
                refresh();
        });

        nullString.addListener((observable, oldValue, newValue) -> {
            if (selectedItem.get() == null)
                refresh();
        });

        itemToStringFunc.addListener((observable, oldValue, newValue) -> {
            refresh();
        });
    }

    void refresh(){
        setText(itemToStringFunc.get().apply(selectedItem.get()));
    }
}
