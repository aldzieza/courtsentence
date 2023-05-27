package pl.olointeria.wyrok.utils.converters;

import com.j256.ormlite.dao.LruObjectCache;
import pl.olointeria.wyrok.database.dao.SygnatureDao;
import pl.olointeria.wyrok.database.models.Sygnature;
import pl.olointeria.wyrok.modelFX.SygnatureFX;

public class ConvertToSygnature {

    public static Sygnature convertSygnatureFXToSygnature(SygnatureFX sygnatureFX) {
        SygnatureDao sygnatureDao = new SygnatureDao();
        Sygnature sygnature = new Sygnature();
        sygnature.setSygnatureNo(sygnatureFX.getSygnatureNo());
        sygnature.setId(sygnatureFX.getId());
        return sygnature;
    }
    public static SygnatureFX convertSygnatureToSygnatureFX(Sygnature sygnature) {
        SygnatureDao sygnatureDao = new SygnatureDao();
        SygnatureFX sygnatureFX=new SygnatureFX();
        sygnatureFX.setSygnatureNo(sygnature.getSygnatureNo());
        sygnatureFX.setId(sygnature.getId());
        return sygnatureFX;
    }
}
