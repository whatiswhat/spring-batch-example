package com.example.home.demo2.mysql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class UserItemReader implements ItemReader<User> {

    private static final Logger log = LoggerFactory.getLogger(UserItemReader.class);

    List<User> items;

    public UserItemReader() {
        List<User> items = new ArrayList<User>();
        
        User user1 = new User();
        user1.setId(1001);
        user1.setName("User1001");
        user1.setEmail("1001@sony.com");
        
        User user2 = new User();
        user2.setId(1002);
        user2.setName("User1002");
        user2.setEmail("1002@sony.com");
        
        User user3 = new User();
        user3.setId(1003);
        user3.setName("User1003");
        user3.setEmail("1003@sony.com");
        
        items.add(user1);
        items.add(user2);
        items.add(user3);
        
        this.items = items;
    }
    
    public UserItemReader(List<User> items) {
        this.items = items;
    }

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!items.isEmpty()) {
            return (User) items.remove(0);
        }

        return null;
    }
}
