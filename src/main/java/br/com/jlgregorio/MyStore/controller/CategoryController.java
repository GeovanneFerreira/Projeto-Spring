package br.com.jlgregorio.MyStore.controller;
import br.com.jlgregorio.MyStore.exceptions.ResourceNotFoundException;
import br.com.jlgregorio.MyStore.model.CategoryModel;
import br.com.jlgregorio.MyStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

//    @GetMapping
//    public String index(){
//        return "category/index";
//    }

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ModelAndView index(){
        List<CategoryModel> categories = categoryRepository.findAll();
        ModelAndView mv = new ModelAndView("category/index");
        mv.addObject("categories", categories);
        return mv;
    }

    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView mv = new ModelAndView("category/create");
        CategoryModel categoryModel = new CategoryModel();
        mv.addObject("category", categoryModel);
        return mv;
    }

    @PostMapping
    public String save(@ModelAttribute("category") CategoryModel categoryModel,
                       RedirectAttributes redirectAttributes){
        categoryRepository.save(categoryModel);
        redirectAttributes.addFlashAttribute("message",
                "Categoria salva com sucesso!");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit( @PathVariable long id ){
        var found = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Categoria não encontrada!")
        );
        ModelAndView mv = new ModelAndView("category/edit");
        mv.addObject("category", found);
        return mv;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id,
                         @ModelAttribute("category") CategoryModel categoryModel,
                         RedirectAttributes redirectAttributes){
        var found = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Categoria não encontrada!")
        );
        found.setName(categoryModel.getName());
        categoryRepository.save(found);
        redirectAttributes.addFlashAttribute(
                "message", "Categoria atualizada com sucesso!");
        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id,
                         RedirectAttributes redirectAttributes){
        var found = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Categoria não encontrada!")
        );
        categoryRepository.delete(found);
        redirectAttributes.addFlashAttribute("message",
                "Categoria excluída com sucesso!");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<CategoryModel> show(@PathVariable long id){
        var found = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Categoria não encontrada!")
        );
        return new ResponseEntity<>(found, HttpStatus.OK);
    }


}
