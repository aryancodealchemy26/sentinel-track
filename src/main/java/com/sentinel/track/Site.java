package com.sentinel.track;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Site {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    
    public String name; // Site Name
    public String url;  // Website Link
    public String st;   // Status (UP/DOWN)
    public String last; // Last checked time

    // Noob-style empty constructor
    public Site() {}
}