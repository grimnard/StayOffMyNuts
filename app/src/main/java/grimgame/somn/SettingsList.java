package grimgame.somn;

/**
 * Created by grimnard on 1/26/2015.
 */
public class SettingsList {
    private String name;
    private String savedValue;


    public SettingsList(String title,String value){
        name = title;
        savedValue = value;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSavedValue() {
        return savedValue;
    }

    public void setSavedValue(String savedValue) {
        this.savedValue = savedValue;
    }
}
