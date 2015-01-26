package grimgame.somn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import grimgame.framework.FileIO;

/**
 * Created by grimnard on 1/26/2015.
 */
public class Settings {
    // Create variables that will hold the values you want to save in your game.
    // Default values:
    final static int MAX_HIGH_SCORE = 10;
    final static String STR_SOUND_ENABLED = "soundEnabled";
    public static boolean soundEnabled = true;
    final static String STR_HIGH_SCORES = "highScores";
    public static ArrayList<Integer> highScores = new ArrayList<>();
    final static String STR_CURRENT_LEVEL = "currentLevel";
    public static int currentLevel = 0;
    private static String saveFileName = "somn.savedata";

    private static ArrayList<SettingsList> listSavedSettings = new ArrayList<>();
    private static ArrayList<SettingsList> otherSavedSettings = new ArrayList<>();

    public static void addOtherSavedSettings(String name,String value){
        for (int i = 0; i < otherSavedSettings.size() ; i++) {
            SettingsList sL = otherSavedSettings.get(i);
            if (sL.getName() == name){
                otherSavedSettings.remove(i);
                break;
            }

        }
        SettingsList sL = new SettingsList(name,value);
        otherSavedSettings.add(sL);
    }
    public String getOtherSavedSettings(String name){
        for (int i = 0; i < otherSavedSettings.size() ; i++) {
            SettingsList sL = otherSavedSettings.get(i);
            if (sL.getName() == name){
               return sL.getSavedValue();
            }

        }
        return null;

    }

    private static void setVariblesSaved(String name, String value) {
        switch (name) {
            case STR_SOUND_ENABLED:
                soundEnabled = Boolean.parseBoolean(value);
                break;
            case STR_HIGH_SCORES:
                addScore(Integer.getInteger(value));
                break;
            case STR_CURRENT_LEVEL:
                currentLevel = Integer.getInteger(value);
                break;
            default:
                addOtherSavedSettings(name,value);
        }
    }

    private static void setSavedVaribles() {
        listSavedSettings.clear();

        for (int i = 0; i < highScores.size(); i++) {
            SettingsList sL = new SettingsList(STR_HIGH_SCORES, Integer.toString(highScores.get(i)));
            listSavedSettings.add(sL);
        }
        SettingsList sL = new SettingsList(STR_SOUND_ENABLED, Boolean.toString(soundEnabled));
        listSavedSettings.add(sL);

        sL = new SettingsList(STR_CURRENT_LEVEL, Integer.toString(currentLevel));
        listSavedSettings.add(sL);
        for (int i = 0; i < otherSavedSettings.size() ; i++) {
            listSavedSettings.add(otherSavedSettings.get(i));
        }
    }

    public static void save(FileIO files) {
        setSavedVaribles();
        BufferedWriter out = null;
        try {

            // Writes a file called .savedata to the SD Card
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(saveFileName)));

            // Line by line ("\n" creates a new line), write the value of each variable to the file.
            for (int i = 0; i < listSavedSettings.size(); i++) {
                SettingsList sL = listSavedSettings.get(i);

                out.write("<");
                out.write(sL.getName());
                out.write(">");
                out.write(sL.getSavedValue());
                out.write("<\\");
                out.write(sL.getName());
                out.write(">");
                out.write("\n");
            }


            // This section handles errors in file management!

        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            // Reads file called Save Data
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(saveFileName)));
            highScores.clear();
            String inLine;
            inLine = in.readLine();
            // Loads values from the file and replaces default values.
            while (inLine != null) {

                int leftCarrot = inLine.indexOf("<");
                int rightCarrot = inLine.indexOf(">");
                int endCarrot = inLine.indexOf("<\\");
                String name = inLine.substring(leftCarrot, rightCarrot - 1);
                String value = inLine.substring(rightCarrot, endCarrot - 1);
                setVariblesSaved(name, value);
                inLine = in.readLine();

            }
        } catch (IOException e) {
            // Catches errors. Default values are used.
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }


    // Use this method to add 5 numbers to the high score.
    public static void addScore(int score) {
        if( highScores.size() == 0){
                highScores.add(score);
            return;
        }
        if(highScores.get(highScores.size()-1) > score && highScores.size() == MAX_HIGH_SCORE){
            return;}

        ArrayList<Integer> oldScores = highScores;
        highScores.clear();
        int lnSize = oldScores.size();
        int x =0;

        for (int i = 0; i < lnSize; i++) {
            if (i > MAX_HIGH_SCORE){ break;}

            x = oldScores.get(i);
            if (x > score) {
                highScores.add(x);
            } else {
                highScores.add(score);
                score = x;
            }

        }
        if(x > 0 && highScores.size()< MAX_HIGH_SCORE) {
            highScores.add(x);
        }
    }
}
