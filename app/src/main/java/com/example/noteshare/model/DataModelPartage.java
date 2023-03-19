package com.example.noteshare.model;

import java.util.ArrayList;
import java.util.Date;

public class DataModelPartage extends DataModel {
    ArrayList<String> TabMembre;
    public DataModelPartage(){
        super();
    }
    public DataModelPartage(String id, String titre, String contenu, Date date, Boolean isLike,ArrayList<String> TabMembre) {
        super(id, titre, contenu, date, isLike);
        this.TabMembre = TabMembre;
    }

    public ArrayList<String> getTabMembre() {
        return TabMembre;
    }
}
