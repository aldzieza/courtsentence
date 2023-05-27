package pl.olointeria.wyrok.main;

import pl.olointeria.wyrok.database.dao.SentenceDao;
import pl.olointeria.wyrok.database.dao.ParagraphDao;
import pl.olointeria.wyrok.database.dbutils.DbManager;
import pl.olointeria.wyrok.database.models.Sygnature;
import pl.olointeria.wyrok.database.models.Sentence;
import pl.olointeria.wyrok.database.models.Paragraph;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

import java.util.Date;

public class FillDataBase {
    public static  void fillDatabase(){
        Paragraph paragraph1 = new Paragraph();
        paragraph1.setParagraphNo("123");
        System.out.println("paragraf1:\t"+paragraph1);
        Sygnature sygnature1 = new Sygnature();
        sygnature1.setSygnatureNo("SO1245/2021");

        Sentence sentence1 = new Sentence();
        sentence1.setParagraph(paragraph1);
        sentence1.setSygnature(sygnature1);
        sentence1.setDescription("kradzie¿ z w³amaniem");
        sentence1.setMaxSentence(12.00);
        sentence1.setMinSentence(4.00); // Category = Paragraph Book = Sentence  Autor = Sygnature



        Paragraph paragraph2 = new Paragraph();
        paragraph2.setParagraphNo("162");
        ParagraphDao paragraphDao = new ParagraphDao();
        System.out.println("paragrafDao:\t"+paragraphDao);
        try {
            paragraphDao.creatOrUpdate(paragraph2);
            DbManager.closeConnectionSource();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }


        Paragraph paragraph3 = new Paragraph();
        paragraph3.setParagraphNo("273");
        Sygnature sygnature2 = new Sygnature();// Category = Paragraph Book = Sentence  Autor = Sygnature
        sygnature2.setSygnatureNo("MK140536/2021");

        Sentence sentence2 = new Sentence();
        sentence2.setParagraph(paragraph3);
        sentence2.setSygnature(sygnature2);
        sentence2.setMaxSentence(26.00);
        sentence2.setMinSentence(14.);
        sentence2.setDescription("Rozbój z broni¹ ");


        Paragraph paragraph4 = new Paragraph();
        paragraph4.setParagraphNo("304");
        Sygnature sygnature3 = new Sygnature();
        sygnature3.setSygnatureNo("JR658448/2457");
        // Category = Paragraph
        // Book = Sentence
        // Autor = Sygnature

        Sentence sentence3 = new Sentence();
        sentence3.setParagraph(paragraph4);
        sentence3.setSygnature(sygnature3);
        sentence3.setMaxSentence(56.);
        sentence3.setMinSentence(42.);
        sentence3.setDescription("Gwa³t na nieletnim");// Category = Paragraph
        // Book = Sentence
        // Autor = Sygnature
        Sygnature sygnature4 = new Sygnature();
        sygnature4.setSygnatureNo("TJ2154/2011 ");

        Sentence sentence4 = new Sentence();
        sentence4.setParagraph(paragraph4);
        sentence4.setSygnature(sygnature4);
        sentence4.setMaxSentence(42.);
        sentence4.setMinSentence(32.);
        sentence4.setDescription("kradzie¿ z broni¹");


        SentenceDao sentenceDao = new SentenceDao();
        try {
            sentenceDao.creatOrUpdate(sentence1);
            sentenceDao.creatOrUpdate(sentence2);
            sentenceDao.creatOrUpdate(sentence3);
            sentenceDao.creatOrUpdate(sentence4);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        DbManager.closeConnectionSource();
    }
}

