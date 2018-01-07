package com.mygdx.anima.tools;

import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.StartScreen;

/**
 * Created by Steffen on 08.10.2017.
 */

public class UniversalDisposer {
  public static void disposeCurrentGame(AnimaRPG game){
        ((Playscreen)game.previousScreen).aktiveElementeEntfernen();
        game.currentPlayScreen.dispose();
        game.currentScreen.dispose();
        game.changeScreen(new StartScreen(game));
    }
}
