package pl.olointeria.wyrok.modelFX;


import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.olointeria.wyrok.database.dao.SygnatureDao;
import pl.olointeria.wyrok.database.dbutils.DbManager;
import pl.olointeria.wyrok.database.models.Sygnature;
import pl.olointeria.wyrok.utils.converters.ConvertToSygnature;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

import java.util.List;

import static pl.olointeria.wyrok.utils.converters.ConvertToSygnature.convertSygnatureFXToSygnature;

public class SygnatureModel {
//potrzebujemy obiekt ktory bedzie obsługiwał nam naszą Sygnature
    private ObjectProperty<SygnatureFX> sygnatureFXObjectProperty =new SimpleObjectProperty<>(new SygnatureFX());
    private ObjectProperty<SygnatureFX> sygnatureFXObjectPropertyEDIT =new SimpleObjectProperty<>(new SygnatureFX());
//potrzebujrmy lisstę
    private ObservableList<SygnatureFX> sygnatureFXObservableList = FXCollections.observableArrayList();


public void saveSygnatureInDatabase( ) throws ApplicationException {
    saveOrUpdate(this.getSygnatureFXObjectProperty());
}
    public void saveSygnatureEditInDatabase( ) throws ApplicationException {
        saveOrUpdate(this.getSygnatureFXObjectPropertyEDIT());
    }
    private void saveOrUpdate(SygnatureFX sygnatureFXObjectProperty) throws ApplicationException {
        SygnatureDao sygnatureDao = new SygnatureDao();
        Sygnature sygnature = ConvertToSygnature.convertSygnatureFXToSygnature(sygnatureFXObjectProperty);
        sygnatureDao.creatOrUpdate(sygnature);

        DbManager.closeConnectionSource();
        this.fillSygnatureList();
    }


public void fillSygnatureList() throws ApplicationException {
    SygnatureDao sygnatureDao=new SygnatureDao();
    this.sygnatureFXObservableList.clear();
    List<Sygnature> sygnatureList=sygnatureDao.queryForAll(Sygnature.class);
                sygnatureList.forEach(a->{
                    SygnatureFX sygnatureFX=ConvertToSygnature.convertSygnatureToSygnatureFX(a);
                    this.sygnatureFXObservableList.add(sygnatureFX);
        });

    DbManager.closeConnectionSource();
}
public void deleteSygnatureInDataBase() throws ApplicationException {
    SygnatureDao sygnatureDao=new SygnatureDao();
    sygnatureDao.deleteById(Sygnature.class,this.getSygnatureFXObjectPropertyEDIT().getId());

    DbManager.closeConnectionSource();
    this.fillSygnatureList();
}

    public SygnatureFX getSygnatureFXObjectProperty() {
        return sygnatureFXObjectProperty.get();
    }

    public ObjectProperty<SygnatureFX> sygnatureFXObjectPropertyProperty() {
        return sygnatureFXObjectProperty;
    }

    public void setSygnatureFXObjectProperty(SygnatureFX sygnatureFXObjectProperty) {
        this.sygnatureFXObjectProperty.set(sygnatureFXObjectProperty);
    }

    public ObservableList<SygnatureFX> getSygnatureFXObservableList() {
        return sygnatureFXObservableList;
    }

    public void setSygnatureFXObservableList(ObservableList<SygnatureFX> sygnatureFXObservableList) {
        this.sygnatureFXObservableList = sygnatureFXObservableList;
    }

    public SygnatureFX getSygnatureFXObjectPropertyEDIT() {
        return sygnatureFXObjectPropertyEDIT.get();
    }

    public ObjectProperty<SygnatureFX> sygnatureFXObjectPropertyEDITProperty() {
        return sygnatureFXObjectPropertyEDIT;
    }

    public void setSygnatureFXObjectPropertyEDIT(SygnatureFX sygnatureFXObjectPropertyEDIT) {
        this.sygnatureFXObjectPropertyEDIT.set(sygnatureFXObjectPropertyEDIT);
    }
}