package com.codescafe.dailytask.Util;

import java.io.Serializable;

public class TaskModel implements Serializable {
    String id,title,subject,date,picture,voice,description,theme,font,color,size;

    public TaskModel(String id, String title, String subject, String date, String picture, String voice, String description) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.date = date;
        this.picture = picture;
        this.voice = voice;
        this.description = description;
    }

    public TaskModel(String id, String title, String subject, String date, String picture, String voice, String description, String theme) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.date = date;
        this.picture = picture;
        this.voice = voice;
        this.description = description;
        this.theme = theme;
    }

    public TaskModel(String id, String title, String subject, String date, String picture, String voice, String description, String theme, String font, String color, String size) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.date = date;
        this.picture = picture;
        this.voice = voice;
        this.description = description;
        this.theme = theme;
        this.font = font;
        this.color = color;
        this.size = size;
    }

    public TaskModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
