package com.ossbar.platform.core.controller;

import javax.servlet.http.HttpSession;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

	@RequestMapping("user")
	public String getUser(HttpSession session) {
		return "user";
	}
	
	@RequestMapping("func1")
	@Secured("ROLE_USER11")
	public String func1(HttpSession session) {
		return "func1";
	}
	
	@RequestMapping("func2")
	@PreAuthorize("hasAuthority('MY_ROLE1&MY_MENU1')")
	public String func2(HttpSession session) {
		return "func2";
	}
}
