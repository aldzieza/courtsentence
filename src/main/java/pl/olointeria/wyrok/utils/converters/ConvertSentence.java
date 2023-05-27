package pl.olointeria.wyrok.utils.converters;

import pl.olointeria.wyrok.database.models.Sentence;
import pl.olointeria.wyrok.modelFX.SentenceFX;

public class ConvertSentence {

    public static Sentence convertSentenceFXTOSentence(SentenceFX  sentenceFX){
        Sentence sentence=new Sentence();
        sentence.setId(sentenceFX.getId());
        sentence.setDescription(sentenceFX.getDescSentence());

        sentence.setMaxSentence(sentenceFX.getMaxSentence());
        sentence.setMinSentence(sentenceFX.getMinSentence());
        //ale brakuje nam jeszcze Paragrafu i Sygnatury które są w FX
        return sentence;

    }

    public static SentenceFX convertSentenceTOSentenceFX(Sentence sentence) {
        SentenceFX sentenceFX=new SentenceFX();
        sentenceFX.setId(sentence.getId());
        sentenceFX.setDescSentence(sentence.getDescription());
        //sentenceFX.setMinSentence(sentence.getMinSentence());
        //tu setMaxSentence przyjmuje zmienną double a w getMaxSentence() przyjmuje Double
        //sentenceFX.setMaxSentence(sentence.getMaxSentence());
        double maxSentence1 =(double) sentence.getMaxSentence();
        double minSentence2 =(double) sentence.getMinSentence();

        sentenceFX.setMaxSentence(ConvertSentence.convertToDouble(maxSentence1));
        sentenceFX.setMinSentence(ConvertSentence.convertToDouble(minSentence2));
        //sentenceFX.setMaxSentence(ConvertSentence.convertToDouble(sentence.getMaxSentence().doubleValue()));
       // double maxSentence = sentence.getMaxSentence().doubleValue();
       // double minSentence = sentence.getMinSentence().doubleValue();
       // String string = ((Double) maxSentence).toString();
        //System.out.println("maxSentence:\t"+string+maxSentence);

        //sentenceFX.setMinSentence(ConvertSentence.convertToDouble(sentence.getMinSentence().doubleValue()));

        //ale brakuje nam jeszcze Paragrafu i Sygnatury które są w sentence
        sentenceFX.setParagraphFX(ConverterParagraph.convertParagraphToParagraphFX(sentence.getParagraph()));
        sentenceFX.setSygnatureFX(ConvertToSygnature.convertSygnatureToSygnatureFX(sentence.getSygnature()));
        return sentenceFX;
    }

    private static double convertToDouble(Double maxSentence) {

       // Double aDouble = Double.valueOf(maxSentence);

        return (double) maxSentence;
    }
}
