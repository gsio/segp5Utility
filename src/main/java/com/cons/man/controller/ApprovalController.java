package com.cons.man.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import com.cons.man.services.ApprovalService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Controller(value="ApprovalController")
public class ApprovalController {
	
	@Resource(name="ApprovalService")
	private ApprovalService approvalService;
	
}

