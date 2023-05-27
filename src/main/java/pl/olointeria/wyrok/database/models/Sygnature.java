package pl.olointeria.wyrok.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName ="SYGNATURES")
public class Sygnature implements BaseModel{
    public Sygnature(){
    }
    @DatabaseField(generatedId =true)
    private int id;

    @DatabaseField(columnName ="SYGNATURE_NO", canBeNull =false,unique = true)
    private String sygnatureNo;
    //TU JEST KOLEKCJA WYROKÓW
    @ForeignCollectionField(columnName = "SENTENCE_ID")
    private ForeignCollection<Sentence> sentences;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSygnatureNo() {
        return sygnatureNo;
    }

    public void setSygnatureNo(String sygnatureNo) {
        this.sygnatureNo = sygnatureNo;
    }

    public ForeignCollection<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(ForeignCollection<Sentence> sentences) {
        this.sentences = sentences;
    }
//GETTERY I SETTERY


}
