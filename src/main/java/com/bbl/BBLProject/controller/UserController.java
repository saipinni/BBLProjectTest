package com.bbl.BBLProject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bbl.BBLProject.domain.ListingNested;
import com.bbl.BBLProject.domain.Post;
import com.bbl.BBLProject.domain.User;

@RestController
@RequestMapping("/api/user") 
public class UserController { 
    
    @RequestMapping("/getList")
    private ResponseEntity<User[]> getUserList() {
        String uri = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> response = restTemplate.getForEntity(uri,User[].class);
        return response;
    }

    @RequestMapping("/getById")
    private User getUserById(@RequestParam("id") Integer id) {
        String uri = "https://jsonplaceholder.typicode.com/users/"+ id;
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(uri, User.class);
        return user;
    }

    
    private List<Post> getPostList(@RequestParam("userId") Integer userId) {
        String uri = "https://jsonplaceholder.typicode.com/posts/"+ userId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Post[]> response = restTemplate.getForEntity(uri,Post[].class);
        List<Post> resListAllPost = new ArrayList<Post>();
        Post[] arrPost = response.getBody();
        for(Post p : arrPost){
            resListAllPost.add(p);
        }
        return resListAllPost;
    }

    @RequestMapping("/ListingNasted")
    private List<ListingNested>  getListingNasted(@RequestParam("userId") Integer userId) {
        User user = this.getUserById(userId);
        String uri = "https://jsonplaceholder.typicode.com/posts/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Post[]> response = restTemplate.getForEntity(uri,Post[].class);
        List<ListingNested> resListAllPost = new ArrayList<ListingNested>();
        Post[] arrPost = response.getBody();
        for(Post p : arrPost){
            if(p.getUserId() == userId){
                ListingNested listNested  = new ListingNested();
                listNested.setUserId(user.getId());
                listNested.setName(user.getName());
                listNested.setUsername(user.getUsername());
                listNested.setEmail(user.getEmail());
                listNested.setPostId(p.getId());
                listNested.setTitle(p.getTitle());
                listNested.setBody(p.getBody());
                resListAllPost.add(listNested);
            }
           
        }
        return resListAllPost;
    }

}
