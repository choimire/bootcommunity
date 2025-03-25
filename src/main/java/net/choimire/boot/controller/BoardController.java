package net.choimire.boot.controller;



import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import net.choimire.boot.entity.Board;
import net.choimire.boot.service.BoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;









@Controller
public class BoardController {
        private final BoardService service;
        public BoardController(BoardService service){
            this.service = service;
        }
        //게시물 조회
        @GetMapping("/")
        public String list(
            @PageableDefault(size=10,sort="id", direction= Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "searchKey", required = false)String searchKey,
            @RequestParam(value = "searchVal",required = false)String searchVal, 
            
            Model model) {
            Page<Board> lists;
            if(searchKey != null && searchVal != null && !searchVal.isEmpty()){
                lists = service.search(searchKey, searchVal,pageable);
            }else{
                lists= service.findAll(pageable);
            }
            model.addAttribute("lists", lists);
            model.addAttribute("searchVal", searchVal);
            model.addAttribute("searchKey", searchKey);
            // //시간을 24시간이후를 rdate로 보냄.  
            // List<Map<String,Object>> res= new ArrayList<>();
            //     for(Board bbs : lists){
            //         Map<String,Object>map = new HashMap<>();
            //         map.put("bbs", bbs);

            //         LocalDateTime wdate = bbs.getWdate();
            //         Duration duration = Duration.between(wdate, LocalDateTime.now());

            //         String rdate;
            //         if(duration.toHours()<24){
            //             rdate = wdate.format(DateTimeFormatter.ofPattern("HH:mm"));
            //         }else{
            //             rdate = wdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //         }
            //         map.put("rdate", rdate);
            //         res.add(map);
            //     }
            model.addAttribute("lists" ,lists);
        return "list";
        }

        //글 상세보기
        @GetMapping("/view")
        public String view(@ModelAttribute("id") long id, Model model) {
            Optional<Board> board = service.findById(id);
            if(board.isPresent()){
                board.get().setHit(board.get().getHit() +1);
                service.save(board.get());
                
                board.get().setContent(Jsoup.clean(board.get().getContent(), Safelist.relaxed()));
                model.addAttribute("board", board.get());
                return "view";
            }else{
                return "redirect/";
            }
        }
        

        //글쓰기
        @GetMapping("/write")
        public String writeForm(Model model) {
            model.addAttribute("ntime",System.currentTimeMillis());
            return "write";
        }
        //글 등록
        @PostMapping("/write")
        public String write(@ModelAttribute Board board) {
            service.save(board);
            
            return "redirect:/";
        }
      //    글수정
      @GetMapping("/edit")
      public String editForm(@ModelAttribute("id") long id, Model model) {
        Optional<Board> board = service.findById(id);
                    
        if(board.isPresent()){
            // board.ifPresent(bbs ->{
            //     bbs.setContent(Jsoup.clean(bbs.getContent(), Safelist.relaxed()));
            board.get().setContent(Jsoup.clean(board.get().getContent(), Safelist.relaxed()));
            model.addAttribute("board", board.get());
            return "edit";
        }else{
            return "redirect/";
        }
      }

      @PostMapping("/edit")
      public String edit(@ModelAttribute Board board, Model model) {
        Optional<Board> bbs=service.findById(board.getId());
        if(bbs.isPresent()){
            Board obbs= bbs.get();
            if(obbs.getPass() !=null && obbs.getPass().equals(board.getPass())){
                //나머지는 bbs의 값으로 하고 board로 받은 값(이름,제목,내용)만 업데이트
                obbs.setWriter(board.getWriter());
                obbs.setTitle(board.getTitle());
                obbs.setContent(board.getContent());
                service.save(board);
                return "redirect:/view?id=" +board.getId();
            }else{
                model.addAttribute("error", "비밀번호가 일치하지않습니다.");
                model.addAttribute("board", obbs);
                return "edit";                
            }

        }
        service.save(board);
          
          return "redirect/";
      }
      
    
        
        //글 삭제
        @PostMapping("/delete")
        @ResponseBody
        public String delete(@RequestBody Map<String, Object> param){
            long id = Long.parseLong(param.get("id").toString());
            String pass = param.get("pass").toString();

            Optional<Board> boardOp = service.findById(id); //값이 있을수도있고 없을수도있을때 받는 Optional
            if(boardOp.isPresent()){
                Board board = boardOp.get();
                //비밀번호 비교
                if(board.getPass() != null && board.getPass().equals(pass)){
                    service.deleteById(id);
                    return "success";
                }else{
                    return "ohmygod";
                }
                }else{
                    return "fail";
                }

            }
        }
        


