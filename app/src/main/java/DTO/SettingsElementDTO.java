package DTO;

import com.example.root.sens.R;

public class SettingsElementDTO {
    private String title;
    private int iconID;

    public SettingsElementDTO(String title, int iconID) {
        this.title = title;
        this.iconID = iconID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
