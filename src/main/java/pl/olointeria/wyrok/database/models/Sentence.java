package pl.olointeria.wyrok.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "SENTENCES")
public class Sentence implements BaseModel {
    //public static final String PARAGRAPH_ID = "PARAGRAPH_ID";
  //  public static final String SYGN_ID = "SYGNATURE_ID";
public Sentence(){

}


    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "PARAGRAPH_ID", foreign = true, foreignAutoRefresh = true,foreignAutoCreate = true,canBeNull = false)
    private Paragraph paragraph;
    @DatabaseField(columnName = "AUTHOR_ID", foreign = true, foreignAutoRefresh = true,foreignAutoCreate = true,canBeNull = false)
    private Sygnature sygnature;

    @DatabaseField(columnName = "DESCRIPTION")
    private String description;
    @DatabaseField(columnName ="MAX_SENTENCE",width = 1)
    private Double maxSentence;
    @DatabaseField(columnName ="MIN_SENTENCE",width = 1)
    private Double minSentence;

    //GETTERY I SETTERY


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraph(Paragraph paragraph) {
        this.paragraph = paragraph;
    }

    public Sygnature getSygnature() {
        return sygnature;
    }

    public void setSygnature(Sygnature sygnature) {
        this.sygnature = sygnature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaxSentence() {
        return maxSentence;
    }

    public void setMaxSentence(Double maxSentence) {
        this.maxSentence = maxSentence;
    }

    public Double getMinSentence() {
        return minSentence;
    }

    public void setMinSentence(Double minSentence) {
        this.minSentence = minSentence;
    }
}
