/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Martin
 */
public class MovieDTO {
    
    public Long id;
    public int year;
    public String name;

    public MovieDTO(Long id, int year, String name) {
        this.id = id;
        this.year = year;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }
    

}
