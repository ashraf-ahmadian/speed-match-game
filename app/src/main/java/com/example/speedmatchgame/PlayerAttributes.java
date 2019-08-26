package com.example.speedmatchgame;

public class PlayerAttributes {

    private String name;
    private int score;
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "PlayerAttributes{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", level=" + level +
                '}';
    }
}
