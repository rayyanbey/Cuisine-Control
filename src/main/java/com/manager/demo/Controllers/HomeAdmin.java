package com.manager.demo.Controllers;


import com.manager.demo.Models.ManagerInfo;
import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Repos.ManagerInfoRepo;
import com.manager.demo.Services.ManagerInfoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeAdmin {

    @Autowired
    HttpSession session;

    private final ManagerInfoService service;

    @Autowired
    private ManagerInfoRepo repo;
    public HomeAdmin(ManagerInfoService service) {
        this.service = service;

    }


    @GetMapping("/adminDashboard")
    public String showAllManagers(Model model)
    {
        if(session.getAttribute("admin") == null)
            return "redirect:/login";
        List<ManagerInfo> managerInfos = service.getAllManagers();
        model.addAttribute("manager", managerInfos);
        return "adminDashboard";
    }

    @GetMapping("/UpdateManager/{email}/edit")
    public String showUpdateFrom(@PathVariable("email") String email, Model model)
    {
        if(session.getAttribute("admin") == null)
            return "redirect:/login";
       ManagerInfo managerInfo = service.getManagerByEmail(email);
       model.addAttribute("manager", managerInfo);
        return "UpdateManager";
    }

    @PostMapping("/UpdateManager/{email}/update")
    public String updateManager(@PathVariable("email") String email, @ModelAttribute ManagerInfo updated)
    {
        if(session.getAttribute("admin") == null)
            return "redirect:/login";
        ManagerInfo existingManager = service.getManagerByEmail(email);

        if(!email.equals(updated.getEmail()) && repo.findByEmail(updated.getEmail()).isPresent()) {
            return "redirect:/UpdateManager/" + email + "/edit";
        }

        existingManager.setFullname(updated.getFullname());
        existingManager.setEmail(updated.getEmail());
        existingManager.setPassword(updated.getPassword());
        existingManager.setPhonenumber(updated.getPhonenumber());
        existingManager.setAddress(updated.getAddress());

        service.saveManager(existingManager);

        return"redirect:/adminDashboard";
    }

    @GetMapping("/AddManager")
    public String showAddForm(Model model)
    {
        if(session.getAttribute("admin") == null)
            return "redirect:/login";
        model.addAttribute("manager",new ManagerInfo());
        return "AddManager";
    }

    @PostMapping("/AddManager")
    public String AddNewManager(@ModelAttribute ManagerInfo managerInfo, BindingResult result, Model model)
    {

        if(session.getAttribute("admin") == null)
            return "redirect:/login";
        //model.addAttribute("waiter",waiterInfo);
        if(result.hasErrors()){return "redirect:/AddManager";}

        if(repo.findByEmail(managerInfo.getEmail()).isPresent())
        {
            result.rejectValue("Email",null,"Already Exists");
            return "redirect:/AddManager";
        }

        if(repo.findByFullname(managerInfo.getFullname()).isPresent())
        {
            result.rejectValue("Fullname",null,"Already Exists");
            return "redirect:/AddManager";
        }

        managerInfo.setRole("Manager");

        service.saveManager(managerInfo);

        return "redirect:/adminDashboard";
    }

    @GetMapping ("/UpdateManager/{email}/delete")
    public String deleteManager(@PathVariable String email,Model model) {
        if(session.getAttribute("admin") == null)
            return "redirect:/login";
        // Call the service method to delete the waiter by email
        ManagerInfo managerInfo  = service.getManagerByEmail(email);
        service.deleteManager(managerInfo);
        model.addAttribute("message","deletion successfully performed");
        // Redirect to a suitable URL after deletion, for example, the waiter list page
        return "redirect:/adminDashboard";
    }
}
