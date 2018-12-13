package com.example.manu.radiov2.Classes;

import java.util.List;

public class AProgram {
    private String title;
    private String category;
    private List<BProgram> programs;

    public AProgram(String title, String category, List<BProgram> programs) {
        this.title = title;
        this.category = category;
        this.programs = programs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<BProgram> getPrograms() {
        return programs;
    }

    public void setPrograms(List<BProgram> programs) {
        this.programs = programs;
    }
}
