package com.manager.demo.Controllers;


import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Repos.WaiterInfoRepo;
import com.manager.demo.Services.ManagerInfoService;
import com.manager.demo.Services.WaiterInfoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeManager {

    @Autowired
    HttpSession session;
    private final WaiterInfoService waiterInfoService;

    @Autowired
    private WaiterInfoRepo repo;

    public HomeManager(WaiterInfoService waiterInfoService) {
        this.waiterInfoService =waiterInfoService ;
    }


    @GetMapping("/managerDashboard")
    public String showAllWaiters(Model model)
    {
        if(session.getAttribute("manager") == null)
            return "redirect:/login";
        List<WaiterInfo> waiterInfos =  waiterInfoService.findAllWaiters();
        model.addAttribute("waiterinfos",waiterInfos);
        return "managerDashboard";
    }


    @GetMapping("/UpdateWaiter/{email}/edit")
    public String showUpdateFrom(@PathVariable("email") String email, Model model)
    {
        if(session.getAttribute("manager") == null)
            return "redirect:/login";
        WaiterInfo waiterInfo = waiterInfoService.getWaiterByEmail(email);
        model.addAttribute("waiter",waiterInfo);
        return "UpdateWaiter";
    }

    @PostMapping("/UpdateWaiter/{email}/update")
    public String updateWaiter(@PathVariable("email") String email, @ModelAttribute WaiterInfo updated)
    {
        if(session.getAttribute("manager") == null)
            return "redirect:/login";
        WaiterInfo existingWaiter = waiterInfoService.getWaiterByEmail(email);

        if(!email.equals(updated.getEmail()) && repo.findByEmail(updated.getEmail()).isPresent())
            return "redirect:/UpdateWaiter/" + email + "/edit";

        existingWaiter.setFullname(updated.getFullname());
        existingWaiter.setEmail(updated.getEmail());
        existingWaiter.setPassword(updated.getPassword());
        existingWaiter.setPhonenumber(updated.getPhonenumber());
        existingWaiter.setAddress(updated.getAddress());

        waiterInfoService.saveNewWaiter(existingWaiter);

        return"redirect:/managerDashboard";
    }
    @GetMapping ("/UpdateWaiter/{email}/delete")
    public String deleteWaiter(@PathVariable String email,Model model) {
        if(session.getAttribute("manager") == null)
            return "redirect:/login";
        // Call the service method to delete the waiter by email
        WaiterInfo waiterInfo  = waiterInfoService.getWaiterByEmail(email);
        waiterInfoService.deleteWaiter(waiterInfo);
        model.addAttribute("message","deletion successfully performed");
        // Redirect to a suitable URL after deletion, for example, the waiter list page
        return "redirect:/managerDashboard";
    }


    @GetMapping("/AddWaiter")
    public String showAddForm(Model model)
    {
        if(session.getAttribute("manager") == null)
            return "redirect:/login";
        model.addAttribute("waiter",new WaiterInfo());
        return "AddWaiter";
    }

    @PostMapping("/AddWaiter")
    public String AddNewWaiter(@ModelAttribute WaiterInfo waiterInfo,BindingResult result,Model model)
    {
        if(session.getAttribute("manager") == null)
            return "redirect:/login";

        //model.addAttribute("waiter",waiterInfo);
        if(result.hasErrors()){return "redirect:/AddWaiter";}

        if(repo.findByEmail(waiterInfo.getEmail()).isPresent())
        {
            result.rejectValue("Email",null,"Already Exists");
            return "redirect:/AddWaiter";
        }

        if(repo.findByFullname(waiterInfo.getFullname()).isPresent())
        {
            result.rejectValue("Fullname",null,"Already Exists");
            return "redirect:/AddWaiter";
        }

        waiterInfo.setRole("Waiter");

        repo.save(waiterInfo);

        return "redirect:/managerDashboard";
    }

}
