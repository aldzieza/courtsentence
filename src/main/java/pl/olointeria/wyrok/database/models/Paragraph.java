package pl.olointeria.wyrok.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName ="PARAGRAPHS")
public class Paragraph implements BaseModel{

    public Paragraph() {
    }

    @DatabaseField(generatedId =true)
    private int id;

    @DatabaseField(columnName ="PARAGRAPH", canBeNull =false,unique = true)
    private String paragraphNo;
    //TU JEST KOLEKCJA WYROKÓW
    @ForeignCollectionField(columnName ="SENTENCE_ID")
    private ForeignCollection<Sentence> sentences;
//    @ForeignCollectionField(columnName ="SYGNATURE_ID")
//    private ForeignCollection<Sygnature> sygnatures;
//GETTERY i SETTERY


//    public ForeignCollection<Sygnature> getSygnatures() {
//        return sygnatures;
//    }
//
//    public void setSygnatures(ForeignCollection<Sygnature> sygnatures) {
//        this.sygnatures = sygnatures;
//    }

    public ForeignCollection<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(ForeignCollection<Sentence> sentences) {
        this.sentences = sentences;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParagraphNo() {
        return paragraphNo;
    }

    public void setParagraphNo(String paragraphNo) {
        this.paragraphNo = paragraphNo;
    }

    //    @Override
//    public String toString() {
//        return paragraphNo;
//    }
}
