package pl.olointeria.wyrok.utils.converters;

import pl.olointeria.wyrok.database.models.Paragraph;
import pl.olointeria.wyrok.modelFX.ParagraphFX;

public class ConverterParagraph {
    // z Paragraph na ParagraphFX
public static ParagraphFX convertParagraphToParagraphFX(Paragraph paragraf) {
    ParagraphFX paragraphFX = new ParagraphFX();
    paragraphFX.setId(paragraf.getId());
    paragraphFX.setParagraphNo(paragraf.getParagraphNo());
    return paragraphFX;
}
}
