package com.cotizador.controller;

import com.cotizador.entity.Brands;
import com.cotizador.entity.Models;
import com.cotizador.service.BrandService;
import com.cotizador.service.ModelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class exampleController {
    BrandService brandService;
    ModelService modelService;

    public exampleController(BrandService brandService, ModelService modelService) {
        this.brandService = brandService;
        this.modelService = modelService;
    }

    @GetMapping("/procesarFormulario")
    public String procesarFormulario(@RequestParam("categoriaSeleccionada") int idCategoria, Model theModel) {

        //Get all brands from DB and fill to List
        List<Brands> listOfBrands=brandService.findAll();

        theModel.addAttribute("theBrands",listOfBrands);

        // Obtén los datos desde la base de datos según el ID de la categoría seleccionada
        System.out.println("id categoria "+idCategoria);
        List<Models> datos = modelService.findById(idCategoria);

        theModel.addAttribute("datos", datos);
        return "individuals/example"; // Retorna el nombre de la vista del formulario
    }

}
