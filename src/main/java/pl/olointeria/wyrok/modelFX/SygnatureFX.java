package pl.olointeria.wyrok.modelFX;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SygnatureFX {
    private IntegerProperty id =new SimpleIntegerProperty();
    private StringProperty sygnatureNo=new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getSygnatureNo() {
        return sygnatureNo.get();
    }

    public StringProperty sygnatureNoProperty() {
        return sygnatureNo;
    }

    @Override
    public String toString() {
        return sygnatureNo.get();
    }

    public void setSygnatureNo(String sygnatureNo) {
        this.sygnatureNo.set(sygnatureNo);
    }

}
