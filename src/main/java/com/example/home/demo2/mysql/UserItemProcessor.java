package com.example.home.demo2.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, User> {

    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);

    @Override
    public User process(final User user) throws Exception {
        final Integer id = 1001;
        final String name = "User1001";
        final String email = "1001@sony.com"; 
        
        final User transformedUser = new User();
        transformedUser.setId(id);
        transformedUser.setName(name);
        transformedUser.setEmail(email);

        log.info("transformedUser is prepared: " + transformedUser);

        return transformedUser;
    }

}