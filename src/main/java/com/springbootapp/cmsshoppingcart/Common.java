package com.springbootapp.cmsshoppingcart;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springbootapp.cmsshoppingcart.models.CategoryRepository;
import com.springbootapp.cmsshoppingcart.models.PageRepository;
import com.springbootapp.cmsshoppingcart.models.data.Cart;
import com.springbootapp.cmsshoppingcart.models.data.Category;
import com.springbootapp.cmsshoppingcart.models.data.Page;

@ControllerAdvice
@SuppressWarnings("unchecked")
public class Common {

	@Autowired
	private PageRepository pageRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@ModelAttribute
	public void sharedData(Model model, HttpSession session, Principal principal) {

		if (principal != null) 
			model.addAttribute("principal", principal.getName());
		

		List<Page> pages = pageRepo.findAllByOrderBySortingAsc();

		List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();

		boolean cartActive = false;
		if (session.getAttribute("cart") != null) {
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
			int totalCartSize = 0;
			double totalPrice = 0;
			for (Cart value : cart.values()) {
				totalCartSize += value.getQuantity();
				totalPrice += value.getQuantity() * Double.parseDouble(value.getPrice());
			}
			model.addAttribute("csize", totalCartSize);
			model.addAttribute("ctotal", totalPrice);
			cartActive = true;
		}

		model.addAttribute("ccategories", categories);
		model.addAttribute("cpages", pages);
		model.addAttribute("cartActive", cartActive);
	}

}
