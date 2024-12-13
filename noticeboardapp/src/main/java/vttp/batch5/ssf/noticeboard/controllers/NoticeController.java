package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.models.Post;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

import org.springframework.web.bind.annotation.PostMapping;



// Use this class to write your request handlers

@Controller
@RequestMapping(value={"/","/notice"})
public class NoticeController {

    @Autowired 
    NoticeService noticeService;

    @GetMapping
    public String getNoticeBoardPage(Model model){
        Notice n = new Notice();
        model.addAttribute("notice",n);
        return "notice";

    }

    @PostMapping("/notice")
    public String postNoticeBoardPage(@Valid @ModelAttribute("notice") Notice notice, BindingResult result, Model model ) {
        model.addAttribute("notice",notice);
        if(result.hasErrors()){
            return "notice";
        }
        Notice n = new Notice(notice.getTitle(),notice.getPoster(),notice.getPostDate(),notice.getCategories(),notice.getText());
        Post p = noticeService.postToNoticeServer(n);
        if (p.getIsSuccess()==true){
            String pid = p.getId();
            model.addAttribute("id",pid);
            return "view2";
        }
 
        else{
            String message = p.getMessage();
            model.addAttribute("message",message);
            return "view3";
        }
        
    }
    @GetMapping("/status")
    @ResponseBody
    public ResponseEntity<String> getStatus(){
        Object key = noticeService.getRandomKey();
        if (key!=null){
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE,"application/json");
            return new ResponseEntity<>(headers,HttpStatus.OK);
            
        }

        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE,"application/json");
            return new ResponseEntity<>(headers,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
