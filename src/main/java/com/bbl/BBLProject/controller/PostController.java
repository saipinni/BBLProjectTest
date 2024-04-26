package com.bbl.BBLProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.bbl.BBLProject.domain.Post;


@RestController
@RequestMapping("/api/post") 
public class PostController {
    @SuppressWarnings("null")
    @RequestMapping("/demo/{id}")
    private ModelAndView getPost(@PathVariable Integer id,Model model) {
        String uri = "https://jsonplaceholder.typicode.com/posts/"+ id;
        RestTemplate restTemplate = new RestTemplate();
        Post post = restTemplate.getForObject(uri, Post.class);

        ModelAndView modelAndView = new ModelAndView("post");
        modelAndView.addObject("userId", post.getUserId());
        modelAndView.addObject("id", post.getId());
        modelAndView.addObject("title", post.getTitle());
        modelAndView.addObject("body", post.getBody());
        
        return modelAndView;
    }
    @RequestMapping("/getById")
    private Post getPostById(@RequestParam("id") Integer id) {
        String uri = "https://jsonplaceholder.typicode.com/posts/"+ id;
        RestTemplate restTemplate = new RestTemplate();
        Post post = restTemplate.getForObject(uri, Post.class);
        return post;
    }

    @RequestMapping("/getList")
    private ResponseEntity<Post[]> getPost() {
        String uri = "https://jsonplaceholder.typicode.com/posts";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Post[]> response = restTemplate.getForEntity(uri,Post[].class);

        return response;
    }

    @RequestMapping("/createPost")
    @PostMapping(path = "https://jsonplaceholder.typicode.com/posts",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> greetPersonPost(@RequestBody Post data) {
        Post post = new Post();
        post.setId(data.getId());
        post.setUserId(data.getUserId());
        post.setTitle(data.getTitle());
        post.setBody(data.getBody());
	    return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }

    @RequestMapping("/updatePost")
    @PutMapping(path = "https://jsonplaceholder.typicode.com/posts",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> greetPersonPut(@RequestBody Post data) {
        Post post = new Post();
        post.setId(data.getId());
        post.setUserId(data.getUserId());
        post.setTitle("Update Title");
        post.setBody(data.getBody());
	    return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }

    @RequestMapping("/patchPost")
    @PatchMapping(path = "https://jsonplaceholder.typicode.com/posts/1",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> greetPersonPatch(@RequestBody Post data) {
        Post post = (Post)this.getPostById(1);
        post.setTitle(data.getTitle());
	    return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }
    


    @DeleteMapping("/deletePost")
    public List<Post> greetPersonDe(@RequestParam("id") Integer id) {
        ResponseEntity<Post[]> postList = (ResponseEntity<Post[]>)this.getPost();
        List<Post> resList = new ArrayList<Post>();
        Post[] arrPost = postList.getBody();
        for(Post p : arrPost){
            if(id != p.getId()){
                resList.add(p);
            }
        }
	    return resList;
    }

    @RequestMapping("/filterPost")
    public List<Post> filterPost(@RequestParam("userId") Integer userId) {
        ResponseEntity<Post[]> postList = (ResponseEntity<Post[]>)this.getPost();
        List<Post> resListAllPost = new ArrayList<Post>();
        Post[] arrPost = postList.getBody();
        for(Post p : arrPost){
            resListAllPost.add(p);
        }
        List<Post> resFilterList = resListAllPost
        .stream()
        .filter(r -> r.getUserId() == userId)
        .collect(Collectors.toList());
	    return resFilterList;
    }
}
