package com.gideontong.sighduk.API;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class pulledData implements Serializable {
    private ArrayList<String> title = null;
    private ArrayList<String> name = null;
    private ArrayList<String> image_url = null;
    private ArrayList<String> url = null;
    private ArrayList<String> synopsis = null;
    private ArrayList<Long> episodes = null;
    private ArrayList<String> score = null;
    private ArrayList<String> rank = null;
    private ArrayList<String> id = null;

    public pulledData(ArrayList<String> title, ArrayList<String> image_url, ArrayList<String> url, ArrayList<String> synopsis, ArrayList<Long> episodes, ArrayList<String> score, ArrayList<String> rank, ArrayList<String> name, ArrayList<String> id){
        this.title = title;
        this.name = name;
        this.image_url = image_url;
        this.url = url;
        this.synopsis = synopsis;
        this.episodes = episodes;
        this.score = score;
        this.rank = rank;
        this.id = id;
    }

    public ArrayList<String> getTitle(){
        return title;
    }

    public ArrayList<String> getImage_url(){
        return image_url;
    }

    public ArrayList<String> getUrl(){
        return url;
    }

    public ArrayList<String> getSynopsis(){
        return synopsis;
    }

    public ArrayList<String> getScore(){
        return score;
    }

    public ArrayList<String> getRank(){
        return rank;
    }

    public ArrayList<String> getName(){
        return name;
    }

    public ArrayList<Long> getEpisodes(){
        return episodes;
    }

    public ArrayList<String> getID(){
        return id;
    }

    public int find(String name){
        int index = 0;
        for (int i = 0; i < this.title.size(); i++){
            if (this.title.get(i).equalsIgnoreCase(name)){
                index = i;
                break;
            }
        }
        return index;
    }
}
