package ru.legonat.sportsnow.DatabaseReader;

public class Club {

    public int id;
    public String name, url;

    public Club(){}

    @Override
    public String toString() {
        return "Club [id=" + id
                + ",name=" + name
                + ",url=" + url
                +"]";
    }
}