package com.example.lab1;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;


@Controller
@Log(topic = "homeController")
public class HomeController {

    @Autowired
    private RegistrationManager registrationManager;


    @GetMapping("/")
    public String home(@ModelAttribute("logincommand") @Valid LoginCommand lc, BindingResult br) {
        return "homepage";
    }

    @GetMapping("/register")
    public String registrationPage(Model model, @ModelAttribute("command") RegistrationCommand registrationCommand) {

        log.info(registrationCommand.toString());

        return "registration";
    }


    /*
    @ModelAttribute(“command”). Tale annotazione provvede ad inserire nel ViewModel che verrà passato al motore di gestione delle viste
    un oggetto di tipo RegistrationCommand cui le viste potranno accedere usando la chiave
    “command”.

    @Valid
    La presenza dell’annotazione fa sì che il framework Spring provveda a verificare,
    prima di invocare il metodo, se i dati memorizzati nei singoli attributi dell’oggetto
    RegistrationCommand rispettano i vincoli sintattici espressi tramite le annotazioni sui singoli campi.
    In assenza del successivo parametro, eventuali discrepanze lancerebbero un’eccezione. La presenza
    del parametro di tipo BindingResult fa sì che tali errori siano descritti al suo interno.

    */
    @PostMapping("/register")
    public String register(Model model,
                           @ModelAttribute("command") @Valid RegistrationCommand rc,
                           BindingResult br) {

        log.info("Accessing via model attribute command " + model.getAttribute("command").toString());


        // Utilizzo un secondo oggetto RD perchè è bene separare i dati DTO (Data Transfer Object) in questo caso RegistrationCommand
        // dai dati che poi verranno salvati all'interno della Base Dati (Entities)
        // In questo caso la base dati è simulata dalla mappa e l'entità dall'oggetto RegistrationDetails

        //Check passwords
        if (!rc.getPassword().equals(rc.getRepeatpassword())) {
            br.addError(new FieldError("command", "password", "Passwords don't match."));
        }

        //Check privacy tick
        if (rc.getPrivacy().booleanValue() == false) {
            br.addError(new FieldError("command", "privacy", "You need to agree to the privacy policy. "));
        }


        // Checks are passed
        RegistrationDetails rd =
                RegistrationDetails.builder()
                        .name(rc.getName())
                        .surname(rc.getSurname())
                        .email(rc.getEmail())
                        .password(rc.getPassword())
                        .privacy(rc.getPrivacy())
                        .registrationDate(new Date())
                        .build();

        if (registrationManager.containsKey(rc.getEmail())) {
            br.addError(new FieldError("command", "email", "Email already exists."));
        }

        // Check if fields are valid.
        if (br.hasErrors()) {
            return "/registration"; // "redirect:/registration ---> si noti che con il redirect perdi info riguardo agli errori
        }

        if(registrationManager.putIfAbsent(rc.getEmail(), rd)!=null) return "/registration";

        // Se tutto è ok vai alla pagina di login
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("logincommand") LoginCommand lc, HttpSession httpSession) {

        /*Tale metodo deve rispondere alla URL “/login”
        richiesta con il metodo POST. Al suo interno, validare che l’oggetto LoginCommand abbia un campo
        utente non nullo e usare tale campo per trovare l’oggetto RegistrationDetails corrispondente
        tramite le funzioni offerte dall’oggetto RegistrationManager. Si verifichi la correttezza della
        password indicata e, se tutti i controlli sono positivi, si aggiunga all’oggetto HttpSession l’attributo
        “username” indicando come valore quello contenuto nel LoginCommand e si ridiriga verso la URL
        “/private”. In caso contrario si ridiriga verso la URL “/”.
        */
      //  log.info(lc.toString());

        // campo email non nullo corrisponde al campo utente
        if (lc.getEmail() != null) {

            if (registrationManager.containsKey(lc.getEmail())) {

                if (registrationManager.get(lc.getEmail()).getPassword().equals(lc.getPassword())) {
                    httpSession.setAttribute("username", lc.getEmail());
                    // Tutto ok
                    return "redirect:/private";
                }

            }
        }
        //Error case
        return "redirect:/";
    }


    @RequestMapping(value = "/private")
    public String privatePage(HttpSession httpSession) {

        if (httpSession.getAttribute("username") == null)
            return "redirect:/";
        else
            return "private";
    }


    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {

        if (httpSession.getAttribute("username") != null) {

            //log.info(httpSession.getAttribute("username").toString());
            httpSession.removeAttribute("username");
            //  log.info(httpSession.getAttribute("username").toString());  // it produce null pointer exception -> it was deleted

        }
        return "redirect:/";
    }

}
