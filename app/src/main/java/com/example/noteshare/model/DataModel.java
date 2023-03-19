package com.example.noteshare.model;

import java.util.Date;

public class DataModel {
      protected  String titre;
      protected  String contenu;
      protected  Date date;
      protected  Boolean isLike;
      protected  String id;
    public   DataModel(){}
      public DataModel(String id,String titre, String contenu, Date date, Boolean isLike) {
           this.titre = titre;
           this.contenu = contenu;
           this.date = date;
           this.isLike = isLike;
           this.id = id;
      }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {return  id;}


}
