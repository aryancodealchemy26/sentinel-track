// package com.sentinel.track;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class MonitorService {

//     @Autowired
//     SiteRepo repo;

//     // This tells Spring to run this method every 30,000 milliseconds (30 seconds)
//     @Scheduled(fixedRate = 30000)
//     public void checkAll() {
//         List<Site> all = repo.findAll();
//         System.out.println("--- QA CRON JOB STARTED: Checking " + all.size() + " sites ---");
        
//         for (Site s : all) {
//             System.out.println("Testing: " + s.url);
//             try {
//                 URL siteUrl = new URL(s.url);
//                 HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
//                 conn.setRequestMethod("GET");
//                 conn.setConnectTimeout(3000);
                
//                 int code = conn.getResponseCode();
//                 System.out.println("Response Code for " + s.name + ": " + code);
                
//                 if (code == 200) {
//                     s.st = "UP";
//                 } else {
//                     s.st = "DOWN (" + code + ")";
//                 }
//             } catch (Exception e) {
//                 System.out.println("Error testing " + s.url + ": " + e.getMessage());
//                 s.st = "DOWN (Error)";
//             }
//             s.last = LocalDateTime.now().toString();
//             repo.save(s);
//         }
//         System.out.println("--- QA CRON JOB FINISHED ---");
//     }
// }
package com.sentinel.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service; // CRITICAL IMPORT
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service // <-- MAKE SURE THIS IS HERE
public class MonitorService {

    @Autowired
    SiteRepo repo;

    // @Scheduled(fixedRate = 30000) // Runs every 30 seconds
    // public void checkAll() {
    //     List<Site> all = repo.findAll();
    //     // This line will force a message into your terminal
    //     System.out.println(">>> MONITOR CHECK START: " + all.size() + " sites found.");
        
    //     for (Site s : all) {
    //         try {
    //             URL siteUrl = new URL(s.url);
    //             HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
    //             conn.setRequestMethod("GET");
    //             conn.setConnectTimeout(3000);
                
    //             int code = conn.getResponseCode();
    //             s.st = (code == 200) ? "UP" : "DOWN (" + code + ")";
    //             System.out.println("Site: " + s.name + " is " + s.st);
    //         } catch (Exception e) {
    //             s.st = "DOWN (Error)";
    //             System.out.println("Site: " + s.name + " failed: " + e.getMessage());
    //         }
    //         s.last = LocalDateTime.now().toString();
    //         repo.save(s);
    //     }
    // }

    @Scheduled(fixedRate = 30000)
    public void checkAll() {
    List<Site> all = repo.findAll();
    System.out.println(">>> MONITOR CHECK START: " + all.size() + " sites found.");
        for (Site s : all) {
            try {
                long start = System.currentTimeMillis(); // Start stopwatch
                
                URL siteUrl = new URL(s.url);
                HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                int code = conn.getResponseCode();
                
                long end = System.currentTimeMillis(); // Stop stopwatch
                s.ping = end - start; // Calculate duration
                
                s.st = (code == 200) ? "UP" : "DOWN (" + code + ")";
                System.out.println("Site: " + s.name + " is " + s.st);

            } catch (Exception e) {
                s.st = "DOWN (Error)";
                s.ping = 0;
                System.out.println("Site: " + s.name + " failed: " + e.getMessage());

            }
            s.last = LocalDateTime.now().toString();
            repo.save(s);
        }
    }
}
