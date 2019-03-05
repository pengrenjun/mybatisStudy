package tk.mybatis.web.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liuzh
 */
@Controller
public class IndexController {
    @RequestMapping(value = {"", "/index"})
    public ModelAndView dicts() {
        ModelAndView mv = new ModelAndView("/WEB-INF/index.jsp");
        mv.addObject("now", new Date());
        return mv;
    }


    @RequestMapping(value = "intosystem")
    public ModelAndView intosystem() {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("redirect:/dicts");
        } catch (Exception e){
        }
        return mv;
    }
}
