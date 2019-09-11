package com.springbootapp.cmsshoppingcart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootapp.cmsshoppingcart.models.CategoryRepository;
import com.springbootapp.cmsshoppingcart.models.ProductRepository;
import com.springbootapp.cmsshoppingcart.models.data.Category;
import com.springbootapp.cmsshoppingcart.models.data.Product;

@Controller
@RequestMapping("/category")
public class CategoriesController {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ProductRepository productRepo;
	

	
	@GetMapping("/{slug}")
	public String category(@PathVariable String slug, Model model, @RequestParam(value = "page", required = false)Integer p) {
			
		int perPage = 6;
		int page = (p != null) ? p : 0;
		Pageable pagealbe = PageRequest.of(page, perPage);
		long count = 0;
		
		if (slug.equals("all")) {
			Page<Product> products = productRepo.findAll(pagealbe);
			count = productRepo.count();
			model.addAttribute("products", products);
		}
		else {
			Category category = categoryRepo.findBySlug(slug);
			
			if (category == null)
				return "redirect:/";
			
			int categoryId = category.getId();
			String categoryName = category.getName();
			List<Product> products = productRepo.findAllByCategoryId(String.valueOf(categoryId), pagealbe);
			
			count = productRepo.countByCategoryId(Integer.toString(categoryId));
			
			model.addAttribute("products", products);
			model.addAttribute("categoryName", categoryName);

		}

		double pageCount = Math.ceil((double)count / (double)perPage);
		
		model.addAttribute("pageCount", (int)pageCount);
		model.addAttribute("perPage", perPage);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		
		return "products";
	}
}
