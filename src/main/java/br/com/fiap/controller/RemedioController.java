package br.com.fiap.controller;

import br.com.fiap.model.dto.RemedioDTO;
import br.com.fiap.model.entity.Remedio;
import br.com.fiap.model.repository.RemedioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/drogaria")
public class RemedioController {
    @Autowired
    private RemedioRepository remedioRepository;

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("formulario");
        return mv;
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid RemedioDTO remedioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/drogaria");
        }
        Remedio remedio = new Remedio(remedioDTO);
        remedioRepository.save(remedio);
        ModelAndView mv = new ModelAndView("redirect:/drogaria");
        return mv;
    }

    // pro front
//    @PostMapping
//    public ResponseEntity<?> cadastrar(@RequestBody @Valid RemedioDTO remedioDTO) {
//        try {
//            Remedio remedio = new Remedio(remedioDTO);
//            remedioRepository.save(remedio);
//            return ResponseEntity.status(HttpStatus.CREATED).body(remedio); // 201 - Created
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o remédio"); // 400 - Bad Request
//        }
//    }

    // pro front
//    @GetMapping
//    public ResponseEntity<List<Remedio>> consultar() {
//        List<Remedio> remedios = remedioRepository.findAll();
//        return ResponseEntity.ok(remedios);
//    }

    @GetMapping
    public ModelAndView consultar() {
        List<Remedio> remedios = remedioRepository.findAll();
        ModelAndView mv = new ModelAndView("remedios");
        mv.addObject("remedios", remedios);
        return mv;
    }

    // pro front
//    @GetMapping("/{codigo}")
//    public ResponseEntity<?> ConsultarPorCoddigo(@PathVariable Long codigo) {
//        Remedio remedio = remedioRepository.findByCodigo(codigo);
//        if (remedio != null) {
//            return ResponseEntity.status(HttpStatus.OK).body(remedio);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Remédio não encontrado");
//        }
//    }

    @GetMapping("/editar/{codigo}")
    public ModelAndView consultarPorCodigo(@PathVariable Long codigo) {
        Remedio remedio = remedioRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("formulario");
        mv.addObject("remedio", remedio);
        mv.addObject("acao", "/drogaria/atualizar/"+codigo);
        return mv;
    }


// pro front
//    @DeleteMapping("/{codigo}")
//    public ResponseEntity<String> excluir(@PathVariable Long codigo) {
//        if (remedioRepository.existsById(codigo)) {
//            remedioRepository.deleteById(codigo);
//            return ResponseEntity.ok("Remédio deletado com sucesso");
//            // return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Remédio deletado com sucesso");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Remédio não encontrado");
//        }
//    }

    @GetMapping("/excluir/{codigo}")
    public ModelAndView excluir(@PathVariable Long codigo) {
        remedioRepository.deleteById(codigo);
        ModelAndView mv = new ModelAndView("redirect:/drogaria");
        return mv;
    }

    // pro front
//    @PutMapping("/{codigo}")
//    public ResponseEntity<String> atualizar(@PathVariable Long codigo,@RequestBody @Valid RemedioDTO remedioDTO) {
//        try {
//            Remedio remedio = new Remedio(remedioDTO);
//            remedio.setCodigo(codigo);
//            remedioRepository.save(remedio);
//            return ResponseEntity.ok("Remédio atualizado com sucesso.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o remédio");
//        }
//    }

    @PostMapping("/atualizar/{codigo}")
    public ModelAndView atualizar(@PathVariable Long codigo, @Valid RemedioDTO remedioDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/drogaria");
        }
        Remedio remedio = new Remedio(remedioDTO);
        remedio.setCodigo(codigo);
        remedioRepository.save(remedio);
        ModelAndView mv = new ModelAndView("redirect:/drogaria");
        return mv;
    }
}
