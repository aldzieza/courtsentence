package pl.olointeria.wyrok.modelFX;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParagraphFX {
    //czyli wersja Properties 1.int id; 2.String paragraphNo
    private IntegerProperty id =new SimpleIntegerProperty();
    private StringProperty paragraphNo =new SimpleStringProperty();
    //[34.2]
    //met to STRING  ale tylko dla paragarfu i koniecznie getValue

    //GETTERY i SETTERY

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getParagraphNo() {
        return paragraphNo.get();
    }

    public StringProperty paragraphNoProperty() {
        return paragraphNo;
    }

    public void setParagraphNo(String paragraphNo) {
        this.paragraphNo.set(paragraphNo);
    }

    @Override
    public String toString() {
        return paragraphNo.getValue();
    }
}
