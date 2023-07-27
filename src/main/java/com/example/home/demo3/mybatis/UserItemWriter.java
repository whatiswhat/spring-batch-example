package com.example.home.demo3.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class UserItemWriter extends MyBatisBatchItemWriter<User> {

    private static final Logger log = LoggerFactory.getLogger(UserItemWriter.class);
    
//    public MyBatisBatchItemWriter<User> writer() throws Exception {
//        // TODO Auto-generated method stub
//        return new MyBatisBatchItemWriterBuilder<User>()
//                .build();
//        
//    }
    
    @Override
    public void write(Chunk<? extends User> items) {
        // TODO Auto-generated method stub
        super.write(items);
    }
    
    

//    List<User> items;
//
//    public UserItemWriter() {
//        
//        org.mybatis.spring.batch.MyBatisBatchItemWriter<T>
//        List<User> items = new ArrayList<User>();
//        
//        User user1 = new User();
//        user1.setId(1001);
//        user1.setName("User1001");
//        user1.setEmail("1001@sony.com");
//        
//        User user2 = new User();
//        user2.setId(1002);
//        user2.setName("User1002");
//        user2.setEmail("1002@sony.com");
//        
//        User user3 = new User();
//        user3.setId(1003);
//        user3.setName("User1003");
//        user3.setEmail("1003@sony.com");
//        
//        items.add(user1);
//        items.add(user2);
//        items.add(user3);
//        
//        this.items = items;
//    }
//    
//    public UserItemWriter(List<User> items) {
//        this.items = items;
//    }
//
//    @Override
//    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//
//        if (!items.isEmpty()) {
//            return (User) items.remove(0);
//        }
//
//        return null;
//    }
}
