package com.cotizador.controller;

import com.cotizador.entity.Brands;
import com.cotizador.entity.Individual;
import com.cotizador.entity.Models;
import com.cotizador.service.BrandService;
import com.cotizador.service.ModelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/creditos/auto")
public class exampleController {
    BrandService brandService;
    ModelService modelService;

    public exampleController(BrandService brandService, ModelService modelService) {
        this.brandService = brandService;
        this.modelService = modelService;
    }
    @GetMapping("/simulador-credito-automotriz2")
    public String addPaymentCalculator(Model theModel){
        List<Integer> yearsVehicle;
        Individual individual=new Individual();
        Brands brands =new Brands();

        //Get all brands from DB and fill to List
        List<Brands> listOfBrands=brandService.findAll();

        theModel.addAttribute("theBrands",listOfBrands);

        //Get all models from DB and fill to list
       // List<Models> models=modelService.findAll();


        // Inicialmente, cargar los productos de la primera categoría (si existe)
        if (!listOfBrands.isEmpty()) {
            Brands firstBrand = listOfBrands.get(0); // seleccionar la primera categoría por defecto
            List<Models> listOfModels = modelService.findById(firstBrand.getBrandId());
            theModel.addAttribute("theModels", listOfModels);
        }

        //Add models to view
        theModel.addAttribute("individual", individual);

        return "test/dropdown";
    }
    @GetMapping("/modelos/{brandId}")
    @ResponseBody
    public List<Models> obtenerProductosPorCategoria(@PathVariable int brandId) {
        System.out.println("BrandId "+brandId);
        Brands brands = brandService.findById(brandId);

        if (brands==null || brands.getBrandId()==0){
            System.out.println("Marca de auto no encontrada");
        }
        List<Models> theModels=modelService.findById(brandId);
        System.out.println("id "+theModels.get(0).getModelId()+" name "+theModels.get(0).getName());
        return theModels;

    }

}
